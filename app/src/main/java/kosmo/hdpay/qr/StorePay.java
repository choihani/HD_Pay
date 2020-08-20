package kosmo.hdpay.qr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import kosmo.hdpay.R;
import kosmo.hdpay.database.HD_Connection;
import kosmo.hdpay.session.SessionManager;

public class StorePay extends AppCompatActivity {
    private TextView card_num;
    private EditText score;
    private EditText pay_receipt;
    private EditText pay_money;
    private Button scorepay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_pay);

        HD_Connection conn = new HD_Connection();
        SessionManager sm = new SessionManager(getApplicationContext());

        score = findViewById(R.id.score);
        pay_receipt = findViewById(R.id.pay_receipt);
        pay_money = findViewById(R.id.pay_money);
        card_num = findViewById(R.id.card_num);


        String res_card_num = String.valueOf(sm.getCardNum());
        card_num.setText(res_card_num);

        scorepay = findViewById(R.id.scorepay);
        scorepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String strScore = score.getText().toString().trim();
                    String strpay_receipt = pay_receipt.getText().toString().trim();
                    String strpay_money = pay_money.getText().toString().trim();

                    System.out.println("값:"+strScore+","+strpay_receipt+","+strpay_money);

                    String result = result = conn.execute("payment","8","card_num",res_card_num,
                            "pay_money",strpay_money,"pay_receipt",strpay_receipt,"score",strScore).get();

                    System.out.println("result : " + result);
                    Toast.makeText(getApplicationContext(),"결제완료!",Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
