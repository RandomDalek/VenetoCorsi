package jhonnyhueller.venetocorsi.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jhonnyhueller.venetocorsi.R;


public class BlankFragmentHome extends Fragment {
    private int number;
    public BlankFragmentHome(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        return rootView;
    }
}