package kosmo.hdpay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class Login extends AppCompatActivity {

    Button loginBtn;
    EditText mem_email, mem_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        setTitle("ORACLE");

        loginBtn = findViewById(R.id.loginBtn);
        mem_email = findViewById(R.id.mem_email);
        mem_pwd = findViewById(R.id.mem_pwd);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    Toast.makeText(Login.this,"버튼눌림",Toast.LENGTH_SHORT).show();
                    String result;
                    String email = mem_email.getText().toString();
                    String pw = mem_pwd.getText().toString();

                    //System.out.println("id:"+id+",pw:"+pw);

                    RegisterActivity task = new RegisterActivity();
                    result = task.execute(email, pw).get();
                    System.out.println("result:"+result);

                    if(result.equals("login success")){
                        System.out.println("성공");
                        //Intent intent=new Intent(LoginDB.this,MainPage.class);
                        Intent intent = new Intent(getApplicationContext(),MainPage.class);
                        startActivity(intent);
                    }else{
                        System.out.println("실패");
                        Toast.makeText(getApplicationContext(),"Email과 PW과 맞지 않습니다. 확인후 로그인해주세요",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });
    }
}