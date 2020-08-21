package kosmo.hdpay.main_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kosmo.hdpay.R;
import kosmo.hdpay.depositInquire.DepositAtivity;
import kosmo.hdpay.qr.QRActivity;
import kosmo.hdpay.session.SessionManager;

public class FragmentActivity extends AppCompatActivity {

    TextView mem_name;
    Button logoutBtn;
    SessionManager sessionManager;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentMainPage fragmentMainPage = new FragmentMainPage();
    private FragmentPayList fragmentPayList = new FragmentPayList();
    private FragmentCall fragmentCall = new FragmentCall();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_after_main_page);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentMainPage).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    public void qrpay(View view){
        Intent intent = new Intent(getApplicationContext(), QRActivity.class);
        startActivity(intent);
    }

    public void inquireClick(View view){
        startActivity(new Intent(getApplicationContext()
                , DepositAtivity.class));
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.searchMainPage:
                    transaction.replace(R.id.frameLayout, fragmentMainPage).commitAllowingStateLoss();
                    break;
//                case R.id.cameraItem:
//                    transaction.replace(R.id.frameLayout, fragmentPayList).commitAllowingStateLoss();
//                    break;
//                case R.id.callItem:
//                    transaction.replace(R.id.frameLayout, fragmentCall).commitAllowingStateLoss();
//                    break;
            }
            return true;
        }
    }
}
