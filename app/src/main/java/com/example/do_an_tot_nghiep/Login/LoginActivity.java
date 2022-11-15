package com.example.do_an_tot_nghiep.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.do_an_tot_nghiep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "login";
    private EditText txtPhoneNumber;
    private AppCompatButton btnGetVerificationCode;
    private ImageButton btnGoogleLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupComponent();
        setupEvent();
    }

    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     */
    private void setupComponent()
    {
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnGetVerificationCode = findViewById(R.id.btnGetVerificationCode);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("vi");
    }


    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     */
    private void setupEvent()
    {
        /*BUTTON GET CONFIRM CODE*/
        btnGetVerificationCode.setOnClickListener(view -> {
            String phoneNumber = txtPhoneNumber.getText().toString();

            /*Step 1 - verify input */
            if(TextUtils.isEmpty(phoneNumber) )
            {
                Toast.makeText(this, R.string.do_not_let_phone_number_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            if(phoneNumber.length() == 10)
            {
                Toast.makeText(this, R.string.only_enter_number_except_first_zero, Toast.LENGTH_SHORT).show();
                return;
            }
            String phoneNumberFormatted = "+84" + phoneNumber;

            /*Step 2 - setup phone auth options*/
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneNumberFormatted)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                            // This callback will be invoked in two situations:
                                            // 1 - Instant verification. In some cases the phone number can be instantly
                                            //     verified without needing to send or enter a verification code.
                                            // 2 - Auto-retrieval. On some devices Google Play services can automatically
                                            //     detect the incoming verification SMS and perform verification without
                                            //     user action.
                                            //Log.d(TAG, "onVerificationCompleted:" + credential);

                                            signInWithPhoneAuthCredential(credential);
                                        }



                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            // This callback is invoked in an invalid request for verification is made,
                                            // for instance if the the phone number format is not valid.

                                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                                // Invalid request
                                            } else if (e instanceof FirebaseTooManyRequestsException) {
                                                // The SMS quota for the project has been exceeded
                                            }

                                            Toast.makeText(LoginActivity.this, "Verification  Failed !", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "error: " + e.getMessage() );
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId,
                                                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                            // The SMS verification code has been sent to the provided phone number, we
                                            // now need to ask the user to enter the code and then construct a credential
                                            // by combining the code with a verification ID.

                                            Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                                            intent.putExtra("verificationId", verificationId);
                                            intent.putExtra("phoneNumber", phoneNumberFormatted);
                                            startActivity(intent);
                                        }

                                    }
                            )          // OnVerificationStateChangedCallbacks
                            .build();/*end Step 2*/

            /*Step 3 - setup phone auth provider*/
            PhoneAuthProvider.verifyPhoneNumber(options);
        });/*end BUTTON GET CONFIRM CODE*/



        /*BUTTON GOOGLE LOGIN*/
        btnGoogleLogin.setOnClickListener(view->{
            Toast.makeText(this, "Login with Google Account", Toast.LENGTH_SHORT).show();
        });/*end BUTTON GOOGLE LOGIN*/
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

                            Toast.makeText(LoginActivity.this, "done !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(LoginActivity.this, "exception", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "error: " + task.getException() );
                            }
                        }
                    }
                });
    }//** end signInWithPhoneAuthCredential
}