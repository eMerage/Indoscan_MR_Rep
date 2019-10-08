package emerge.project.mr_indoscan_rep.ui.activity.visits;


import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.TargetDetails;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;
import emerge.project.mr_indoscan_rep.utils.entittes.VisitProducts;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class VisitsInteractorImpil implements VisitsInteractor {

    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    private static final String USER_NAME = "userName";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Visit> visitList;
    ArrayList<Doctor> doctorsList;
    ArrayList<LocationEntitie> locationList;
    ArrayList<Products> productsList;

   Visit visitsRespond=new Visit();
    Boolean visitImageRespond=false;


    TargetDetails targetDetails ;

    @Override
    public void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener) {
        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Expenses", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Mileage", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Pharmacy Visits", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Products", R.drawable.ic_product_defult_small));

        onsetNavigationFinishedListener.navigationItems(navigationItems);
    }

    @Override
    public void getVisits(Context context, final int locationID, final int doctorID, final String date, final OnGetVisitsFinishedListener onGetVisitsFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetVisitsFinishedListener.visitsListNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<Visit> visitItems = new ArrayList<Visit>();
                final ArrayList<Doctor> docListForFilter = new ArrayList<Doctor>();
                final ArrayList<LocationEntitie> locListForFilter = new ArrayList<LocationEntitie>();
                final ArrayList<Products> productsListForFilter = new ArrayList<Products>();


                final ArrayList<String> docNameListForFilter = new ArrayList<String>();
                final ArrayList<String> locNameListForFilter = new ArrayList<String>();
                final ArrayList<String> proNameListForFilter = new ArrayList<String>();

                System.out.println("cccc : "+userId+" "+locationID+" "+doctorID+" "+date);


                apiService.getAllVisits(userId, locationID, doctorID, date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Visit>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Visit> visit) {
                                visitList = visit;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetVisitsFinishedListener.visitsListFail("Something went wrong, Please try again", locationID, doctorID, date);
                            }

                            @Override
                            public void onComplete() {
                                if (visitList.isEmpty()) {
                                    onGetVisitsFinishedListener.visitsListNoItems();
                                } else {
                                    for (Visit visit : visitList) {
                                        ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                                        for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                            visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                                    visitProducts.getImageUrl()));

                                        }

                                        visitItems.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                                visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));
                                    }
                                    onGetVisitsFinishedListener.visitsList(visitItems);


                                    for (Visit visit : visitList) {

                                        if (docListForFilter.isEmpty()) {
                                            docListForFilter.add(new Doctor(visit.getDoctorID(), visit.getDoctorName(), false));
                                            docNameListForFilter.add(visit.getDoctorName());
                                        } else {
                                            boolean status = false;
                                            for (Doctor d : docListForFilter) {
                                                if (d.getId() == visit.getDoctorID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                docListForFilter.add(new Doctor(visit.getDoctorID(), visit.getDoctorName(), false));
                                                docNameListForFilter.add(visit.getDoctorName());
                                            } else {
                                            }
                                        }


                                        if (locListForFilter.isEmpty()) {
                                            locListForFilter.add(new LocationEntitie(visit.getLocationID(), visit.getLocation(), false));
                                            locNameListForFilter.add(visit.getLocation());
                                        } else {
                                            boolean status = false;
                                            for (LocationEntitie loc : locListForFilter) {
                                                if (loc.getId() == visit.getLocationID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                locListForFilter.add(new LocationEntitie(visit.getLocationID(), visit.getLocation(), false));
                                                locNameListForFilter.add(visit.getLocation());
                                            } else {
                                            }
                                        }


                                        for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                            if (productsListForFilter.isEmpty()) {
                                                productsListForFilter.add(new Products(visitProducts.getProductID(), visitProducts.getProductName(), false));
                                                proNameListForFilter.add(visitProducts.getProductName());
                                            } else {
                                                boolean status = false;
                                                for (Products pro : productsListForFilter) {
                                                    if (pro.getId() == visitProducts.getProductID()) {
                                                        status = true;
                                                        break;
                                                    } else {
                                                    }
                                                }
                                                if (!status) {
                                                    productsListForFilter.add(new Products(visitProducts.getProductID(), visitProducts.getProductName(), false));
                                                    proNameListForFilter.add(visitProducts.getProductName());
                                                } else {
                                                }
                                            }

                                        }

                                    }

                                    onGetVisitsFinishedListener.visitsDoctorsList(docListForFilter);
                                    onGetVisitsFinishedListener.visitsLocationList(locListForFilter);
                                    onGetVisitsFinishedListener.visitsProductsList(productsListForFilter);

                                    onGetVisitsFinishedListener.visitsDoctorsNameList(docNameListForFilter);
                                    onGetVisitsFinishedListener.visitsLocationNameList(locNameListForFilter);
                                    onGetVisitsFinishedListener.visitsProductsNameList(proNameListForFilter);



                                }

                            }
                        });
            } catch (Exception ex) {
                onGetVisitsFinishedListener.visitsListFail("Something went wrong, Please try again", locationID, doctorID, date);
            }

        }


    }

    @Override
    public void getDoctors(Context context, Location location, final OnGetDoctorFinishedListener onGetDoctorFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetDoctorFinishedListener.doctorsGetingNetworkFail();
        } else if (location == null) {
            onGetDoctorFinishedListener.doctorsGetingError("we are unable to find your current location,please try again");
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                final ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();
                final ArrayList<String> doctorNameList = new ArrayList<String>();


                apiService.getApprovedDoctorsNearLocation(userId, location.getLatitude(), location.getLongitude())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doctors) {
                                doctorsList = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetDoctorFinishedListener.doctorsGetingFail("No Doctors are loaded,please check your location and try again");
                            }

                            @Override
                            public void onComplete() {
                                if (doctorsList.isEmpty()) {
                                    onGetDoctorFinishedListener.doctorsEmpty();
                                } else {
                                    for (Doctor doc : doctorsList) {
                                        doctorArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getImageUrl(), false));
                                        doctorNameList.add(doc.getName());
                                    }
                                    onGetDoctorFinishedListener.doctorsList(doctorArrayList, doctorNameList);

                                }
                            }
                        });

            } catch (Exception ex) {
                onGetDoctorFinishedListener.doctorsGetingFail("No Doctors are loaded,please check your location and try again");
            }

        }

    }

    @Override
    public void searchDoctor(ArrayList<Doctor> doctorsArrayList, String doctorName, OnSearchDoctorFinishedListener onSearchDoctorFinishedListener) {
        final ArrayList<Doctor> searchArrayList = new ArrayList<Doctor>();
        if (doctorsArrayList.isEmpty()) {
        } else {
            for (Doctor doc : doctorsArrayList) {
                if (doc.getName().equals(doctorName)) {
                    searchArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getImageUrl(), false));
                } else {
                }
            }
        }

        if ((searchArrayList.isEmpty()) && (!doctorsArrayList.isEmpty())) {
            if (doctorName.isEmpty() || doctorName.equals("") || doctorName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(doctorsArrayList);
            } else {
                for (Doctor doc : doctorsArrayList) {
                    String text = doc.getName();
                    String patternString = doctorName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getImageUrl(), false));
                    } else {
                    }

                }

            }
        } else {
        }
        onSearchDoctorFinishedListener.searchDoctorList(searchArrayList);

    }

    @Override
    public void getSelectedDoc(Doctor doc, OnSelectedDoctorsFinishedListener onSelectedDoctorsFinishedListener) {
        onSelectedDoctorsFinishedListener.selectedDoc(doc.getId());
    }

    @Override
    public void createVisitNumber(Context context, OnCreateVisitNumberFinishedListener onCreateVisitNumberFinishedListener) {

        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        int userId = encryptedPreferences.getInt(USER_ID, 0);
        String userName = encryptedPreferences.getString(USER_NAME, "").substring(0, 3).toUpperCase();
        Calendar c = Calendar.getInstance();
        String numberFromTime = String.valueOf(c.get(Calendar.DATE)) + String.valueOf(c.get(Calendar.HOUR)) + String.valueOf(c.get(Calendar.MINUTE));

        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int count = 3;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }
        String visitNumber = userName + String.valueOf(userId) + numberFromTime + builder.toString();
        onCreateVisitNumberFinishedListener.visitNumber(visitNumber);


    }

    @Override
    public void getLocation(Context context, int docId, Location location, final OnGetLocationFinishedListener onGetLocationFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetLocationFinishedListener.locationNetworkFail();
        } else if (location == null) {
            onGetLocationFinishedListener.locationetingError("we are unable to find your current location,please try again");
        } else {
            try {
                final ArrayList<LocationEntitie> locationArrayList = new ArrayList<LocationEntitie>();
                apiService.getNearbyLocationsOfDoctor(docId, location.getLatitude(), location.getLongitude())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<LocationEntitie>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<LocationEntitie> locationEntities) {
                                locationList = locationEntities;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetLocationFinishedListener.locationFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (locationList.isEmpty()) {
                                    onGetLocationFinishedListener.locationListEmpty();
                                } else {
                                    for (LocationEntitie loc : locationList) {
                                        locationArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(), false));
                                    }
                                    onGetLocationFinishedListener.locationList(locationArrayList);

                                }
                            }
                        });

            } catch (Exception ex) {
                onGetLocationFinishedListener.locationFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void getSelectedLocation(LocationEntitie location, OnSelectedLocationFinishedListener onSelectedLocationFinishedListener) {
        onSelectedLocationFinishedListener.selectedLocation(location.getId());
    }

    @Override
    public void getProducts(Context context, final ArrayList<Products> productArrayList, final OnProductsFinishedListener onProductsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onProductsFinishedListener.productsGetingNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<Products> productsArrayList = new ArrayList<Products>();
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
                                productsList = products;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onProductsFinishedListener.productsGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (productsList.isEmpty()) {
                                    onProductsFinishedListener.productsEmpty();
                                } else {
                                    for (Products pro : productsList) {
                                        boolean isAdded = false;
                                        for (Products addedPro : productArrayList) {
                                            if (addedPro.getId() == pro.getId()) {
                                                isAdded = true;
                                                break;
                                            } else {
                                            }

                                        }
                                        if (isAdded) {
                                            productsArrayList.add(new Products(pro.getId(), pro.getName(), pro.getCode(), pro.getImageUrl(), true));
                                        } else {
                                            productsArrayList.add(new Products(pro.getId(), pro.getName(), pro.getCode(), pro.getImageUrl(), false));
                                        }
                                        productsNameList.add(pro.getName());
                                    }
                                    onProductsFinishedListener.productsList(productsArrayList, productsNameList);
                                }

                            }
                        });
            } catch (Exception ex) {
                onProductsFinishedListener.productsGetingFail("Something went wrong, Please try again");
            }
        }

    }

    @Override
    public void searchProduct(ArrayList<Products> productArrayList, String productName, OnSearchProductFinishedListener onSearchProductFinishedListener) {
        final ArrayList<Products> searchArrayList = new ArrayList<Products>();

        if (productArrayList.isEmpty()) {
        } else {
            for (Products pro : productArrayList) {
                if (pro.getName().equals(productName)) {
                    searchArrayList.add(new Products(pro.getId(), pro.getName(), pro.getCode(), pro.getImageUrl(), false));
                } else {
                }
            }
        }


        if ((searchArrayList.isEmpty()) && (!productArrayList.isEmpty())) {
            if (productName.isEmpty() || productName.equals("") || productName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(productArrayList);
            } else {
                for (Products pro : productArrayList) {
                    String text = pro.getName();
                    String patternString = productName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Products(pro.getId(), pro.getName(), pro.getCode(), pro.getImageUrl(), false));
                    } else {
                    }
                }
            }
        } else {
        }

        onSearchProductFinishedListener.searchProductList(searchArrayList);
    }

    @Override
    public void addProductsToVisitsStart(Products products, boolean addOrRemove, OnAddProductsToVisitsStartFinishedListener onAddProductsToVisitsStartFinishedListener) {
        onAddProductsToVisitsStartFinishedListener.productsToVisitsStart(products, addOrRemove);
    }

    @Override
    public void addProductsToVisits(ArrayList<Products> productsListGlobal, Products products, boolean addOrRemove, OnAddProductsToVisitsFinishedListener onAddProductsToVisitsFinishedListener) {

        if (addOrRemove) {
            if (productsListGlobal.isEmpty()) {
                productsListGlobal.add(new Products(products.getId(), products.getName()));
            } else {
                boolean isFound = false;

                for (Products prod : productsListGlobal) {
                    if (prod.getId() == products.getId()) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound)
                    productsListGlobal.add(new Products(products.getId(), products.getName()));
            }
        } else {
            if (productsListGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < productsListGlobal.size(); i++) {
                    if (productsListGlobal.get(i).getId() == products.getId()) {
                        productsListGlobal.remove(i);
                    }
                }
            }
        }
        onAddProductsToVisitsFinishedListener.productsToVisits(productsListGlobal);


    }

    @Override
    public void showProductsToVisits(ArrayList<Products> addedProListGlobal, ArrayList<Products> allProcList, OnShowProductsToVisitsFinishedListener onShowProductsToVisitsFinishedListener) {
        final ArrayList<Products> proArrayList = new ArrayList<Products>();


        for (Products pro : addedProListGlobal) {
            proArrayList.add(new Products(pro.getId(), pro.getName(), pro.getCode(), pro.getImageUrl(), true));
        }
        for (Products proAll : allProcList) {
            boolean status = false;
            for (Products sp : addedProListGlobal) {
                if (proAll.getId() == sp.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                proArrayList.add(new Products(proAll.getId(), proAll.getName(), proAll.getCode(), proAll.getImageUrl(), false));

            }
        }
        onShowProductsToVisitsFinishedListener.showProducts(proArrayList);

    }

    @Override
    public void generateImageCode(Context context, String vistisNumber, OnGenerateImageCodeFinishedListener onGenerateImageCodeFinishedListener) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int count = 5;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        String imageCode = vistisNumber + builder.toString();
        onGenerateImageCodeFinishedListener.generateImageCode(imageCode);

    }

    @Override
    public void addVisits(Context context, final int docid, final String visitsNumber, final String imageCode, final int locationID, final ArrayList<Products> productsArrayList, final String comment, Location location, final OnAddVisitsFinishedListener onAddVisitsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onAddVisitsFinishedListener.addVisitsNetworkFail();
        } else if (docid == 0) {
            onAddVisitsFinishedListener.addVisitsEmpty("Please select a Doctor");
        } else if (locationID == 0) {
            onAddVisitsFinishedListener.addVisitsEmpty("Please select a Location");
        } else if (productsArrayList.isEmpty()) {
            onAddVisitsFinishedListener.addVisitsEmpty("Please select Products");
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String currentDate = sdfDate.format(new Date(System.currentTimeMillis()));

            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("Code", visitsNumber);
            jsonObject.addProperty("VisitNumber", visitsNumber);
            jsonObject.addProperty("LocationID", locationID);
            jsonObject.addProperty("DoctorID", docid);
            jsonObject.addProperty("RepID", userId);
            jsonObject.addProperty("Comment", comment);
            jsonObject.addProperty("ImageCode", imageCode);
            jsonObject.addProperty("DeviceDateTime", currentDate);
            jsonObject.addProperty("Latitude", location.getLatitude());
            jsonObject.addProperty("Longitude", location.getLongitude());

            JsonArray productJsonArr = new JsonArray();
            for (Products pro : productsArrayList) {
                JsonObject ob = new JsonObject();
                ob.addProperty("ProductID", pro.getId());
                productJsonArr.add(ob);
            }

            jsonObject.add("VisitsProduct", productJsonArr);
            try {
                apiService.saveVisit(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Visit>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Visit respond) {
                                visitsRespond = respond;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onAddVisitsFinishedListener.addVisitsFail("Something went wrong, Please try again", docid, visitsNumber, imageCode, locationID, productsArrayList, comment);
                            }

                            @Override
                            public void onComplete() {
                                onAddVisitsFinishedListener.addVisitsSuccessful();
                            }
                        });

            } catch (Exception ex) {
                onAddVisitsFinishedListener.addVisitsFail("Something went wrong, Please try again"+ex.toString(), docid, visitsNumber, imageCode, locationID, productsArrayList, comment);
            }
        }
    }

    @Override
    public void addDoctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove, OnAddDoctorsToVisitsStartFilterFinishedListener onAddDoctorsToVisitsStartFilterFinishedListener) {
        onAddDoctorsToVisitsStartFilterFinishedListener.doctorsToVisitsFilterStart(doctor, addOrRemove);
    }

    @Override
    public void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal, Doctor doctor, boolean addOrRemove, OnAddDoctorsToVisitsFilterFinishedListener onAddDoctorsToVisitsFilterFinishedListener) {
        if (addOrRemove) {
            if (docArrayListToFilterGlobal.isEmpty()) {
                docArrayListToFilterGlobal.add(new Doctor(doctor.getId(), doctor.getName(), false));

            } else {
                boolean isFound = false;

                for (Doctor doct : docArrayListToFilterGlobal) {
                    if (doct.getId() == doctor.getId()) {
                        isFound = true;
                        break;
                    }

                }
                if (!isFound)
                    docArrayListToFilterGlobal.add(new Doctor(doctor.getId(), doctor.getName(), false));
            }
        } else {
            if (docArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < docArrayListToFilterGlobal.size(); i++) {
                    if (docArrayListToFilterGlobal.get(i).getId() == doctor.getId()) {
                        docArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }
        onAddDoctorsToVisitsFilterFinishedListener.doctorsToVisitsFilter(docArrayListToFilterGlobal);


    }

    @Override
    public void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal, ArrayList<Doctor> allFilterDocList, OnAddDoctorsToFilterFinishedListener onAddDoctorsToFilterFinishedListener) {


        final ArrayList<Doctor> docArrayList = new ArrayList<Doctor>();

        for (Doctor doc : addedDocListGlobal) {
            docArrayList.add(new Doctor(doc.getId(), doc.getName(), true));
        }

        for (Doctor docAll : allFilterDocList) {
            boolean status = false;
            for (Doctor doc : addedDocListGlobal) {
                if (docAll.getId() == doc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                docArrayList.add(new Doctor(docAll.getId(), docAll.getName(), false));

            }
        }
        onAddDoctorsToFilterFinishedListener.addDoctorsToFilter(docArrayList);


    }

    @Override
    public void addLocationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove, OnAddLocationToVisitsStartFilterFinishedListener onAddLocationToVisitsStartFilterFinishedListener) {
        onAddLocationToVisitsStartFilterFinishedListener.locationToVisitsFilterStart(locationEntitie, addOrRemove);
    }

    @Override
    public void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal, LocationEntitie locationEntitie, boolean addOrRemove, OnAddLocationToVisitsFilterFinishedListener onAddLocationToVisitsFilterFinishedListener) {

        if (addOrRemove) {
            if (locArrayListToFilterGlobal.isEmpty()) {
                locArrayListToFilterGlobal.add(new LocationEntitie(locationEntitie.getId(), locationEntitie.getName(), false));
            } else {
                boolean isFound = false;

                for (LocationEntitie loca : locArrayListToFilterGlobal) {
                    if (loca.getId() == locationEntitie.getId()) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound)
                    locArrayListToFilterGlobal.add(new LocationEntitie(locationEntitie.getId(), locationEntitie.getName(), false));
            }
        } else {
            if (locArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < locArrayListToFilterGlobal.size(); i++) {
                    if (locArrayListToFilterGlobal.get(i).getId() == locationEntitie.getId()) {
                        locArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }
        onAddLocationToVisitsFilterFinishedListener.locationToVisitsFilter(locArrayListToFilterGlobal);

    }

    @Override
    public void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal, ArrayList<LocationEntitie> allFilterLocList, OnAddLocationToFilterFinishedListener onAddLocationToFilterFinishedListener) {

        final ArrayList<LocationEntitie> locArrayList = new ArrayList<LocationEntitie>();

        for (LocationEntitie loc : addedLocListGlobal) {
            locArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), true));
        }

        for (LocationEntitie locAll : allFilterLocList) {
            boolean status = false;
            for (LocationEntitie loc : addedLocListGlobal) {
                if (locAll.getId() == loc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                locArrayList.add(new LocationEntitie(locAll.getId(), locAll.getName(), false));

            }
        }

        onAddLocationToFilterFinishedListener.addLocationToFilter(locArrayList);


    }

    @Override
    public void addProductsToVisitsFilterStart(Products products, boolean addOrRemove, OnAddProductsToVisitsStartFilterFinishedListener onAddProductsToVisitsStartFilterFinishedListener) {
        onAddProductsToVisitsStartFilterFinishedListener.productsToVisitsFilterStart(products, addOrRemove);
    }

    @Override
    public void addProductsToVisitsFilter(ArrayList<Products> productsArrayListToFilterGlobal, Products products, boolean addOrRemove, OnAddProductsToVisitsFilterFinishedListener onAddProductsToVisitsFilterFinishedListener) {
        if (addOrRemove) {
            if (productsArrayListToFilterGlobal.isEmpty()) {
                productsArrayListToFilterGlobal.add(new Products(products.getId(), products.getName(), false));
            } else {
                boolean isFound = false;

                for (Products loca : productsArrayListToFilterGlobal) {
                    if (loca.getId() == products.getId()) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound)
                    productsArrayListToFilterGlobal.add(new Products(products.getId(), products.getName(), false));
            }
        } else {
            if (productsArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < productsArrayListToFilterGlobal.size(); i++) {
                    if (productsArrayListToFilterGlobal.get(i).getId() == products.getId()) {
                        productsArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }

        onAddProductsToVisitsFilterFinishedListener.productsToVisitsFilter(productsArrayListToFilterGlobal);

    }

    @Override
    public void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal, ArrayList<Products> allFilterProductsList, OnAddProductsToFilterFinishedListener onAddProductsToFilterFinishedListener) {
        final ArrayList<Products> productsArrayList = new ArrayList<Products>();

        for (Products products : addedLocListGlobal) {
            productsArrayList.add(new Products(products.getId(), products.getName(), true));
        }

        for (Products locAll : allFilterProductsList) {
            boolean status = false;
            for (Products loc : addedLocListGlobal) {
                if (locAll.getId() == loc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                productsArrayList.add(new Products(locAll.getId(), locAll.getName(), false));

            }
        }

        onAddProductsToFilterFinishedListener.addProductsToFilter(productsArrayList);


    }

    @Override
    public void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList, ArrayList<Doctor> selectedDocList, OnSetSelectedFilterDoctorsFinishedListener onSetSelectedFilterDoctorsFinishedListener) {

        ArrayList<Doctor> newDocList = new ArrayList<Doctor>();

        if (selectedDocList.isEmpty()) {
            for (Doctor d : totalDocList) {
                d.setSelect(false);
            }
            newDocList = totalDocList;
        } else {
            for (Doctor added : selectedDocList) {
                newDocList.add(new Doctor(added.getId(), added.getName(), true));
            }

            for (Doctor added : totalDocList) {
                newDocList.add(new Doctor(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newDocList.size(); i++) {
                for (int j = i + 1; j < newDocList.size(); j++) {
                    if (newDocList.get(i).getId() == newDocList.get(j).getId()) {
                        newDocList.remove(j);
                        j--;
                    }
                }
            }

        }

        onSetSelectedFilterDoctorsFinishedListener.showSelectedFilterDoctors(newDocList);


    }

    @Override
    public void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList, ArrayList<LocationEntitie> selectedLocList, OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener) {
        ArrayList<LocationEntitie> newLocList = new ArrayList<LocationEntitie>();
        if (selectedLocList.isEmpty()) {
            if(!totalLocList.isEmpty()){
                for (LocationEntitie l : totalLocList) {
                    l.setSelect(false);
                }
                newLocList = totalLocList;
            }else {

            }

        } else {
            for (LocationEntitie added : selectedLocList) {
                newLocList.add(new LocationEntitie(added.getId(), added.getName(), true));
            }
            for (LocationEntitie added : totalLocList) {
                newLocList.add(new LocationEntitie(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newLocList.size(); i++) {
                for (int j = i + 1; j < newLocList.size(); j++) {
                    if (newLocList.get(i).getId() == newLocList.get(j).getId()) {
                        newLocList.remove(j);
                        j--;
                    }
                }
            }

        }

        onSetSelectedFilterLocationFinishedListener.showSelectedFilterLocation(newLocList);
    }

    @Override
    public void setSelectedFilterProducts(ArrayList<Products> totalProList, ArrayList<Products> selectedProList, OnSetSelectedFilterProductsFinishedListener onSetSelectedFilterProductsFinishedListener) {
        ArrayList<Products> newProList = new ArrayList<Products>();
        if (selectedProList.isEmpty()) {
            for (Products p : totalProList) {
                p.setSelect(false);
            }
            newProList = totalProList;
        } else {
            for (Products added : selectedProList) {
                newProList.add(new Products(added.getId(), added.getName(), true));
            }
            for (Products added : totalProList) {
                newProList.add(new Products(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newProList.size(); i++) {
                for (int j = i + 1; j < newProList.size(); j++) {
                    if (newProList.get(i).getId() == newProList.get(j).getId()) {
                        newProList.remove(j);
                        j--;
                    }
                }
            }


        }
        onSetSelectedFilterProductsFinishedListener.showSelectedFilterProducts(newProList);

    }

    @Override
    public void visitsFilter(ArrayList<Visit> visitArrayList, String startDate, String endDate, ArrayList<Doctor> docList, ArrayList<LocationEntitie> locList, ArrayList<Products> proList, OnVisitsFilterFinishedListener onVisitsFilterFinishedListener) {

        ArrayList<Visit> filterdVisitList = new ArrayList<Visit>();


        //filter date - only start date
        if (!startDate.equals("") && endDate.equals("")) {

            for (Visit visit : visitArrayList) {
                if (visit.getVisitDate().substring(0, 10).equals(startDate)) {
                    ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                    for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                        visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                visitProducts.getImageUrl()));

                    }
                    filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                            visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                }

            }

            //filter date - start date and end date
        } else if (!startDate.equals("") && !endDate.equals("")) {
            SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = null;
            Date dateEnd = null;
            try {
                dateStart = oldFormat.parse(startDate);
                dateEnd = oldFormat.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateStart);

            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(dateEnd);

            List<Date> datesInRange = new ArrayList<>();

            while (endCalendar.after(calendar)) {
                Date result = calendar.getTime();
                datesInRange.add(result);
                for (Visit visit : visitArrayList) {
                    if (visit.getVisitDate().substring(0, 10).equals(oldFormat.format(result))) {
                        ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                        for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                            visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                    visitProducts.getImageUrl()));

                        }
                        filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                    }

                }
                calendar.add(Calendar.DATE, 1);
            }
        } else {
        }


        //filter doctor - wihout filter dates
        if (filterdVisitList.isEmpty()) {

            if (docList.isEmpty()) {

            } else {
                for (Visit visit : visitArrayList) {

                    for (Doctor doc : docList) {
                        if (visit.getDoctorID() == doc.getId()) {
                            ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                            for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                        visitProducts.getImageUrl()));

                            }
                            filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                    visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                        } else {

                        }
                    }
                }


                for (int i = 0; i < filterdVisitList.size(); i++) {
                    for (int j = i + 1; j < filterdVisitList.size(); j++) {
                        if (filterdVisitList.get(i).getId() == filterdVisitList.get(j).getId()) {
                            filterdVisitList.remove(j);
                            j--;
                        }
                    }
                }

            }


            //filter doctor - wight filter dates
        } else {
            if (docList.isEmpty()) {

            } else {

                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (int j = 0; j < docList.size(); j++) {
                        if (filterdVisitList.get(i).getDoctorID() == docList.get(j).getId()) {
                            status = true;
                            break;
                        } else {
                            status = false;
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }

            }


        }


        //filter doctor - wihout filter dates

        if (filterdVisitList.isEmpty()) {

            if (locList.isEmpty()) {
            } else {
                for (Visit visit : visitArrayList) {


                    for (LocationEntitie loc : locList) {
                        if (visit.getLocationID() == loc.getId()) {
                            ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                            for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                        visitProducts.getImageUrl()));

                            }
                            filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                    visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));


                        } else {


                        }
                    }
                }
                for (int i = 0; i < filterdVisitList.size(); i++) {
                    for (int j = i + 1; j < filterdVisitList.size(); j++) {
                        if (filterdVisitList.get(i).getId() == filterdVisitList.get(j).getId()) {
                            filterdVisitList.remove(j);
                            j--;
                        }
                    }
                }

            }

            //filter location - wight filter dates
        } else {

            if (locList.isEmpty()) {
            } else {
                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (int j = 0; j < locList.size(); j++) {
                        if (filterdVisitList.get(i).getLocationID() == locList.get(j).getId()) {
                            status = true;
                            break;
                        } else {
                            status = false;
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }

            }
        }


        //filter Products - wihout filter dates

        if (filterdVisitList.isEmpty()) {
            if (proList.isEmpty()) {
            } else {
                for (Visit visit : visitArrayList) {
                    boolean status = false;
                    ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                    for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                        if (status) {
                        } else {
                            for (Products pro : proList) {
                               int aa =pro.getId();

                                int bb =visitProducts.getProductID();

                                if (pro.getId() == visitProducts.getProductID()) {
                                    status = true;
                                    break;
                                } else {
                                    status = false;
                                }
                            }
                        }

                        visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                    visitProducts.getImageUrl()));


                    }

                    if (status) {
                        filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));
                    } else {

                    }


                }


            }

            //filter location - wight filter dates
        } else {
            if (proList.isEmpty()) {
            } else {
                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (VisitProducts visitProducts : filterdVisitList.get(i).getVisitsProduct()) {
                        if (status) {
                        } else {
                            for (int j = 0; j < proList.size(); j++) {
                                if (visitProducts.getProductID() == proList.get(j).getId()) {
                                    status = true;
                                    break;
                                } else {
                                    status = false;
                                }
                            }
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }
            }
        }

        if ((startDate.equals("")) & (endDate.equals("")) & (docList.isEmpty()) & (locList.isEmpty()) & (proList.isEmpty()) ) {
            filterdVisitList = visitArrayList;
        } else {
        }

        onVisitsFilterFinishedListener.visitsFilterList(filterdVisitList);

    }

    @Override
    public void addVisitImage(Context context, final Bitmap img, final String imageCode, final OnAddVisitImageFinishedListener onAddVisitImageFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onAddVisitImageFinishedListener.addVisitImageNetworkFail();
        } else if (imageCode.equals("")) {
        } else {
            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("imagecode", imageCode);
            jsonObject.addProperty("ImageBase64", BitMapToString(img));



            try {
                apiService.saveVisitImage(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean respond) {

                                visitImageRespond = respond;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onAddVisitImageFinishedListener.addVisitImageFail("Something went wrong, Please try again", img, imageCode);
                            }

                            @Override
                            public void onComplete() {

                                if (visitImageRespond) {
                                    onAddVisitImageFinishedListener.addVisitImageSuccessful();
                                } else {
                                    onAddVisitImageFinishedListener.addVisitImageFail("Something went wrong, Please try again", img, imageCode);

                                }

                            }
                        });

            } catch (Exception ex) {
                onAddVisitImageFinishedListener.addVisitImageFail("Something went wrong, Please try again", img, imageCode);
            }


        }


    }

    @Override
    public void showVisitDetails(Visit visit, OnShowVisitDetailsListener onShowVisitDetailsListener) {
        onShowVisitDetailsListener.visitDetails(visit);
    }

    @Override
    public void searchDocForFilter(ArrayList<Doctor> docArrayList, String docName, OnSearchDocForFilterFinishedListener onSearchDocForFilterFinishedListener) {

        final ArrayList<Doctor> searchArrayList = new ArrayList<Doctor>();
        if (docArrayList.isEmpty()) {
        } else {
            for (Doctor doc : docArrayList) {
                if (doc.getName().equals(docName)) {
                    searchArrayList.add(new Doctor(doc.getId(), doc.getName(), false));
                } else {
                }
            }
        }

        if ((searchArrayList.isEmpty()) && (!docArrayList.isEmpty())) {
            if (docName.isEmpty() || docName.equals("") || docName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(docArrayList);
            } else {
                for (Doctor doc : docArrayList) {
                    String text = doc.getName();
                    String patternString = docName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Doctor(doc.getId(), doc.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchDocForFilterFinishedListener.docListForFilter(searchArrayList);



    }

    @Override
    public void searchLocForFilter(ArrayList<LocationEntitie> locArrayList, String locName, OnSearchLocForFilterFinishedListener onSearchLocForFilterFinishedListener) {


        final ArrayList<LocationEntitie> searchArrayList = new ArrayList<LocationEntitie>();
        if (locArrayList.isEmpty()) {
        } else {
            for (LocationEntitie loc : locArrayList) {
                if (loc.getName().equals(locName)) {
                    searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), false));
                } else {
                }
            }
        }


        if ((searchArrayList.isEmpty()) && (!locArrayList.isEmpty())) {
            if (locName.isEmpty() || locName.equals("") || locName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(locArrayList);
            } else {
                for (LocationEntitie loc : locArrayList) {
                    String text = loc.getName();
                    String patternString = locName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchLocForFilterFinishedListener.locListForFilter(searchArrayList);


    }

    @Override
    public void searchProductsForFilter(ArrayList<Products> proArrayList, String productsName, OnSearchProductsForFilterFinishedListener onSearchProductsForFilterFinishedListener) {
        final ArrayList<Products> searchArrayList = new ArrayList<Products>();
        if (proArrayList.isEmpty()) {
        } else {
            for (Products pro : proArrayList) {
                if (pro.getName().equals(productsName)) {
                    searchArrayList.add(new Products(pro.getId(), pro.getName(), false));
                } else {
                }
            }
        }


        if ((searchArrayList.isEmpty()) && (!proArrayList.isEmpty())) {
            if (productsName.isEmpty() || productsName.equals("") || productsName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(proArrayList);
            } else {
                for (Products pro : proArrayList) {
                    String text = pro.getName();
                    String patternString = productsName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Products(pro.getId(), pro.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchProductsForFilterFinishedListener.productsListForFilter(searchArrayList);



    }

    @Override
    public void getTargetDetails(Context context, final OnTargetDetailsFinishedListener onTargetDetailsFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
        } else {
            try {

                targetDetails = new TargetDetails();

                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                apiService.getTargetAchievement(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TargetDetails>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(TargetDetails respond) {
                                targetDetails =respond;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onTargetDetailsFinishedListener.targetDetailsError("Something went wrong when getting Target from server , Please try again "+e);
                            }

                            @Override
                            public void onComplete() {
                                onTargetDetailsFinishedListener.targetDetails(targetDetails);
                            }
                        });

            } catch (Exception ex) {
                onTargetDetailsFinishedListener.targetDetailsError("Something went wrong when getting Target from server , Please try again "+ex);
            }


        }

    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}


