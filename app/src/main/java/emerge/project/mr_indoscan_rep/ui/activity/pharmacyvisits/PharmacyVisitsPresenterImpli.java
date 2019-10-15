package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractorImpil;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileagePresenter;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageView;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class PharmacyVisitsPresenterImpli implements PharmacyVisitsPresenter,
        PharmacyVisitsInteractor.OnPharmacyFinishedListener,
        PharmacyVisitsInteractor.OnSearchPharmacyFinishedListener,
        PharmacyVisitsInteractor.OnSelectedPharmacyIDFinishedListener,
        PharmacyVisitsInteractor.OnProductFinishedListener{


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
}
