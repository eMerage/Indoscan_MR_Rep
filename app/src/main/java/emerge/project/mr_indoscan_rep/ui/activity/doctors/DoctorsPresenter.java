package emerge.project.mr_indoscan_rep.ui.activity.doctors;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface DoctorsPresenter {
    void getDoctor(Context context);
    void searchDoctor(ArrayList<Doctor> docArrayList, String doctorName);

    void getSpecialization(Context context);


    void addSpecToDocStart(Specialization product, boolean addOrRemove);
    void addSpecToDoc(ArrayList<Specialization> specListGlobal,Specialization product,boolean addOrRemove );
    void showAddSpec(ArrayList<Specialization> addedSpecListGlobal,ArrayList<Specialization> allSpecList);


    void searchSpec(ArrayList<Specialization> specArrayList,String specName);

    void postDoctor(Context context,String name,String tpNumber,String regNumber,String qualification,int isAfterSuggestion,ArrayList<Specialization> specArrayList );


    void addLocationToDocFilterStart(LocationEntitie locationEntitie,boolean addOrRemove );
    void addLocationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal,LocationEntitie locationEntitie,boolean addOrRemove );
    void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal,ArrayList<LocationEntitie> allFilterLocList);
    void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList,ArrayList<LocationEntitie> selectedLocList);



    void addSpecToDocFilterStart(Specialization specialization,boolean addOrRemove );
    void addSpecToDocFilter(ArrayList<Specialization> specArrayListToFilterGlobal,Specialization specialization,boolean addOrRemove );
    void showAddSpecToFilter(ArrayList<Specialization> addedSpecListGlobal,ArrayList<Specialization> allFilterSpecist);
    void setSelectedFilterSpec(ArrayList<Specialization> totalSpecList,ArrayList<Specialization> selectedSpecList );



    void doctorsFilter(ArrayList<Doctor> docArrayList,ArrayList<String> statusList,ArrayList<LocationEntitie> locList,ArrayList<Specialization> specList);



    void searchLocationForFilter(ArrayList<LocationEntitie> locArrayList,String locationName);



    void searchSpecForFilter(ArrayList<Specialization> specArrayList,String specName);




}
