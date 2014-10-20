package by.aliesha.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.aliesha.utils.AppConstants;

public class URLParser {
    
    private static final String URL_DELIMETER = "/";
    private static final String INDEX_CTRL = "index";
    private static final String INDEX_ACTION = "index";
    private static final Logger logger = LoggerFactory.getLogger(AppConstants.LOGGER_NAME);

    public static ParsedUrl parseUrl(String url) {
        logger.debug("ENTER");
        logger.debug("URL: " + url);
        url = url.substring(1);
        String ctrlUrl = INDEX_CTRL;
        String actionUrl = INDEX_ACTION;
        if(!URL_DELIMETER.equals(url)) {
            if(url.endsWith(URL_DELIMETER)) {
                url = url.substring(0, url.length() - 1);
            }
            String[] urlArr = url.split(URL_DELIMETER);
            logger.debug("urlArr size: " + urlArr.length);
            ctrlUrl = urlArr[0];
            if(urlArr.length > 1) {
                actionUrl = urlArr[1];
            }
        }
        
        ParsedUrl parsedUrl = new ParsedUrl(URL_DELIMETER+ctrlUrl, URL_DELIMETER+actionUrl);
        logger.debug("EXIT");
        return parsedUrl;
    }

}
