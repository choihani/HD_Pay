package kosmo.hdpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    TextView mem_name;
    Button logoutBtn;

    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        // Assign variable
        mem_name = findViewById(R.id.mem_name);
        logoutBtn = findViewById(R.id.logoutBtn);

        // Initialize session manager
        sessionManager = new SessionManager(getApplicationContext());

        // Get username from session
        String sMem_name = sessionManager.getMemname();

        // Set username on TextView
        mem_name.setText(sMem_name);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                // Set title
                builder.setTitle("로그아웃");
                // Set message
                builder.setMessage("로그아웃 하십니까?");
                // Set positive button
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Set login false
                        sessionManager.setLogin(false);
                        // Set username empty
                        sessionManager.removeSession();
                        //Redirect activity
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        // Finish activity
                        finish();
                    }
                });

                // Set negative button
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                // Initialize alert dialog
                AlertDialog alertDialog = builder.create();
                // Show alert dialog
                alertDialog.show();
            }
        });
    }
}
