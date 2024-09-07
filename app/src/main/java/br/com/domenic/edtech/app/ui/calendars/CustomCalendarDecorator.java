package br.com.domenic.edtech.app.ui.calendars;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Set;

public class CustomCalendarDecorator implements DayViewDecorator {

    private final Set<CalendarDay> dates;
    private final ColorDrawable colorDrawable;

    public CustomCalendarDecorator(Context context, Set<CalendarDay> dates, int colorResId) {
        this.dates = dates;
        this.colorDrawable = new ColorDrawable(ContextCompat.getColor(context, colorResId));
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(colorDrawable);
    }
}
