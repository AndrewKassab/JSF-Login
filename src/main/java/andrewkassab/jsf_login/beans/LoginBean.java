package andrewkassab.jsf_login.beans;

import andrewkassab.jsf_login.model.User;
import andrewkassab.jsf_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named("loginBean")
@SessionScoped
public class LoginBean {

    private String username;
    private String password;

    @Autowired
    private UserService userService;

    public void login() throws IOException {
        if (userService.login(username, password).isPresent()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
