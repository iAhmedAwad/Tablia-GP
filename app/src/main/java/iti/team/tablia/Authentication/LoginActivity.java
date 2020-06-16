package iti.team.tablia.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import iti.team.tablia.ChefHome.ChiefActivity;
import iti.team.tablia.CustomerAccount.CustomerActivity.CustomerActivity;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;
import iti.team.tablia.util.Constants;
import iti.team.tablia.util.Permissions;

public class LoginActivity extends AppCompatActivity {

  //constants
  public static final int VERIFY_PERMISSIONS_REQUEST = 1;
  private static final String TAG = "LoginActivity";
  //Firebase
  private FirebaseAuth.AuthStateListener mAuthListener;
  private FirebaseAuth mAuth;
  private FirebaseDatabase mFirebaseDatabase;
  private DatabaseReference mDatabaseReference;
  private Database database;
  //widgets
  private TextView mRegister;
  private EditText mEmail, mPassword;
  private Button mLogin;
  private ProgressBar mProgressBar;
  private ImageView mLogo;
  private ValueEventListener eventListener;
  private SharedPreferences sharedPref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mRegister = findViewById(R.id.link_register);
    mEmail = findViewById(R.id.xChefEmail);
    mPassword = findViewById(R.id.xChefPassword);
    mLogin = findViewById(R.id.btn_login);
    //mLogo = findViewById(R.id.logo);
    //vars
    sharedPref = getSharedPreferences(Constants.isLogged, MODE_PRIVATE);

    if (Permissions.checkPermissionsArray(this, Permissions.PERMISSIONS)) {

    } else {
      Permissions.verifyPermissions(this, Permissions.PERMISSIONS);
    }

    boolean isLogged = sharedPref.getBoolean(Constants.isLogged, false);

    if (isLogged) {
      User user = new User();
      String type = sharedPref.getString(Constants.userType, "customer");
      String fullName = sharedPref.getString(Constants.fullName, "Customer");
      user.setType(type);
      if (Locale.getDefault().getLanguage().equals("ar")){
        Toast.makeText(LoginActivity.this, "مرحبا, " +
                fullName, Toast.LENGTH_SHORT).show();
      }else {
        Toast.makeText(LoginActivity.this, "Welcome, " +
                fullName, Toast.LENGTH_SHORT).show();
      }

      navigateToChosenActivity(user);

    } else {
      setupFirebaseAuth();
      initProgressBar();
      init();
    }


  }


  private void init() {
    mLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mAuth = FirebaseAuth.getInstance();
        //check if the fields are filled out
        if (!isEmpty(mEmail.getText().toString())
            && !isEmpty(mPassword.getText().toString())) {
          showProgressBar();
          mAuth.signInWithEmailAndPassword(mEmail.getText().toString(),
              mPassword.getText().toString())
              .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                FirebaseUser user = mAuth.getCurrentUser();

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (!task.isSuccessful()) {
                    if (Locale.getDefault().getLanguage().equals("ar")){
                      Toast.makeText(LoginActivity.this, "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show();

                    }else {
                      Toast.makeText(LoginActivity.this, "Failed to authenticate", Toast.LENGTH_SHORT).show();
                    }
                    mProgressBar.setVisibility(View.GONE);
                  } else {
                    try {
                      if (user.isEmailVerified()) {
//                                                Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
//                                                startActivity(intent);
                        //Triaaaaaaaaaaaaaaal
                        setupFirebaseAuth();
                      } else {
                        if (Locale.getDefault().getLanguage().equals("ar")){
                          Toast.makeText(LoginActivity.this, "لم يتم تأكيد حسابك \n برجاء مراجعة البريد الوارد", Toast.LENGTH_SHORT).show();

                        }else {
                          Toast.makeText(LoginActivity.this, "Email is not yet verified \n Check your inbox", Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                        mAuth.signOut();
                      }
                    } catch (NullPointerException e) {
                    }
                  }
                }
              });

        } else {
          if (Locale.getDefault().getLanguage().equals("ar")){
            Toast.makeText(LoginActivity.this, "برجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show();

          }else {
            Toast.makeText(LoginActivity.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });

    mRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationOptionsActivity.class);
        startActivity(intent);
      }
    });


    hideSoftKeyboard();
  }

  /**
   * Return true if the @param is null
   *
   * @param string
   * @return
   */
  private boolean isEmpty(String string) {
    return string.equals("");
  }

  /**
   * Progressbar methods: initialization, show and hide
   */
  private void initProgressBar() {
    mProgressBar = findViewById(R.id.progressBar);
    mProgressBar.setVisibility(View.INVISIBLE);
  }

  private void showProgressBar() {
    mProgressBar.setVisibility(View.VISIBLE);

  }

  private void hideProgressBar() {
    if (mProgressBar.getVisibility() == View.VISIBLE) {
      mProgressBar.setVisibility(View.INVISIBLE);
    }
  }

  private void hideSoftKeyboard() {
    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }

  private boolean isNetworkConnected() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
  }

  private void navigateToChosenActivity(User user) {
//        mDatabaseReference.removeEventListener(eventListener);
    if (user.getType().equals(this.getString(R.string.typeChef))) {

      Intent intent = new Intent(LoginActivity.this, ChiefActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
      finish();
    } else {
      if (user.getType().equals(this.getString(R.string.typeCustomer))) {
        Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
      }
    }
  }

  /*
      ----------------------------- Firebase setup ---------------------------------
   */
  private void setupFirebaseAuth() {
    mFirebaseDatabase = FirebaseDatabase.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

          //check if email is verified
          if (user.isEmailVerified()) {

            database = new Database(LoginActivity.this);
            mDatabaseReference = mFirebaseDatabase.getReference("users")
                .child(user.getUid());
            eventListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //retrieve user information from database

                User user = database.retrieveUserFromDatabase(dataSnapshot);
                if (Locale.getDefault().getLanguage().equals("ar")){
                  Toast.makeText(LoginActivity.this, "مرحبا, " +
                          user.getFullName(), Toast.LENGTH_SHORT).show();
                }else {
                  Toast.makeText(LoginActivity.this, "Welcome, " +
                          user.getFullName(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(LoginActivity.this, "Welcome, " +
                    user.getFullName(), Toast.LENGTH_SHORT).show();

                //Add the user type to shared preferences
                SharedPreferences sharedPref = getSharedPreferences(Constants.isLogged, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(Constants.isLogged, true);
                editor.putString(Constants.userType, user.getType());
                editor.putString(Constants.fullName, user.getFullName());

                editor.commit();
                //Sorry ya Huda, had to move it here
                mDatabaseReference.removeEventListener(eventListener);
                navigateToChosenActivity(user);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
            });

            //
                        /*
                        Intent intent = new Intent(LoginActivity.this, CustomerProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                         */

          } else {
            if (Locale.getDefault().getLanguage().equals("ar")){
              Toast.makeText(LoginActivity.this, "لم يتم تأكيد حسابك \n برجاء مراجعة البريد الوارد", Toast.LENGTH_SHORT).show();

            }else {
              Toast.makeText(LoginActivity.this, "Email is not yet verified \n Check your inbox", Toast.LENGTH_SHORT).show();
            }
            FirebaseAuth.getInstance().signOut();
            hideProgressBar();
          }

        } else {
        }
        // ...
      }
    };


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
}