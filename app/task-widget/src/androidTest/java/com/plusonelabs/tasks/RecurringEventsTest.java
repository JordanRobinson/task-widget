package com.plusonelabs.tasks;

import android.test.InstrumentationTestCase;

import com.plusonelabs.tasks.calendar.CalendarQueryRow;
import com.plusonelabs.tasks.calendar.MockCalendarContentProvider;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * @author yvolk@yurivolkov.com
 */
public class RecurringEventsTest extends InstrumentationTestCase {
    private static final String TAG = RecurringEventsTest.class.getSimpleName();

    private MockCalendarContentProvider provider = null;
    private EventRemoteViewsFactory factory = null;
    private int eventId = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        provider = MockCalendarContentProvider.getContentProvider(this);
        factory = new EventRemoteViewsFactory(provider.getContext());
        assertTrue(factory.getWidgetEntries().isEmpty());
        eventId = 0;
    }

    @Override
    protected void tearDown() throws Exception {
        provider.tearDown();
        super.tearDown();
    }

    /**
     * @see <a href="https://github.com/plusonelabs/calendar-widget/issues/191">Issue 191</a>
     */
    public void testShowRecurringEvents() {
        DateTime date = DateTime.now().withTimeAtStartOfDay();
        long millis = date.getMillis() + TimeUnit.HOURS.toMillis(10);
        eventId++;
        for (int ind=0; ind<15; ind++) {
            millis += TimeUnit.DAYS.toMillis(1);
            provider.addRow(new CalendarQueryRow().setEventId(eventId).setTitle("Work each day")
                    .setBegin(millis).setEnd(millis + TimeUnit.HOURS.toMillis(9)));
        }
        factory.onDataSetChanged();
        factory.logWidgetEntries(TAG);
        assertTrue("Entries: " + factory.getWidgetEntries().size(), factory.getWidgetEntries().size() > 15);
    }
}
