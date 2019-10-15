package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PharmacyVisitsPresenter {
    void getPharmacy(Context con);
    void searchPharmacy(ArrayList<Pharmacy> pharmacyList, String phaName);
    void getSelectedPharmacyID(Pharmacy pha );


    void getProduct(Context con);







}
