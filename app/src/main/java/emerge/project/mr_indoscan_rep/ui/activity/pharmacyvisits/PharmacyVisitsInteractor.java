package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PharmacyVisitsInteractor {


    interface OnPharmacyFinishedListener {
        void pharmacyList(ArrayList<Pharmacy> pharmacyList,ArrayList<String> pharmacyNAmeList);
        void pharmacyFail(String failMsg);
        void pharmacyNetworkFail();
    }
    void getPharmacy(Context con,OnPharmacyFinishedListener onPharmacyFinishedListener );


    interface OnSearchPharmacyFinishedListener {
        void searchPharmacyList(ArrayList<Pharmacy> pharmacyList);
    }
    void searchPharmacy(ArrayList<Pharmacy> pharmacyList,String phaName, OnSearchPharmacyFinishedListener onSearchPharmacyFinishedListener);


    interface OnSelectedPharmacyIDFinishedListener {
        void selectedPharmacyID(int selectedPharmacyId);
    }
    void getSelectedPharmacyID(Pharmacy pha ,OnSelectedPharmacyIDFinishedListener onSelectedPharmacyIDFinishedListener);



    interface OnProductFinishedListener {
        void productList(ArrayList<Products> productList, ArrayList<String>productNameList);
        void productFail(String failMsg);
        void productNetworkFail();
    }
    void getProduct(Context con,OnProductFinishedListener onProductFinishedListener );




}
