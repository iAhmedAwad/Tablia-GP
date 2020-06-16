package iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails.Rating;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

import iti.team.tablia.CustomerAccount.MyOrders.CustOrderDetails.CustOrderDetails;
import iti.team.tablia.R;

public class RatingDialog extends AppCompatDialogFragment {
    private RatingBar ratingBar;
    private Button submit,later;
    private String chefId,custId;
    private DialogViewModel model;
    public RatingDialog(String chefId,String custId) {
        this.chefId = chefId;
        this.custId = custId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view);
        ratingBar = view.findViewById(R.id.chef_rate);
        submit = view.findViewById(R.id.submitRate);
        later = view.findViewById(R.id.submitLater);
        model = ViewModelProviders.of(this).get(DialogViewModel.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.saveCustRating(chefId,custId,ratingBar.getRating());
                RatingDialog.this.dismiss();
            }
        });
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setCancelable(false);
        return  builder.create();
    }
}
