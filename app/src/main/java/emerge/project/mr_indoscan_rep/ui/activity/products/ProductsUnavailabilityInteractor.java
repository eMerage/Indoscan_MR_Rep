package emerge.project.mr_indoscan_rep.ui.activity.products;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProductsUnavailabilityInteractor {


    interface OnProductFinishedListener {
        void productList(ArrayList<Products> productList, ArrayList<String> productNameList);
        void productFail(String failMsg);
        void productNetworkFail();
    }
    void getProduct(Context con,OnProductFinishedListener onProductFinishedListener );



    interface OnSearchUnProductFinishedListener {
        void searchUnProductList(ArrayList<Products> productList);
    }
    void searchUnProduct(ArrayList<Products> productList,String productName,OnSearchUnProductFinishedListener onSearchUnProductFinishedListener   );

    interface OnSearchExpProductFinishedListener {
        void searchExpProductList(ArrayList<Products> productList);
    }
    void searchExpProduct(ArrayList<Products> productList,String productName,OnSearchExpProductFinishedListener onSearchExpProductFinishedListener   );
    

    interface OnSelectedUnProductIDFinishedListener {
        void selectedUnProductID(int selectedProductId);
    }
    void getSelectedUnProductID(Products product,OnSelectedUnProductIDFinishedListener onSelectedUnProductIDFinishedListener);


    interface OnSelectedExpProductIDFinishedListener {
        void selectedExpProductID(int selectedProductId);
    }
    void getSelectedExpProductID(Products product,OnSelectedExpProductIDFinishedListener onSelectedExpProductIDFinishedListener);



    interface OnPostUnProductFinishedListener {
        void  postUnProductError(String msg);
        void  postUnProductSuccess();
        void  postUnProductFail(String failMsg);
        void  postUnProductNetworkFail();
    }
    void postUnProduct(Context context, int productID,String reason,
                            OnPostUnProductFinishedListener onPostUnProductFinishedListener);


    interface OnPostExProductFinishedListener {
        void  postExProductError(String msg);
        void  postExProductSuccess();
        void  postExProductFail(String failMsg);
        void  postExProductNetworkFail();
    }
    void postExProduct(Context context, int productID,String exDate,
                       OnPostExProductFinishedListener onPostExProductFinishedListener);







}
