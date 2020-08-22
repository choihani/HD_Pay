package kosmo.hdpay.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import kosmo.hdpay.R;
import kosmo.hdpay.session.SessionManager;
import kosmo.hdpay.vo.deposit.AccountHistoryVO;
import kosmo.hdpay.vo.deposit.AccountVO;

public class DepositMethod {

    public String numberChange(int number){
        DecimalFormat format = new DecimalFormat("###,###");
        String changeNumber = format.format(number);
        return changeNumber;
    }

    // 계좌 정보를 가져온다.
    public ArrayList<HashMap<String, Object>> getDeposit(Context context){
        int[] img = {R.drawable.deposit_logo_1,R.drawable.deposit_logo_2,R.drawable.deposit_logo_3,R.drawable.deposit_logo_4,R.drawable.deposit_logo_5,R.drawable.deposit_logo_6,
                R.drawable.deposit_logo_7,R.drawable.deposit_logo_8,R.drawable.deposit_logo_9};
        String result;
        String mem_code="";
        SessionManager sessionManager = new SessionManager(context);
        ArrayList<HashMap<String, Object>> myAclist = new ArrayList<>();
        HashMap<String, Object> myMap;
        if (sessionManager.getLogin()){
            mem_code = String.valueOf(sessionManager.getMemcode());
        }
        HD_Connection task = new HD_Connection();

        try {
            result = task.execute("listAc","2","mem_code",mem_code).get();
            Gson gson = new Gson();
            myMap = new HashMap<>();
            // *****************************************************************************
            List<AccountVO> list = gson.fromJson(result,new TypeToken<ArrayList<AccountVO>>(){}.getType());
            System.out.println("list : "+list);
            int i=0;
            for(AccountVO e : list){
                myMap = new HashMap<>();
                myMap.put("colImg",img[i]);
                myMap.put("colName",e.getAc_name());
                myMap.put("colNum",e.getAc_num());
                myMap.put("colBalance",numberChange(e.getAc_balance())+"원");
                myAclist.add(myMap);
                i++;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myAclist;
    }

    // 계좌 상세 보기
    public ArrayList<HashMap<String, Object>> getAccountDetailLsit(Context context){
        int[] img = {R.drawable.deposit_logo_1,R.drawable.deposit_logo_2,R.drawable.deposit_logo_3,R.drawable.deposit_logo_4,R.drawable.deposit_logo_5,R.drawable.deposit_logo_6,
                R.drawable.deposit_logo_7,R.drawable.deposit_logo_8,R.drawable.deposit_logo_9};
        System.out.println("getAccountDetailLsit 메소드 실행");
        String result;
        String mem_code="";
        String accountNum="";
        SessionManager sessionManager = new SessionManager(context);
        ArrayList<HashMap<String, Object>> myAclist = new ArrayList<>();
        HashMap<String, Object> myMap;
        if (sessionManager.getLogin()){
            mem_code = String.valueOf(sessionManager.getMemcode());
            accountNum = sessionManager.getAccountnum();
        }
        HD_Connection task = new HD_Connection();

        try {
            result = task.execute("allListAc","4","mem_code",mem_code,"ac_num",accountNum).get();
            Gson gson = new Gson();
            myMap = new HashMap<>();
            // *****************************************************************************
            List<AccountHistoryVO> list = gson.fromJson(result,new TypeToken<ArrayList<AccountHistoryVO>>(){}.getType());
            System.out.println("list : "+list);
            int i=0;
            for(AccountHistoryVO e : list){
                myMap = new HashMap<>();
                myMap.put("colImg",img[i]);
                myMap.put("colName",e.getName());

                if (e.getDep_money()!=null){
                    // 입금
                    myMap.put("colNum","+"+e.getDep_money());
                }else {
                    // 출금
                    myMap.put("colNum","-"+e.getWit_money());
                }
                myMap.put("colBalance","잔액 : "+numberChange(Integer.parseInt(e.getBalance()))+"원");

                myAclist.add(myMap);
                i++;
                if (i==8){
                    i=0;
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myAclist;
    }

    public int getBalance(Context context){
        int totalBalance = 0;
        String result;
        String mem_code="";
        SessionManager sessionManager = new SessionManager(context);
        if (sessionManager.getLogin()){
            mem_code = String.valueOf(sessionManager.getMemcode());
        }else {
            return -1;
        }

        HD_Connection task = new HD_Connection();

        try {
            result = task.execute("listAc","2","mem_code",mem_code).get();
            Gson gson = new Gson();

            // *****************************************************************************
            List<AccountVO> list = gson.fromJson(result,new TypeToken<ArrayList<AccountVO>>(){}.getType());
            for(AccountVO e : list){
                totalBalance += e.getAc_balance();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return totalBalance;
    }
}
