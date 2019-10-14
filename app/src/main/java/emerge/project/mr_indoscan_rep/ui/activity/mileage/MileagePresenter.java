package emerge.project.mr_indoscan_rep.ui.activity.mileage;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface MileagePresenter {


    void checkDayStartMileage(Context context);

    void postDayStartMileage(Context context,int currentOdometerReading,int currentDayOdometerReading,Double latitude,Double longitude);





}
