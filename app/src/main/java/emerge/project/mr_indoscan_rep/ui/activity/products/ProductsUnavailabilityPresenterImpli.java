package emerge.project.mr_indoscan_rep.ui.activity.products;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractorImpil;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileagePresenter;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageView;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class ProductsUnavailabilityPresenterImpli implements ProductsUnavailabilityPresenter,
        ProductsUnavailabilityInteractor.OnProductFinishedListener,
        ProductsUnavailabilityInteractor.OnSearchExpProductFinishedListener,
        ProductsUnavailabilityInteractor.OnSearchUnProductFinishedListener,
        ProductsUnavailabilityInteractor.OnSelectedExpProductIDFinishedListener,
        ProductsUnavailabilityInteractor.OnSelectedUnProductIDFinishedListener,
        ProductsUnavailabilityInteractor.OnPostUnProductFinishedListener,
        ProductsUnavailabilityInteractor.OnPostExProductFinishedListener{


    private ProductsUnavailabilityView productsUnavailabilityView;
    ProductsUnavailabilityInteractor productsUnavailabilityInteractor;

    public ProductsUnavailabilityPresenterImpli(ProductsUnavailabilityView view) {
        this.productsUnavailabilityView = view;
        this.productsUnavailabilityInteractor = new ProductsUnavailabilityInteractorImpil();
    }


    @Override
    public void getProduct(Context con) {
        productsUnavailabilityInteractor.getProduct(con,this);
    }


    @Override
    public void productList(ArrayList<Products> productList, ArrayList<String> productNameList) {
        productsUnavailabilityView.productList( productList,productNameList);
    }

    @Override
    public void productFail(String failMsg) {
        productsUnavailabilityView.productFail(failMsg);
    }
    @Override
    public void productNetworkFail() {
        productsUnavailabilityView.productNetworkFail();
    }


    @Override
    public void searchUnProduct(ArrayList<Products> productList, String productName) {
        productsUnavailabilityInteractor.searchUnProduct(productList,  productName,this);
    }
    @Override
    public void searchUnProductList(ArrayList<Products> productList) {
        productsUnavailabilityView.searchUnProductList(productList);
    }



    @Override
    public void searchExpProduct(ArrayList<Products> productList, String productName) {
        productsUnavailabilityInteractor.searchExpProduct( productList,  productName,this);
    }


    @Override
    public void searchExpProductList(ArrayList<Products> productList) {
        productsUnavailabilityView.searchExpProductList(productList);
    }




    @Override
    public void getSelectedUnProductID(Products product) {
        productsUnavailabilityInteractor.getSelectedUnProductID(product,this);
    }

    @Override
    public void selectedUnProductID(int selectedProductId) {
        productsUnavailabilityView.selectedUnProductID( selectedProductId);
    }




    @Override
    public void getSelectedExpProductID(Products product) {
        productsUnavailabilityInteractor.getSelectedExpProductID(product,this);
    }


    @Override
    public void selectedExpProductID(int selectedProductId) {
        productsUnavailabilityView.selectedExpProductID(selectedProductId);
    }





    @Override
    public void postUnProduct(Context context, int productID, String reason) {
        productsUnavailabilityInteractor.postUnProduct(context,productID,reason,this);
    }



    @Override
    public void postUnProductError(String msg) {
        productsUnavailabilityView.postUnProductError(msg);
    }

    @Override
    public void postUnProductSuccess() {
        productsUnavailabilityView.postUnProductSuccess();
    }

    @Override
    public void postUnProductFail(String failMsg) {
        productsUnavailabilityView.postUnProductFail(failMsg);
    }

    @Override
    public void postUnProductNetworkFail() {
        productsUnavailabilityView.postUnProductNetworkFail();
    }





    @Override
    public void postExProduct(Context context, int productID, String exDate) {
        productsUnavailabilityInteractor.postExProduct(context,productID,  exDate,this);
    }

    @Override
    public void postExProductError(String msg) {
        productsUnavailabilityView.postExProductError( msg);
    }

    @Override
    public void postExProductSuccess() {
        productsUnavailabilityView.postExProductSuccess();
    }

    @Override
    public void postExProductFail(String failMsg) {
        productsUnavailabilityView.postExProductFail(failMsg);
    }

    @Override
    public void postExProductNetworkFail() {
        productsUnavailabilityView.postExProductNetworkFail();
    }
}
