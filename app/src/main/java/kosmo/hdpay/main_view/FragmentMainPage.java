package kosmo.hdpay.main_view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import kosmo.hdpay.database.DepositMethod;
import kosmo.hdpay.R;
import kosmo.hdpay.session.SessionManager;


public class FragmentMainPage extends Fragment {

    TextView mem_name,totalBalance;
    Button logoutBtn;

    SessionManager sessionManager;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mainpage, container, false);
        DepositMethod depositMethod = new DepositMethod();

        mem_name = view.findViewById(R.id.mem_name);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        totalBalance = view.findViewById(R.id.totalBalance);

        // Initialize session manager
        sessionManager = new SessionManager(getContext());
        String sMem_name = sessionManager.getMemname();
        System.out.println("sMem_name: "+sMem_name);
        mem_name.setText(sMem_name);
        totalBalance.setText(depositMethod.numberChange(depositMethod.getBalance(getContext()))+"원");
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
                        startActivity(new Intent(getContext()
                                , LoginActivity.class));
                        // Finish activity
                        try {
                            finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
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
        return view;
    }



}
