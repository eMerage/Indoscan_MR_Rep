package emerge.project.mr_indoscan_rep.ui.activity.doctors;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class DoctorsPresenterImpli implements DoctorsPresenter,
        DoctorsInteractor.OnGetDoctorFinishedListener,
        DoctorsInteractor.OnSearchDoctorFinishedListener,
        DoctorsInteractor.OnGetSpecializationFinishedListener,
        DoctorsInteractor.OnAddSpecToDocStartFinishedListener,
        DoctorsInteractor.OnAddSpecToDocFinishedListener,
        DoctorsInteractor.OnShowAddSpecFinishedListener,
        DoctorsInteractor.OnSearchSpecFinishedListener,
        DoctorsInteractor.OnPostDoctorFinishedListener,
        DoctorsInteractor.OnAddLocationToDocFilterStartFinishedListener,
        DoctorsInteractor.OnAddLocationToDocFilterFinishedListener,
        DoctorsInteractor.OnAddLocationToFilterFinishedListener,
        DoctorsInteractor.OnSetSelectedFilterLocationFinishedListener,
        DoctorsInteractor.OnAddSpecToDocFilterFinishedListener,
        DoctorsInteractor.OnShowSpecToFilterFinishedListener,
        DoctorsInteractor.OnSetSelectedFilterSpecFinishedListener,
        DoctorsInteractor.OnAddSpecToDocFilterStartFinishedListener,
        DoctorsInteractor.OnDoctorFilterFinishedListener,
        DoctorsInteractor.OnSearchLocationForFilterFinishedListener,
        DoctorsInteractor.OnSearchSpecForFilterFinishedListener

