package emerge.project.mr_indoscan_rep.ui.activity.doctors;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface DoctorsInteractor {

    interface OnGetDoctorFinishedListener {
        void doctorsList(ArrayList<Doctor> doctorArrayList,ArrayList<String> doctorNAmeList);
        void doctorsEmpty();
        void doctorsGetingFail(String failMsg);
        void doctorsGetingNetworkFail();

        void visitsLocationList( ArrayList<LocationEntitie> locationListForFilter);
        void visitsLocationNameList( ArrayList<String> locationNameListForFilter);

        void visitsSpecList( ArrayList<Specialization> specListForFilter);
        void visitsSpecNameList( ArrayList<String> specNameListForFilter);
    }
    void getDoctor(Context context, OnGetDoctorFinishedListener onGetDoctorFinishedListener);


    interface OnSearchDoctorFinishedListener {
        void searchDoctorList(ArrayList<Doctor> doctorArrayList);
    }
    void searchDoctor(ArrayList<Doctor> docArrayList,String doctorName, OnSearchDoctorFinishedListener onSearchDoctorFinishedListener);



    interface OnGetSpecializationFinishedListener {
        void SpecializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList);
        void SpecializationEmpty();
        void SpecializationGetingFail(String failMsg);
        void SpecializationGetingNetworkFail();
    }
    void getSpecialization(Context context, OnGetSpecializationFinishedListener onGetSpecializationFinishedListener);



    interface OnAddSpecToDocStartFinishedListener {
        void addedSpecStart(Specialization specialization,boolean addOrRemove);
    }
    void addSpecToDocStart(Specialization spec,boolean addOrRemove, OnAddSpecToDocStartFinishedListener onAddSpecToDocStartFinishedListener);




    interface OnAddSpecToDocFinishedListener {
        void addedSpec(ArrayList<Specialization> addedArrayList);
    }
    void addSpecToDoc(ArrayList<Specialization> specListGlobal,Specialization product,boolean addOrRemove, OnAddSpecToDocFinishedListener onAddSpecToDocFinishedListener);



    interface OnShowAddSpecFinishedListener {
        void showAddedSpec(ArrayList<Specialization> addedArrayList);
    }
    void showAddSpec(ArrayList<Specialization> addedSpecListGlobal,ArrayList<Specialization> allSpecList, OnShowAddSpecFinishedListener onShowAddSpecFinishedListener);




    interface OnSearchSpecFinishedListener {
        void searchSpecList(ArrayList<Specialization> specArrayList);
    }
    void searchSpec(ArrayList<Specialization> specArrayList,String specName, OnSearchSpecFinishedListener onSearchSpecFinishedListener);



    interface OnPostDoctorFinishedListener {
        void  postDoctorError(String msg);
        void  postDoctorSuccess();
        void  postDoctorDuplicate(ArrayList<Doctor> duplicateDoctorsList,String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion,ArrayList<Specialization> specArrayList);
        void  postDoctorFail(String failMsg, String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion,ArrayList<Specialization> specArrayList);
        void  postDoctorNetworkFail();
    }
    void postDoctor(Context context,String name,String tpNumber,String regNumber,String qualification ,int isAfterSuggestion,ArrayList<Specialization> specArrayList ,OnPostDoctorFinishedListener onPostDoctorFinishedListener);




    interface OnAddLocationToDocFilterStartFinishedListener {
        void locationToDocFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    }
    void addLocationToDocFilterStart(LocationEntitie locationEntitie,boolean addOrRemove, OnAddLocationToDocFilterStartFinishedListener  onAddLocationToDocFilterStartFinishedListener);

    interface OnAddLocationToDocFilterFinishedListener {
        void locationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilter);
    }
    void addLocationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal,LocationEntitie locationEntitie,boolean addOrRemove, OnAddLocationToDocFilterFinishedListener onAddLocationToDocFilterFinishedListener );

    interface OnAddLocationToFilterFinishedListener {
        void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter);
    }
    void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal,ArrayList<LocationEntitie> allFilterLocList, OnAddLocationToFilterFinishedListener onAddLocationToFilterFinishedListener);

    interface OnSetSelectedFilterLocationFinishedListener {
        void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList);
    }
    void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList,ArrayList<LocationEntitie> selectedLocList,  OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener );






    interface OnAddSpecToDocFilterStartFinishedListener {
        void specToDocFilterStart(Specialization specialization,boolean addOrRemove);
    }
    void addSpecToDocFilterStart(Specialization specialization,boolean addOrRemove, OnAddSpecToDocFilterStartFinishedListener onAddSpecToDocFilterStartFinishedListener );


    interface OnAddSpecToDocFilterFinishedListener {
        void specToDocFilter(ArrayList<Specialization> specArrayListToFilter);
    }
    void addSpecToDocFilter(ArrayList<Specialization> specArrayListToFilterGlobal,Specialization specialization,boolean addOrRemove,OnAddSpecToDocFilterFinishedListener onAddSpecToDocFilterFinishedListener );



    interface OnShowSpecToFilterFinishedListener {
        void addSpecToFilter(ArrayList<Specialization> addedArrayListToFilter);
    }
    void showAddSpecToFilter(ArrayList<Specialization> addedSpecListGlobal,ArrayList<Specialization> allFilterSpecist,OnShowSpecToFilterFinishedListener  onShowSpecToFilterFinishedListener);



    interface OnSetSelectedFilterSpecFinishedListener {
        void showSelectedFilterSpec(ArrayList<Specialization> filterList);
    }
    void setSelectedFilterSpec(ArrayList<Specialization> totalSpecList,ArrayList<Specialization> selectedSpecList,OnSetSelectedFilterSpecFinishedListener onSetSelectedFilterSpecFinishedListener );



    interface OnDoctorFilterFinishedListener {
        void doctorsFilterListEmpty(String msg,ArrayList<Doctor> doctorsArrayList);
        void doctorsFilterList(ArrayList<Doctor> doctorsArrayList);
    }
    void doctorsFilter(ArrayList<Doctor> docArrayList,ArrayList<String> statusList,ArrayList<LocationEntitie> locList,ArrayList<Specialization> specList,OnDoctorFilterFinishedListener onDoctorFilterFinishedListener);



    interface OnSearchLocationForFilterFinishedListener {
        void searchLocationList(ArrayList<LocationEntitie> locationArrayList);
    }
    void searchLocationForFilter(ArrayList<LocationEntitie> locArrayList,String locationName, OnSearchLocationForFilterFinishedListener  onSearchLocationForFilterFinishedListener);


    interface OnSearchSpecForFilterFinishedListener {
        void specListForFilter(ArrayList<Specialization> specArrayList);
    }
    void searchSpecForFilter(ArrayList<Specialization> specArrayList,String specName, OnSearchSpecForFilterFinishedListener  onSearchSpecForFilterFinishedListener);





}
