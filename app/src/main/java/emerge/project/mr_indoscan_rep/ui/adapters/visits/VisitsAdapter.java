package emerge.project.mr_indoscan_rep.ui.adapters.visits;


import android.content.Context;
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
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;
import emerge.project.mr_indoscan_rep.utils.entittes.VisitProducts;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Visit> visitItems;

    VisitsPresenter visitsPresenter;

    public VisitsAdapter(Context mContext, ArrayList<Visit> item, VisitsView visitsView) {
        this.mContext = mContext;
        this.visitItems = item;

        visitsPresenter = new VisitsPresenterImpli(visitsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Visit visit = visitItems.get(position);

        setSubItems(mContext, holder, visit.getVisitsProduct());


        holder.textviewVisitno.setText(visit.getVisitNumber());
        holder.textviewDate.setText(visit.getVisitDate().substring(0,10));
        holder.textviewDoctor.setText(visit.getDoctorName());
        holder.textviewLocation.setText(visit.getLocation());


        holder.imageViewBtnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.relativeLayoutVisitsPoducts.setVisibility(View.VISIBLE);


            }
        });

        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.showVisitDetails(visit);
            }
        });


    }

    @Override
    public int getItemCount() {
        return visitItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.relativeLayout_visits_poducts)
        RelativeLayout relativeLayoutVisitsPoducts;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;




        @BindView(R.id.imageView_btn_expand)
        ImageView imageViewBtnExpand;

        @BindView(R.id.recyclerview_products)
        RecyclerView recyclerViewProducts;

        @BindView(R.id.textview_visitno)
        TextView textviewVisitno;

        @BindView(R.id.textview_date)
        TextView textviewDate;


        @BindView(R.id.textview_doctor)
        TextView textviewDoctor;

        @BindView(R.id.textview_location)
        TextView textviewLocation;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setSubItems(Context context, MyViewHolder holder, ArrayList<VisitProducts> visitProductsList) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerViewProducts.setLayoutManager(layoutManager);
        holder.recyclerViewProducts.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerViewProducts.setNestedScrollingEnabled(true);


        VisitsSubProductAdapter visitsSubProductAdapter = new VisitsSubProductAdapter(context, visitProductsList);
        holder.recyclerViewProducts.setAdapter(visitsSubProductAdapter);

    }


}
