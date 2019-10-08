package emerge.project.mr_indoscan_rep.ui.activity.locations;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LocationInteractor {

    interface OnGetLocationFinishedListener {
        void locationList(ArrayList<LocationEntitie> locationArrayList);
        void noLocation();
        void locationFail(String failMsg);
        void locationNetworkFail();
    }
    void getLocation(Context context, OnGetLocationFinishedListener onGetLocationFinishedListener);



    interface OnGetDistrictFinishedListener {
        void districtList(ArrayList<District> districtArrayList,ArrayList<String> districtNameList);
        void noDistrict();
        void districtFail(String failMsg);
        void districtNetworkFail();
    }
    void getDistrict(Context context, OnGetDistrictFinishedListener onGetDistrictFinishedListener);


    interface OnSelectedDistrictIDFinishedListener {
        void selectedDistrictID(int selectedDsistrictId);
    }
    void getSelectedDistrictID(ArrayList<District> districtArrayList,String name ,OnSelectedDistrictIDFinishedListener onSelectedDistrictIDFinishedListener);


    interface OnPostLocationFinishedListener {
        void  postLocationError(String msg);
        void  postLocationSuccess();
        void  postLocationDuplicate(ArrayList<LocationEntitie> duplicateLocationList, LocationEntitie locationEntitie,int isAfterSuggestion);
        void  postLocationFail(String failMsg,LocationEntitie location,int isAfterSuggestion);
        void  postLocationNetworkFail();
    }
    void postLocation(Context context, LocationEntitie locationEntitie,int isAfterSuggestion, OnPostLocationFinishedListener onPostLocationFinishedListener);





}
