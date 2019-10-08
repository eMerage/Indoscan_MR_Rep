package emerge.project.mr_indoscan_rep.ui.adapters.expenses;


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
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class ExpensesImagesAdapter extends RecyclerView.Adapter<ExpensesImagesAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Bitmap> doctorItems;


    public ExpensesImagesAdapter(Context mContext, ArrayList<Bitmap> item) {
        this.mContext = mContext;
        this.doctorItems = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_images, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Bitmap bitmap =doctorItems.get(position);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimage);
        requestOptions.error(R.drawable.noimage);

        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) { return false; }
            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) { return false; }
        };


        Glide.with(mContext)
                .asBitmap()
                .load(bitmap)
                .apply(requestOptions)
                .listener(requestListener)
                .into(holder.imgExpenses);


    }

    @Override
    public int getItemCount() {
        return doctorItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_imagelist_image)
        ImageView imgExpenses;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
