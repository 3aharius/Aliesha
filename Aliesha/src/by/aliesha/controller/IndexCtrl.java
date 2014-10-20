package by.aliesha.controller;

import javax.servlet.http.HttpServletRequest;

import by.aliesha.frontcontroller.annotation.Action;
import by.aliesha.frontcontroller.annotation.Controller;

@Controller(urlPattern = "/index")
public class IndexCtrl {

    @Action(urlPattern = "/index")
    public String indexAction(HttpServletRequest request) {
        return "index";
    }
    
}
