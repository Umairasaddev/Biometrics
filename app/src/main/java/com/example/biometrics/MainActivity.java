package com.example.biometrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    Button btn_fp;
    TextView tvAuthStatus;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    tvAuthStatus = findViewById(R.id.tvAuthStatus);
        btn_fp = findViewById(R.id.btn_fp);

    executor = ContextCompat.getMainExecutor(this);
    biometricPrompt = new androidx.biometric.BiometricPrompt(MainActivity.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);

            //error authentication,stop  tasks that requires auth
       tvAuthStatus.setText("Authentication Error :"+errString);
            Toast.makeText(MainActivity.this, "Authentication Error :"+errString, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);


            //authenticaiton  suceded continues task that requires authenticaion
            tvAuthStatus.setText("Authentication suceed");
            Toast.makeText(MainActivity.this, "Authentication Suceeded", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();

            //failed authentication stop tasks that requires auth
            tvAuthStatus.setText("Authentication Failed");
            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }
    });



//setup title description on auth dialog

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Aithentication")
                        .setSubtitle("Login using fingerprint authentication")
                                .setNegativeButtonText("User App Password")
                                        .build();
    btn_fp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //show auth dialog
            biometricPrompt.authenticate(promptInfo);
        }
    });
    }
}