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

    }


}


