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
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsDocsAdapter extends RecyclerView.Adapter<VisitsDocsAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Doctor> doctorItems;


    public VisitsDocsAdapter(Context mContext, ArrayList<Doctor> item) {
        this.mContext = mContext;
        this.doctorItems = item;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sunnery_towns, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Doctor doctor  = doctorItems.get(position);
        holder.textviewName.setText(doctor.getName());




    }

    @Override
    public int getItemCount() {
        return doctorItems.size();
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
