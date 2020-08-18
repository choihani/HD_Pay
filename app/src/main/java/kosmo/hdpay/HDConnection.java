package kosmo.hdpay;

import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;

public class HDConnection extends AppCompatActivity {

    static RequestQueue requestQueue;
    private CookieManager cookieManager;

    public void request(String urlStr){

        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("응답 -> "+response);

                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("에러 -> "+error.toString());
                    }
                }
        ){
            //요청 파라미터를 처리하는 메소드. 웹에서 서버쪽으로 데이터를 보낼때
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                return params;
            }
        };
        //request.setShouldCache(false);
        requestQueue.add(request);
        System.out.println("요청 보냄");

    }
    public MemberVo processResponse(String response){
        Gson gson = new Gson();

        MemberVo member = gson.fromJson(response, MemberVo.class);
        return member;
    }

//    private boolean setUpCookies(){
//
//        boolean ris;

//        cookieManager = new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL);
//        CookieHandler.setDefault(cookieManager);

//        SharedPreferences preferences = getSharedPreferences(PersistentCookieStore.class.getName(), MODE_PRIVATE);
//        String session_cookie = preferences.getString("session_cookie",null);

//        if(session_cookie != null && !session_cookie.isEmpty()){
//
//            Log.d(TAG,session_cookie);
//            ris = true;
//
//        }else{
//
//            ris = false;
//        }

//        return ris;
//     }


//    public void request(String urlStr) {
//        try {
//            StringBuilder builder = new StringBuilder();
//
//            URL url = new URL(urlStr);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            if (conn != null) {
//                conn.setConnectTimeout(10000);
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                //conn.setDoOutput(true);
//
//                int resCode = conn.getResponseCode(); //요청을 보내고 받는 역할
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String line = null;
//                while (true) {
//                    line = reader.readLine();
//                    if (line == null) { //문자가 없는 경우
//                        break;
//                    }
//
//                    builder.append(line + "/n");
//                }
//                reader.close();
//                conn.disconnect();
//            }
//            System.out.println("응답 -> " + builder.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
