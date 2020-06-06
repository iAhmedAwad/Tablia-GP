package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import iti.team.tablia.CustomerAccount.Profile.EditProfile.CustomerEditProfileFragment;
import iti.team.tablia.R;

public class EditCustProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cust_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        if (savedInstanceState == null) {

            CustomerEditProfileFragment editProfileFragment = new CustomerEditProfileFragment();

            editProfileFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, editProfileFragment).commit();
        }
    }
}
