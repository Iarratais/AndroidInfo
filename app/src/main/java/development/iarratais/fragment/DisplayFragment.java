/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import development.iarratais.androidinfo.R;
import development.iarratais.utils.DisplayInfoUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {

    View rootView;

    DisplayInfoUtil displayInfoUtil;

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_display, container, false);

        displayInfoUtil = new DisplayInfoUtil(getActivity());

        injectInformation();

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Add the information into the views.
     */
    public void injectInformation(){
        TextView orientationTextView = (TextView) rootView.findViewById(R.id
                .textview_display_information_orientation);
        TextView densityTextView = (TextView) rootView.findViewById(R.id
                .textview_display_information_density);
        TextView widthTextView = (TextView) rootView.findViewById(R.id
                .textview_display_information_width);
        TextView heightTextView = (TextView) rootView.findViewById(R.id
                .textview_display_information_height);

        String orientationText = getString(R.string.display_information_orientation,
                displayInfoUtil.getDisplayOrientation());
        orientationTextView.setText(orientationText);

        String densityText = getString(R.string.display_information_dpi, displayInfoUtil
                .getDisplayDPI());
        densityTextView.setText(densityText);

        String widthText = getString(R.string.display_information_width, displayInfoUtil
                .getDisplayWidth());
        widthTextView.setText(widthText);

        String heightText = getString(R.string.display_information_height, displayInfoUtil
                .getDisplayHeight());
        heightTextView.setText(heightText);
    }
}
