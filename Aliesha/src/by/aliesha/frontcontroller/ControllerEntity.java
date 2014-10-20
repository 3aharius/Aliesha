package by.aliesha.frontcontroller;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerEntity {

    private Object controller;
    private Map<String, Map<String, Method>> methodsBindings;
    
    public ControllerEntity(Object controller) {
        this.controller = controller;
        methodsBindings = new ConcurrentHashMap<String, Map<String, Method>>();
    }
    
    public void addMethodBinding(String url, String httpMethod, Method method){
        if(methodsBindings.containsKey(httpMethod)) {
            methodsBindings.get(httpMethod).put(url, method);
        } else {
            Map<String, Method> methodbind = new ConcurrentHashMap<String, Method>();
            methodbind.put(url, method);
            methodsBindings.put(httpMethod, methodbind);
        }
    }
    
    public Method getMethod(String httpMethod, String url) {
        return methodsBindings.get(httpMethod).get(url);
    }

    public Object getController() {
        return controller;
    }
    
}
