package iti.team.tablia.CustomerAccount.Filter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import iti.team.tablia.R;

public class MultipleChoicesDialogFragment extends DialogFragment {
  onMultiChoiceListener mListener;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    try {
      mListener = (onMultiChoiceListener) context;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    final ArrayList<String> selectedItems = new ArrayList<>();
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final String[] list = getActivity().getResources().getStringArray(R.array.Spinner_Item);
    builder.setTitle("Select your choices")
        .setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            if (isChecked) {
              selectedItems.add(list[which]);
            } else {
              selectedItems.remove(list[which]);
            }
          }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

        mListener.onPositiveButtonClicked(list, selectedItems);
      }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        mListener.onNegativeButtonClicked();
      }
    });

    return builder.create();
  }

  public interface onMultiChoiceListener {

    void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItems);

    void onNegativeButtonClicked();
  }
}
