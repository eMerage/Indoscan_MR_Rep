package emerge.project.mr_indoscan_rep.ui.activity.doctors;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface DoctorsView {
    void doctorsList(ArrayList<Doctor> doctorArrayList,ArrayList<String> doctorNAmeList);
    void doctorsEmpty();
    void doctorsGetingFail(String failMsg);
    void doctorsGetingNetworkFail();


    void visitsLocationList( ArrayList<LocationEntitie> locationListForFilter);
    void visitsLocationNameList( ArrayList<String> locationNameListForFilter);

    void visitsSpecList( ArrayList<Specialization> specListForFilter);
    void visitsSpecNameList( ArrayList<String> specNameListForFilter);





    void searchDoctorList(ArrayList<Doctor> doctorArrayList);


    void SpecializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList);
    void SpecializationEmpty();
    void SpecializationGetingFail(String failMsg);
    void SpecializationGetingNetworkFail();

    void addedSpecStart(Specialization specialization,boolean addOrRemove);

    void addedSpec(ArrayList<Specialization> productArrayList);

    void showAddedSpec(ArrayList<Specialization> addedArrayList);

    void searchSpecList(ArrayList<Specialization> specArrayList);


    void  postDoctorError(String msg);
    void  postDoctorSuccess();
    void  postDoctorDuplicate(ArrayList<Doctor> duplicateDoctorsList, String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion,ArrayList<Specialization> specArrayList);
    void  postDoctorFail(String failMsg, String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion,ArrayList<Specialization> specArrayList);
    void  postDoctorNetworkFail();


    void locationToDocFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    void locationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilter);
    void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter);
    void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList);




    void specToDocFilterStart(Specialization specialization,boolean addOrRemove);
    void specToDocFilter(ArrayList<Specialization> specArrayListToFilter);
    void addSpecToFilter(ArrayList<Specialization> addedArrayListToFilter);
    void showSelectedFilterSpec(ArrayList<Specialization> filterList);


    void doctorsFilterListEmpty(String msg,ArrayList<Doctor> doctorsArrayList);
    void doctorsFilterList(ArrayList<Doctor> doctorsArrayList);




    void searchLocationList(ArrayList<LocationEntitie> locationArrayList);

    void specListForFilter(ArrayList<Specialization> specArrayList);






}
