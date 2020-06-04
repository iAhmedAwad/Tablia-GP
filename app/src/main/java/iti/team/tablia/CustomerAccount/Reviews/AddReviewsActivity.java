package iti.team.tablia.CustomerAccount.Reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.R;
import iti.team.tablia.others.Database;


/**
 * This activity has to be started by putting Item ID and Chef ID strings to the starting intent.
 */
public class AddReviewsActivity extends AppCompatActivity {

  //constants
  public static final String INCOMING_ITEM_ID = "item_id";
  public static final String INCOMING_CHEF_ID = "chef_id";
    public static final String INCOMING_ITEM_NAME = "item_name";
  //Views
  private RatingBar xRatingBar;
  private EditText xReviewText;
  private Button xSubmitReview;
  //ViewModel
  private AddReviewsActivityViewModel mViewModel;
  //vars
  private Review mReview;
  //Database
  private Database mDatabase;
  private String mITEM_id;
  private String mCHEF_id;
  private String mITEM_name;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_reviews);

    //Hooks
    xRatingBar = findViewById(R.id.xRatingBar);
    xReviewText = findViewById(R.id.xReviewText);
    xSubmitReview = findViewById(R.id.xSubmitReview);
    //Database
    mDatabase = new Database(this);
    mViewModel = new ViewModelProvider(this).get(AddReviewsActivityViewModel.class);

    getIncomingIntent();
    init();
    initButton();
    hideSoftKeyboard();

  }

  private void getIncomingIntent() {
    Intent intent = getIntent();
    mITEM_id = intent.getStringExtra(INCOMING_ITEM_ID);
    mCHEF_id = intent.getStringExtra(INCOMING_CHEF_ID);
    mITEM_name = intent.getStringExtra(INCOMING_ITEM_NAME);
  }

  private void init() {

    mViewModel.getReview(mITEM_id)
        .observe(this, new Observer<Review>() {
          @Override
          public void onChanged(Review review) {
            mReview = new Review(review);
            xRatingBar.setRating(review.getRating());
            xReviewText.setText(review.getReviewText());

          }
        });
  }

  private void initButton() {
    //TODO here, you gotta complete the review object!

    xSubmitReview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        float rating = xRatingBar.getRating();
        final String reviewText = xReviewText.getText().toString();

        if (mReview == null) {
          mReview = new Review();
          mReview.setItemId(mITEM_id);
          mReview.setChefId(mCHEF_id);
          mReview.setItemName(mITEM_name);
          mReview.setRating(0);
        }
        mReview.setReviewText(reviewText);
//        if(rating == Float.parseFloat(null)){
//          rating =0.0f;
//        }
        mReview.setRating(rating);
        mDatabase.addReview(mReview);
        finish();
      }
    });

  }

  private void hideSoftKeyboard() {
    this.getWindow().
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
}
