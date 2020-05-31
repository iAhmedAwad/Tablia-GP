package iti.team.tablia.CustomerAccount.Reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 *
 * */
public class AddReviewsActivity extends AppCompatActivity {

  //constants
  public static final String INCOMING_ITEM_ID = "item_id";
  public static final String INCOMING_CHEF_ID = "chef_id";
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
    mViewModel =  new ViewModelProvider(this).get(AddReviewsActivityViewModel.class);

    getIncomingIntent();
    init();
    initButton();

  }

  private void getIncomingIntent(){
    Intent intent =  getIntent();
    mITEM_id = intent.getStringExtra(INCOMING_ITEM_ID);
    mCHEF_id = intent.getStringExtra(INCOMING_CHEF_ID);
  }

  private void init(){

    mViewModel.getReview(mITEM_id)
        .observe(this, new Observer<Review>() {
      @Override
      public void onChanged(Review review) {
        if(review != null && review.getCustomerId() != null){
          mReview = new Review(review);
        xRatingBar.setRating(review.getRating());
        xReviewText.setText(review.getReviewText());
        }
      }
    });
  }
  private void initButton(){
    //TODO here, you gotta complete the review object!

    xSubmitReview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
    final float rating = xRatingBar.getRating();
    final String reviewText = xReviewText.getText().toString();

    if(mReview == null){
      mReview = new Review();
    }
        mReview.setItemId(mITEM_id);
        mReview.setChefId(mCHEF_id);
        mReview.setReviewText(reviewText);
        mReview.setRating(rating);
        mDatabase.addReview(mReview);
      }
    });

  }
}
