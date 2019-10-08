package emerge.project.mr_indoscan_rep.ui.adapters.doctors;


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
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsView;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class DoctorsFilterLocationAdapter extends RecyclerView.Adapter<DoctorsFilterLocationAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<LocationEntitie> locItems;

    DoctorsPresenter doctorsPresenter;


    public DoctorsFilterLocationAdapter(Context mContext, ArrayList<LocationEntitie> item, DoctorsView doctorsView) {
        this.mContext = mContext;
        this.locItems = item;

        doctorsPresenter = new DoctorsPresenterImpli(doctorsView);


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits_filter_dialog, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final  LocationEntitie locationEntitie =locItems.get(position);


        holder.textvieDocname.setText(locationEntitie.getName());


        if (locationEntitie.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationEntitie.isSelect()){
                    locItems.get(position).setSelect(false);
                    doctorsPresenter.addLocationToDocFilterStart(locationEntitie,false);
                }else {
                    locItems.get(position).setSelect(true);
                    doctorsPresenter.addLocationToDocFilterStart(locationEntitie,true);
                }
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return locItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_doctor_name)
        TextView textvieDocname;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
