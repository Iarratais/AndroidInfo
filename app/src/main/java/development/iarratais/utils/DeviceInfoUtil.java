/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

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

    public String getModel(){
        return capitalize(Build.MODEL);
    }

    public String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

}
