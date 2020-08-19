package kosmo.hdpay.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kosmo.hdpay.session.SessionManager;
import kosmo.hdpay.vo.deposit.AccountVO;

public class DepositMethod {

    public String numberChange(int number){
        DecimalFormat format = new DecimalFormat("###,###");
        String changeNumber = format.format(number);
        return changeNumber;
    }
    public int getBalance(Context context){
        int totalBalance = 0;
        String result;
        String mem_code="";
        SessionManager sessionManager = new SessionManager(context);
        if (sessionManager.getLogin()){
            mem_code = String.valueOf(sessionManager.getMemcode());
        }

        HD_Connection task = new HD_Connection();

        try {
            result = task.execute("listAc","2","mem_code",mem_code).get();
            System.out.println("result:"+result.getClass());
            Gson gson = new Gson();
            System.out.println("gson : "+gson);

            // *****************************************************************************
            List<AccountVO> list = gson.fromJson(result,new TypeToken<ArrayList<AccountVO>>(){}.getType());
            for(AccountVO e : list){
                totalBalance += e.getAc_balance();
                System.out.println("totalBalance: "+totalBalance);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return totalBalance;
    }
}
