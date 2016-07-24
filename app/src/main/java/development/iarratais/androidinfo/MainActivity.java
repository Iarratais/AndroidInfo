/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.androidinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import development.iarratais.utils.DeviceInfoUtil;
import development.iarratais.utils.GoogleServicesUtil;

public class MainActivity extends AppCompatActivity {

    GoogleServicesUtil googleServicesUtil;

    private static final String GOOGLE_SERVICES_PLAY_LINK = "https://play.google" +
            ".com/store/apps/details?id=com.google.android.gms&hl=en";

    DeviceInfoUtil deviceInfoUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleServicesUtil = new GoogleServicesUtil(getApplicationContext());
        deviceInfoUtil = new DeviceInfoUtil(getApplicationContext());

        injectGooglePlayInformation();
        injectDeviceInformation();
    }

    /**
     * This adds the information into the views regarding information about the google play
     * services on the device.
     */
    private void injectGooglePlayInformation(){
        // Get the textviews.
        TextView googlePlayStatus = (TextView) findViewById(R.id
                .textview_google_play_services_status);
        TextView googlePlayVersion = (TextView) findViewById(R.id
                .textview_google_play_services_version_code);
        TextView googlePlayVersionName = (TextView) findViewById(R.id
                .textview_google_play_services_version_name);

        // Get the buttons.
        Button getFromGooglePlayStore = (Button) findViewById(R.id
                .button_get_google_play_services_google_play_store);

        // Get the version code.
        int versionCode = googleServicesUtil.findGooglePlayServicesVersion();

        // Get the version name;
        String versionName = googleServicesUtil.findGooglePlayServicesVersionName();

        // If the versionCode is 0, then there is no play services installed on the device. This
        // then hides the versionCode textview and shows the button to get it from the google
        // play store.
        if(versionCode != 0 && !versionName.equals("Not Found")){

            Log.d(getClass().getSimpleName(), "Google Play services are installed on device");

            if (googlePlayStatus != null) {
                googlePlayStatus.setText(R.string.google_play_services_installed);
            }

            if (googlePlayVersion != null) {
                String versionText = getString(R.string.google_play_services_version_code,
                        versionCode);
                googlePlayVersion.setText(versionText);
            }

            if (getFromGooglePlayStore != null) {
                getFromGooglePlayStore.setVisibility(View.GONE);
            }

            if(googlePlayVersionName != null){
                String versionNameText = getString(R.string.google_play_services_version_name,
                        versionName);
                googlePlayVersionName.setText(versionNameText);
            }
        } else {

            Log.d(getClass().getSimpleName(), "Google Play services are not installed on the " +
                    "device");
            if(googlePlayStatus != null){
                googlePlayStatus.setText(R.string.google_play_services_not_installed);
            }

            if (googlePlayVersion != null) {
                googlePlayVersion.setVisibility(View.GONE);
            }

            if (googlePlayVersionName != null){
                googlePlayVersionName.setVisibility(View.GONE);
            }

            if (getFromGooglePlayStore != null) {
                getFromGooglePlayStore.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * This sends the user to the play store to download the play services.
     *
     * @param v the view this method was called from.
     */
    public void sendUserToPlayStore(View v){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_SERVICES_PLAY_LINK)));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.error_check_your_network_settings, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This adds the information into the views regarding information about the device.
     */
    public void injectDeviceInformation(){
        TextView manufacturerText = (TextView) findViewById(R.id
                .textview_device_information_manufacturer);
        TextView modelText = (TextView) findViewById(R.id.textview_device_information_model);
        TextView androidVersionText = (TextView) findViewById(R.id
                .textview_device_information_android_version);

        if(manufacturerText != null){
            String manufacturer = getString(R.string.device_information_manufacturer,
                    deviceInfoUtil.getManufacturer());
            manufacturerText.setText(manufacturer);
        }

        if(modelText != null){
            String model = getString(R.string.device_information_device_model, deviceInfoUtil
                    .getModel());
            modelText.setText(model);
        }

        if(androidVersionText != null){
            String androidVersion = getString(R.string.device_information_android_version,
                    deviceInfoUtil.getAndroidVersion());
            androidVersionText.setText(androidVersion);
        }
    }
}
