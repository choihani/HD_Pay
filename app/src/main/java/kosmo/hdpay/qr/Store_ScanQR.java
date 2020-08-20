package kosmo.hdpay.qr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import kosmo.hdpay.R;
import kosmo.hdpay.main_view.LoginActivity;
import kosmo.hdpay.session.SessionManager;
import kosmo.hdpay.vo.deposit.CardDTO;

public class Store_ScanQR extends AppCompatActivity {

    private IntentIntegrator qrScan; //가로, 세로 변경을 위한 객체
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        //new IntentIntegrator(this).initiateScan(); //가로버전
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        CardDTO cardDTO = null;

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                cardDTO = gson.fromJson(result.getContents(), CardDTO.class);
                sm = new SessionManager(getApplicationContext());

                int card_num = cardDTO.getCard_num();
                sm.saveCardNum(card_num);

                startActivity(new Intent(getApplicationContext(), StorePay.class));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}