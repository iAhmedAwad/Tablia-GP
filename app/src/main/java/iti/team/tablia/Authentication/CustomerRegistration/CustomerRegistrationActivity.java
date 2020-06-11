package iti.team.tablia.Authentication.CustomerRegistration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import iti.team.tablia.Authentication.LoginActivity;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;

public class CustomerRegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private Database database;

    //widgets
    private EditText mEmail, mName, mPassword, mConfirmPassword, mUsername;
    private Button mRegister;
    private ProgressBar mProgressBar;

    //vars
    private Context mContext;
    private String email, name, password, username;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registeration);
        mRegister = findViewById(R.id.xChefBtnRegister);
        mEmail = findViewById(R.id.xCustomerEmail);
        mPassword = findViewById(R.id.xCustomerPassword);
        mUsername = findViewById(R.id.xCustomerUsername);
        mConfirmPassword = findViewById(R.id.xCustomerConfirmPassword);
        mName = findViewById(R.id.xCustomrRegName);
        mContext = CustomerRegistrationActivity.this;
        mUser = new User();

        initProgressBar();
        setupFirebaseAuth();
        init();

        hideSoftKeyboard();
    }

    private void init() {


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mEmail.getText().toString();
                name = mName.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();

                if (checkInputs(email, name, username, password, mConfirmPassword.getText().toString())) {
                    if (doStringsMatch(password, mConfirmPassword.getText().toString())) {
                        registerNewEmail(email, password);
                    } else {
                        if (Locale.getDefault().getLanguage().equals("ar")) {
                            Toast.makeText(mContext, "لم تتطابق كلمة السر", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, "passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (Locale.getDefault().getLanguage().equals("ar")) {
                        Toast.makeText(mContext, "يجب ملء جميع الحقول", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    /**
     * Return true if @param 's1' matches @param 's2'
     *
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2) {
        return s1.equals(s2);
    }


    /**
     * Checks all the input fields for null
     *
     * @param email
     * @param username
     * @param password
     * @return
     */
    private boolean checkInputs(String email, String fullName, String username, String password, String confirmPassword) {
        if (email.equals("") || fullName.equals("") || password.equals("") || username.equals("") || confirmPassword.equals("")) {
            if (Locale.getDefault().getLanguage().equals("ar")) {
                Toast.makeText(mContext, "يجب ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "All fields must be filled", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }


    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initProgressBar() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

      /*
    ---------------------------Firebase-----------------------------------------
     */

    /**
     * Setup the Authentication basics:
     * Authentication listener which is invoked whenever a changes
     * occurs in the authentication
     */
    private void setupFirebaseAuth() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is authenticated
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (checkIfUsernameExists(username, dataSnapshot)) {
                                Log.d(TAG, "onDataChange: Username already exists");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out");

                }
                // ...
            }
        };

    }

    /**
     * Register a new email and password to Firebase Authentication
     *
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, final String password) {

        showProgressBar();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //send email verificaiton
                            sendVerificationEmail();

                            //add user details to firebase database
                            addNewCustomer();
                        }
                        if (!task.isSuccessful()) {
                            if (Locale.getDefault().getLanguage().equals("ar")) {
                                Toast.makeText(mContext, "هذا البريد الإلكتروني مسجل بالفعل", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Someone with that email already exists", Toast.LENGTH_SHORT).show();
                            }
                            hideProgressBar();

                        }
                        hideProgressBar();
                        // ...
                    }
                });
    }

    /**
     * Adds data to the node: "users"
     */
    public void addNewCustomer() {

        database = new Database(this);
        CustomerAccountSettings settings = new CustomerAccountSettings();
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d(TAG, "addNewUser: Adding new User: \n user_id:" + userid);

        settings.setDisplayName(name);
        settings.setUserName(username);
        mUser.setFullName(name);
        mUser.setUsername(username);
        mUser.setEmail(email);
        mUser.setUser_id(userid);
        mUser.setType(this.getString(R.string.typeCustomer));
        database.addCustomerToDatabase(new CustomerSettings(mUser, settings));
        FirebaseAuth.getInstance().signOut();
        redirectLoginScreen();
    }

    /**
     * sends an email verification link to the user
     */
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            } else {
                                if (Locale.getDefault().getLanguage().equals("ar")) {
                                    Toast.makeText(mContext, "لم يتم إرسال رسالة التأكيد", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "couldn't send email", Toast.LENGTH_SHORT).show();
                                }
                                hideProgressBar();
                            }
                        }
                    });
        }

    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen() {
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(CustomerRegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkIfUsernameExists: ");
        User user = new User();
        for (DataSnapshot ds : dataSnapshot.child(CustomerRegistrationActivity.this.getString(R.string.usersNode)).getChildren()) {
            user.setUsername(ds.getValue(User.class).getUsername());
            if (user.getUsername().equals(username)) {
                Log.d(TAG, "Checking if username exists!" + user.getUsername());

                return true;
            }
        }
        return false;
    }

}

