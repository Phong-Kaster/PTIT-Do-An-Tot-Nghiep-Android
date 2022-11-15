package com.example.do_an_tot_nghiep.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.do_an_tot_nghiep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerificationActivity extends AppCompatActivity {

    private final String TAG = "Verification-Activity";
    private String phoneNumber;
    private String verificationId;


    private EditText txtVerificationCode;
    private AppCompatButton btnConfirm;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        getVariable();
        setupComponent();
        setupEvent();
    }

    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     */
    private void getVariable()
    {
        verificationId = getIntent().getStringExtra("verificationId");
        phoneNumber = getIntent().getStringExtra("phoneNumber");


        if(TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(verificationId) )
        {
            Toast.makeText(this, R.string.empty_phone_number_or_verificationId, Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     */
    private void setupComponent()
    {
        txtVerificationCode = findViewById(R.id.txtVerificationCode);
        btnConfirm = findViewById(R.id.btnConfirm);

        firebaseAuth = FirebaseAuth.getInstance();
    }


    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     */
    private void setupEvent()
    {
        btnConfirm.setOnClickListener(view->{
            /*Step 1 - get verificationCode and create credential*/
            String verificationCode = txtVerificationCode.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);


            /*Step 2 - verify and go ahead*/
            signInWithPhoneAuthCredential(credential);
        });
    }


    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = task.getResult().getUser();

                            Log.d(TAG,"Firebase User: " + user);
                            assert user != null;
                            Log.d(TAG,"Firebase User - getTenantId: " + user.getTenantId());
                            Log.d(TAG,"Firebase User - getProviderId: " + user.getProviderId());
                            Log.d(TAG,"Firebase User - getUid: " + user.getUid());
                            Log.d(TAG,"Firebase User - getPhoneNumber: " + user.getPhoneNumber());
                            Log.d(TAG,"Firebase User - getPhoneNumber: " + user.getDisplayName());

                            Toast.makeText(VerificationActivity.this, "done !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerificationActivity.this, "exception", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "error: " + task.getException() );
                            }
                        }
                    }
                });
    }//** end signInWithPhoneAuthCredential
}