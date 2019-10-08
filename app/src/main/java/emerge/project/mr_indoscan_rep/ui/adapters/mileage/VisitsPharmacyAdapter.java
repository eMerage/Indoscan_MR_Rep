package emerge.project.mr_indoscan_rep.ui.adapters.mileage;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsPharmacyAdapter extends RecyclerView.Adapter<VisitsPharmacyAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Pharmacy> pharmacyItems;


    public VisitsPharmacyAdapter(Context mContext, ArrayList<Pharmacy> item) {
        this.mContext = mContext;
        this.pharmacyItems = item;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sunnery_towns, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Pharmacy pharmacy  = pharmacyItems.get(position);
        holder.textviewName.setText(pharmacy.getName());




    }

    @Override
    public int getItemCount() {
        return pharmacyItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview101)
        TextView textviewName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
