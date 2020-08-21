package kosmo.hdpay.customcalendar_ios.ui.viewmodel;


import androidx.lifecycle.ViewModel;

import kosmo.hdpay.customcalendar_ios.data.TSLiveData;

public class CalendarHeaderViewModel extends ViewModel {
    public TSLiveData<Long> mHeaderDate = new TSLiveData<>();

    public void setHeaderDate(long headerDate) {
        this.mHeaderDate.setValue(headerDate);
    }
}
