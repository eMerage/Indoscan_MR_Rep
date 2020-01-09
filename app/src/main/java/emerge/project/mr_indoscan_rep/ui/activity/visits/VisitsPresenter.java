package emerge.project.mr_indoscan_rep.ui.activity.visits;


import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.Sample;
import emerge.project.mr_indoscan_rep.utils.entittes.SampleType;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface VisitsPresenter {



    void setNavigation();




    void getVisits(Context context, int locationID, int doctorID, String date);
    void getDoctors(Context context, Location location);
    void searchDoctor(ArrayList<Doctor> doctorsArrayList, String doctorName);
    void createVisitNumber(Context context);
    void getLocation(Context context,int docId,Location location);
    void getSelectedDoc(Doctor doc);
    void getSelectedLocation(LocationEntitie location);
    void getProducts(Context context,ArrayList<Products> productArrayList);
    void searchProduct(ArrayList<Products> productArrayList, String productName);

    void addProductsToVisitsStart(Products products,boolean addOrRemove);

    void addProductsToVisits(ArrayList<Products> productsListGlobal,Products products,boolean addOrRemove);
    void generateImageCode(Context context,String vistisNumber);

    void addVisits(Context context,int docid,String visitsNumber,String imageCode,int locationID,ArrayList<Products> productsArrayList,
                   String comment,Location location,ArrayList<Sample> sampleArrayList);

    void showProductsToVisits(ArrayList<Products> addedProListGlobal,ArrayList<Products> allProcList);


    void addDoctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove);

    void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal,Doctor doctor,boolean addOrRemove );


    void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal,ArrayList<Doctor> allFilterDocList);



    void addLocationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal,LocationEntitie locationEntitie,boolean addOrRemove );
    void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal,ArrayList<LocationEntitie> allFilterLocList);



    void addProductsToVisitsFilterStart(Products products,boolean addOrRemove);
    void addProductsToVisitsFilter(ArrayList<Products> ProductsArrayListToFilterGlobal,Products products,boolean addOrRemove);
    void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal,ArrayList<Products> allFilterProductsList );


    void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList,ArrayList<Doctor> selectedDocList);
    void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList,ArrayList<LocationEntitie> selectedLocList );
    void setSelectedFilterProducts(ArrayList<Products> totalProList,ArrayList<Products> selectedProList );


    void visitsFilter(ArrayList<Visit> visitArrayList,String startDate, String endDate, ArrayList<Doctor> docList, ArrayList<LocationEntitie> locList,ArrayList<Products> proList);


    void addVisitImage(Context context, Bitmap img, String imageCode);

    void showVisitDetails(Visit visit);



    void searchDocForFilter(ArrayList<Doctor> docArrayList,String docName);


    void searchLocForFilter(ArrayList<LocationEntitie> locArrayList,String locName);



    void searchProductsForFilter(ArrayList<Products> proArrayList,String productsName);


    void getTargetDetails(Context context);



    void getSampleType(Context context);

    void getSelectedSampleType(SampleType sampleType);



    void getSample(Context context,int sampletypeid,ArrayList<Sample> addedSample);
    void addSampleToVisit(Sample sample,boolean addOrRemove);


    void updateSample(Context context,ArrayList<Sample> allSampleTypes,ArrayList<Sample> addedSampleTypes );





}
