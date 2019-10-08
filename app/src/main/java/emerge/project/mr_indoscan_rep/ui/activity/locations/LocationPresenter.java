package emerge.project.mr_indoscan_rep.ui.activity.locations;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LocationPresenter {

    void getLocation(Context context);
    void getDistrict(Context context);
    void getSelectedDistrictID(ArrayList<District> districtArrayList, String name);
    void postLocation(Context context, LocationEntitie locationEntitie,int isAfterSuggestion);


}
