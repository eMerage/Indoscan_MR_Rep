package emerge.project.mr_indoscan_rep.ui.activity.locations;


import android.content.Context;

import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationType;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class LocationInteractorImpil implements LocationInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    ArrayList<LocationEntitie> locationList;
    ArrayList<District> districtList;

    ArrayList<LocationType> locationTypeList;

    ArrayList<LocationEntitie> locationArrayListforDuplicate;

    @Override
    public void getLocation(Context context, final OnGetLocationFinishedListener onGetLocationFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetLocationFinishedListener.locationNetworkFail();

        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID,0);
                final ArrayList<LocationEntitie> locationArrayList = new ArrayList<LocationEntitie>();

                apiService.getAllLocationsByMR(tokenID,userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer< ArrayList<LocationEntitie>>() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onNext( ArrayList<LocationEntitie> locationEntities) {
                                locationList = locationEntities;
                            }
                            @Override
                            public void onError(Throwable e) {
                                onGetLocationFinishedListener.locationFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {
                                if(locationList.isEmpty()){
                                    onGetLocationFinishedListener.noLocation();
                                }else {
                                    for (LocationEntitie loc : locationList) {

                                        String status ="";
                                        if(loc.getApproved()){
                                            status="Approved";
                                        }else if(loc.getRejected()){
                                            status="Rejected";
                                        }else {
                                            status="Pending";
                                        }

                                        locationArrayList.add(new LocationEntitie(loc.getId(),loc.getName(),loc.getAddress(),loc.getArea(),loc.getTown(),loc.getDistrictID(),loc.getDistrict(),loc.getLatitude(),loc.getLongitude()
                                        ,loc.getCreatedByID(),loc.getCreatedByName(),loc.getLocationTypeID(),loc.getLocationType(),loc.getApproved(),loc.getRejected(),status));
                                    }
                                    onGetLocationFinishedListener.locationList(locationArrayList);
                                }
                            }
                        });
            }catch (Exception ex){
                onGetLocationFinishedListener.locationFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void getDistrict(Context context, final OnGetDistrictFinishedListener onGetDistrictFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetDistrictFinishedListener.districtNetworkFail();
        } else {
            try {
                final ArrayList<District> districtArrayList = new ArrayList<District>();
                final ArrayList<String> districtNameList = new ArrayList<String>();

                apiService.getAllDistricts(tokenID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer< ArrayList<District>>() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onNext( ArrayList<District> districts) {
                                districtList=districts;
                            }
                            @Override
                            public void onError(Throwable e) {
                                onGetDistrictFinishedListener.districtFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {
                                if(districtList.isEmpty()){
                                    onGetDistrictFinishedListener.noDistrict();
                                }else {
                                    for (District dis : districtList) {
                                        districtArrayList.add(new District(dis.getId(),dis.getName()));
                                        districtNameList.add(dis.getName());

                                        System.out.println("cccccccccc : "+dis.getName());
                                    }
                                    onGetDistrictFinishedListener.districtList(districtArrayList,districtNameList);
                                }
                            }
                        });
            }catch (Exception ex){
                onGetDistrictFinishedListener.districtFail("Something went wrong, Please try again");
            }
        }
    }

    @Override
    public void getSelectedDistrictID(ArrayList<District> districtArrayList, String name, OnSelectedDistrictIDFinishedListener onSelectedDistrictIDFinishedListener) {
        int id = 0;
           for(District dis : districtArrayList){
              if(dis.getName().equals(name)){ id=dis.getId(); }else { }
          }
        onSelectedDistrictIDFinishedListener.selectedDistrictID(id);

    }

    @Override
    public void postLocation(Context context, final LocationEntitie locationEntitie, final int isAfterSuggestion, final OnPostLocationFinishedListener onPostLocationFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostLocationFinishedListener.postLocationNetworkFail();
        } else if(locationEntitie.getName().isEmpty()){
            onPostLocationFinishedListener.postLocationError("Location Name is empty");
        }else if(locationEntitie.getAddress().isEmpty()){
            onPostLocationFinishedListener.postLocationError("Location Address is empty");
        }else if(locationEntitie.getArea().isEmpty()){
            onPostLocationFinishedListener.postLocationError("Location Area is empty");
        }else if(locationEntitie.getTown().isEmpty()){
            onPostLocationFinishedListener.postLocationError("Location town is empty");
        }else if(locationEntitie.getDistrictID()==0){
            onPostLocationFinishedListener.postLocationError("Please select the District");
        }else if(locationEntitie.getLatitude()==null){
            onPostLocationFinishedListener.postLocationError("We're having trouble locating you");
        }else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", locationEntitie.getName());
            jsonObject.addProperty("LocationTypeID", locationEntitie.getLocationTypeID());
            jsonObject.addProperty("Address", locationEntitie.getAddress());
            jsonObject.addProperty("Town", locationEntitie.getTown());
            jsonObject.addProperty("DistrictID", locationEntitie.getDistrictID());
            jsonObject.addProperty("Area", locationEntitie.getArea());
            jsonObject.addProperty("Latitude", locationEntitie.getLatitude());
            jsonObject.addProperty("Longitude", locationEntitie.getLongitude());
            jsonObject.addProperty("CreatedByID", userId);
            jsonObject.addProperty("IsAfterSuggestion", isAfterSuggestion);


            System.out.println("jsonObject  :"+jsonObject);



            try {


                final ArrayList<LocationEntitie> locArrayList = new ArrayList<LocationEntitie>();

                apiService.saveLocation(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<LocationEntitie>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<LocationEntitie> locationEntities) {
                                locationArrayListforDuplicate = locationEntities;

                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostLocationFinishedListener.postLocationFail("Something went wrong, Please try again",locationEntitie,isAfterSuggestion);

                            }

                            @Override
                            public void onComplete() {

                                if (locationArrayListforDuplicate.isEmpty()) {
                                    onPostLocationFinishedListener.postLocationError("Empty Respond");

                                } else {
                                    int districtID = 0;
                                    for(LocationEntitie loc : locationArrayListforDuplicate){
                                        districtID =loc.getDistrictID();
                                    }


                                    if(districtID!=0){
                                        for(LocationEntitie loc : locationArrayListforDuplicate){
                                            locArrayList.add(new LocationEntitie(loc.getId(),loc.getName(),loc.getAddress(),loc.getLatitude(),loc.getLongitude(),loc.getCreatedByName()));
                                        }
                                        onPostLocationFinishedListener.postLocationDuplicate(locArrayList,locationEntitie,isAfterSuggestion);

                                    }else {
                                        onPostLocationFinishedListener.postLocationSuccess();
                                    }

                                }
                            }
                        });

            } catch (Exception ex) {
                onPostLocationFinishedListener.postLocationFail("Something went wrong, Please try again",locationEntitie,isAfterSuggestion);
            }
        }
    }

    @Override
    public void getLocationType(Context context, final OnGetLocationTypeListFinishedListener onGetLocationTypeListFinishedListener) {


        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetLocationTypeListFinishedListener.locationTypeListNetworkFail();
        } else {
            try {

                   locationTypeList = new ArrayList<LocationType>();

                final ArrayList<LocationType> list = new ArrayList<LocationType>();
                apiService.getAllLocationTypes(tokenID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<LocationType>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<LocationType> list) {
                                locationTypeList = list;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetLocationTypeListFinishedListener.locationTypeListgetingFail("Something went wrong, Please try again ");

                            }

                            @Override
                            public void onComplete() {
                                onGetLocationTypeListFinishedListener.locationTypeList(locationTypeList);


                            }
                        });
            } catch (Exception ex) {
                onGetLocationTypeListFinishedListener.locationTypeListgetingFail("Something went wrong, Please try again");
            }
        }

    }
}


