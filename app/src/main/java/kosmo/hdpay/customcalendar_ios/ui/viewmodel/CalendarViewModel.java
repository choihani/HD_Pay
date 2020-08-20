package kosmo.hdpay.customcalendar_ios.ui.viewmodel;


import androidx.lifecycle.ViewModel;

import kosmo.hdpay.customcalendar_ios.data.TSLiveData;

import java.util.Calendar;

public class CalendarViewModel extends ViewModel {
    public TSLiveData<Calendar> mCalendar = new TSLiveData<>();

    public void setCalendar(Calendar calendar) {
        this.mCalendar.setValue(calendar);
    }


}
