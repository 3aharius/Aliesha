package by.aliesha.frontcontroller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public class ControllerActionInvoker {
    
    private static final String VEIW_URL_TEMPLATE = "%s/%s.jsp";
    private final String VIEW_LOCATION;

    public ControllerActionInvoker(String viewLocation) {
        VIEW_LOCATION = viewLocation;
    }
    
    public String invoke(ControllerEntity ctrlEntry, String actionUrl, HttpServletRequest request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object ctrl = ctrlEntry.getController();
        Method method = ctrlEntry.getMethod(request.getMethod().toUpperCase(), actionUrl);
        String view = (String) method.invoke(ctrl, request);
        return String.format(VEIW_URL_TEMPLATE, VIEW_LOCATION, view);
    }
    
}
