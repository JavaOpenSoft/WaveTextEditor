package wtxt.core;

@SuppressWarnings("ALL")
public class SoftwareInfo {
    private static final String buildMode = "Alpha";
    private static final String About = "WaveUSB Text Editor";
    private static final String versionNumber = "1.0";

    public static String getVersionNumber() {
        return versionNumber;
    }

    public static String getVersion() {
        return versionNumber + "-" + buildMode;
    }

    public static String getAbout() {
        return About;
    }

    public static String getBuildMode() {
        return buildMode;
    }

    public static String getPlatform() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows"))
            return "Windows";
        else if (osName.equals("Mac OS X"))
            return System.getProperty("os.name");
        else return "Linux";
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    public static String getAppDataDirectory() {
        switch (getPlatform()) {
            case "Mac OS X", "Linux" -> {
                return getHomeDirectory() + "/.WaveUSB";
            }
            case "Windows" -> {
                return getHomeDirectory() + "\\.WaveUSB";
            }
        }
        return null;
    }

    public static String getISODirectory() {
        switch (getPlatform()) {
            case "Mac OS X", "Linux" -> {
                return getAppDataDirectory() + "/ISO";
            }
            case "Windows" -> {
                return getAppDataDirectory() + "\\ISO";
            }
        }
        return null;
    }

}
