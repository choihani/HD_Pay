package kosmo.hdpay.database;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HD_Connection extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            for(int i = 0; i<strings.length; i++){
                System.out.println("string["+i+"]"+strings[i]);
            }
            // 로그인 시 : hdpaylogin
            // 카드 생성 : addCard
            // 계좌 조회 : listAc
            // ...
            // *** strings[0] 에 @RequestMapping(value = "/listAc"); 를 담는다. ***
            String path = strings[0];
            System.out.println("path: "+ path);
            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://192.168.0.18/project_Dank/" + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());


            String encStr = null;

            // 보내야할 (데이터의 수*2)를 정한뒤 보낸다.
            int dataSize = Integer.parseInt(strings[1]);
            StringBuffer sb = new StringBuffer();
            // 사용 예시) 짝수번에 파라미터 이름, 홀수번에 데이터를 넣는다.
            for (int i = 2; i<=dataSize+1; i++){
                System.out.println("strings["+i+"] : "+strings[i]);
                if(i%2==0){
                    sb.append(strings[i]).append("=");
                }else{
                    encStr =URLEncoder.encode(strings[i],"euc-kr");
                    System.out.println(encStr);
                    sb.append(encStr);

                    if (dataSize+1 > i){
                        sb.append("&");
                    }
                }
            }
            System.out.println("sb : " + sb.toString());

            sendMsg = sb.toString();

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

    public void request(String urlStr) {
        try {
            StringBuilder builder = new StringBuilder();

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                //conn.setDoOutput(true);

                int resCode = conn.getResponseCode(); //요청을 보내고 받는 역할

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while (true) {
                    line = reader.readLine();
                    if (line == null) { //문자가 없는 경우
                        break;
                    }

                    builder.append(line + "/n");
                }
                reader.close();
                conn.disconnect();
            }
            System.out.println("응답 -> " + builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}