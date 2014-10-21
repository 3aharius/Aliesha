package by.aliesha.utils;

import java.io.InputStream;

public class FileUtils {
    
    public static InputStream getFileAsStreamFromClassPath(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
    
    public static String getClassLoaderPath() {
        return getClassLoaderPath("");
    }
    
    public static String getClassLoaderPath(String path) {
        return Thread.currentThread().getContextClassLoader().getResource(path).getPath();
    }

}
