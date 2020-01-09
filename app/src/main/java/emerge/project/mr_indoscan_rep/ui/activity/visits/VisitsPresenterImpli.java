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

public class VisitsPresenterImpli implements VisitsPresenter,
        VisitsInteractor.OnsetNavigationFinishedListener,
        VisitsInteractor.OnGetVisitsFinishedListener,
        VisitsInteractor.OnGetDoctorFinishedListener,
        VisitsInteractor.OnSearchDoctorFinishedListener,
        VisitsInteractor.OnCreateVisitNumberFinishedListener,
        VisitsInteractor.OnGetLocationFinishedListener,
        VisitsInteractor.OnSelectedDoctorsFinishedListener,
        VisitsInteractor.OnSelectedLocationFinishedListener,
        VisitsInteractor.OnProductsFinishedListener,
        VisitsInteractor.OnSearchProductFinishedListener,
        VisitsInteractor.OnAddProductsToVisitsStartFinishedListener,
        VisitsInteractor.OnAddProductsToVisitsFinishedListener,
        VisitsInteractor.OnGenerateImageCodeFinishedListener,
        VisitsInteractor.OnAddVisitsFinishedListener,
        VisitsInteractor.OnShowProductsToVisitsFinishedListener,
        VisitsInteractor.OnAddDoctorsToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddDoctorsToVisitsFilterFinishedListener,
        VisitsInteractor.OnAddDoctorsToFilterFinishedListener,
        VisitsInteractor.OnAddLocationToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddLocationToVisitsFilterFinishedListener,
        VisitsInteractor.OnAddLocationToFilterFinishedListener,
        VisitsInteractor.OnVisitsFilterFinishedListener,
        VisitsInteractor.OnAddProductsToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddProductsToVisitsFilterFinishedListener,
        VisitsInteractor.OnAddProductsToFilterFinishedListener,
        VisitsInteractor.OnSetSelectedFilterDoctorsFinishedListener,
        VisitsInteractor.OnSetSelectedFilterLocationFinishedListener,
        VisitsInteractor.OnSetSelectedFilterProductsFinishedListener,
        VisitsInteractor.OnAddVisitImageFinishedListener,
        VisitsInteractor.OnShowVisitDetailsListener,
        VisitsInteractor.OnSearchDocForFilterFinishedListener,
        VisitsInteractor.OnSearchLocForFilterFinishedListener,
        VisitsInteractor.OnSearchProductsForFilterFinishedListener,
        VisitsInteractor.OnTargetDetailsFinishedListener,
        VisitsInteractor.OnGetSampleTypeFinishedListener ,
        VisitsInteractor.OnSelectedSampleTypeFinishedListener,
 VisitsInteractor.OnGetSampleFinishedListener,
        VisitsInteractor.OnAddSampleToVisitFinishedListener,
        VisitsInteractor.OnGetUpdatedSampleFinishedListener
{




    private VisitsView visitsView;
    VisitsInteractor visitsInteractor;


    public VisitsPresenterImpli(VisitsView visitsview) {
        this.visitsView = visitsview;
        this.visitsInteractor = new VisitsInteractorImpil();

    }


    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        visitsView.navigationItems(navigationArrayList);
    }

    @Override
    public void setNavigation() {
        visitsInteractor.setNavigation(this);
    }


    @Override
    public void visitsList(ArrayList<Visit> visitArrayList) {
        visitsView.visitsList(visitArrayList);
    }

    @Override
    public void visitsListNoItems() {
        visitsView.visitsListNoItems();
    }

    @Override
    public void visitsListFail(String failMsg, int locationID, int doctorID, String date) {
        visitsView.visitsListFail(failMsg, locationID, doctorID, date);
    }

    @Override
    public void visitsListNetworkFail() {
        visitsView.visitsListNetworkFail();
    }

    @Override
    public void visitsDoctorsList(ArrayList<Doctor> docListForFilter) {
        visitsView.visitsDoctorsList(docListForFilter);
    }

    @Override
    public void visitsDoctorsNameList(ArrayList<String> docNameListForFilter) {
        visitsView.visitsDoctorsNameList(docNameListForFilter);
    }

    @Override
    public void visitsLocationList(ArrayList<LocationEntitie> locListForFilter) {
        visitsView.visitsLocationList(locListForFilter);
    }

    @Override
    public void visitsLocationNameList(ArrayList<String> locNameListForFilter) {
        visitsView.visitsLocationNameList(locNameListForFilter);
    }

    @Override
    public void visitsProductsList(ArrayList<Products> productsListForFilter) {
        visitsView.visitsProductsList(productsListForFilter);
    }

    @Override
    public void visitsProductsNameList(ArrayList<String> productsNameListForFilter) {
        visitsView.visitsProductsNameList(productsNameListForFilter);
    }


    @Override
    public void getVisits(Context context, int locationID, int doctorID, String date) {
        visitsInteractor.getVisits(context, locationID, doctorID, date, this);
    }

    @Override
    public void getDoctors(Context context, Location location) {
        visitsInteractor.getDoctors(context, location, this);
    }


    @Override
    public void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames) {
        visitsView.doctorsList(doctorArrayList, doctorsNames);
    }

    @Override
    public void doctorsEmpty() {
        visitsView.doctorsEmpty();
    }

    @Override
    public void doctorsGetingError(String msg) {
        visitsView.doctorsGetingError(msg);
    }

    @Override
    public void doctorsGetingFail(String failMsg) {
        visitsView.doctorsGetingFail(failMsg);
    }

    @Override
    public void doctorsGetingNetworkFail() {
        visitsView.doctorsGetingNetworkFail();
    }


    @Override
    public void searchDoctor(ArrayList<Doctor> doctorsArrayList, String doctorName) {
        visitsInteractor.searchDoctor(doctorsArrayList, doctorName, this);
    }

    @Override
    public void searchDoctorList(ArrayList<Doctor> doctorArrayList) {
        visitsView.searchDoctorList(doctorArrayList);
    }


    @Override
    public void createVisitNumber(Context context) {
        visitsInteractor.createVisitNumber(context, this);
    }


    @Override
    public void visitNumber(String visitNumber) {
        visitsView.visitNumber(visitNumber);
    }


    @Override
    public void getLocation(Context context, int docId, Location location) {
        visitsInteractor.getLocation(context, docId, location, this);
    }

    @Override
    public void locationList(ArrayList<LocationEntitie> locationArrayList) {
        visitsView.locationList(locationArrayList);
    }

    @Override
    public void locationListEmpty() {
        visitsView.locationListEmpty();
    }

    @Override
    public void locationetingError(String msg) {
        visitsView.locationetingError(msg);
    }

    @Override
    public void locationFail(String failMsg) {
        visitsView.locationFail(failMsg);
    }

    @Override
    public void locationNetworkFail() {
        visitsView.locationNetworkFail();
    }


    @Override
    public void getSelectedDoc(Doctor doc) {
        visitsInteractor.getSelectedDoc(doc, this);
    }

    @Override
    public void selectedDoc(int docID) {
        visitsView.selectedDoc(docID);
    }


    @Override
    public void getSelectedLocation(LocationEntitie location) {
        visitsInteractor.getSelectedLocation(location, this);
    }


    @Override
    public void selectedLocation(int locationID) {
        visitsView.selectedLocation(locationID);
    }


    @Override
    public void getProducts(Context context, ArrayList<Products> productArrayList) {
        visitsInteractor.getProducts(context, productArrayList, this);
    }


    @Override
    public void productsList(ArrayList<Products> productArrayList, ArrayList<String> productNameList) {
        visitsView.productsList(productArrayList, productNameList);
    }

    @Override
    public void productsEmpty() {
        visitsView.productsEmpty();
    }

    @Override
    public void productsGetingFail(String failMsg) {
        visitsView.productsGetingFail(failMsg);
    }

    @Override
    public void productsGetingNetworkFail() {
        visitsView.productsGetingNetworkFail();
    }


    @Override
    public void searchProduct(ArrayList<Products> productArrayList, String productName) {
        visitsInteractor.searchProduct(productArrayList, productName, this);
    }

    @Override
    public void searchProductList(ArrayList<Products> productArrayList) {
        visitsView.searchProductList(productArrayList);
    }


    @Override
    public void addProductsToVisitsStart(Products products, boolean addOrRemove) {
        visitsInteractor.addProductsToVisitsStart(products, addOrRemove, this);
    }

    @Override
    public void productsToVisitsStart(Products products, boolean addOrRemove) {
        visitsView.productsToVisitsStart(products, addOrRemove);
    }


    @Override
    public void addProductsToVisits(ArrayList<Products> productsListGlobal, Products products, boolean addOrRemove) {
        visitsInteractor.addProductsToVisits(productsListGlobal, products, addOrRemove, this);
    }

    @Override
    public void productsToVisits(ArrayList<Products> productsArrayList) {
        visitsView.productsToVisits(productsArrayList);
    }


    @Override
    public void generateImageCode(Context context, String vistisNumber) {
        visitsInteractor.generateImageCode(context, vistisNumber, this);
    }


    @Override
    public void generateImageCode(String imageCode) {
        visitsView.generateImageCode(imageCode);
    }


    @Override
    public void addVisits(Context context, int docid, String visitsNumber, String imageCode, int locationID, ArrayList<Products> productsArrayList,
                          String comment, Location location,ArrayList<Sample> sampleArrayList) {
        visitsInteractor.addVisits(context, docid, visitsNumber, imageCode, locationID, productsArrayList, comment, location, sampleArrayList,this);
    }


    @Override
    public void addVisitsSuccessful() {
        visitsView.addVisitsSuccessful();
    }

    @Override
    public void addVisitsEmpty(String msg) {
        visitsView.addVisitsEmpty(msg);
    }

    @Override
    public void addVisitsFail(String failMsg, int docid, String visitsNumber, String imageCode, int locationID, ArrayList<Products> productsArrayList, String comment) {
        visitsView.addVisitsFail(failMsg, docid, visitsNumber, imageCode, locationID, productsArrayList, comment);
    }

    @Override
    public void addVisitsNetworkFail() {
        visitsView.addVisitsNetworkFail();
    }


    @Override
    public void showProductsToVisits(ArrayList<Products> addedProListGlobal, ArrayList<Products> allProcList) {
        visitsInteractor.showProductsToVisits(addedProListGlobal, allProcList, this);
    }


    @Override
    public void showProducts(ArrayList<Products> addedArrayList) {
        visitsView.showProducts(addedArrayList);
    }


    @Override
    public void addDoctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove) {
        visitsInteractor.addDoctorsToVisitsFilterStart(doctor, addOrRemove, this);
    }

    @Override
    public void doctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove) {
        visitsView.doctorsToVisitsFilterStart(doctor, addOrRemove);
    }


    @Override
    public void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal, Doctor doctor, boolean addOrRemove) {
        visitsInteractor.addDoctorsToVisitsFilter(docArrayListToFilterGlobal, doctor, addOrRemove, this);
    }


    @Override
    public void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter) {
        visitsView.doctorsToVisitsFilter(docArrayListToFilter);
    }


    @Override
    public void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal, ArrayList<Doctor> allFilterDocList) {
        visitsInteractor.showAddDoctorsToFilter(addedDocListGlobal, allFilterDocList, this);
    }

    @Override
    public void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter) {
        visitsView.addDoctorsToFilter(addedArrayListToFilter);
    }


    @Override
    public void addLocationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsInteractor.addLocationToVisitsFilterStart(locationEntitie, addOrRemove, this);
    }

    @Override
    public void locationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsView.locationToVisitsFilterStart(locationEntitie, addOrRemove);
    }


    @Override
    public void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal, LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsInteractor.addLocationToVisitsFilter(locArrayListToFilterGlobal, locationEntitie, addOrRemove, this);
    }

    @Override
    public void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter) {
        visitsView.locationToVisitsFilter(locArrayListToFilter);
    }


    @Override
    public void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal, ArrayList<LocationEntitie> allFilterLocList) {
        visitsInteractor.showAddLocationToFilter(addedLocListGlobal, allFilterLocList, this);
    }


    @Override
    public void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter) {
        visitsView.addLocationToFilter(addedArrayListToFilter);
    }


    @Override
    public void visitsFilter(ArrayList<Visit> visitArrayList, String startDate, String endDate, ArrayList<Doctor> docList, ArrayList<LocationEntitie> locList, ArrayList<Products> proList) {
        visitsInteractor.visitsFilter(visitArrayList, startDate, endDate, docList, locList, proList, this);
    }


    @Override
    public void visitsFilterListEmpty(String msg, ArrayList<Visit> visitArrayList) {
        visitsView.visitsFilterListEmpty(msg, visitArrayList);
    }

    @Override
    public void visitsFilterList(ArrayList<Visit> visitArrayList) {
        visitsView.visitsFilterList(visitArrayList);
    }


    @Override
    public void addProductsToVisitsFilterStart(Products products, boolean addOrRemove) {
        visitsInteractor.addProductsToVisitsFilterStart(products, addOrRemove, this);
    }

    @Override
    public void productsToVisitsFilterStart(Products products, boolean addOrRemove) {
        visitsView.productsToVisitsFilterStart(products, addOrRemove);
    }


    @Override
    public void addProductsToVisitsFilter(ArrayList<Products> ProductsArrayListToFilterGlobal, Products products, boolean addOrRemove) {
        visitsInteractor.addProductsToVisitsFilter(ProductsArrayListToFilterGlobal, products, addOrRemove, this);
    }

    @Override
    public void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter) {
        visitsView.productsToVisitsFilter(productsArrayListToFilter);
    }


    @Override
    public void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal, ArrayList<Products> allFilterProductsList) {
        visitsInteractor.showAddProductsToFilter(addedLocListGlobal, allFilterProductsList, this);
    }


    @Override
    public void addProductsToFilter(ArrayList<Products> addedArrayListToFilter) {
        visitsView.addProductsToFilter(addedArrayListToFilter);
    }


    @Override
    public void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList, ArrayList<Doctor> selectedDocList) {
        visitsInteractor.setSelectedFilterDoctors(totalDocList, selectedDocList, this);
    }


    @Override
    public void showSelectedFilterDoctors(ArrayList<Doctor> filterList) {
        visitsView.showSelectedFilterDoctors(filterList);
    }


    @Override
    public void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList, ArrayList<LocationEntitie> selectedLocList) {
        visitsInteractor.setSelectedFilterLocation(totalLocList, selectedLocList, this);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList) {
        visitsView.showSelectedFilterLocation(filterList);
    }


    @Override
    public void setSelectedFilterProducts(ArrayList<Products> totalProList, ArrayList<Products> selectedProList) {
        visitsInteractor.setSelectedFilterProducts(totalProList, selectedProList, this);
    }

    @Override
    public void showSelectedFilterProducts(ArrayList<Products> filterList) {
        visitsView.showSelectedFilterProducts(filterList);
    }


    @Override
    public void addVisitImage(Context context, Bitmap img, String imageCode) {
        visitsInteractor.addVisitImage(context, img, imageCode, this);
    }


    @Override
    public void addVisitImageSuccessful() {
        visitsView.addVisitImageSuccessful();
    }

    @Override
    public void addVisitImageFail(String failMsg, Bitmap img, String imageCode) {
        visitsView.addVisitImageFail(failMsg, img, imageCode);
    }

    @Override
    public void addVisitImageNetworkFail() {
        visitsView.addVisitImageNetworkFail();
    }


    @Override
    public void showVisitDetails(Visit visit) {
        visitsInteractor.showVisitDetails(visit, this);
    }


    @Override
    public void visitDetails(Visit visit) {
        visitsView.visitDetails(visit);
    }


    @Override
    public void searchDocForFilter(ArrayList<Doctor> docArrayList, String docName) {
        visitsInteractor.searchDocForFilter(docArrayList, docName, this);
    }

    @Override
    public void docListForFilter(ArrayList<Doctor> docArrayList) {
        visitsView.docListForFilter(docArrayList);
    }


    @Override
    public void searchLocForFilter(ArrayList<LocationEntitie> locArrayList, String locName) {
        visitsInteractor.searchLocForFilter(locArrayList, locName, this);
    }


    @Override
    public void locListForFilter(ArrayList<LocationEntitie> locArrayList) {
        visitsView.locListForFilter(locArrayList);
    }


    @Override
    public void searchProductsForFilter(ArrayList<Products> proArrayList, String productsName) {
        visitsInteractor.searchProductsForFilter(proArrayList, productsName, this);
    }


    @Override
    public void productsListForFilter(ArrayList<Products> proArrayList) {
        visitsView.productsListForFilter(proArrayList);
    }


    @Override
    public void getTargetDetails(Context context) {
        visitsInteractor.getTargetDetails(context, this);
    }


    @Override
    public void targetDetails(TargetDetails target) {

        visitsView.targetDetails(target);
    }

    @Override
    public void targetDetailsError(String failMsg) {
        visitsView.targetDetailsError(failMsg);
    }


    @Override
    public void getSampleType(Context context) {
        visitsInteractor.getSampleType(context, this);
    }


    @Override
    public void sampleTypeListEmpty(String msg) {
        visitsView.sampleTypeListEmpty(msg);
    }

    @Override
    public void sampleTypeList(ArrayList<SampleType> sampleTypes) {
        visitsView.sampleTypeList(sampleTypes);
    }



    @Override
    public void getSelectedSampleType(SampleType sampleType) {
        visitsInteractor.getSelectedSampleType(sampleType,this);
    }





    @Override
    public void selectedSampleType(int sampleTypeID) {
        visitsView.selectedSampleType(sampleTypeID);
    }
    @Override
    public void getSample(Context context, int sampletypeid,ArrayList<Sample> addedSample ) {
        visitsInteractor.getSample(context,sampletypeid,addedSample,this);
    }
    @Override
    public void sampleListEmpty(String msg,int sampleTypeID) {
        visitsView.sampleListEmpty( msg, sampleTypeID);
    }

    @Override
    public void sampleList(ArrayList<Sample> sampleTypes) {
        visitsView.sampleList(sampleTypes);
    }



    @Override
    public void addSampleToVisit(Sample sample,boolean addOrRemove) {
        visitsInteractor.addSampleToVisit(sample,addOrRemove,this);
    }



    @Override
    public void sampleToVisit(Sample sample,boolean addOrRemove) {
        visitsView.sampleToVisit(sample,addOrRemove);
    }


    @Override
    public void updateSample(Context context, ArrayList<Sample> allSampleTypes, ArrayList<Sample> addedSampleTypes) {
        visitsInteractor.updateSample(context,allSampleTypes,addedSampleTypes,this);
    }

    @Override
    public void updatedSampleList(ArrayList<Sample> sampleTypes) {
        visitsView.updatedSampleList(sampleTypes);
    }
}


