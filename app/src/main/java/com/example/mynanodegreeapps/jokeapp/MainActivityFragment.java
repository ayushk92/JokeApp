package com.example.mynanodegreeapps.jokeapp;

/**
 * Created by akhatri on 29/01/16.
 */
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.example.mynanodegreeapps.joketellerlibrary.JokeActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    public MainActivityFragment() {
        jokeText = null;
    }

    private String jokeText;

    private static final int JOKE_LOADER = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Button tellJokeButton = (Button) root.findViewById(R.id.tellJokeButton);

        tellJokeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                tellJoke(v);
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(JOKE_LOADER,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    public void tellJoke(View view){
        if(jokeText != null)
            Toast.makeText(getActivity(), jokeText,Toast.LENGTH_LONG).show();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new EndpointsAsyncTask(getContext());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
       jokeText = data;
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}