package com.eeft.ren.sqlrunner.config;

import java.io.File;

public class PathUtil {

    public static String getJarPath() {
        try {
            return new File(PathUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get JAR path", e);
        }
    }

    public static String getPath(String requestPath) {
        String basePath;
        
        if (requestPath != null && !requestPath.isEmpty()) {
            basePath = requestPath;
        } else {
            basePath = getJarPath() + File.separator + "sql";
        }

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return basePath.replace("/", "\\");
        } else {
            return basePath.replace("\\", "/");
        }
    }

    public static void main(String[] args) {
        // Example usage
        String requestPath = args.length > 0 ? args[0] : null;

        String dbPath = getPath(requestPath);
        System.out.println("Path: " + dbPath);
    }
}



