package kosmo.hdpay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import kosmo.hdpay.vo.MemberVO;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText mem_email, mem_pwd;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        System.out.println("로그인 페이지 입장");

        sessionManager = new SessionManager(getApplicationContext());
        // Assign Variable
        mem_email = findViewById(R.id.mem_email);
        mem_pwd = findViewById(R.id.mem_pwd);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                System.out.println("mem_email:"+mem_email+",mem_pwd"+mem_pwd);
                String sMem_email = mem_email.getText().toString().trim();
                String sMem_pwd = mem_pwd.getText().toString().trim();
                String result;
                HD_Connection task = new HD_Connection();

                System.out.println("여기까지 가능");
                try {
                    result = task.execute("hdpaylogin","4","mem_email",sMem_email,"mem_pwd", sMem_pwd).get();
                    System.out.println("result:"+result);

                    Gson gson = new Gson();
                    System.out.println("gson : "+gson);
                    MemberVO member = gson.fromJson(result, MemberVO.class);
                    System.out.println("member : "+member);
                    if(member != null){
                        Toast.makeText(getApplicationContext(),"로그인 성공!",Toast.LENGTH_SHORT).show();
                        sessionManager.setLogin(true);
                        sessionManager.saveSession(member);
                        startActivity(new Intent(getApplicationContext(),
                            FragmentActivity.class));
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"Email과 PW과 맞지 않습니다. 확인후 로그인해주세요",Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                System.out.println("지문로그인 성공 : " + result +" : "+ result.getClass().getSimpleName());
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        Button biometricLoginButton = findViewById(R.id.biometric_login);
        biometricLoginButton.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });

        // 재접속시 세션을 유지하고 있다면 자동 로그인
        if(sessionManager.getLogin()){
            startActivity(new Intent(getApplicationContext()
                    ,FragmentActivity.class));
        }
    }
}
