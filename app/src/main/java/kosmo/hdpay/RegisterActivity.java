package kosmo.hdpay;
import android.os.AsyncTask;
import android.webkit.CookieManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://192.168.0.16:8080/project_Dank/hdpaylogin");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            String cookieString = CookieManager.getInstance().getCookie("세션받아올 URL");
            if (cookieString != null) {
                //세션을 위한 쿠키값 셋팅
                conn.setRequestProperty("Cookie", cookieString);
            }

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            sendMsg = "mem_email=" + strings[0] + "&mem_pwd=" + strings[1];

            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            System.out.println("접속 전까지 실행 완료");
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                // jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                System.out.println("receiveMsg:"+receiveMsg);
            } else {
                // 통신 실패
                System.out.println("통신 실패");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return receiveMsg;
    }

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




//        try {
//            StringBuilder builder = new StringBuilder();
//
//            URL url = new URL(urlStr);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            if(conn != null) {
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
//                    if(line == null){ //문자가 없는 경우
//                        break;
//                    }
//
//                    builder.append(line+ "/n");
//                }
//                reader.close();
//                conn.disconnect();
//            }
//            System.out.println("응답 -> "+ builder.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
    public void processResponse(String response){
        Gson gson = new Gson();

        MemberVo member = gson.fromJson(response, MemberVo.class);
    }


}