package kosmo.hdpay.qr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.concurrent.ExecutionException;

import kosmo.hdpay.R;
import kosmo.hdpay.database.DepositMethod;
import kosmo.hdpay.database.HD_Connection;
import kosmo.hdpay.vo.deposit.CardDTO;

public class QRActivity extends AppCompatActivity {
    private ImageView iv;
    private TextView cardBal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_pay);

        //final CardDTO card = null;


        final String[] cardType = {"HD노리카드", "HD체크카드","card"};
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.style_spiner_layout, cardType);
        adapter.setDropDownViewResource(R.layout.style_spiner_layout);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"Selected:"+cardType[position],Toast.LENGTH_SHORT).show();
                System.out.println("버튼 눌러짐 : " + cardType[position] );
//                CardDTO card = new CardDTO();
                int cardbal = setQR(cardType[position]);

                DepositMethod depositMethod = new DepositMethod();
                cardBal = findViewById(R.id.cardBal);
                cardBal.setText(depositMethod.numberChange(cardbal)+"원");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("버튼이 눌러지지 않음" );
            }
        });

    }

    public int setQR(String card_type) {
        iv = (ImageView) findViewById(R.id.qrcode);
        HD_Connection conn = new HD_Connection();
        CardDTO cardDTO = null;
        try {
            //DB에 있는 카드정보를 불러오기위해 DB와 Connection하는 부분
            String result = conn.execute("cardDetail", "2", "card_type", card_type).get();
            System.out.println("result : " + result);

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try{
                //QR코드 구현 부분
                BitMatrix bitMatrix = multiFormatWriter.encode(result, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                iv.setImageBitmap(bitmap);
            }catch (Exception e){}
            //JSon형식의 String으로 받아온 데이터를 Gson을 이용해 파싱
            Gson gson = new Gson();
            cardDTO = gson.fromJson(result, CardDTO.class);
            System.out.println(cardDTO.getAc_balance());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cardDTO.getAc_balance();

    }
}



