package by.aliesha.controller;

import javax.servlet.http.HttpServletRequest;

import by.aliesha.frontcontroller.annotation.Action;
import by.aliesha.frontcontroller.annotation.Controller;


@Controller(urlPattern = "/user")
public class UserCtrl {

    @Action(urlPattern = "/index")
    public String index(HttpServletRequest request) {
        return "index";
    }
    
}
