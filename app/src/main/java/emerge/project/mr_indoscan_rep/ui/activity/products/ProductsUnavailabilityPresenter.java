package emerge.project.mr_indoscan_rep.ui.activity.products;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProductsUnavailabilityPresenter {

    void getProduct(Context con);


    void searchUnProduct(ArrayList<Products> productList, String productName);
    void searchExpProduct(ArrayList<Products> productList, String productName);

    void getSelectedUnProductID(Products product);
    void getSelectedExpProductID(Products product);



    void postUnProduct(Context context, int productID,String reason);



    void postExProduct(Context context, int productID,String exDate);



}
