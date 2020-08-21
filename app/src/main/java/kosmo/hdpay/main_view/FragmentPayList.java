package kosmo.hdpay.main_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import kosmo.hdpay.R;

public class FragmentPayList extends Fragment {

    int[] img = {R.drawable.deposit_logo_1,R.drawable.deposit_logo_2,R.drawable.deposit_logo_3,R.drawable.deposit_logo_4,R.drawable.deposit_logo_5,R.drawable.deposit_logo_6,
            R.drawable.deposit_logo_7,R.drawable.deposit_logo_8,R.drawable.deposit_logo_9};

    ListView payList;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_lsit,container,false);

        payList = view.findViewById(R.id.payList);

        ArrayList<HashMap<String,Object>> myArrayList = new ArrayList<>();

        int i = 0;
        for(int e : img){
            HashMap<String,Object> map = new HashMap<>();
            map.put("colPayImg",e);
            map.put("colPrice","XMan"+(i+1));
            map.put("colMemo","김길동 "+(i+1)+"세");
            myArrayList.add(map);
            i++;
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),myArrayList,R.layout.pay_column,new String[]{"colPayImg","colPrice","colMemo"},new int[]{R.id.colImg, R.id.colPrice, R.id.colMemo});
        payList.setAdapter(simpleAdapter);
        payList.setOnItemClickListener((adapterView, view1, position, l) -> {
            Toast.makeText(getContext(),"클릭되어짐",Toast.LENGTH_SHORT);
        });
        return view;
    }

}
