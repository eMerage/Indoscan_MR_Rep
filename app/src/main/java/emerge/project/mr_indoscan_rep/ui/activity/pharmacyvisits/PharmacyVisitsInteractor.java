package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
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

    interface OnSearchProductFinishedListener {
        void searchProductList(ArrayList<Products> productList);
    }
    void searchProduct(ArrayList<Products> productList,String productName, OnSearchProductFinishedListener onSearchProductFinishedListener );


    interface OnSelectedProductIDFinishedListener {
        void selectedProductID(int selectedProductId);
    }
    void getSelectedProductID(Products product,OnSelectedProductIDFinishedListener onSelectedProductIDFinishedListener);



    interface OnDoctorsFinishedListener {
        void doctorsList(ArrayList<Doctor> doctorsList, ArrayList<String> doctorsNameList);
        void doctorsFail(String failMsg);
        void doctorsNetworkFail();
    }
    void getDoctors(Context con,OnDoctorsFinishedListener onDoctorsFinishedListener  );

    interface OnSearchDoctorsFinishedListener {
        void searchDoctorsList(ArrayList<Doctor> doctorsList);
    }
    void searchDoctors(ArrayList<Doctor> doctorList,String doctorsName,  OnSearchDoctorsFinishedListener onSearchDoctorsFinishedListener);



    interface OnSelectedDoctorIDFinishedListener {
        void selectedDoctorID(int selectedDoctorId);
    }
    void getSelectedDoctorID(Doctor doctor,OnSelectedDoctorIDFinishedListener onSelectedDoctorIDFinishedListener );



    interface OnPostPharmacyVisitsFinishedListener {
        void  postPharmacyVisitsError(String msg);
        void  postPharmacyVisitsSuccess();
        void  postPharmacyVisitsFail(String failMsg);
        void  postPharmacyVisitsNetworkFail();
    }
    void postPharmacyVisits(Context context, int pharmacyID, int productID, int doctorID, int noOfprescription, String prescriptionType,
                            String comProductName , int noOfComprescription, String comPrescriptionType,
                      OnPostPharmacyVisitsFinishedListener onPostPharmacyVisitsFinishedListener);






}
