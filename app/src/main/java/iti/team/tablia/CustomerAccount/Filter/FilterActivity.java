package iti.team.tablia.CustomerAccount.Filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import iti.team.tablia.R;

public class FilterActivity extends AppCompatActivity implements MultipleChoicesDialogFragment.onMultiChoiceListener {

  private Button xSelectCategories, xApply;
  private TextView xTextView;
  private String choices;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    xSelectCategories = findViewById(R.id.xSelectCategories);
    xApply = findViewById(R.id.xApply);
    xTextView = findViewById(R.id.xTextView);
    initButtons();

  }

  private void initButtons() {
    xSelectCategories.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        DialogFragment mDialogFragment = new MultipleChoicesDialogFragment();
        mDialogFragment.setCancelable(false);
        mDialogFragment.show(getSupportFragmentManager(), "Dialog Manager");
      }
    });
    xApply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Log.d("Apply", "Apply button clicked!");
        Intent intent = new Intent(FilterActivity.this, FilteredDataActivity.class);
        intent.putExtra(FilteredDataActivity.INCOMING_CATEGORIES, choices);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItems) {

    if (selectedItems.isEmpty()) {
      Toast.makeText(this,
          "Nothing was selected",
          Toast.LENGTH_SHORT).show();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      //stringBuilder.append("Selected items: ");
      for (String str : selectedItems) {
        stringBuilder.append(str + ",");
      }
      String str = stringBuilder.toString();
      choices = (str.substring(0, str.length() - 1));

    }
  }

  @Override
  public void onNegativeButtonClicked() {
    Toast.makeText(this,
        "Nothing was selected",
        Toast.LENGTH_SHORT).show();
  }
}