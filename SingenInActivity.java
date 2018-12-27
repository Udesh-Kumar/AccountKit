package com.example.cc.accountkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

public class SingenInActivity extends AppCompatActivity {
    Button signout;
   TextView tvPhoneNumber, tvEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singen_in);

        signout=findViewById(R.id.btn_logout);
        tvPhoneNumber=(TextView)findViewById(R.id.et_phonenumber);
        tvEmail=(TextView)findViewById(R.id.et_email);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();

                tvPhoneNumber.setText(" ");
                tvEmail.setText(" ");
            }
        });


    }



    @Override
    protected void onPostResume() { //onResume Method me hi hoga
        super.onPostResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {

                TextView tvPhoneNumber, tvEmail;

                tvPhoneNumber=(TextView)findViewById(R.id.et_phonenumber);
                tvEmail=(TextView)findViewById(R.id.et_email);

                if(account.getPhoneNumber()!=null) {
                     // Get phone number
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = phoneNumber.toString();
                        tvPhoneNumber.setText(phoneNumberString);
                    }

                    if(account.getEmail()!=null)
                        Log.e("Email",account.getEmail());




            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }

}
