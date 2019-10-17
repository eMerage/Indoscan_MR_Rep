package emerge.project.mr_indoscan_rep.ui.activity.products;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Mileage;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class ProductsUnavailabilityInteractorImpil implements ProductsUnavailabilityInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Products> productsArrayList;
    Boolean proUnStatus = false;
    Boolean proExStatus = false;


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
    public void searchUnProduct(ArrayList<Products> productList, String productName, OnSearchUnProductFinishedListener onSearchUnProductFinishedListener) {
        try {
            ArrayList<Products> newProductList = new ArrayList<Products>();

            if (productName.isEmpty() || productName.equals("") || productName.equalsIgnoreCase("all")) {
                onSearchUnProductFinishedListener.searchUnProductList(productList);
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
                onSearchUnProductFinishedListener.searchUnProductList(newProductList);

            }
        } catch (Exception ex) {
            onSearchUnProductFinishedListener.searchUnProductList(productList);
        }
    }

    @Override
    public void searchExpProduct(ArrayList<Products> productList, String productName, OnSearchExpProductFinishedListener onSearchExpProductFinishedListener) {
        try {
            ArrayList<Products> newProductList = new ArrayList<Products>();

            if (productName.isEmpty() || productName.equals("") || productName.equalsIgnoreCase("all")) {
                onSearchExpProductFinishedListener.searchExpProductList(productList);
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
                onSearchExpProductFinishedListener.searchExpProductList(newProductList);

            }
        } catch (Exception ex) {
            onSearchExpProductFinishedListener.searchExpProductList(productList);
        }

    }

    @Override
    public void getSelectedUnProductID(Products product, OnSelectedUnProductIDFinishedListener onSelectedUnProductIDFinishedListener) {
        onSelectedUnProductIDFinishedListener.selectedUnProductID(product.getId());
    }

    @Override
    public void getSelectedExpProductID(Products product, OnSelectedExpProductIDFinishedListener onSelectedExpProductIDFinishedListener) {
        onSelectedExpProductIDFinishedListener.selectedExpProductID(product.getId());
    }

    @Override
    public void postUnProduct(Context context, int productID, String reason, final OnPostUnProductFinishedListener onPostUnProductFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostUnProductFinishedListener.postUnProductNetworkFail();
        } else if (productID == 0) {
            onPostUnProductFinishedListener.postUnProductError("Please select a product");
        } else if (reason.equals("")) {
            onPostUnProductFinishedListener.postUnProductError("Please select a reason");
        } else {
            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);
            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("UserID", userId);
            jsonObject.addProperty("ProductID", productID);
            jsonObject.addProperty("Reason", reason);


            try {
                apiService.saveProductUnavailabilty(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean expen) {
                                proUnStatus = expen;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostUnProductFinishedListener.postUnProductFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (proUnStatus) {
                                    onPostUnProductFinishedListener.postUnProductSuccess();
                                } else {
                                    onPostUnProductFinishedListener.postUnProductFail("Something went wrong, Please try again");
                                }

                            }
                        });
            } catch (Exception ex) {
                onPostUnProductFinishedListener.postUnProductFail("Something went wrong, Please try again");
            }

        }
    }

    @Override
    public void postExProduct(Context context, int productID, String exDate, final OnPostExProductFinishedListener onPostExProductFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostExProductFinishedListener.postExProductNetworkFail();
        } else if (productID == 0) {
            onPostExProductFinishedListener.postExProductError("Please select a product");
        } else if (exDate.equals("")) {
            onPostExProductFinishedListener.postExProductError("Please add expiry date");
        } else {
            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);
            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("UserID", userId);
            jsonObject.addProperty("ProductID", productID);
            jsonObject.addProperty("ExpiryDate", exDate);



            try {
                apiService.saveProductShortExpiry(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean expen) {
                                proUnStatus = expen;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostExProductFinishedListener.postExProductFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (proUnStatus) {
                                    onPostExProductFinishedListener.postExProductSuccess();
                                } else {
                                    onPostExProductFinishedListener.postExProductFail("Something went wrong, Please try again");
                                }
                            }
                        });
            } catch (Exception ex) {
                onPostExProductFinishedListener.postExProductFail("Something went wrong, Please try again");
            }

        }
    }
}


