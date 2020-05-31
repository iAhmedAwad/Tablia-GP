package iti.team.tablia.CustomerAccount.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.R;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
  private ArrayList<MenuPojo> mList;
  private Context mContext;

  public FilterAdapter(Context context, ArrayList<MenuPojo> filteredDataList) {
    this.mContext = context;
    this.mList = filteredDataList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(mContext)
        .inflate(R.layout.cat_row, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.xTextView.setText(mList.get(position).getItemName());

  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView xTextView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      xTextView = itemView.findViewById(R.id.xTextView);
    }
  }
}
