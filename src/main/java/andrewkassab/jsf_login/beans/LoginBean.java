package andrewkassab.jsf_login.beans;

import andrewkassab.jsf_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
@Component
public class LoginBean {

    private String username;
    private String password;
    private boolean loggedIn;

    @Autowired
    private UserService userService;

    public String login() {
        if (userService.validateUser(username, password)) {
            loggedIn = true;
            return "welcome.xhtml?faces-redirect=true";
        } else {
            loggedIn = false;
            return "login.xhtml?error=true";
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
