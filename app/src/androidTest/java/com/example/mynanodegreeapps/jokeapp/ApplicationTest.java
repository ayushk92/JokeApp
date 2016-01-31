package com.example.mynanodegreeapps.jokeapp;

import android.app.Application;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.Loader;
import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.LoaderTestCase;
import junit.framework.Assert;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {
    public ApplicationTest() {
    }

    private static final String EMPTY_STRING = "";

    private  <T> T getLoaderResultSynchronously(final Loader<T> loader) {
        // The test thread blocks on this queue until the loader puts it's result in
        final ArrayBlockingQueue<T> queue = new ArrayBlockingQueue<T>(1);

        // This callback runs on the "main" thread and unblocks the test thread
        // when it puts the result into the blocking queue
        final Loader.OnLoadCompleteListener<T> listener = new Loader.OnLoadCompleteListener<T>() {
            @Override
            public void onLoadComplete(Loader<T> completedLoader, T data) {
                // Shut the loader down
                completedLoader.unregisterListener(this);
                completedLoader.stopLoading();
                completedLoader.reset();

                // Store the result, unblocking the test thread
                queue.add(data);
            }
        };

        // This handler runs on the "main" thread of the process since AsyncTask
        // is documented as needing to run on the main thread and many Loaders use
        // AsyncTask
        final Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                loader.registerListener(0, listener);
                loader.startLoading();
            }
        };

        // Ask the main thread to start the loading process
        mainThreadHandler.sendEmptyMessage(0);

        // Block on the queue waiting for the result of the load to be inserted
        T result;
        while (true) {
            try {
                result = queue.take();
                break;
            } catch (InterruptedException e) {
                throw new RuntimeException("waiting thread interrupted", e);
            }
        }

        return result;
    }



    EndpointsAsyncTask endpointsAsyncTask;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        endpointsAsyncTask = new EndpointsAsyncTask(getContext());

    }

    public void testAsyncTask() {
        String loaderData = getLoaderResultSynchronously(endpointsAsyncTask).toString();
        Assert.assertFalse("AsyncTask test ", EMPTY_STRING.contentEquals(loaderData));
    }
}