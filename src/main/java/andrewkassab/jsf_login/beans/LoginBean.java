package andrewkassab.jsf_login.beans;

import andrewkassab.jsf_login.model.User;
import andrewkassab.jsf_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_OK;

@Named("loginBean")
@SessionScoped
public class LoginBean {

    private String username;
    private String password;

    private String confirmPassword;

    @Autowired
    private UserService userService;

    @Value("${login.url}")
    private String loginUrl;

    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);  // Keeps messages for the next request

        if (authenticate(username, password)) {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("username", username);
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml");
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Credentials.", null));
        }
    }

    private boolean authenticate(String username, String password) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == HTTP_OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void register() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);  // Keeps messages for the next request

        if (!password.equals(confirmPassword)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null));
            return;
        }

        Optional<User> existingUser = userService.findByUsername(username);

        if (existingUser.isPresent()) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already taken.", null));
            return;
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            userService.saveUser(newUser);

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account created successfully", null));

            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }

    public void logout() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logged out successfully", null));
        facesContext.getExternalContext().redirect("login.xhtml");
    }

    public void checkLogin() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            context.getExternalContext().redirect("login.xhtml");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
