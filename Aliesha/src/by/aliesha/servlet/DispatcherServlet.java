package by.aliesha.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.aliesha.exception.PageNotFoundException;
import by.aliesha.exception.UnsupportedHttpMethodException;
import by.aliesha.frontcontroller.Frontcontroller;
import by.aliesha.utils.AppConstants;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/dispatcher/*")
public class DispatcherServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AppConstants.LOGGER_NAME);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Frontcontroller frontcontroller = Frontcontroller.getInstance();
	    try {
            String view = null;
            try {
                view = frontcontroller.invokeAction(request);
                request.getRequestDispatcher(view).forward(request, response);
            } catch (PageNotFoundException | UnsupportedHttpMethodException e) {
                logger.error(e.getMessage(), e);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
