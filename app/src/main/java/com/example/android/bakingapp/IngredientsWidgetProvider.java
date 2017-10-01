package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;


public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String LOG_TAG = IngredientsWidgetProvider.class.getSimpleName();
    public static final String EXTRA_RECIPE_ID = "com.example.android.bakingapp.RECIPE_ID";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
//        Intent initialUpdateIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        initialUpdateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        context.sendBroadcast(initialUpdateIntent);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        remoteViews.setRemoteAdapter(R.id.widget_list_view, intent);
        Intent ingredientDetailsIntent = new Intent(context, RecipeDetailsActivity.class);
        ingredientDetailsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        SharedPreferences sharedPreferences = context.getSharedPreferences("recipeDetails", Context.MODE_PRIVATE);
        ingredientDetailsIntent.putExtra("recipeId", sharedPreferences.getInt("recipeDetailId", -2));
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ingredientDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        if (intent.getAction().equals(
//                AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), IngredientsWidgetProvider.class.getName());
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,  R.id.widget_list_view);
//        }
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

