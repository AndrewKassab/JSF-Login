package andrewkassab.jsf_login.beans;

import andrewkassab.jsf_login.model.User;
import andrewkassab.jsf_login.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.Optional;

@Named("loginBean")
@SessionScoped
public class LoginBean {

    private String username;
    private String password;

    private String confirmPassword;

    @Autowired
    private UserServiceImpl userService;

    public void login() throws IOException {
        if (userService.login(username, password).isPresent()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
        }
    }

    public void register() throws IOException {
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml?error=password_mismatch");
            return;
        }

        Optional<User> existingUser = userService.findByUsername(username);
        if (existingUser.isPresent()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml?error=user_exists");
            return;
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            userService.saveUser(newUser);
            FacesContext.getCurrentInstance().getExternalContext().redirect("registration_success.xhtml");
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

    public UserServiceImpl getUserService() {
        return userService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

}
