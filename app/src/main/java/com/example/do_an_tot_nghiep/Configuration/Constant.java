package com.example.do_an_tot_nghiep.Configuration;

/**
 * @author Phong-Kaster
 * @since 18-11-2022
 * this class contains all constant variable in this application
 */
public class Constant {

    /**
     * @since 17-11-2022
     */
    public static String UPLOAD_URI()
    {
        return "http://192.168.1.221:8080/PTIT-Do-An-Tot-Nghiep/api/assets/uploads/";
    }


    /**
     * @since 18-11-2022
     * Use this APP_PATH if testing device is a real hardware device
     */
    public static String APP_PATH()
    {
        return "http://192.168.1.221:8080/PTIT-Do-An-Tot-Nghiep/";
    }

    /**
     * @since 18-11-2022
     * Use this APP_PATH if testing device is the Android emulator
     */
    public static String APP_PATH_EMULATOR()
    {
        return "http://10.0.2.2:8080/PTIT-Do-An-Tot-Nghiep/";
    }

    /**
     * @since 18-11-2022
     * application name
     */
    public static String APP_NAME()
    {
        return "Umbrella Health";
    }

    public static String accessToken;
    public static void setAccessToken(String accessToken)
    {
        Constant.accessToken = accessToken;
    }
    public static String getAccessToken()
    {
        return Constant.accessToken;
    }
}
