package iti.team.tablia.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import iti.team.tablia.Authentication.ChefRegistration.ChefRegistrationActivity;
import iti.team.tablia.Authentication.CustomerRegistration.CustomerRegistrationActivity;
import iti.team.tablia.R;

public class RegistrationOptionsActivity extends AppCompatActivity implements View.OnClickListener {

  ImageView xChefPhoto, xCustomerPhoto;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration_options);
    xCustomerPhoto = findViewById(R.id.xCustomerPhoto);
    xChefPhoto = findViewById(R.id.xChefPhoto);

    xCustomerPhoto.setOnClickListener(this);
    xChefPhoto.setOnClickListener(this);
  }

  private void navigateToCustomerRegistrationActivity() {
    Intent intent = new Intent(RegistrationOptionsActivity.this, CustomerRegistrationActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
    startActivity(intent);
    //finish();
  }

  private void navigateToChefRegistrationActivity() {
    Intent intent = new Intent(RegistrationOptionsActivity.this, ChefRegistrationActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
    startActivity(intent);
    //finish();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == xCustomerPhoto.getId()) {
      navigateToCustomerRegistrationActivity();
    } else {
      if (v.getId() == xChefPhoto.getId()) {
        navigateToChefRegistrationActivity();
      }
    }
  }
}
