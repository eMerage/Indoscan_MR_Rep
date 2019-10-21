package emerge.project.mr_indoscan_rep.ui.activity.locations;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationType;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LocationView {

    void locationList(ArrayList<LocationEntitie> locationArrayList);

    void noLocation();

    void locationFail(String failMsg);

    void locationNetworkFail();


    void districtList(ArrayList<District> districtArrayList, ArrayList<String> districtNameList);
    void noDistrict();
    void districtFail(String failMsg);
    void districtNetworkFail();


    void selectedDistrictID(int selectedDsistrictId);

    void  postLocationError(String msg);
    void  postLocationSuccess();
    void  postLocationFail(String failMsg,LocationEntitie location,int isAfterSuggestion);
    void  postLocationDuplicate(ArrayList<LocationEntitie> duplicateLocationList, LocationEntitie locationEntitie,int isAfterSuggestion);
    void  postLocationNetworkFail();



        void locationTypeList(ArrayList<LocationType> locationTypeList);
        void locationTypeListgetingFail(String failMsg);
        void locationTypeListNetworkFail();






}
