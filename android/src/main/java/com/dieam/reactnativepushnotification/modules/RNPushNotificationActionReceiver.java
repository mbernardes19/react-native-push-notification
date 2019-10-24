package com.dieam.reactnativepushnotification.modules;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

import static com.dieam.reactnativepushnotification.modules.RNPushNotification.LOG_TAG;

public class RNPushNotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Bundle notification = intent.getBundleExtra("notification");
        Log.v(LOG_TAG, "onReceiveAction " + notification);
        Log.v(LOG_TAG, "onReceiveAction.context " + context);

        ReactApplication application = (ReactApplication) context.getApplicationContext();
        RNPushNotificationHelper mRNPushNotificationHelper = new RNPushNotificationHelper((Application) application);

        if (!RNPushNotificationHelper.isAppOnForeground((context))) {
            /**
             * In background or when app is closed -> start HeadlessJs task
             */
            Intent serviceIntent = new Intent(context, RNPushNotificationActionService.class);
            serviceIntent.putExtra("notification", notification);
            context.startService(serviceIntent);
            HeadlessJsTaskService.acquireWakeLockNow(context);
        } else {
            /**
             * In foreground -> send action event
             */
            ReactInstanceManager mReactInstanceManager = application.getReactNativeHost().getReactInstanceManager();
            ReactContext reactContext = mReactInstanceManager.getCurrentReactContext();
            RNPushNotificationJsDelivery jsDelivery = new RNPushNotificationJsDelivery((ReactApplicationContext) reactContext);
            jsDelivery.notifyNotificationAction(notification);
        }

        // Dismiss the notification
        mRNPushNotificationHelper.clearNotification(notification);
    }
}


