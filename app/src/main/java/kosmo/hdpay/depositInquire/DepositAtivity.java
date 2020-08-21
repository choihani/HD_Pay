package kosmo.hdpay.depositInquire;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import kosmo.hdpay.R;
import kosmo.hdpay.database.DepositMethod;
import kosmo.hdpay.database.HD_Connection;
import kosmo.hdpay.session.SessionManager;

public class DepositAtivity extends AppCompatActivity {

    DepositMethod depositMethod;

    SessionManager sessionManager;
    TextView mem_name, totalBalance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_list);

        depositMethod = new DepositMethod();


        // 조회 페이지의 ListView를 불러온다.
        ListView listView = findViewById(R.id.depositDataList);
        totalBalance = findViewById(R.id.totalBalance);
        mem_name = findViewById(R.id.mem_name);

        // Initialize session manager
        sessionManager = new SessionManager(getApplicationContext());
        String sMem_name = sessionManager.getMemname();
        mem_name.setText(sMem_name);
        totalBalance.setText(depositMethod.numberChange(depositMethod.getBalance(getApplicationContext()))+"원");
        ArrayList<HashMap<String,Object>> myArrayList = depositMethod.getDeposit(getApplicationContext());

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),myArrayList,R.layout.deposit_column,new String[]{"colImg","colName","colNum","colBalance"},new int[]{R.id.colImg, R.id.colName, R.id.colNum, R.id.colBalance});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Toast.makeText(getApplicationContext(),"클릭되어짐",Toast.LENGTH_SHORT);
            depositMethod.getDeposit(getApplicationContext());
        });
    }
}
