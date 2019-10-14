package emerge.project.mr_indoscan_rep.ui.activity.mileage;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface MileageInteractor {


    interface OnDayStartMileageFinishedListener {
        void dayStartMileage();
        void dayStartMileageFail(String failMsg);
        void dayStartMileageNetworkFail();
    }
    void checkDayStartMileage(Context context, OnDayStartMileageFinishedListener onDayStartMileageFinishedListener);



    interface OnpostDayStartMileageFinishedListener {
        void  postDayStartMileageError(String msg);
        void  postDayStartMileageSuccess();
        void  postDayStartMileageFail(String failMsg);
        void  postDayStartMileageNetworkFail();
    }
    void postDayStartMileage(Context context,int currentOdometerReading,int currentDayOdometerReading,Double latitude,Double longitude,OnpostDayStartMileageFinishedListener onpostDayStartMileageFinishedListener);



}
