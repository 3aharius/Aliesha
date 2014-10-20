package by.aliesha.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.aliesha.frontcontroller.Frontcontroller;
import by.aliesha.url.ParsedUrl;
import by.aliesha.url.URLParser;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/a")
public class DispatcherServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //if(request)
	    Frontcontroller frontcontroller = Frontcontroller.getInstance();
	    try {
            String view = frontcontroller.invokeAction(request);
            request.getRequestDispatcher(view).forward(request, response);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(request.getRequestURL());
		out.println(request.getLocalAddr());
		out.println(request.getPathInfo());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
