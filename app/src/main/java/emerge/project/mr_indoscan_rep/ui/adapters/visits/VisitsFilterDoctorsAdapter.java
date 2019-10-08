package emerge.project.mr_indoscan_rep.ui.adapters.visits;


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
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsView;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsFilterDoctorsAdapter extends RecyclerView.Adapter<VisitsFilterDoctorsAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Doctor> doctorItems;

    VisitsPresenter visitsPresenter;

    public VisitsFilterDoctorsAdapter(Context mContext, ArrayList<Doctor> item, VisitsView visitsView) {
        this.mContext = mContext;
        this.doctorItems = item;

        visitsPresenter = new VisitsPresenterImpli(visitsView);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits_filter_dialog, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Doctor doctor = doctorItems.get(position);


        holder.textvieDocname.setText(doctor.getName());


        if (doctor.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doctor.isSelect()){
                    doctorItems.get(position).setSelect(false);
                    visitsPresenter.addDoctorsToVisitsFilterStart(doctor,false);
                }else {
                    doctorItems.get(position).setSelect(true);
                    visitsPresenter.addDoctorsToVisitsFilterStart(doctor,true);
                }
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return doctorItems.size();
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
