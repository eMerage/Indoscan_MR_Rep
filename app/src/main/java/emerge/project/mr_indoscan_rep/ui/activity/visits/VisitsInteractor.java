package emerge.project.mr_indoscan_rep.ui.activity.visits;


import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.Sample;
import emerge.project.mr_indoscan_rep.utils.entittes.SampleType;
import emerge.project.mr_indoscan_rep.utils.entittes.TargetDetails;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface VisitsInteractor {


    interface OnsetNavigationFinishedListener {
        void navigationItems(ArrayList<Navigation> navigationArrayList);
    }
    void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener);




    interface OnGetVisitsFinishedListener {
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

    }
    void getVisits(Context context, int locationID, int doctorID, String date, OnGetVisitsFinishedListener onGetVisitsFinishedListener);


    interface OnGetDoctorFinishedListener {
        void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames);
        void doctorsEmpty();
        void doctorsGetingError(String msg);
        void doctorsGetingFail(String failMsg);
        void doctorsGetingNetworkFail();
    }
    void getDoctors(Context context, Location location, OnGetDoctorFinishedListener onGetDoctorFinishedListener);


    interface OnSearchDoctorFinishedListener {
        void searchDoctorList(ArrayList<Doctor> doctorArrayList);
    }
    void searchDoctor(ArrayList<Doctor> doctorsArrayList,String doctorName, OnSearchDoctorFinishedListener onSearchDoctorFinishedListener);



    interface OnSelectedDoctorsFinishedListener {
        void selectedDoc(int docID);
    }
    void getSelectedDoc(Doctor doc, OnSelectedDoctorsFinishedListener onSelectedDoctorsFinishedListener);


    interface OnCreateVisitNumberFinishedListener {
        void visitNumber(String visitNumber);
    }
    void createVisitNumber(Context context,OnCreateVisitNumberFinishedListener onCreateVisitNumberFinishedListener);


    interface OnGetLocationFinishedListener {
        void locationList(ArrayList<LocationEntitie> locationArrayList);
        void locationListEmpty();
        void locationetingError(String msg);
        void locationFail(String failMsg);
        void locationNetworkFail();
    }
    void getLocation(Context context,int docId,Location location, OnGetLocationFinishedListener onGetLocationFinishedListener);


    interface OnSelectedLocationFinishedListener {
        void selectedLocation(int locationID);
    }
    void getSelectedLocation(LocationEntitie location, OnSelectedLocationFinishedListener onSelectedLocationFinishedListener);


    interface OnProductsFinishedListener {
        void productsList(ArrayList<Products> productArrayList,ArrayList<String> productNameList);
        void productsEmpty();
        void productsGetingFail(String failMsg);
        void productsGetingNetworkFail();
    }
    void getProducts(Context context,ArrayList<Products> productArrayList,OnProductsFinishedListener onProductsFinishedListener);

    interface OnSearchProductFinishedListener {
        void searchProductList(ArrayList<Products> productArrayList);
    }
    void searchProduct(ArrayList<Products> productArrayList,String productName, OnSearchProductFinishedListener onSearchProductFinishedListener);


    interface OnAddProductsToVisitsStartFinishedListener {
        void productsToVisitsStart(Products products,boolean addOrRemove);
    }
    void addProductsToVisitsStart(Products products,boolean addOrRemove, OnAddProductsToVisitsStartFinishedListener onAddProductsToVisitsStartFinishedListener);


    interface OnAddProductsToVisitsFinishedListener {
        void productsToVisits(ArrayList<Products> productsArrayList);
    }
    void addProductsToVisits(ArrayList<Products> productsListGlobal,Products products,boolean addOrRemove, OnAddProductsToVisitsFinishedListener onAddProductsToVisitsFinishedListener);



    interface OnShowProductsToVisitsFinishedListener {
        void showProducts(ArrayList<Products> addedArrayList);
    }
    void showProductsToVisits(ArrayList<Products> addedProListGlobal,ArrayList<Products> allProcList, OnShowProductsToVisitsFinishedListener onShowProductsToVisitsFinishedListener);




    interface OnGenerateImageCodeFinishedListener {
        void generateImageCode(String imageCode);
    }
    void generateImageCode(Context context,String vistisNumber,OnGenerateImageCodeFinishedListener onGenerateImageCodeFinishedListener);



    interface OnAddVisitsFinishedListener {
        void addVisitsSuccessful();
        void addVisitsEmpty(String msg);
        void addVisitsFail(String failMsg,int docid,String visitsNumber,String imageCode,int locationID,ArrayList<Products> productsArrayList,String comment);
        void addVisitsNetworkFail();
    }
    void addVisits(Context context,int docid,String visitsNumber,String imageCode,int locationID,ArrayList<Products> productsArrayList,String comment,Location location,ArrayList<Sample> sampleArrayList,OnAddVisitsFinishedListener onAddVisitsFinishedListener);




    interface OnAddDoctorsToVisitsStartFilterFinishedListener {
        void doctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove);
    }
    void addDoctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove, OnAddDoctorsToVisitsStartFilterFinishedListener onAddDoctorsToVisitsStartFilterFinishedListener);


    interface OnAddDoctorsToVisitsFilterFinishedListener {
        void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter);
    }
    void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal,Doctor doctor,boolean addOrRemove, OnAddDoctorsToVisitsFilterFinishedListener onAddDoctorsToVisitsFilterFinishedListener );


    interface OnAddDoctorsToFilterFinishedListener {
        void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter);
    }
    void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal,ArrayList<Doctor> allFilterDocList, OnAddDoctorsToFilterFinishedListener onAddDoctorsToFilterFinishedListener);




    interface OnAddLocationToVisitsStartFilterFinishedListener {
        void locationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    }
    void addLocationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove, OnAddLocationToVisitsStartFilterFinishedListener onAddLocationToVisitsStartFilterFinishedListener);


    interface OnAddLocationToVisitsFilterFinishedListener {
        void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter);
    }
    void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal,LocationEntitie locationEntitie,boolean addOrRemove, OnAddLocationToVisitsFilterFinishedListener onAddLocationToVisitsFilterFinishedListener );


    interface OnAddLocationToFilterFinishedListener {
        void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter);
    }
    void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal,ArrayList<LocationEntitie> allFilterLocList, OnAddLocationToFilterFinishedListener onAddLocationToFilterFinishedListener);




    interface OnAddProductsToVisitsStartFilterFinishedListener {
        void productsToVisitsFilterStart(Products products,boolean addOrRemove);
    }
    void addProductsToVisitsFilterStart(Products products,boolean addOrRemove, OnAddProductsToVisitsStartFilterFinishedListener onAddProductsToVisitsStartFilterFinishedListener);


    interface OnAddProductsToVisitsFilterFinishedListener {
        void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter);
    }
    void addProductsToVisitsFilter(ArrayList<Products> ProductsArrayListToFilterGlobal,Products products,boolean addOrRemove, OnAddProductsToVisitsFilterFinishedListener onAddProductsToVisitsFilterFinishedListener );


    interface OnAddProductsToFilterFinishedListener {
        void addProductsToFilter(ArrayList<Products> addedArrayListToFilter);
    }
    void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal,ArrayList<Products> allFilterProductsList, OnAddProductsToFilterFinishedListener onAddProductsToFilterFinishedListener );




    interface OnSetSelectedFilterDoctorsFinishedListener {
        void showSelectedFilterDoctors(ArrayList<Doctor> filterList);
    }
    void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList,ArrayList<Doctor> selectedDocList, OnSetSelectedFilterDoctorsFinishedListener onSetSelectedFilterDoctorsFinishedListener );


    interface OnSetSelectedFilterLocationFinishedListener {
        void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList);
    }
    void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList,ArrayList<LocationEntitie> selectedLocList,  OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener );


    interface OnSetSelectedFilterProductsFinishedListener {
        void showSelectedFilterProducts(ArrayList<Products> filterList);
    }
    void setSelectedFilterProducts(ArrayList<Products> totalProList,ArrayList<Products> selectedProList,OnSetSelectedFilterProductsFinishedListener onSetSelectedFilterProductsFinishedListener  );



    interface OnVisitsFilterFinishedListener {
        void visitsFilterListEmpty(String msg,ArrayList<Visit> visitArrayList);
        void visitsFilterList(ArrayList<Visit> visitArrayList);
    }
    void visitsFilter(ArrayList<Visit> visitArrayList,String startDate,String endDate,ArrayList<Doctor> docList,ArrayList<LocationEntitie> locList,ArrayList<Products> proList,OnVisitsFilterFinishedListener onVisitsFilterFinishedListener);





    interface OnAddVisitImageFinishedListener {
        void addVisitImageSuccessful();
        void addVisitImageFail(String failMsg,Bitmap img,String imageCode);
        void addVisitImageNetworkFail();
    }
    void addVisitImage(Context context, Bitmap img,String imageCode,OnAddVisitImageFinishedListener onAddVisitImageFinishedListener);


    interface OnShowVisitDetailsListener {
        void visitDetails(Visit visit);
    }
    void showVisitDetails(Visit visit,OnShowVisitDetailsListener onShowVisitDetailsListener);



    interface OnSearchDocForFilterFinishedListener {
        void docListForFilter(ArrayList<Doctor> docArrayList);
    }
    void searchDocForFilter(ArrayList<Doctor> docArrayList,String docName, OnSearchDocForFilterFinishedListener  onSearchDocForFilterFinishedListener);


    interface OnSearchLocForFilterFinishedListener {
        void locListForFilter(ArrayList<LocationEntitie> locArrayList);
    }
    void searchLocForFilter(ArrayList<LocationEntitie> locArrayList,String locName, OnSearchLocForFilterFinishedListener  onSearchLocForFilterFinishedListener);


    interface OnSearchProductsForFilterFinishedListener {
        void productsListForFilter(ArrayList<Products> proArrayList);
    }
    void searchProductsForFilter(ArrayList<Products> proArrayList,String productsName,OnSearchProductsForFilterFinishedListener onSearchProductsForFilterFinishedListener);

    interface OnTargetDetailsFinishedListener {
        void targetDetails(TargetDetails targetDetails);
        void targetDetailsError(String failMsg);
    }
    void getTargetDetails(Context context,OnTargetDetailsFinishedListener  onTargetDetailsFinishedListener);



    interface OnGetSampleTypeFinishedListener {
        void sampleTypeListEmpty(String msg);
        void sampleTypeList(ArrayList<SampleType> sampleTypes);
    }
    void getSampleType(Context context,OnGetSampleTypeFinishedListener  onGetSampleTypeFinishedListener);


    interface OnSelectedSampleTypeFinishedListener {
        void selectedSampleType(int sampleTypeID);
    }
    void getSelectedSampleType(SampleType sampleType, OnSelectedSampleTypeFinishedListener onSelectedSampleTypeFinishedListener);


    interface OnGetSampleFinishedListener {
        void sampleListEmpty(String msg,int sampleTypeID);
        void sampleList(ArrayList<Sample> sampleTypes);
    }
    void getSample(Context context,int sampletypeid,ArrayList<Sample> addedSample ,OnGetSampleFinishedListener  onGetSampleFinishedListener);


    interface OnAddSampleToVisitFinishedListener {
        void sampleToVisit(Sample sample,boolean addOrRemove);
    }
    void addSampleToVisit(Sample sample,boolean addOrRemove, OnAddSampleToVisitFinishedListener onAddSampleToVisitFinishedListener);



    interface OnGetUpdatedSampleFinishedListener {
        void updatedSampleList(ArrayList<Sample> sampleTypes);
    }
    void updateSample(Context context,ArrayList<Sample> allSampleTypes,ArrayList<Sample> addedSampleTypes ,  OnGetUpdatedSampleFinishedListener onGetUpdatedSampleFinishedListener);



}
