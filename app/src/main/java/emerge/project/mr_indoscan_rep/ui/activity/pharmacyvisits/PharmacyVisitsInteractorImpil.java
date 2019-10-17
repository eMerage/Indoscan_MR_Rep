package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;


import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
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
    ArrayList<Doctor> doctorsArrayList;

    Boolean pharmacyVisitsSaveStatus = false;

     String code;

    @Override
    public void getPharmacy(Context con, OnPharmacyFinishedListener onPharmacyFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(con)) {
            onPharmacyFinishedListener.pharmacyNetworkFail();
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(con).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

           code = genaratePharmacyVisitCode(String.valueOf(userId));

            ArrayList<Pharmacy> pha = new ArrayList<>();
            pha.add(new Pharmacy(1, "New Phama"));
            pha.add(new Pharmacy(2, "Uniq"));
            pha.add(new Pharmacy(4, "Nawalako"));
            pha.add(new Pharmacy(5, "Asiri"));
            pha.add(new Pharmacy(9, "Hemas"));


            ArrayList<String> phaName = new ArrayList<>();
            phaName.add("New Phama");
            phaName.add("Uniq");
            phaName.add("Nawalako");
            phaName.add("Asiri");
            phaName.add("Hemas");

            onPharmacyFinishedListener.pharmacyList(pha, phaName);

        }

    }

    @Override
    public void searchPharmacy(ArrayList<Pharmacy> pharmacyList, String phaName, OnSearchPharmacyFinishedListener onSearchPharmacyFinishedListener) {
        try {
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
        } catch (Exception ex) {
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
                                for (Products pr : productsArrayList) {
                                    productsNameList.add(pr.getName());
                                }
                                onProductFinishedListener.productList(productsArrayList, productsNameList);

                            }
                        });
            } catch (Exception ex) {
                onProductFinishedListener.productFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void searchProduct(ArrayList<Products> productList, String productName, OnSearchProductFinishedListener onSearchProductFinishedListener) {
        try {
            ArrayList<Products> newProductList = new ArrayList<Products>();

            if (productName.isEmpty() || productName.equals("") || productName.equalsIgnoreCase("all")) {
                onSearchProductFinishedListener.searchProductList(productList);
            } else {
                for (Products pro : productList) {
                    String text = pro.getName();
                    String patternString = productName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);

                    if (matcher.lookingAt()) {
                        newProductList.add(new Products(pro.getId(), pro.getName(), false));
                    }

                }
                onSearchProductFinishedListener.searchProductList(newProductList);

            }
        } catch (Exception ex) {
            onSearchProductFinishedListener.searchProductList(productList);
        }

    }

    @Override
    public void getSelectedProductID(Products product, OnSelectedProductIDFinishedListener onSelectedProductIDFinishedListener) {
        onSelectedProductIDFinishedListener.selectedProductID(product.getId());
    }

    @Override
    public void getDoctors(Context con, final OnDoctorsFinishedListener onDoctorsFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(con)) {
            onDoctorsFinishedListener.doctorsNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(con).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                doctorsArrayList = new ArrayList<Doctor>();
                final ArrayList<String> doctorsNameList = new ArrayList<String>();

                apiService.getAllDoctors(tokenID,userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doc) {
                                doctorsArrayList = doc;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onDoctorsFinishedListener.doctorsFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                for (Doctor doc : doctorsArrayList) {
                                    doctorsNameList.add(doc.getName());
                                }
                                onDoctorsFinishedListener.doctorsList(doctorsArrayList,doctorsNameList);
                            }
                        });
            } catch (Exception ex) {
                onDoctorsFinishedListener.doctorsFail("Something went wrong, Please try again");
            }
        }
    }

    @Override
    public void searchDoctors(ArrayList<Doctor> doctorList, String doctorsName, OnSearchDoctorsFinishedListener onSearchDoctorsFinishedListener) {

        try {
            ArrayList<Doctor> newdoctorList = new ArrayList<Doctor>();

            if (doctorsName.isEmpty() || doctorsName.equals("") || doctorsName.equalsIgnoreCase("all")) {
                onSearchDoctorsFinishedListener.searchDoctorsList(doctorList);
            } else {
                for (Doctor doc : doctorList) {
                    String text = doc.getName();
                    String patternString = doctorsName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);

                    if (matcher.lookingAt()) {
                        newdoctorList.add(new Doctor(doc.getId(), doc.getName(), false));
                    }

                }
                onSearchDoctorsFinishedListener.searchDoctorsList(newdoctorList);
            }
        } catch (Exception ex) {
            onSearchDoctorsFinishedListener.searchDoctorsList(doctorList);
        }


    }

    @Override
    public void getSelectedDoctorID(Doctor doctor, OnSelectedDoctorIDFinishedListener onSelectedDoctorIDFinishedListener) {
        onSelectedDoctorIDFinishedListener.selectedDoctorID(doctor.getId());
    }

    @Override
    public void postPharmacyVisits(Context context, int pharmacyID, int productID, int doctorID, int noOfprescription,
                                   String prescriptionType, String comProductName, int noOfComprescription, String comPrescriptionType,
                                   final OnPostPharmacyVisitsFinishedListener onPostPharmacyVisitsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostPharmacyVisitsFinishedListener.postPharmacyVisitsNetworkFail();
        } else if (pharmacyID == 0) {
            onPostPharmacyVisitsFinishedListener.postPharmacyVisitsError("Please select the Pharmacy");
        } else if ((productID==0) && (doctorID==0)) {
            onPostPharmacyVisitsFinishedListener.postPharmacyVisitsError("Please select at least product or doctor");
        } else if (noOfprescription <=0) {
            onPostPharmacyVisitsFinishedListener.postPharmacyVisitsError("Please add number of prescriptions");
        } else if ((comProductName.equals("")) && (noOfComprescription !=0)) {
            onPostPharmacyVisitsFinishedListener.postPharmacyVisitsError("Please add competitor product Name");
        } else if ((!comProductName.equals("")) && (noOfComprescription ==0)) {
            onPostPharmacyVisitsFinishedListener.postPharmacyVisitsError("Please add competitor product prescriptions count");
        } else {
            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);


            final JsonObject jsonObject = new JsonObject();


            jsonObject.addProperty("MobileCode", code);
            jsonObject.addProperty("UserID",userId);
            jsonObject.addProperty("LocationID",pharmacyID);
            jsonObject.addProperty("DoctorID", doctorID);
            jsonObject.addProperty("ProductID", productID);
            jsonObject.addProperty("NoOfPrescriptions", noOfprescription);
            jsonObject.addProperty("PrescriptionType", prescriptionType);
            jsonObject.addProperty("CompetitorProduct", comProductName);
            jsonObject.addProperty("PrescriptionsForCompetitor", noOfComprescription);
            jsonObject.addProperty("CompetitorProductsPrescriptionType", comPrescriptionType);


            System.out.println("xxxxxxxxxxxxxxxxxxxx  PharmacyVisit jsonObject :"+jsonObject);

            try {
                apiService.savePharmacyVisit(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean expen) {
                                pharmacyVisitsSaveStatus = expen;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostPharmacyVisitsFinishedListener.postPharmacyVisitsFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {

                                onPostPharmacyVisitsFinishedListener.postPharmacyVisitsSuccess();
                            }
                        });
            } catch (Exception ex) {
                onPostPharmacyVisitsFinishedListener.postPharmacyVisitsFail("Something went wrong, Please try again");
            }

        }

    }


    public String genaratePharmacyVisitCode(String userID) {
        Calendar c = Calendar.getInstance();
        String numberFromTime = String.valueOf(c.get(Calendar.YEAR)).substring(1) + String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DATE)) + String.valueOf(c.get(Calendar.HOUR)) + String.valueOf(c.get(Calendar.MINUTE)) + String.valueOf(c.get(Calendar.SECOND)) + String.valueOf(c.get(Calendar.MILLISECOND));
        if (numberFromTime.length() == 16) {
        } else {
            if (numberFromTime.length() > 16) {
                numberFromTime = numberFromTime.substring(0, 16);
            } else {
                int len = 16 - numberFromTime.length();
                for (int i = 0; i < len; i++) {
                    numberFromTime = numberFromTime + "0";
                }
            }
        }
        String expCode ="PVIS"+userID + numberFromTime;
        return expCode;
    }
}


