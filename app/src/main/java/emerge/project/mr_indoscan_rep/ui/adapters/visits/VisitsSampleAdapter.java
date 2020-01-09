package emerge.project.mr_indoscan_rep.ui.adapters.visits;


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
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsView;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.Sample;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsSampleAdapter extends RecyclerView.Adapter<VisitsSampleAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Sample> sampleItems;


    VisitsPresenter visitsPresenter;

    public VisitsSampleAdapter(Context mContext, ArrayList<Sample> item, VisitsView visitsView) {
        this.mContext = mContext;
        this.sampleItems = item;
        visitsPresenter = new VisitsPresenterImpli(visitsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sample, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Sample sample  = sampleItems.get(position);


      /*  if (sample.getSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }*/

        if(sample.getRedeemQty()==0){
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        }


        holder.textviewSample.setText(sample.getName());
        holder.textviewSampletQty.setText( String.valueOf(sample.getRedeemQty()));

        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sample.getSelect()){
                    sampleItems.get(position).setSelect(false);
                    visitsPresenter.addSampleToVisit(sample,false);
                }else {
                    sampleItems.get(position).setSelect(true);
                    visitsPresenter.addSampleToVisit(sample,true);
                }
                notifyDataSetChanged();
            }
        });




    }

    @Override
    public int getItemCount() {
        return sampleItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.textview_sample)
        TextView textviewSample;

        @BindView(R.id.textview_samplet_qty)
        TextView textviewSampletQty;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
