/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by KarlJones on 24/07/16.
 *
 * This class is involved with getting information about Google Play Services on the device. Also
 * gets information if it is missing with a link to the google play store.
 */

public class GoogleServicesUtil {

    Context context;

    public GoogleServicesUtil(Context context){
        this.context = context;
    }

    /**
     * Find the google play services version code from the device.
     *
     * @return the google play services version code.
     */
    public int findGooglePlayServicesVersion(){
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0 )
                    .versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Find the google play services version name from the device.
     *
     * @return the google play services version name.
     */
    public String findGooglePlayServicesVersionName(){
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Not Found";
        }
    }
}
