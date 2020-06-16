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
import java.util.Locale;

import iti.team.tablia.R;
import iti.team.tablia.util.Constants;

public class MultipleChoicesDialogFragment extends DialogFragment {
    onMultiChoiceListener mListener;
    private String select;
    private String ok;
    private String cancel;

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
        if (Locale.getDefault().getLanguage().equals("ar")){
          select = "إختر من القائمة";
          ok="موافق";
          cancel="إلغاء";
        }else {
          select = "Select your choices";
          ok="Ok";
          cancel="Cancel";
        }
        final String[] list = getActivity().getResources().getStringArray(R.array.Spinner_Item);
        builder.setTitle(select)
                .setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String cat = getCategory(list[which]);
                        if (isChecked) {
                            selectedItems.add(list[which]);
                            selectedItems.add(cat);
                        } else {
                            selectedItems.remove(list[which]);
                            selectedItems.remove(cat);
                        }
                    }
                }).setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mListener.onPositiveButtonClicked(list, selectedItems);
            }
        }).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
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

    private String getCategory(String category) {
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("en")) {
            if (category.equals(Constants.BACKING)) {
                category = Constants.BACKING_AR;
            } else if (category.equals(Constants.DESSERT)) {
                category = Constants.DESSERT_AR;
            } else if (category.equals(Constants.GRILLED)) {
                category = Constants.GRILLED_AR;
            } else if (category.equals(Constants.JUICE)) {
                category = Constants.JUICE_AR;
            } else if (category.equals(Constants.MACARONI)) {
                category = Constants.MACARONI_AR;
            } else if (category.equals(Constants.MAHASHY)) {
                category = Constants.MAHASHY_AR;
            } else if (category.equals(Constants.MAIN_DISHES)) {
                category = Constants.MAIN_DISHES_AR;
            } else if (category.equals(Constants.SALAD)) {
                category = Constants.SALAD_AR;
            } else if (category.equals(Constants.SEAFOOD)) {
                category = Constants.SEAFOOD_AR;
            } else if (category.equals(Constants.SIDE_DISHES)) {
                category = Constants.SIDE_DISHES_AR;
            } else if (category.equals(Constants.SOUPS)) {
                category = Constants.SOUPS_AR;
            }
        } else {
            if (category.equals(Constants.BACKING_AR)) {
                category = Constants.BACKING;
            } else if (category.equals(Constants.DESSERT_AR)) {
                category = Constants.DESSERT;
            } else if (category.equals(Constants.GRILLED_AR)) {
                category = Constants.GRILLED;
            } else if (category.equals(Constants.JUICE_AR)) {
                category = Constants.JUICE;
            } else if (category.equals(Constants.MACARONI_AR)) {
                category = Constants.MACARONI;
            } else if (category.equals(Constants.MAHASHY_AR)) {
                category = Constants.MAHASHY;
            } else if (category.equals(Constants.MAIN_DISHES_AR)) {
                category = Constants.MAIN_DISHES;
            } else if (category.equals(Constants.SALAD_AR)) {
                category = Constants.SALAD;
            } else if (category.equals(Constants.SEAFOOD_AR)) {
                category = Constants.SEAFOOD;
            } else if (category.equals(Constants.SIDE_DISHES_AR)) {
                category = Constants.SIDE_DISHES;
            } else if (category.equals(Constants.SOUPS_AR)) {
                category = Constants.SOUPS;
            }
        }
        return category;
    }

}
