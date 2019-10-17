package emerge.project.mr_indoscan_rep.ui.adapters.pharmacy;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsView;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsView;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Pharmacy> locItems;


    PharmacyVisitsPresenter pharmacyVisitsPresenter;

    public PharmacyAdapter(Context mContext, ArrayList<Pharmacy> item, PharmacyVisitsView view) {
        this.mContext = mContext;
        this.locItems = item;

        pharmacyVisitsPresenter = new PharmacyVisitsPresenterImpli(view);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits_location, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Pharmacy loc = locItems.get(position);
        holder.textviewLocation.setText(loc.getName());

        Boolean select = false;
        try {
            select = loc.isSelect();
        }catch (Exception ex){
            select = false;
        }

        if(select == null){
            select = false;
        }else {

        }


       if (select) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Pharmacy l : locItems) {
                    l.setSelect(false);
                }
                locItems.get(position).setSelect(true);
                notifyDataSetChanged();

                pharmacyVisitsPresenter.getSelectedPharmacyID(loc);

            }
        });


    }


    @Override
    public int getItemCount() {
        return locItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.textview_location_address)
        TextView textviewAddress;

        @BindView(R.id.textview_location)
        TextView textviewLocation;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
