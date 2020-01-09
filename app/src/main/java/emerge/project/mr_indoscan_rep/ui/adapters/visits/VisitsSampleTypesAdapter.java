package emerge.project.mr_indoscan_rep.ui.adapters.visits;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsView;
import emerge.project.mr_indoscan_rep.utils.entittes.SampleType;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;
import emerge.project.mr_indoscan_rep.utils.entittes.VisitProducts;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsSampleTypesAdapter extends RecyclerView.Adapter<VisitsSampleTypesAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<SampleType> visitItems;

    VisitsPresenter visitsPresenter;

    public VisitsSampleTypesAdapter(Context mContext, ArrayList<SampleType> item, VisitsView visitsView) {
        this.mContext = mContext;
        this.visitItems = item;

        visitsPresenter = new VisitsPresenterImpli(visitsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sample_type, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final SampleType visitSample = visitItems.get(position);


        if (visitSample.getSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }

        holder.textviewSampletype.setText(visitSample.getName());

        holder.relativeLayoutHeaderMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SampleType l : visitItems) {
                    l.setSelect(false);
                }
                visitItems.get(position).setSelect(true);
                notifyDataSetChanged();
                visitsPresenter.getSelectedSampleType(visitSample);
            }
        });


    }

    @Override
    public int getItemCount() {
        return visitItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutHeaderMain;
        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.textview_sampletype)
        TextView textviewSampletype;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
