package kosmo.hdpay.main_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

import kosmo.hdpay.BR;
import kosmo.hdpay.R;
import kosmo.hdpay.customcalendar_ios.ui.adapter.CalendarAdapter;
import kosmo.hdpay.customcalendar_ios.ui.viewmodel.CalendarListViewModel;
import kosmo.hdpay.databinding.CalendarListBinding;

public class FragmentCall extends Fragment {


    private CalendarListBinding binding;
    private CalendarAdapter calendarAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_main, container,false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.calendar_main);
        binding.setVariable(BR.model, new ViewModelProvider(this).get(CalendarListViewModel.class));
        binding.setLifecycleOwner(this);

        binding.getModel().initCalendarList();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        calendarAdapter = new CalendarAdapter();
        binding.pagerCalendar.setLayoutManager(manager);
        binding.pagerCalendar.setAdapter(calendarAdapter);
        observe();
        return view;
    }

    private void observe() {
        binding.getModel().mCalendarList.observe(getActivity(), new Observer<ArrayList<Object>>() {
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