package emerge.project.mr_indoscan_rep.ui.adapters.visits;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import emerge.project.mr_indoscan_rep.utils.entittes.VisitProducts;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class VisitsSubProductAdapter extends RecyclerView.Adapter<VisitsSubProductAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<VisitProducts> visitItems;



    public VisitsSubProductAdapter(Context mContext, ArrayList<VisitProducts> item) {
        this.mContext = mContext;
        this.visitItems = item;



    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sub_visits_products, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        VisitProducts visitProducts = visitItems.get(position);
        holder.textvieProductname.setText(visitProducts.getProductName());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_product_defult_small);
        requestOptions.error(R.drawable.ic_product_defult_small);


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


        if(visitProducts.getImageUrl()==null || visitProducts.getImageUrl().equals("")){

        }else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(visitProducts.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(holder.imageViewProduct);
        }




    }

    @Override
    public int getItemCount() {
        return visitItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.imageView_product)
        ImageView imageViewProduct;


        @BindView(R.id.textview_productname)
        TextView textvieProductname;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
