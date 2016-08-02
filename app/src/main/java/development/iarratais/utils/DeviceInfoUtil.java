/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;

import development.iarratais.androidinfo.R;

/**
 * Created by KarlJones on 24/07/16.
 *
 * This class gets information about the device. Gets the device model, android version,
 */
public class DeviceInfoUtil {

    Context context;

    public DeviceInfoUtil(Context context){
        this.context = context;
    }

    /**
     * Get the devices manufacturer.
     *
     * @return the manufacturer of the device.
     */
    public String getManufacturer(){
        return capitalize(Build.MANUFACTURER);
    }

    /**
     * Get the model name for the device.
     *
     * @return the model name of the device.
     */
    public String getModel(){
        return capitalize(Build.MODEL);
    }

    /**
     * Get the android version that the device is running.
     *
     * @return string with the android version that the device is running.
     */
    public String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * Get the android name of the version on the device.
     *
     * @return string with android version name.
     */
    public String getAndroidVersionName(){
        int SDKVersion = getSDKVersion();

        switch (SDKVersion){
            case Build.VERSION_CODES.CUPCAKE:
                return context.getString(R.string.android_version_cupcake);
            case Build.VERSION_CODES.DONUT:
                return context.getString(R.string.android_version_donut);
            case Build.VERSION_CODES.ECLAIR:
                return context.getString(R.string.android_version_eclair);
            case Build.VERSION_CODES.FROYO:
                return context.getString(R.string.android_version_froyo);
            case Build.VERSION_CODES.GINGERBREAD:
                return context.getString(R.string.android_version_gingerbread);
            case Build.VERSION_CODES.HONEYCOMB:
                return context.getString(R.string.android_version_honeycomb);
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                return context.getString(R.string.android_version_ice_cream_sandwich);
            case Build.VERSION_CODES.JELLY_BEAN:
                return context.getString(R.string.android_version_jelly_bean);
            case Build.VERSION_CODES.KITKAT:
                return context.getString(R.string.android_version_kitkat);
            case Build.VERSION_CODES.LOLLIPOP:
                return context.getString(R.string.android_version_lollipop);
            case Build.VERSION_CODES.M:
                return context.getString(R.string.android_version_marshmallow);
            default:
                return context.getString(R.string.android_version_nougat);
        }
    }

    /**
     * Get the SDK version that the device is running.
     *
     * @return int number of SDK version.
     */
    public int getSDKVersion(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * Get the IMEI of the device.
     *
     * @return string with the IMEI of the device.
     */
    public String getIMEI(){
        TelephonyManager mngr = (TelephonyManager)context.getSystemService(Context
                .TELEPHONY_SERVICE);

        return mngr.getDeviceId();
    }

    /**
     * Checks if the device is rooted.
     *
     * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
     */
    public boolean isRooted() {

        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }

        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }

    /**
     * Capitalise the string that is passed into the method.
     *
     * @param originalString string to be capitalised.
     *
     * @return capitalised string.
     */
    private static String capitalize(String originalString) {
        if (TextUtils.isEmpty(originalString)) {
            return originalString;
        }
        char[] arr = originalString.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder captilisedString = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                captilisedString.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            captilisedString.append(c);
        }

        return captilisedString.toString();
    }

}
