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


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsProductAdapter extends RecyclerView.Adapter<VisitsProductAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Products> productItems;


    VisitsPresenter visitsPresenter;

    public VisitsProductAdapter(Context mContext, ArrayList<Products> item, VisitsView visitsView) {
        this.mContext = mContext;
        this.productItems = item;
        visitsPresenter = new VisitsPresenterImpli(visitsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits_products, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final Products products  = productItems.get(position);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_product_defult_small);
        requestOptions.error(R.drawable.ic_product_defult_small);


        if (products.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }



        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };

        if(products.getImageUrl()==null){

        }else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(products.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(holder.imageViewProduct);
        }

        holder.textviewProductname.setText(products.getName());

        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(products.isSelect()){
                    productItems.get(position).setSelect(false);
                    visitsPresenter.addProductsToVisitsStart(products,false);
                }else {
                    productItems.get(position).setSelect(true);
                   visitsPresenter.addProductsToVisitsStart(products,true);
                }
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.imageView_product)
        ImageView imageViewProduct;

        @BindView(R.id.textview_productname)
        TextView textviewProductname;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