{


    private DoctorsView doctorsView;
    DoctorsInteractor doctorsInteractor;


    public DoctorsPresenterImpli(DoctorsView doctorsView) {
        this.doctorsView = doctorsView;
        this.doctorsInteractor = new DoctorsInteractorImpil();

    }


    @Override
    public void getDoctor(Context context) {
        doctorsInteractor.getDoctor(context, this);
    }

    @Override
    public void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorNAmeList) {
        doctorsView.doctorsList(doctorArrayList, doctorNAmeList);
    }

    @Override
    public void doctorsEmpty() {
        doctorsView.doctorsEmpty();
    }

    @Override
    public void doctorsGetingFail(String failMsg) {
        doctorsView.doctorsGetingFail(failMsg);
    }

    @Override
    public void doctorsGetingNetworkFail() {
        doctorsView.doctorsGetingNetworkFail();
    }

    @Override
    public void visitsLocationList(ArrayList<LocationEntitie> locationListForFilter) {
        doctorsView.visitsLocationList(locationListForFilter);
    }

    @Override
    public void visitsLocationNameList(ArrayList<String> locationNameListForFilter) {
        doctorsView.visitsLocationNameList(locationNameListForFilter);
    }

    @Override
    public void visitsSpecList(ArrayList<Specialization> specListForFilter) {
        doctorsView.visitsSpecList(specListForFilter);
    }

    @Override
    public void visitsSpecNameList(ArrayList<String> specNameListForFilter) {
        doctorsView.visitsSpecNameList(specNameListForFilter);
    }


    @Override
    public void searchDoctor(ArrayList<Doctor> repArrayList, String doctorName) {
        doctorsInteractor.searchDoctor(repArrayList, doctorName, this);
    }


    @Override
    public void searchDoctorList(ArrayList<Doctor> doctorArrayList) {
        doctorsView.searchDoctorList(doctorArrayList);
    }


    @Override
    public void getSpecialization(Context context) {
        doctorsInteractor.getSpecialization(context, this);
    }

    @Override
    public void SpecializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList) {
        doctorsView.SpecializationList(specArrayList, specNameList);
    }

    @Override
    public void SpecializationEmpty() {
        doctorsView.SpecializationEmpty();
    }

    @Override
    public void SpecializationGetingFail(String failMsg) {
        doctorsView.SpecializationGetingFail(failMsg);
    }

    @Override
    public void SpecializationGetingNetworkFail() {
        doctorsView.SpecializationGetingNetworkFail();
    }


    @Override
    public void addSpecToDocStart(Specialization product, boolean addOrRemove) {
        doctorsInteractor.addSpecToDocStart(product, addOrRemove, this);
    }

    @Override
    public void addedSpecStart(Specialization specialization, boolean addOrRemove) {
        doctorsView.addedSpecStart(specialization, addOrRemove);
    }


    @Override
    public void addSpecToDoc(ArrayList<Specialization> specListGlobal, Specialization product, boolean addOrRemove) {
        doctorsInteractor.addSpecToDoc(specListGlobal, product, addOrRemove, this);
    }


    @Override
    public void addedSpec(ArrayList<Specialization> productArrayList) {
        doctorsView.addedSpec(productArrayList);
    }


    @Override
    public void showAddSpec(ArrayList<Specialization> addedSpecListGlobal, ArrayList<Specialization> allSpecList) {
        doctorsInteractor.showAddSpec(addedSpecListGlobal, allSpecList, this);
    }


    @Override
    public void showAddedSpec(ArrayList<Specialization> addedArrayList) {
        doctorsView.showAddedSpec(addedArrayList);
    }


    @Override
    public void searchSpec(ArrayList<Specialization> specArrayList, String specName) {
        doctorsInteractor.searchSpec(specArrayList, specName, this);
    }
    @Override
    public void searchSpecList(ArrayList<Specialization> specArrayList) {
        doctorsView.searchSpecList(specArrayList);
    }







    @Override
    public void postDoctor(Context context, String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion, ArrayList<Specialization> specArrayList) {
        doctorsInteractor.postDoctor(context,  name, tpNumber, regNumber, qualification,isAfterSuggestion,specArrayList, this);
    }


    @Override
    public void postDoctorError(String msg) {
        doctorsView.postDoctorError(msg);
    }

    @Override
    public void postDoctorSuccess() {
        doctorsView.postDoctorSuccess();
    }

    @Override
    public void postDoctorDuplicate(ArrayList<Doctor> duplicateDoctorsList, String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion,ArrayList<Specialization> specArrayList) {
        doctorsView.postDoctorDuplicate(duplicateDoctorsList, name, tpNumber, regNumber, qualification, isAfterSuggestion,specArrayList);
    }

    @Override
    public void postDoctorFail(String failMsg,String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion, ArrayList<Specialization> specArrayList) {
        doctorsView.postDoctorFail(failMsg, name, tpNumber, regNumber,qualification,isAfterSuggestion,specArrayList);
    }

    @Override
    public void postDoctorNetworkFail() {
        doctorsView.postDoctorNetworkFail();
    }






    @Override
    public void addLocationToDocFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        doctorsInteractor.addLocationToDocFilterStart(locationEntitie,addOrRemove,this);
    }
    @Override
    public void locationToDocFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        doctorsView.locationToDocFilterStart(locationEntitie,addOrRemove);
    }



    @Override
    public void addLocationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal, LocationEntitie locationEntitie, boolean addOrRemove) {
        doctorsInteractor.addLocationToDocFilter(locArrayListToFilterGlobal,locationEntitie,addOrRemove,this);
    }
    @Override
    public void locationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilter) {
        doctorsView.locationToDocFilter(locArrayListToFilter);
    }



    @Override
    public void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal, ArrayList<LocationEntitie> allFilterLocList) {
        doctorsInteractor.showAddLocationToFilter(addedLocListGlobal,allFilterLocList,this);
    }

    @Override
    public void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter) {
        doctorsView.addLocationToFilter(addedArrayListToFilter);
    }



    @Override
    public void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList, ArrayList<LocationEntitie> selectedLocList) {
        doctorsInteractor.setSelectedFilterLocation(totalLocList,selectedLocList,this);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList) {
        doctorsView.showSelectedFilterLocation(filterList);
    }





    @Override
    public void addSpecToDocFilterStart(Specialization specialization, boolean addOrRemove) {
        doctorsInteractor.addSpecToDocFilterStart(specialization,addOrRemove,this);
    }

    @Override
    public void specToDocFilterStart(Specialization specialization, boolean addOrRemove) {
        doctorsView.specToDocFilterStart(specialization,addOrRemove);
    }



    @Override
    public void addSpecToDocFilter(ArrayList<Specialization> specArrayListToFilterGlobal, Specialization specialization, boolean addOrRemove) {
        doctorsInteractor.addSpecToDocFilter(specArrayListToFilterGlobal,specialization,addOrRemove,this);
    }
    @Override
    public void specToDocFilter(ArrayList<Specialization> specArrayListToFilter) {
        doctorsView.specToDocFilter(specArrayListToFilter);
    }



    @Override
    public void showAddSpecToFilter(ArrayList<Specialization> addedSpecListGlobal, ArrayList<Specialization> allFilterSpecist) {
        doctorsInteractor.showAddSpecToFilter(addedSpecListGlobal,allFilterSpecist,this);
    }
    @Override
    public void addSpecToFilter(ArrayList<Specialization> addedArrayListToFilter) {
        doctorsView.addSpecToFilter(addedArrayListToFilter);
    }





    @Override
    public void setSelectedFilterSpec(ArrayList<Specialization> totalSpecList, ArrayList<Specialization> selectedSpecList) {
        doctorsInteractor.setSelectedFilterSpec(totalSpecList,selectedSpecList,this);
    }

    @Override
    public void showSelectedFilterSpec(ArrayList<Specialization> filterList) {
        doctorsView.showSelectedFilterSpec(filterList);
    }




    @Override
    public void doctorsFilter(ArrayList<Doctor> docArrayList,ArrayList<String> statusList, ArrayList<LocationEntitie> locList, ArrayList<Specialization> specList) {
        doctorsInteractor.doctorsFilter( docArrayList,statusList,locList,specList,this);
    }



    @Override
    public void doctorsFilterListEmpty(String msg, ArrayList<Doctor> doctorsArrayList) {
        doctorsView.doctorsFilterListEmpty(msg,doctorsArrayList);
    }

    @Override
    public void doctorsFilterList(ArrayList<Doctor> doctorsArrayList) {
        doctorsView.doctorsFilterList(doctorsArrayList);
    }






    @Override
    public void searchLocationForFilter(ArrayList<LocationEntitie> locArrayList, String locationName) {
        doctorsInteractor.searchLocationForFilter( locArrayList,locationName,this);
    }




    @Override
    public void searchLocationList(ArrayList<LocationEntitie> locationArrayList) {
        doctorsView.searchLocationList(locationArrayList);
    }





    @Override
    public void searchSpecForFilter(ArrayList<Specialization> specArrayList, String specName) {
        doctorsInteractor.searchSpecForFilter( specArrayList,specName,this);
    }
    @Override
    public void specListForFilter(ArrayList<Specialization> specArrayList) {
        doctorsView.specListForFilter(specArrayList);
    }
}
