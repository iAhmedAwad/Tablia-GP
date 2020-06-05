package iti.team.tablia.CustomerAccount.CustomerOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import iti.team.tablia.CustomerAccount.Profile.EditProfile.CustomerEditProfileFragment;
import iti.team.tablia.R;

public class EditCustProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cust_profile);
        if (savedInstanceState == null) {

            CustomerEditProfileFragment editProfileFragment = new CustomerEditProfileFragment();

            editProfileFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, editProfileFragment).commit();
        }
    }
}
