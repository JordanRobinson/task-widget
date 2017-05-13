package com.plusonelabs.tasks;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.RemoteViewsService;

import static com.plusonelabs.tasks.Theme.getCurrentThemeId;
import static com.plusonelabs.tasks.prefs.CalendarPreferences.PREF_ENTRY_THEME;
import static com.plusonelabs.tasks.prefs.CalendarPreferences.PREF_ENTRY_THEME_DEFAULT;


public class EventWidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Context appContext = getApplicationContext();
        int currentThemeId = getCurrentThemeId(appContext, PREF_ENTRY_THEME, PREF_ENTRY_THEME_DEFAULT);
        ContextThemeWrapper context = new ContextThemeWrapper(appContext, currentThemeId);
        return new EventRemoteViewsFactory(context);
    }
}