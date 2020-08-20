package kosmo.hdpay.session;

import android.content.Context;
import android.content.SharedPreferences;

import kosmo.hdpay.vo.MemberVO;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_MemCode = "session_memcode";
    String SESSION_MemName = "session_memname";
    String SESSION_CardNum = "session_cardnum";
    // Create constructor
    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    // Create set login method
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }

    // Create get login method
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    // 세션 저장
    public void saveSession(MemberVO vo){
        //save session of user whenever user is logged in
        int mem_code = vo.getMem_code();
        String mem_name = vo.getMem_name();
        editor.putInt(SESSION_MemCode, mem_code).commit();
        editor.putString(SESSION_MemName, mem_name).commit();
    }

    // 세션 얻기
    public int getMemcode(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_MemCode, -1);
    }
    public String getMemname(){
        //return user id whose session is saved
        return sharedPreferences.getString(SESSION_MemName, null);
    }

    // 세션 지우기
    public void removeSession(){
        editor.putInt(SESSION_MemCode,-1).commit();
        editor.putInt(SESSION_MemName,-1).commit();
    }

    // 카드 저장
    public void saveCardNum(int cardNum){
        //save session of user whenever user is logged in
        editor.putInt(SESSION_CardNum, cardNum).commit();
    }
    // 카드 얻기
    public int getCardNum(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_CardNum, -1);
    }
    // 카드 지우기
    public void removeCardNum(){
        editor.putInt(SESSION_CardNum,-1).commit();
    }

}
