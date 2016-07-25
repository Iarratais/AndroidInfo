/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import development.iarratais.androidinfo.R;
import development.iarratais.utils.GoogleServicesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayServicesFragment extends Fragment {

    View rootView;

    GoogleServicesUtil googleServicesUtil;

    private static final String GOOGLE_SERVICES_PLAY_LINK = "https://play.google" +
            ".com/store/apps/details?id=com.google.android.gms&hl=en";
    private static final String APK_MIRROR_SEARCH_LINK = "http://www.apkmirror" +
            ".com/?s=google+play+services&post_type=app_release&searchtype=apk";

    public PlayServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_play_services, container, false);

        googleServicesUtil = new GoogleServicesUtil(getActivity());

        injectGooglePlayInformation();

        return rootView;
    }

    /**
     * This adds the information into the views regarding information about the google play
     * services on the device.
     */
    private void injectGooglePlayInformation(){
        // Get the textviews.
        TextView googlePlayStatus = (TextView) rootView.findViewById(R.id
                .textview_google_play_services_status);
        TextView googlePlayVersion = (TextView) rootView.findViewById(R.id
                .textview_google_play_services_version_code);
        TextView googlePlayVersionName = (TextView) rootView.findViewById(R.id
                .textview_google_play_services_version_name);

        // Get the buttons.
        Button getFromGooglePlayStore = (Button) rootView.findViewById(R.id
                .button_get_google_play_services_google_play_store);
        getFromGooglePlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToWebsite(GOOGLE_SERVICES_PLAY_LINK);
            }
        });
        Button getFromAPKMirror = (Button) rootView.findViewById(R.id
                .button_get_google_play_services_apk_mirror);
        getFromAPKMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToWebsite(APK_MIRROR_SEARCH_LINK);
            }
        });

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

            if (getFromAPKMirror != null){
                getFromAPKMirror.setVisibility(View.GONE);
            }

            if (googlePlayVersionName != null){
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

            if (getFromAPKMirror != null){
                getFromAPKMirror.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * This sends the user to the play store to download the play services.
     */
    private void sendUserToWebsite(String url){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_check_your_network_settings, Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
