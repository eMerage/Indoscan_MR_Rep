package emerge.project.mr_indoscan_rep.ui.activity.mileage;


import android.content.Context;
import android.graphics.Bitmap;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class MileageInteractorImpil implements MileageInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    @Override
    public void checkDayStartMileage(Context context, OnDayStartMileageFinishedListener onDayStartMileageFinishedListener) {

    }

    @Override
    public void postDayStartMileage(Context context, int currentOdometerReading, int currentDayOdometerReading, Double latitude, Double longitude, OnpostDayStartMileageFinishedListener onpostDayStartMileageFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onpostDayStartMileageFinishedListener.postDayStartMileageNetworkFail();
        } else if(currentOdometerReading==0){
            onpostDayStartMileageFinishedListener.postDayStartMileageError("Please add previous day Odometer reading");
        }else if(currentDayOdometerReading==0) {
            onpostDayStartMileageFinishedListener.postDayStartMileageError("Please add current day Odometer reading");
        }else {
            onpostDayStartMileageFinishedListener.postDayStartMileageSuccess();

        }

    }

    @Override
    public void postDayEndMileage(Context context, int dayEndOdometerReading, int mileagePerDay, int privetMileagePerDay, Bitmap image, Double latitude, Double longitude,
                                  OnPostDayEndMileageFinishedListener onPostDayEndMileageFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostDayEndMileageFinishedListener.postDayEndMileageNetworkFail();
        } else if(dayEndOdometerReading==0){
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add day end Odometer reading");
        }else if(mileagePerDay==0) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add mileage per day");
        } else if(privetMileagePerDay==0) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add private mileage per day");
        } else if(image==null) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add image of the meeter");
        } else {
            onPostDayEndMileageFinishedListener.postDayEndMileageSuccess();
        }

    }

    @Override
    public void getDetailsSummary(OnDetailsSummaryFinishedListener onDetailsSummaryFinishedListener) {


    }


}


