package br.com.domenic.edtech.app.ui.calendars;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Set;

public class DisabledDaysDecorator implements DayViewDecorator {

    private final Set<CalendarDay> disabledDays;

    public DisabledDaysDecorator(Set<CalendarDay> disabledDays) {
        this.disabledDays = disabledDays;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return disabledDays.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(Color.GRAY));
    }
}