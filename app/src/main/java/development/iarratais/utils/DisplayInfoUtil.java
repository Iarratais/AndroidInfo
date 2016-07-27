/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import development.iarratais.androidinfo.R;

/**
 * Created by KarlJones on 27/07/16.
 *
 * Get information regarding the display of the device.
 */
public class DisplayInfoUtil {

    Context context;

    public DisplayInfoUtil(Context context){
        this.context = context;
    }

    /**
     * Get the DPI of the display.
     *
     * @return the density of the ddevice display.
     */
    public int getDisplayDPI(){
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * Get the current orientation of the display of the device.
     *
     * @return a string orientation.
     */
    public String getDisplayOrientation(){
        int orientation = context.getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            return context.getString(R.string.display_information_orientation_portrait);
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            return context.getString(R.string.display_information_orientation_landscape);
        else
            return context.getString(R.string.not_supported);
    }

    /**
     * Get the current width of the screen in pixels.
     *
     * @return a int with the width in pixels.
     */
    public int getDisplayWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Get the current height of the screen in pixels.
     *
     * @return a int with the height in pixels.
     */
    public int getDisplayHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
