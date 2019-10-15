package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
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





}
