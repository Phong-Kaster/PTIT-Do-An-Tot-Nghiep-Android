package com.example.do_an_tot_nghiep.Loginpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "Login Activity";
    private EditText txtPhoneNumber;
    private AppCompatButton btnGetVerificationCode;
    private ImageButton btnGoogleLogin;
    private String phoneNumber;

    // login with phone number
    private FirebaseAuth firebaseAuth;

    //login with google account
    private GoogleSignInOptions googleSignInOption;
    private GoogleSignInClient googleSignInClient;


    private Dialog dialog;
    private LoadingScreen loadingScreen;
    private LoginViewModel viewModel;

    private GlobalVariable globalVariable;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupComponent();
        setupEvent();
        setupViewModel();
    }

    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     * setup component
     */
    private void setupComponent()
    {
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnGetVerificationCode = findViewById(R.id.btnGetVerificationCode);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        globalVariable = (GlobalVariable)this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);

        //login with phone number
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("vi");

        //login with google account
        googleSignInOption = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption);
    }


    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     * setup event
     */
    private void setupEvent()
    {
        /*BUTTON GET CONFIRM CODE*/
        btnGetVerificationCode.setOnClickListener(view -> {
            phoneNumber = txtPhoneNumber.getText().toString();

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

                                            System.out.println(TAG);
                                            System.out.println("onVerificationCompleted");
                                            System.out.println("signInWithPhoneAuthCredential has been called !");
                                            signInWithPhoneAuthCredential(credential);
                                        }



                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            // This callback is invoked in an invalid request for verification is made,
                                            // for instance if the the phone number format is not valid.

                                            Toast.makeText(LoginActivity.this, getString(R.string.verification_failed), Toast.LENGTH_SHORT).show();
                                            System.out.println(TAG);
                                            System.out.println("Error: "+e.getMessage());
                                            System.out.println(e);

                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId,
                                                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                            // The SMS verification code has been sent to the provided phone number, we
                                            // now need to ask the user to enter the code and then construct a credential
                                            // by combining the code with a verification ID.

                                            System.out.println(TAG);
                                            System.out.println("onCodeSent");
                                            System.out.println("phone number: " + phoneNumber);
                                            System.out.println("verification Id: " + verificationId);

                                            Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                                            intent.putExtra("verificationId", verificationId);
                                            intent.putExtra("phoneNumber", phoneNumber);
                                            startActivity(intent);
                                        }


                                        @Override
                                        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                            super.onCodeAutoRetrievalTimeOut(s);
                                            System.out.println(TAG);
                                            System.out.println("onCodeAutoRetrievalTimeOut");
                                            System.out.println(s);
                                        }
                                    }
                            )          // OnVerificationStateChangedCallbacks
                            .build();/*end Step 2*/

            /*Step 3 - setup phone auth provider*/
            PhoneAuthProvider.verifyPhoneNumber(options);
        });/*end BUTTON GET CONFIRM CODE*/



        /*BUTTON GOOGLE LOGIN*/
        btnGoogleLogin.setOnClickListener(view->{
            Log.d(TAG, "BUTTON GOOGLE LOGIN CLICKED");

            Intent intent = googleSignInClient.getSignInIntent();
            startGoogleSignInForResult.launch(intent);
        });/*end BUTTON GOOGLE LOGIN*/
    }


    /**
     * @author Phong-Kaster
     * @since 16-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });

        /*set up dialog*/
        dialog.announce();
        dialog.btnOK.setOnClickListener(view->dialog.close());

        /* *******************************OPTION LOGIN WITH PHONE NUMBER*********************************************/
        viewModel.getLoginWithPhoneResponse().observe(this, loginResponse -> {

            if (loginResponse == null) {
                dialog.show(getString(R.string.attention),
                        getString(R.string.oops_there_is_an_issue),
                        R.drawable.ic_close);
                return;
            }

            int result = loginResponse.getResult();
            String message = loginResponse.getMsg();

            /*Case 1 - login successfully*/
            if( result == 1)
            {
                /*Lay du lieu tu API ra*/
                String token = loginResponse.getAccessToken();
                User user = loginResponse.getData();


                /*Lay du lieu vao Global Variable*/
                globalVariable.setAccessToken( "JWT " + token );
                globalVariable.setAuthUser(user);

                /*luu accessToken vao Shared Reference*/
                sharedPreferences.edit().putString("accessToken", "JWT " + token.trim()).apply();

                /*hien thi thong bao la dang nhap thanh cong*/
                Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
            /*Case 2 - login failed*/
            else
            {
                dialog.show(getString(R.string.attention),
                        message,
                        R.drawable.ic_close);
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();

            }

        });/*end OPTION LOGIN WITH PHONE NUMBER*/


        /* *******************************OPTION LOGIN WITH GOOGLE*********************************************/
        viewModel.getLoginWithGoogleResponse().observe(this, response->{
            int result = response.getResult();
            String message = response.getMsg();

            if( result == 1)
            {
                /*Lay du lieu tu API ra*/
                String token = response.getAccessToken();
                User user = response.getData();


                /*Lay du lieu vao Global Variable*/
                globalVariable.setAccessToken( "JWT " + token );
                globalVariable.setAuthUser(user);

                /*luu accessToken vao Shared Reference*/
                sharedPreferences.edit().putString("accessToken", "JWT " + token.trim()).apply();

                /*hien thi thong bao la dang nhap thanh cong*/
                Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
            else
            {
                dialog.show(getString(R.string.attention),
                        message,
                        R.drawable.ic_close);
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
            }
        });/*end OPTION LOGIN WITH GOOGLE*/
    }


    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     * LOGIN WITH PHONE NUMBER
     * sign in with phone auth credential
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                    {
                        FirebaseUser user = task.getResult().getUser();

                        assert user != null;
                        String phone = "0" + phoneNumber;// append the zero letter in the first position of phone number
                        String password = user.getUid();
                        System.out.println(TAG);
                        System.out.println("phone: " + phone);
                        System.out.println("password: " + password);

                        viewModel.loginWithPhone(phone, password);
                    }
                    else
                    {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "error: " + task.getException() );
                        }
                    }
                });
    }//** end signInWithPhoneAuthCredential


    /**
     * @since 16-11-2022
     * @author Phong-Kaster
     * LOGIN WITH GOOGLE ACCOUNT
     * START SIGN UP ACTIVITY FOR RESULT
     */
    private final ActivityResultLauncher<Intent> startGoogleSignInForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                int statusResult = result.getResultCode();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

                if( statusResult == RESULT_OK)
                {
                    /*Step 1 - get email & password to server to authentication | sign up*/
                    assert account != null;
                    String email = account.getEmail();
                    String password = account.getId();

                    /*Step 2 - login*/
                    viewModel.loginWithGoogle(email, password);
                }
                else
                {
                    Toast.makeText(this, R.string.oops_there_is_an_issue, Toast.LENGTH_SHORT).show();
                }
            });/*end startGoogleSignInForResult*/
}