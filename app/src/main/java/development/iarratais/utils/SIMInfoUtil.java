/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by KarlJones on 28/07/16.
 *
 * This class gets information from the SIM regarding the number, the network, the network
 * country and the operator name.
 */
public class SIMInfoUtil {

    Context context;

    TelephonyManager telephonyManager;

    public SIMInfoUtil(Context context){
        this.context = context;

         telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * Get the phone number from the SIM in the device.
     *
     * @return string containing the phone number.
     */
    public String getSIMNumber(){
        return telephonyManager.getLine1Number();
    }

    /**
     * Get the network operator that the device is connected to.
     *
     * @return string containing the network operator that the device is connected to.
     */
    public String getNetworkOperator(){
        return telephonyManager.getNetworkOperator();
    }

    /**
     * Get the network operator country that the device is connected to.
     *
     * @return string containing the network operator country.
     */
    public String getNetworkOperatorCountry(){
        return telephonyManager.getNetworkCountryIso();
    }
}
