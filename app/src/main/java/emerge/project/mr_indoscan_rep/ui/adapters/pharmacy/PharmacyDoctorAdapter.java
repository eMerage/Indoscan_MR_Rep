package emerge.project.mr_indoscan_rep.ui.adapters.pharmacy;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsView;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class PharmacyDoctorAdapter extends RecyclerView.Adapter<PharmacyDoctorAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Doctor> docItems;

    PharmacyVisitsPresenter pharmacyVisitsPresenter;

    public PharmacyDoctorAdapter(Context mContext, ArrayList<Doctor> item, PharmacyVisitsView view) {
        this.mContext = mContext;
        this.docItems = item;

        pharmacyVisitsPresenter = new PharmacyVisitsPresenterImpli(view);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits_doctors, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Doctor doc =docItems.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_doctor);
        requestOptions.error(R.drawable.ic_doctor);

        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) { return false; }
            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) { return false; }
        };



        holder.textviewName.setText(doc.getName());

        if (doc.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }



        if(doc.getImageUrl()==null){

        }else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(doc.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(holder.imgDoc);
        }


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Doctor d :docItems){
                    d.setSelect(false);
                }
                docItems.get(position).setSelect(true);
                notifyDataSetChanged();

                pharmacyVisitsPresenter.getSelectedDoctorID(doc);

            }
        });

    }

    @Override
    public int getItemCount() {
        return docItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.img_doc)
        ImageView imgDoc;

        @BindView(R.id.textview_name)
        TextView textviewName;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
