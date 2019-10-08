package emerge.project.mr_indoscan_rep.ui.activity.visits;


import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.TargetDetails;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface VisitsView {


    void navigationItems(ArrayList<Navigation> navigationArrayList);

    void visitsList(ArrayList<Visit> visitArrayList);
    void visitsListNoItems();
    void visitsListFail(String failMsg,int locationID, int doctorID, String date);
    void visitsListNetworkFail();

    void visitsDoctorsList( ArrayList<Doctor> docListForFilter);
    void visitsDoctorsNameList( ArrayList<String> docNameListForFilter);

    void visitsLocationList( ArrayList<LocationEntitie> locListForFilter);
    void visitsLocationNameList( ArrayList<String> locNameListForFilter);

    void visitsProductsList( ArrayList<Products> productsListForFilter);
    void visitsProductsNameList( ArrayList<String> productsNameListForFilter);


    void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames);
    void doctorsEmpty();
    void doctorsGetingFail(String failMsg);
    void doctorsGetingError(String msg);
    void doctorsGetingNetworkFail();


    void searchDoctorList(ArrayList<Doctor> doctorArrayList);

    void visitNumber(String visitNumber);


    void locationList(ArrayList<LocationEntitie> locationArrayList);
    void locationListEmpty();
    void locationetingError(String msg);
    void locationFail(String failMsg);
    void locationNetworkFail();


    void selectedDoc(int docID);
    void selectedLocation(int locationID);

    void productsList(ArrayList<Products> productArrayList, ArrayList<String> productNameList);
    void productsEmpty();
    void productsGetingFail(String failMsg);
    void productsGetingNetworkFail();

    void searchProductList(ArrayList<Products> productArrayList);


    void productsToVisitsStart(Products products,boolean addOrRemove);
    void productsToVisits(ArrayList<Products> productsArrayList);

    void generateImageCode(String imageCode);


    void addVisitsSuccessful();
    void addVisitsEmpty(String msg);
    void addVisitsFail(String failMsg,int docid,String visitsNumber,String imageCode,int locationID,ArrayList<Products> productsArrayList,String comment);
    void addVisitsNetworkFail();

    void showProducts(ArrayList<Products> addedArrayList);


    void doctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove);
    void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter);
    void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter);



    void locationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter);
    void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter);




    void productsToVisitsFilterStart(Products products,boolean addOrRemove);
    void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter);
    void addProductsToFilter(ArrayList<Products> addedArrayListToFilter);




    void showSelectedFilterDoctors(ArrayList<Doctor> filterList);
    void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList);
    void showSelectedFilterProducts(ArrayList<Products> filterList);





    void visitsFilterListEmpty(String msg,ArrayList<Visit> visitArrayList);
    void visitsFilterList(ArrayList<Visit> visitArrayList);





    void addVisitImageSuccessful();
    void addVisitImageFail(String failMsg, Bitmap img, String imageCode);
    void addVisitImageNetworkFail();

    void visitDetails(Visit visit);


    void docListForFilter(ArrayList<Doctor> docArrayList);

    void locListForFilter(ArrayList<LocationEntitie> locArrayList);

    void productsListForFilter(ArrayList<Products> proArrayList);

    void targetDetails(TargetDetails target);
    void targetDetailsError(String failMsg);



}
