package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PharmacyVisitsPresenter {
    void getPharmacy(Context con);
    void searchPharmacy(ArrayList<Pharmacy> pharmacyList, String phaName);
    void getSelectedPharmacyID(Pharmacy pha );


    void getProduct(Context con);
    void searchProduct(ArrayList<Products> productList, String productName );
    void getSelectedProductID(Products product);


    void getDoctors(Context con);
    void searchDoctors(ArrayList<Doctor> doctorList, String doctorsName);

    void getSelectedDoctorID(Doctor doctor);





    void postPharmacyVisits(Context context, int pharmacyID, int productID, int doctorID, int noOfprescription, String prescriptionType,
                            String comProductName , int noOfComprescription, String comPrescriptionType);






}
