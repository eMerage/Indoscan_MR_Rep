package emerge.project.mr_indoscan_rep.ui.activity.products;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProductsUnavailabilityView {
    void productList(ArrayList<Products> productList, ArrayList<String> productNameList);

    void productFail(String failMsg);

    void productNetworkFail();


    void searchUnProductList(ArrayList<Products> productList);

    void searchExpProductList(ArrayList<Products> productList);


    void selectedUnProductID(int selectedProductId);

    void selectedExpProductID(int selectedProductId);


        void  postUnProductError(String msg);
        void  postUnProductSuccess();
        void  postUnProductFail(String failMsg);
        void  postUnProductNetworkFail();


        void  postExProductError(String msg);
        void  postExProductSuccess();
        void  postExProductFail(String failMsg);
        void  postExProductNetworkFail();





}
