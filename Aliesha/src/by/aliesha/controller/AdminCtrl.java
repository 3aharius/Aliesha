package by.aliesha.controller;

import javax.servlet.http.HttpServletRequest;

import by.aliesha.frontcontroller.annotation.Action;
import by.aliesha.frontcontroller.annotation.Controller;

@Controller(urlPattern = "/admin")
public class AdminCtrl {
    
    @Action(urlPattern = "/index")
    public String index(HttpServletRequest request) {
        return "admin/index";
    }
    
    @Action(urlPattern = "/users")
    public String users(HttpServletRequest request) {
        return "admin/users";
    }

}
