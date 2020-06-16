package iti.team.tablia.CustomerAccount.Filter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;


import java.util.ArrayList;

import io.apptik.widget.MultiSlider;
import iti.team.tablia.R;

public class FilterActivity extends AppCompatActivity implements MultipleChoicesDialogFragment.onMultiChoiceListener {

  private Button xSelectCategories, xApply;
  private TextView xPriceTextView;
  private String choices;
  private MultiSlider xPriceRange;
  private final double min = 10.0;
  private final double max = 1000.0;
  private double minValue = 10;
  private double maxValue = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        finish();
      }
    });
    xSelectCategories = findViewById(R.id.xSelectCategories);
    xApply = findViewById(R.id.xApply);

    initButtons();
    initPriceBar(max, min);

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
        intent.putExtra(FilteredDataActivity.INCOMING_MIN, minValue);
        intent.putExtra(FilteredDataActivity.INCOMING_MAX, maxValue);

        //Log.d("filterx", "Intent is out having min and max of"+ min +" & "+ max);

        startActivity(intent);
      }
    });
  }

  private void initPriceBar(double max, double min){

    xPriceRange = findViewById(R.id.xPriceRange);
    xPriceTextView = findViewById(R.id.xPriceTextView);
    xPriceRange.setMin((int) min);
    xPriceRange.setMax((int) max);
    xPriceRange.clearThumbs();
    xPriceRange.addThumbOnPos(0, (int) min);
    xPriceRange.addThumbOnPos(1, (int) max);

    xPriceRange.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
      @Override
      public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {

        if (thumbIndex==0) {
          minValue = value;
        } else {
          maxValue = value;
        }

        xPriceTextView.setTextSize(14);
        xPriceTextView.setText(minValue+ " - " + maxValue);
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