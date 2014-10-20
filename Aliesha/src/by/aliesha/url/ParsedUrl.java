package by.aliesha.url;

public class ParsedUrl {

    private String controller;
    private String action;

    public ParsedUrl(String controller, String action) {
        this.controller = controller;
        this.action = action;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
