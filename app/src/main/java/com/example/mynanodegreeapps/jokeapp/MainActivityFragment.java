package com.example.mynanodegreeapps.jokeapp;

import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mynanodegreeapps.joketellerlibrary.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private static final int JOKE_LOADER = 0;

    public MainActivityFragment() {
        jokeText = null;
    }

    private String jokeText;

    private ProgressBar mprogressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.noadd_fragment, container, false);

        Button tellJokeButton = (Button) root.findViewById(R.id.tellJokeButton);

        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tellJoke(v);
            }
        });
        mprogressBar = (ProgressBar) root.findViewById(R.id.spinner);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //getLoaderManager().initLoader(JOKE_LOADER,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    public void tellJoke(View view){
        mprogressBar.setVisibility(View.VISIBLE);
        //Loader<String> loader = getLoaderManager().getLoader(JOKE_LOADER);
        //if(loader == null)
        getLoaderManager().initLoader(JOKE_LOADER,null,this);
        //else
        //getLoaderManager().restartLoader(JOKE_LOADER,null,this);

//        if(jokeText != null){
//            //Invoke Android Library
//            Intent intent = new Intent(getActivity(),com.example.mynanodegreeapps.joketellerlibrary.JokeActivity.class);
//            intent.putExtra(JokeActivity.JOKE_KEY,jokeText);
//            this.startActivity(intent);
//        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG,"Loader created");
        return new EndpointsAsyncTask(getContext());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        jokeText = data;
        mprogressBar.setVisibility(View.INVISIBLE);
        if(jokeText != null){
            //Invoke Android Library
            Intent intent = new Intent(getActivity(),com.example.mynanodegreeapps.joketellerlibrary.JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY,jokeText);
            this.startActivity(intent);
        }
        getLoaderManager().destroyLoader(JOKE_LOADER);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
