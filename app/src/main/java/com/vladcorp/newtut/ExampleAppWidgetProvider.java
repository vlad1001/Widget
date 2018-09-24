package com.vladcorp.newtut;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import static com.vladcorp.newtut.ExampleAppWidgetConfig.KEY_BUTTON_TEXT;
import static com.vladcorp.newtut.ExampleAppWidgetConfig.SHARED_PREFS;

public class ExampleAppWidgetProvider extends AppWidgetProvider{

    public static final String ACTION_TIMER = "action_timer";
    static RemoteViews views;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Toast.makeText(context, "check2", Toast.LENGTH_LONG).show();

        Log.d("ptt", "dd ");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//            String buttonText = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "Press me");
//
//            Intent intent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//            views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
//            views.setOnClickPendingIntent(R.id.example_widget_button, pendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//
//        }
//}


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Toast.makeText(context, "OnEnabled", Toast.LENGTH_LONG).show();

    }



    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews vies = new RemoteViews(context.getPackageName(), R.layout.example_widget);

        appWidgetManager.updateAppWidget(appWidgetId, vies);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Toast.makeText(context, "OnDeleted", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Toast.makeText(context, "OnRestored", Toast.LENGTH_LONG).show();

    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String buttonText = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "Press me");
//
//        // Construct the RemoteViews object
//        views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
//
//        views.setOnClickPendingIntent(R.id.example_widget_button, pi);
//        Intent click = new Intent(context, ExampleAppWidgetConfig.class);
//
//        views.setCharSequence(R.id.btn_txt, "setText", buttonText);
//
//
//        views.setOnClickPendingIntent(R.id.example_widget_button,
//                getPendingSelfIntent(context, ACTION_TIMER));
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);

            //SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            //String buttonText = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "Press me");

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.setCharSequence(R.id.btn_txt, "setText", buttonText);
        Toast.makeText(context, "updateWidget", Toast.LENGTH_LONG).show();


        views.setOnClickPendingIntent(R.id.example_widget_imageview, pendingIntent);
        Log.d("ptt", "3 ");

            views.setOnClickPendingIntent(R.id.example_widget_imageview,
                getPendingSelfIntent(context, ACTION_TIMER));
        Log.d("ptt", " 4");

            appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Log.d("ptt", "das ");

        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            Log.d("ptt", " one");
            if (ACTION_TIMER.equals(intent.getAction())) {

                int appWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetID, R.id.example_widget_imageview);

                Toast.makeText(context, "A timer for 30 minutes has been activated", Toast.LENGTH_LONG).show();
                Intent setTimer = new Intent(AlarmClock.ACTION_SET_TIMER);
                setTimer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //setTimer.putExtra(AlarmClock.EXTRA_LENGTH, 1800);
                setTimer.putExtra(AlarmClock.EXTRA_LENGTH, 180000);
                Log.d("ptt", " 2");

                setTimer.putExtra(AlarmClock.EXTRA_MESSAGE, "30 Minutes");
                setTimer.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
                context.startActivity(setTimer);

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_widget);
                ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);


//                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
//                        R.drawable.button_default);
//
//                remoteViews.setImageViewBitmap(R.id.red_button, icon);

                appWidgetManager.updateAppWidget(thisWidget, remoteViews);

            }
        }catch (Exception ex){
            Log.d("ptt", ex.getMessage());
        }
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled", Toast.LENGTH_LONG).show();

        // Enter relevant functionality for when the last widget is disabled
    }
}
