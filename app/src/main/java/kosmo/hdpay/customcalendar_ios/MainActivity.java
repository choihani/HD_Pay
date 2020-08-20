package kosmo.hdpay.customcalendar_ios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import kosmo.hdpay.BR;
import kosmo.hdpay.R;
import kosmo.hdpay.customcalendar_ios.ui.adapter.CalendarAdapter;
import kosmo.hdpay.customcalendar_ios.ui.viewmodel.CalendarListViewModel;
import kosmo.hdpay.databinding.CalendarListBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private CalendarListBinding binding;
    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);
        binding = DataBindingUtil.setContentView(this, R.layout.calendar_main);
        binding.setVariable(BR.model, new ViewModelProvider(this).get(CalendarListViewModel.class));
        binding.setLifecycleOwner(this);

        binding.getModel().initCalendarList();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        calendarAdapter = new CalendarAdapter();
        binding.pagerCalendar.setLayoutManager(manager);
        binding.pagerCalendar.setAdapter(calendarAdapter);
        observe();

    }

    private void observe() {
        binding.getModel().mCalendarList.observe(this, new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(ArrayList<Object> objects) {
                calendarAdapter.submitList(objects);
                if (binding.getModel().mCenterPosition > 0) {
                    binding.pagerCalendar.scrollToPosition(binding.getModel().mCenterPosition);
                }
            }
        });
    }
}