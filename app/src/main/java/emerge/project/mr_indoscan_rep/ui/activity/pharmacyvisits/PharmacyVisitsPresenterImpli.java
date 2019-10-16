package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractorImpil;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileagePresenter;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageView;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class PharmacyVisitsPresenterImpli implements PharmacyVisitsPresenter,
        PharmacyVisitsInteractor.OnPharmacyFinishedListener,
        PharmacyVisitsInteractor.OnSearchPharmacyFinishedListener,
        PharmacyVisitsInteractor.OnSelectedPharmacyIDFinishedListener,
        PharmacyVisitsInteractor.OnProductFinishedListener,
        PharmacyVisitsInteractor.OnSearchProductFinishedListener,
        PharmacyVisitsInteractor.OnSelectedProductIDFinishedListener,
        PharmacyVisitsInteractor.OnDoctorsFinishedListener,
        PharmacyVisitsInteractor.OnSearchDoctorsFinishedListener,
        PharmacyVisitsInteractor.OnSelectedDoctorIDFinishedListener,
        PharmacyVisitsInteractor.OnPostPharmacyVisitsFinishedListener{


    private PharmacyVisitsView pharmacyVisitsView;
    PharmacyVisitsInteractor pharmacyVisitsInteractor;


    public PharmacyVisitsPresenterImpli(PharmacyVisitsView pharmacyvisitsview) {
        this.pharmacyVisitsView = pharmacyvisitsview;
        this.pharmacyVisitsInteractor = new PharmacyVisitsInteractorImpil();
    }






    @Override
    public void getPharmacy(Context con) {
        pharmacyVisitsInteractor.getPharmacy(con,this);
    }

    @Override
    public void pharmacyList(ArrayList<Pharmacy> pharmacyList, ArrayList<String> pharmacyNAmeList) {
        pharmacyVisitsView. pharmacyList(pharmacyList, pharmacyNAmeList);
    }

    @Override
    public void pharmacyFail(String failMsg) {
        pharmacyVisitsView.pharmacyFail( failMsg);
    }

    @Override
    public void pharmacyNetworkFail() {
        pharmacyVisitsView.pharmacyNetworkFail();
    }



    @Override
    public void searchPharmacy(ArrayList<Pharmacy> pharmacyList, String phaName) {
        pharmacyVisitsInteractor.searchPharmacy(pharmacyList,phaName,this);
    }


    @Override
    public void searchPharmacyList(ArrayList<Pharmacy> pharmacyList) {
        pharmacyVisitsView.searchPharmacyList(pharmacyList);
    }


    @Override
    public void getSelectedPharmacyID(Pharmacy pha) {
        pharmacyVisitsInteractor.getSelectedPharmacyID(pha,this);
    }



    @Override
    public void selectedPharmacyID(int selectedPharmacyId) {
        pharmacyVisitsView.selectedPharmacyID(selectedPharmacyId);
    }



    @Override
    public void getProduct(Context con) {
        pharmacyVisitsInteractor.getProduct(con,this);
    }



    @Override
    public void productList(ArrayList<Products> productList, ArrayList<String> productNameList) {
        pharmacyVisitsView.productList(productList,productNameList);
    }

    @Override
    public void productFail(String failMsg) {
        pharmacyVisitsView.productFail(failMsg);
    }

    @Override
    public void productNetworkFail() {
        pharmacyVisitsView.productNetworkFail();
    }


    @Override
    public void searchProduct(ArrayList<Products> productList, String productName) {
        pharmacyVisitsInteractor.searchProduct(productList,productName,this);
    }
    @Override
    public void searchProductList(ArrayList<Products> productList) {
        pharmacyVisitsView.searchProductList(productList);
    }



    @Override
    public void getSelectedProductID(Products product) {
        pharmacyVisitsInteractor.getSelectedProductID(product,this);
    }

    @Override
    public void selectedProductID(int selectedProductId) {
        pharmacyVisitsView.selectedProductID(selectedProductId);
    }


    @Override
    public void getDoctors(Context con) {
        pharmacyVisitsInteractor.getDoctors(con,this);
    }


    @Override
    public void doctorsList(ArrayList<Doctor> doctorsList, ArrayList<String> doctorsNameList) {
        pharmacyVisitsView.doctorsList(doctorsList,doctorsNameList);
    }

    @Override
    public void doctorsFail(String failMsg) {
        pharmacyVisitsView.doctorsFail(failMsg);
    }

    @Override
    public void doctorsNetworkFail() {
        pharmacyVisitsView.doctorsNetworkFail();
    }



    @Override
    public void searchDoctors(ArrayList<Doctor> doctorList, String doctorsName) {
        pharmacyVisitsInteractor.searchDoctors(doctorList,doctorsName,this);
    }


    @Override
    public void searchDoctorsList(ArrayList<Doctor> doctorsList) {
        pharmacyVisitsView.searchDoctorsList(doctorsList);
    }


    @Override
    public void getSelectedDoctorID(Doctor doctor) {
        pharmacyVisitsInteractor.getSelectedDoctorID(doctor,this);
    }




    @Override
    public void selectedDoctorID(int selectedDoctorId) {
        pharmacyVisitsView.selectedDoctorID(selectedDoctorId);
    }




    @Override
    public void postPharmacyVisits(Context context, int pharmacyID, int productID, int doctorID, int noOfprescription, String prescriptionType, String comProductName, int noOfComprescription, String comPrescriptionType) {
        pharmacyVisitsInteractor.postPharmacyVisits( context,  pharmacyID,  productID,  doctorID,  noOfprescription,  prescriptionType,  comProductName,  noOfComprescription,  comPrescriptionType,this);
    }


    @Override
    public void postPharmacyVisitsError(String msg) {
        pharmacyVisitsView. postPharmacyVisitsError( msg);
    }

    @Override
    public void postPharmacyVisitsSuccess() {
        pharmacyVisitsView.postPharmacyVisitsSuccess();
    }

    @Override
    public void postPharmacyVisitsFail(String failMsg) {
        pharmacyVisitsView.postPharmacyVisitsFail( failMsg);
    }

    @Override
    public void postPharmacyVisitsNetworkFail() {
        pharmacyVisitsView.postPharmacyVisitsNetworkFail();
    }
}
