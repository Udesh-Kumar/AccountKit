package com.example.cc.accountkit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.AccountKit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    public static final int APP_REQUEST_CODE = 111;
     Button phonebutton,emailButton;
    TextView tvPhoneNumber, tvEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailButton=findViewById(R.id.email_btn);
        phonebutton=findViewById(R.id.phon_btn);
        tvPhoneNumber=findViewById(R.id.et_phonenumber);
        tvEmail=findViewById(R.id.et_email);

        phonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starLoginPage(LoginType.PHONE);
               // getCurrentAccount();

            }
      });



 emailButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        starLoginPage(LoginType.EMAIL);
    }
});

    }



    private void starLoginPage(LoginType loginType) {

        if (loginType == LoginType.PHONE) {
            final Intent intent = new Intent(MainActivity.this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(
                            LoginType.PHONE,
                            AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
            // ... perform additional configuration ...
            intent.putExtra(
                    AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                    configurationBuilder.build());
            startActivityForResult(intent, APP_REQUEST_CODE);

        }else if (loginType==LoginType.EMAIL)
        {
            final Intent intent = new Intent(MainActivity.this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(
                            LoginType.EMAIL,
                            AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
            // ... perform additional configuration ...
            intent.putExtra(
                    AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                    configurationBuilder.build());
            startActivityForResult(intent, APP_REQUEST_CODE);

        }

    }



    @Override
    protected void onActivityResult(
             int requestCode,
             int resultCode,
             Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;

            if (loginResult.getError() != null) {

                toastMessage = loginResult.getError().getErrorType().getMessage();
                return;


            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                return;


            } else {

                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    startActivity(new Intent(MainActivity.this, SingenInActivity.class));

                } else {

                        toastMessage = String.format("Success:%s...", loginResult.getAuthorizationCode().substring(0, 10));
                              // startActivity(new Intent(MainActivity.this, SingenInActivity.class));
                        Log.d("onActivityResult: ", String.valueOf(loginResult.getAccessToken()));

                        //startActivity(new Intent(MainActivity.this, SingenInActivity.class));

                        // If you have an authorization code, retrieve it from
                        // loginResult.getAuthorizationCode()
                        // and pass it to your server and exchange it for an access token.

                        // Success! Start your next activity...
                     //   Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
                    }



              //  startActivity(new Intent(MainActivity.this, SingenInActivity.class));
            // Surface the result to your user in an appropriate way.

        }

   }




    }




}

