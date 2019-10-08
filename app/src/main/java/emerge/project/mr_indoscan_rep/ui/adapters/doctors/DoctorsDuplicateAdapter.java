package emerge.project.mr_indoscan_rep.ui.adapters.doctors;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class DoctorsDuplicateAdapter extends RecyclerView.Adapter<DoctorsDuplicateAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Doctor> doctorItems;


    public DoctorsDuplicateAdapter(Context mContext, ArrayList<Doctor> item) {
        this.mContext = mContext;
        this.doctorItems = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_doctors, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Doctor doctor =doctorItems.get(position);

        holder.textviewDoctorsName.setText(doctor.getName());

        holder.textviewRep.setText(doctor.getCreatedByName());


    }

    @Override
    public int getItemCount() {
        return doctorItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_doctor)
        ImageView imgDoctor;

        @BindView(R.id.textview_doctors_name)
        TextView textviewDoctorsName;

        @BindView(R.id.textview_doctors_spec)
        TextView textviewDoctorsSpec;

        @BindView(R.id.textview_rep)
        TextView textviewRep;

        @BindView(R.id.textview_status)
        TextView textviewStatus;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
