package kosmo.hdpay;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText mem_email, mem_pwd;
    Button loginBtn;

    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assign Variable
        mem_email = findViewById(R.id.mem_email);
        mem_pwd = findViewById(R.id.mem_pwd);
        loginBtn = findViewById(R.id.loginBtn);

        // Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from EditText
                String sMem_email = mem_email.getText().toString().trim();
                String sMem_pwd = mem_pwd.getText().toString().trim();

                // When password is empty
                if(sMem_pwd.equals("")){
                    // Display error message on EditText
                    mem_pwd.setError("Please enter password");
                }
                // When password is root
                if(sMem_pwd.equals("root")){
                    // Store login in session
                    sessionManager.setLogin(true);
                    // Store username in Session

                    // Redirect acticity
                    startActivity(new Intent(getApplicationContext(),
                            MainPage.class));
                    finish();
                }
                // When password is wrong
                else {
                    Toast.makeText(getApplicationContext()
                            ,"Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // If user already logged in
        if(sessionManager.getLogin()){
            startActivity(new Intent(getApplicationContext()
                    ,MainPage.class));
        }
    }

    // 하니가 사용하던 QR기능
//    private Button createQRBtn;
//    private Button scanQRBtn;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        createQRBtn = (Button) findViewById(R.id.createQR);
//        scanQRBtn = (Button) findViewById(R.id.scanQR);
//
//        createQRBtn.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this, CreateQR.class);
//                startActivity(intent);
//            }
//        });
//
//        scanQRBtn.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this, ScanQR.class);
//                startActivity(intent);
//            }
//        });
//    }

}