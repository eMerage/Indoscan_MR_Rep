package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PharmacyVisitsView {

        void pharmacyList(ArrayList<Pharmacy> pharmacyList, ArrayList<String> pharmacyNAmeList);
        void pharmacyFail(String failMsg);
        void pharmacyNetworkFail();


        void searchPharmacyList(ArrayList<Pharmacy> pharmacyList);

        void selectedPharmacyID(int selectedPharmacyId);



        void productList(ArrayList<Products> productList, ArrayList<String>productNameList);
        void productFail(String failMsg);
        void productNetworkFail();


        void searchProductList(ArrayList<Products> productList);

        void selectedProductID(int selectedProductId);



        void doctorsList(ArrayList<Doctor> doctorsList, ArrayList<String> doctorsNameList);
        void doctorsFail(String failMsg);
        void doctorsNetworkFail();


        void searchDoctorsList(ArrayList<Doctor> doctorsList);


        void selectedDoctorID(int selectedDoctorId);


        void  postPharmacyVisitsError(String msg);
        void  postPharmacyVisitsSuccess();
        void  postPharmacyVisitsFail(String failMsg);
        void  postPharmacyVisitsNetworkFail();





}
