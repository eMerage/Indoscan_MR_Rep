package emerge.project.mr_indoscan_rep.ui.activity.mileage;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesInteractor;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesInteractorImpil;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.Mileage;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class MileageInteractorImpil implements MileageInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    Mileage mileageAvalability;
    Boolean mileageDayStart =  false;
    Boolean mileageDayEnd =  false;


    @Override
    public void checkDayStartMileage(Context context, final OnDayStartMileageFinishedListener onDayStartMileageFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onDayStartMileageFinishedListener.dayStartMileageNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                mileageAvalability = new Mileage();


                apiService.getPreviousMileageByUser(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Mileage>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Mileage mileage) {
                                mileageAvalability = mileage;
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                onDayStartMileageFinishedListener.dayStartMileage(mileageAvalability);

                                /*if (mileageAvalability.getMileageID()==0) {
                                    onDayStartMileageFinishedListener.dayStartMileage(false);
                                } else {
                                    onDayStartMileageFinishedListener.dayStartMileage(true);
                                }*/

                            }
                        });
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void postDayStartMileage(Context context, int currentOdometerReading, int currentDayOdometerReading,
                                    Double latitude, Double longitude, final OnpostDayStartMileageFinishedListener onpostDayStartMileageFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onpostDayStartMileageFinishedListener.postDayStartMileageNetworkFail();
        } else if (currentOdometerReading == 0) {
            onpostDayStartMileageFinishedListener.postDayStartMileageError("Please add previous day Odometer reading");
        } else if (currentDayOdometerReading == 0) {
            onpostDayStartMileageFinishedListener.postDayStartMileageError("Please add current day Odometer reading");
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("UserID", userId);
            jsonObject.addProperty("PreviousDayMeterReading",currentOdometerReading);
            jsonObject.addProperty("DayStartOdometerReading",currentDayOdometerReading);
            jsonObject.addProperty("StartLatitude", latitude);
            jsonObject.addProperty("StartLongitude", longitude);



            try {
                apiService.saveDayStartMileage(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean mileage) {
                                mileageDayStart = mileage;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onpostDayStartMileageFinishedListener.postDayStartMileageFail("Something went wrong, Please try again");

                            }
                            @Override
                            public void onComplete() {
                                if(mileageDayStart){
                                    onpostDayStartMileageFinishedListener.postDayStartMileageSuccess();
                                }else {
                                    onpostDayStartMileageFinishedListener.postDayStartMileageFail("already have unfinished start mileage details");
                                }

                            }
                        });
            } catch (Exception ex) {
                onpostDayStartMileageFinishedListener.postDayStartMileageFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void postDayEndMileage(Context context, int dayEndOdometerReading, int mileagePerDay, int privetMileagePerDay,
                                  final Bitmap image, Double latitude, Double longitude,
                                  final OnPostDayEndMileageFinishedListener onPostDayEndMileageFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostDayEndMileageFinishedListener.postDayEndMileageNetworkFail();
        } else if (dayEndOdometerReading == 0) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add day end Odometer reading");
        } else if (mileagePerDay == 0) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add mileage per day");
        } else if (privetMileagePerDay == 0) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add private mileage per day");
        } else if (image == null) {
            onPostDayEndMileageFinishedListener.postDayEndMileageFail("Please add image of the meeter");
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            final JsonObject jsonObject = new JsonObject();

            final String code = genarateMileageImageCode(String.valueOf(userId));

            jsonObject.addProperty("UserID", userId);
            jsonObject.addProperty("DayEndOdometerReading",dayEndOdometerReading);
            jsonObject.addProperty("OfficialMileage",mileagePerDay);
            jsonObject.addProperty("PrivateMileage", privetMileagePerDay);
            jsonObject.addProperty("EndLatitude", latitude);
            jsonObject.addProperty("EndLongitude", longitude);
            jsonObject.addProperty("ImageCode", code);


            try {
                apiService.saveDayEndMileage(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean mileage) {
                                mileageDayEnd = mileage;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostDayEndMileageFinishedListener.postDayEndMileageFail("Something went wrong, Please try again");

                            }
                            @Override
                            public void onComplete() {
                                if(mileageDayEnd){
                                    new AddDayEndMileageWithImage(image,code,onPostDayEndMileageFinishedListener).execute();


                                }else {
                                    onPostDayEndMileageFinishedListener.postDayEndMileageFail("already have unfinished start mileage details");

                                }
                            }
                        });
            } catch (Exception ex) {
                onPostDayEndMileageFinishedListener.postDayEndMileageFail("Something went wrong, Please try again");
            }

        }

    }



    private class AddDayEndMileageWithImage extends AsyncTask<Void, Void, Void> {

        Bitmap bitmapImage;
        OnPostDayEndMileageFinishedListener finishedListener;
        String imageCode ;

        JsonObject jsonObject ;

        public AddDayEndMileageWithImage(Bitmap image, String imagecode, OnPostDayEndMileageFinishedListener listener) {
            bitmapImage = image;
            imageCode = imagecode;
            finishedListener = listener;

            jsonObject = new JsonObject();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonObject.addProperty("ImageCode", imageCode);
            jsonObject.addProperty("ImageBase64", bitMapToString(bitmapImage));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            postEndMilageWithImage(jsonObject,finishedListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }



    private void postEndMilageWithImage(JsonObject json, final OnPostDayEndMileageFinishedListener listener){

        try {
            apiService.saveMileageImage(json)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Boolean mileage) {
                            mileageDayEnd = mileage;
                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.postDayEndMileageFail("Something went wrong, Please try again");

                        }
                        @Override
                        public void onComplete() {
                            if(mileageDayEnd){
                                listener.postDayEndMileageSuccess();
                            }else {
                                listener.postDayEndMileageFail("already have unfinished start mileage details");

                            }
                        }
                    });
        } catch (Exception ex) {
            listener.postDayEndMileageFail("Something went wrong, Please try again");
        }

    }

    public String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void getDetailsSummary(OnDetailsSummaryFinishedListener onDetailsSummaryFinishedListener) {
        DetailsSummary detailsSummary = new DetailsSummary();
        onDetailsSummaryFinishedListener.detailsSummaryList(detailsSummary);
    }

    public String genarateMileageImageCode(String userID) {
        Calendar c = Calendar.getInstance();
        String numberFromTime = String.valueOf(c.get(Calendar.YEAR)).substring(1) + String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DATE)) + String.valueOf(c.get(Calendar.HOUR)) + String.valueOf(c.get(Calendar.MINUTE)) + String.valueOf(c.get(Calendar.SECOND)) + String.valueOf(c.get(Calendar.MILLISECOND));
        if (numberFromTime.length() == 16) {
        } else {
            if (numberFromTime.length() > 16) {
                numberFromTime = numberFromTime.substring(0, 16);
            } else {
                int len = 16 - numberFromTime.length();
                for (int i = 0; i < len; i++) {
                    numberFromTime = numberFromTime + "0";
                }
            }
        }
        String expCode ="MIL"+userID + numberFromTime;
        return expCode;
    }

}


