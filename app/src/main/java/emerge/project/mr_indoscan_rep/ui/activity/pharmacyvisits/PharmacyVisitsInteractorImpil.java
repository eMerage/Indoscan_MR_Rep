package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class PharmacyVisitsInteractorImpil implements PharmacyVisitsInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Products> productsArrayList;

    @Override
    public void getPharmacy(Context con,OnPharmacyFinishedListener onPharmacyFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(con)) {
            onPharmacyFinishedListener.pharmacyNetworkFail();
        } else {

            ArrayList<Pharmacy> pha = new ArrayList<>();
            pha.add(new Pharmacy(1,"New Phama"));
            pha.add(new Pharmacy(2,"Uniq"));
            pha.add(new Pharmacy(4,"Nawalako"));
            pha.add(new Pharmacy(5,"Asiri"));
            pha.add(new Pharmacy(9,"Hemas"));


            ArrayList<String> phaName = new ArrayList<>();
            phaName.add("New Phama");
            phaName.add("Uniq");
            phaName.add("Nawalako");
            phaName.add("Asiri");
            phaName.add("Hemas");

            onPharmacyFinishedListener.pharmacyList(pha,phaName);

        }

    }

    @Override
    public void searchPharmacy(ArrayList<Pharmacy> pharmacyList, String phaName, OnSearchPharmacyFinishedListener onSearchPharmacyFinishedListener) {
        try{
            ArrayList<Pharmacy> newPharmacyList = new ArrayList<Pharmacy>();
            if (phaName.isEmpty() || phaName.equals("") || phaName.equalsIgnoreCase("all")) {
                onSearchPharmacyFinishedListener.searchPharmacyList(pharmacyList);
            } else {
                for (Pharmacy pha : pharmacyList) {
                    String text = pha.getName();
                    String patternString = phaName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);

                    if (matcher.lookingAt()) {
                        newPharmacyList.add(new Pharmacy(pha.getId(), pha.getName(), false));
                    }

                }

                onSearchPharmacyFinishedListener.searchPharmacyList(newPharmacyList);

            }
        }catch (Exception ex){
            onSearchPharmacyFinishedListener.searchPharmacyList(pharmacyList);

        }

    }

    @Override
    public void getSelectedPharmacyID(Pharmacy pha, OnSelectedPharmacyIDFinishedListener onSelectedPharmacyIDFinishedListener) {
        onSelectedPharmacyIDFinishedListener.selectedPharmacyID(pha.getId());
    }

    @Override
    public void getProduct(Context con, final OnProductFinishedListener onProductFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(con)) {
            onProductFinishedListener.productNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(con).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                productsArrayList = new ArrayList<Products>();
                final ArrayList<String> productsNameList = new ArrayList<String>();
                apiService.getAllProductsForMR(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Products>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Products> products) {
                                productsArrayList = products;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onProductFinishedListener.productFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                for(Products pr : productsArrayList){
                                    productsNameList.add(pr.getName());
                                }
                                onProductFinishedListener.productList(productsArrayList,productsNameList);

                            }
                        });
            } catch (Exception ex) {
                onProductFinishedListener.productFail("Something went wrong, Please try again");
            }

        }

    }
}


