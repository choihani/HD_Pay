package kosmo.hdpay.depositInquire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kosmo.hdpay.R;
import kosmo.hdpay.database.DepositMethod;
import kosmo.hdpay.database.HD_Connection;
import kosmo.hdpay.session.SessionManager;
import kosmo.hdpay.vo.deposit.AccountHistoryVO;
import kosmo.hdpay.vo.deposit.AccountVO;

public class Account_allList extends AppCompatActivity {

    private TextView mainAccountNum,detailTotalBalance;
    private ListView accountDetailList;

    DepositMethod depositMethod;

    private SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list);

        depositMethod = new DepositMethod();

        System.out.println("detail_list 페이지에 접속");
        mainAccountNum = findViewById(R.id.mainAccountNum);
        detailTotalBalance = findViewById(R.id.detailTotalBalance);
        accountDetailList = findViewById(R.id.accountDetailList);

        // 세션을 가져온다.
        sessionManager = new SessionManager(getApplicationContext());
        // 계좌 번호를 가져온다.
        String accountNum = sessionManager.getAccountnum();
        // 계좌번호를 시각화
        mainAccountNum.setText(accountNum);



        ArrayList<HashMap<String,Object>> myArrayList = depositMethod.getAccountDetailLsit(getApplicationContext());

        // 계좌의 돈 합계
        detailTotalBalance.setText(myArrayList.get(0).get("colBalance").toString());

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),myArrayList,R.layout.deposit_column,new String[]{"colImg","colName","colNum","colBalance"},new int[]{R.id.colImg, R.id.colName, R.id.colNum, R.id.colBalance});
        accountDetailList.setAdapter(simpleAdapter);
        accountDetailList.setOnItemClickListener((adapterView, view, position, l) -> {
        });

    }


}
