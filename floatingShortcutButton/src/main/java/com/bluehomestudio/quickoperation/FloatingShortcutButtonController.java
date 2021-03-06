package com.bluehomestudio.quickoperation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by mohamedmoamen on 11/30/17.
 */

public class FloatingShortcutButtonController implements Application.ActivityLifecycleCallbacks {

    private FloatingShortcutButton floatingShortcutButton;
    private String startActivityName, helpActivityName;
    private int activityCounter;
    private Context mContext;

    public FloatingShortcutButtonController(Application application) {
        application.registerActivityLifecycleCallbacks(this);

        mContext = application;
        floatingShortcutButton = FloatingShortcutButton.getInstance(application);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

        if (helpActivityName == null && startActivityName == null) {
            Log.e("FloatingShortcutButton", "Help activity and start activity could not be null");
            return;
        }

        //hide button when open quick operation
        if (helpActivityName.equals(activity.getClass().getName())) {
            floatingShortcutButton.hideQuickButton();
        }

        //show quick button
        if (startActivityName.equals(activity.getClass().getName())) {
            floatingShortcutButton.showQuickButton();
        }

        activityCounter++;
    }

    @Override
    public void onActivityPaused(Activity activity) {

        if (helpActivityName == null && startActivityName == null) {
            Log.e("FloatingShortcutButton", "Help activity and start activity could not be null");
            return;
        }

        //show button when close quick operation
        if (helpActivityName.equals(activity.getClass().getName())) {
            floatingShortcutButton.showQuickButton();
        }

        activityCounter--;
    }

    @Override
    public void onActivityStopped(Activity activity) {

        //hide quick button when application closed
        if (activityCounter == 0) {
            floatingShortcutButton.hideQuickButton();
        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    /**
     * Activity to start show quick operation
     *
     * @param cls activity class
     */
    public void setStartActivityName(Class<?> cls) {
        this.startActivityName = cls.getName();
    }


    /**
     * Activity to click in quick operation
     *
     * @param cls activity class
     */
    public void setHelpActivityName(Class<?> cls) {
        this.helpActivityName = cls.getName();
        floatingShortcutButton.setHelpActivityIntent(new Intent(mContext, cls));
    }

    public void setButtonIcon(int buttonIcon , int background) {
        floatingShortcutButton.setButtonIcon(buttonIcon , background);
    }
}
