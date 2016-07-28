/*
 * Copyright (c) 2016 Iarratais Development
 *
 * karl.development@gmail.com
 */

package development.iarratais.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.iarratais.androidinfo.R;
import development.iarratais.utils.SIMInfoUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SIMFragment extends Fragment {

    View rootView;

    SIMInfoUtil simInfoUtil;

    public SIMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sim, container, false);
        simInfoUtil = new SIMInfoUtil(getActivity());

        Log.d(getClass().getSimpleName(), "SIM Number: " + simInfoUtil.getSIMNumber());
        Log.d(getClass().getSimpleName(), "Operator: " + simInfoUtil.getNetworkOperator());
        Log.d(getClass().getSimpleName(), "Operator country: " + simInfoUtil
                .getNetworkOperatorCountry());

        return rootView;
    }

}
