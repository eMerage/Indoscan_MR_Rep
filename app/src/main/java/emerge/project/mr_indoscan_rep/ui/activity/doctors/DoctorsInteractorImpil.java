package emerge.project.mr_indoscan_rep.ui.activity.doctors;


import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class DoctorsInteractorImpil implements DoctorsInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    ArrayList<Doctor> doctorList;
    ArrayList<Specialization> specList;

    ArrayList<Doctor> docArrayListforDuplicate;

    String qualify="";


    @Override
    public void getDoctor(Context context, final OnGetDoctorFinishedListener onGetDoctorFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetDoctorFinishedListener.doctorsGetingNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();
                final ArrayList<String> doctorNameList = new ArrayList<String>();


                final ArrayList<Specialization> specListForFilter = new ArrayList<Specialization>();
                final ArrayList<LocationEntitie> locationListForFilter = new ArrayList<LocationEntitie>();

                final ArrayList<String> locationNameListForFilter = new ArrayList<String>();
                final ArrayList<String> specNameListForFilter = new ArrayList<String>();

                apiService.getAllDoctors(tokenID, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doctors) {
                                doctorList = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetDoctorFinishedListener.doctorsGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (doctorList.isEmpty()) {
                                    onGetDoctorFinishedListener.doctorsEmpty();
                                } else {
                                    for (Doctor doc : doctorList) {

                                        String specialization = "";
                                        String loca = "";

                                        if (doc.getSpecializationList().isEmpty()) {
                                            specialization = "Specialization not assign ";
                                        } else {
                                            for (Specialization spc : doc.getSpecializationList()) {
                                                if (specialization.equals("")) {
                                                    specialization = spc.getName();
                                                } else {
                                                    specialization = specialization + "/" + spc.getName();
                                                }
                                            }
                                        }


                                        if (doc.getLocationList().isEmpty()) {
                                            loca = "Locations not assign ";
                                        } else {
                                            for (LocationEntitie loc : doc.getLocationList()) {
                                                if (loca.equals("")) {
                                                    loca = loc.getName();
                                                } else {
                                                    loca = loca + "/" + loc.getName();
                                                }
                                            }
                                        }

                                        String status;

                                        if (doc.isApproved()) {
                                            status="Approved";
                                        } else if (doc.isRejected()) {
                                            status="Rejected";
                                        } else {
                                            status="Pending";
                                        }


                                        doctorArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getCode(), doc.getContactNo(), doc.getRegistrationNo(), doc.getQualification(), doc.getCreatedDate(),
                                                doc.getCreatedByID(), doc.getCreatedByName(), doc.getImageUrl(), doc.isApproved(), doc.isRejected()
                                                , specialization, loca, doc.getSpecializationList(), doc.getLocationList(),status));

                                        doctorNameList.add(doc.getName());
                                    }



                                 onGetDoctorFinishedListener.doctorsList(doctorArrayList, doctorNameList);


                                    for (Doctor doc : doctorList) {

                                        for (Specialization spc : doc.getSpecializationList()) {
                                            if (specListForFilter.isEmpty()) {
                                                specListForFilter.add(new Specialization(spc.getId(), spc.getName(), false));
                                                specNameListForFilter.add(spc.getName());
                                            } else {
                                                boolean status = false;
                                                for (Specialization s : specListForFilter) {
                                                    if (s.getId() == spc.getId()) {
                                                        status = true;
                                                        break;
                                                    } else {
                                                    }
                                                }
                                                if (!status) {
                                                    specListForFilter.add(new Specialization(spc.getId(), spc.getName(), false));
                                                    specNameListForFilter.add(spc.getName());
                                                } else {
                                                }

                                            }
                                        }


                                        for (LocationEntitie location : doc.getLocationList()) {
                                            if (locationListForFilter.isEmpty()) {
                                                locationListForFilter.add(new LocationEntitie(location.getId(), location.getName(), false));
                                                locationNameListForFilter.add(location.getName());
                                            } else {
                                                boolean status = false;
                                                for (LocationEntitie l : locationListForFilter) {
                                                    if (l.getId() == location.getId()) {
                                                        status = true;
                                                        break;
                                                    } else {
                                                    }
                                                }
                                                if (!status) {
                                                    locationListForFilter.add(new LocationEntitie(location.getId(), location.getName(), false));
                                                    locationNameListForFilter.add(location.getName());
                                                } else {
                                                }

                                            }
                                        }


                                    }


                                    onGetDoctorFinishedListener.visitsLocationList(locationListForFilter);
                                    onGetDoctorFinishedListener.visitsSpecList(specListForFilter);
                                    onGetDoctorFinishedListener.visitsLocationNameList(locationNameListForFilter);
                                    onGetDoctorFinishedListener.visitsSpecNameList(specNameListForFilter);


                                }
                            }
                        });
            } catch (Exception ex) {
                onGetDoctorFinishedListener.doctorsGetingFail("Something went wrong, Please try again");
            }
        }
    }

    @Override
    public void searchDoctor(ArrayList<Doctor> docArrayList, String doctorName, OnSearchDoctorFinishedListener onSearchDoctorFinishedListener) {

        final ArrayList<Doctor> searchArrayList = new ArrayList<Doctor>();
        if (docArrayList.isEmpty()) {
        } else {
            for (Doctor doc : docArrayList) {
                if (doc.getName().equals(doctorName)) {
                    searchArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getCode(), doc.getContactNo(), doc.getRegistrationNo(), doc.getQualification(), doc.getCreatedDate(),
                            doc.getCreatedByID(), doc.getCreatedByName(), doc.getImageUrl(), doc.isApproved(), doc.isRejected(),
                            doc.getSpecialization(), doc.getLocation(), doc.getSpecializationList(), doc.getLocationList(),doc.getStatus()));
                } else {
                }
            }
        }

        if ((searchArrayList.isEmpty()) && (!docArrayList.isEmpty())) {
            if (doctorName.isEmpty() || doctorName.equals("") || doctorName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(docArrayList);
            } else {
                for (Doctor doc : docArrayList) {
                    String text = doc.getName();
                    String patternString = doctorName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getCode(), doc.getContactNo(), doc.getRegistrationNo(), doc.getQualification(), doc.getCreatedDate(),
                                doc.getCreatedByID(), doc.getCreatedByName(), doc.getImageUrl(), doc.isApproved(),
                                doc.isRejected(), doc.getSpecialization(), doc.getLocation(), doc.getSpecializationList(), doc.getLocationList(),doc.getStatus()));
                    } else {
                    }

                }

            }
        } else {
        }

        onSearchDoctorFinishedListener.searchDoctorList(searchArrayList);
    }

    @Override
    public void getSpecialization(Context context, final OnGetSpecializationFinishedListener onGetSpecializationFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetSpecializationFinishedListener.SpecializationGetingNetworkFail();
        } else {
            try {

                final ArrayList<Specialization> specArrayList = new ArrayList<Specialization>();
                final ArrayList<String> specNameList = new ArrayList<String>();

                apiService.getApprovedSpecializations(tokenID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Specialization>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Specialization> specializations) {
                                specList = specializations;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetSpecializationFinishedListener.SpecializationGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (specList.isEmpty()) {
                                    onGetSpecializationFinishedListener.SpecializationEmpty();
                                } else {
                                    for (Specialization spec : specList) {
                                        specArrayList.add(new Specialization(spec.getId(), spec.getName(), false));
                                        specNameList.add(spec.getName());
                                    }
                                    onGetSpecializationFinishedListener.SpecializationList(specList, specNameList);
                                }
                            }
                        });
            } catch (Exception ex) {
                onGetSpecializationFinishedListener.SpecializationGetingFail("Something went wrong, Please try again");
            }
        }

    }

    @Override
    public void addSpecToDocStart(Specialization spec, boolean addOrRemove, OnAddSpecToDocStartFinishedListener onAddSpecToDocStartFinishedListener) {
        onAddSpecToDocStartFinishedListener.addedSpecStart(spec, addOrRemove);
    }

    @Override
    public void addSpecToDoc(ArrayList<Specialization> specListGlobal, Specialization product, boolean addOrRemove, OnAddSpecToDocFinishedListener onAddSpecToDocFinishedListener) {

        if (addOrRemove) {
            if (specListGlobal.isEmpty()) {
                specListGlobal.add(new Specialization(product.getId(), product.getName()));
            } else {
                boolean isFound = false;
                for (int i = 0; i < specListGlobal.size(); i++) {
                    if (specListGlobal.get(i).getId() == product.getId()) {
                        isFound = true;
                        specListGlobal.remove(i);
                        break;
                    }
                }
                if (!isFound) {
                    specListGlobal.add(new Specialization(product.getId(), product.getName()));
                } else {

                }

            }
        } else {
            if (specListGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < specListGlobal.size(); i++) {
                    if (specListGlobal.get(i).getId() == product.getId()) {
                        specListGlobal.remove(i);
                    }
                }
            }
        }

        onAddSpecToDocFinishedListener.addedSpec(specListGlobal);


    }

    @Override
    public void showAddSpec(ArrayList<Specialization> addedSpecListGlobal, ArrayList<Specialization> allSpecList, OnShowAddSpecFinishedListener onShowAddSpecFinishedListener) {

        final ArrayList<Specialization> specArrayList = new ArrayList<Specialization>();

        for (Specialization spc : addedSpecListGlobal) {
            specArrayList.add(new Specialization(spc.getId(), spc.getName(), true));
        }
        for (Specialization spcAll : allSpecList) {
            boolean status = false;
            for (Specialization spc : addedSpecListGlobal) {
                if (spcAll.getId() == spc.getId()) {
                    status = true;
                    break;
                }

            }
            if (status) {
            } else {
                specArrayList.add(new Specialization(spcAll.getId(), spcAll.getName(), false));
            }
        }

        onShowAddSpecFinishedListener.showAddedSpec(specArrayList);

    }

    @Override
    public void searchSpec(ArrayList<Specialization> specArrayList, String specName, OnSearchSpecFinishedListener onSearchSpecFinishedListener) {
        final ArrayList<Specialization> specializationArrayList = new ArrayList<Specialization>();
        if (specArrayList.isEmpty()) {
        } else {
            for (Specialization spc : specArrayList) {
                if (spc.getName().equals(specName)) {
                    specializationArrayList.add(new Specialization(spc.getId(), spc.getName(), false));
                } else {
                }
            }
        }


        if ((specializationArrayList.isEmpty()) && (!specArrayList.isEmpty())) {
            if (specName.isEmpty() || specName.equals("") || specName.equalsIgnoreCase("all")) {
                specializationArrayList.addAll(specArrayList);
            } else {
                for (Specialization spc : specArrayList) {
                    String text = spc.getName();
                    String patternString = specName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);

                    if (matcher.lookingAt()) {
                        specializationArrayList.add(new Specialization(spc.getId(), spc.getName(), false));
                    } else {
                    }

                }

            }
        } else {
        }
        onSearchSpecFinishedListener.searchSpecList(specializationArrayList);
    }

    @Override
    public void postDoctor(Context context, final String name, final String tpNumber, final String regNumber, final String qualification, final int isAfterSuggestion, final ArrayList<Specialization> specArrayList, final OnPostDoctorFinishedListener onPostDoctorFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostDoctorFinishedListener.postDoctorNetworkFail();
        } else if (name.isEmpty()) {
            onPostDoctorFinishedListener.postDoctorError("Doctor Name is empty");
        } else if (specArrayList.isEmpty()) {
            onPostDoctorFinishedListener.postDoctorError("Please add Specialties");
        } else if (qualification.isEmpty() || qualification.equals("") ||qualification == null) {
            onPostDoctorFinishedListener.postDoctorError("Doctor qualification is empty");
        } else if (tpNumber.isEmpty() || tpNumber.equals("") ||tpNumber  == null) {
            onPostDoctorFinishedListener.postDoctorError("Doctor contact number is empty");
        }else if (tpNumber.length()!=10) {
            onPostDoctorFinishedListener.postDoctorError("Doctor contact number invalid");
        } else {


            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("name", name);
            jsonObject.addProperty("ContactNo",tpNumber);
            jsonObject.addProperty("RegistrationNo",regNumber);
            jsonObject.addProperty("Qualification", qualification);
            jsonObject.addProperty("CreatedByID", userId);
            jsonObject.addProperty("IsAfterSuggestion", isAfterSuggestion);

            JsonArray cartJsonArr = new JsonArray();

            for (Specialization spec : specArrayList) {
                JsonObject ob = new JsonObject();
                ob.addProperty("ID", spec.getId());
                cartJsonArr.add(ob);
            }

            jsonObject.add("Products", cartJsonArr);

            try {

                final ArrayList<Doctor> docArrayList = new ArrayList<Doctor>();

                apiService.saveDoctor(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doctors) {
                                docArrayListforDuplicate = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostDoctorFinishedListener.postDoctorFail("Something went wrong, Please try again", name, tpNumber, regNumber, qualification, isAfterSuggestion, specArrayList);

                            }

                            @Override
                            public void onComplete() {
                                if (docArrayListforDuplicate.isEmpty()) {
                                    onPostDoctorFinishedListener.postDoctorError("Empty Respond");
                                } else {


                                    for(Doctor doctor : docArrayListforDuplicate){
                                        qualify= doctor.getQualification();

                                    }

                                    if(qualify==null){
                                        onPostDoctorFinishedListener.postDoctorSuccess();
                                    }else {
                                        for (Doctor d : docArrayListforDuplicate) {
                                            docArrayList.add(new Doctor(d.getId(), d.getName(), d.getContactNo(), d.getRegistrationNo(), d.getCreatedByID(), d.getCreatedByName()));
                                        }
                                        onPostDoctorFinishedListener.postDoctorDuplicate(docArrayList,  name, tpNumber, regNumber, qualification, isAfterSuggestion, specArrayList);

                                    }



                                 /*   String statusValue = docArrayListforDuplicate.get(0).getStatus();
                                    if(statusValue.equals("Successful")){
                                        onPostDoctorFinishedListener.postDoctorSuccess();
                                    }else {
                                        for (Doctor d : docArrayListforDuplicate) {
                                            docArrayList.add(new Doctor(d.getId(), d.getName(), d.getContactNo(), d.getRegistrationNo(), d.getCreatedByID(), d.getCreatedByName()));
                                        }
                                        onPostDoctorFinishedListener.postDoctorDuplicate(docArrayList,  name, tpNumber, regNumber, qualification, isAfterSuggestion, specArrayList);

                                    }*/

                                }
                            }
                        });
            } catch (Exception ex) {
                onPostDoctorFinishedListener.postDoctorFail("Something went wrong, Please try again",  name, tpNumber, regNumber, qualification, isAfterSuggestion, specArrayList);
            }

        }

    }


    @Override
    public void addLocationToDocFilterStart(LocationEntitie locationEntitie, boolean addOrRemove, OnAddLocationToDocFilterStartFinishedListener onAddLocationToDocStartFilterFinishedListener) {
        onAddLocationToDocStartFilterFinishedListener.locationToDocFilterStart(locationEntitie, addOrRemove);
    }

    @Override
    public void addLocationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal, LocationEntitie locationEntitie, boolean addOrRemove, OnAddLocationToDocFilterFinishedListener onAddLocationToDocFilterFinishedListener) {
        if (addOrRemove) {
            if (locArrayListToFilterGlobal.isEmpty()) {
                locArrayListToFilterGlobal.add(new LocationEntitie(locationEntitie.getId(), locationEntitie.getName()));
            } else {
                boolean isFound = false;

                for (LocationEntitie loc : locArrayListToFilterGlobal) {
                    if (loc.getId() == locationEntitie.getId()) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound)
                    locArrayListToFilterGlobal.add(new LocationEntitie(locationEntitie.getId(), locationEntitie.getName()));
            }
        } else {
            if (locArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < locArrayListToFilterGlobal.size(); i++) {
                    if (locArrayListToFilterGlobal.get(i).getId() == locationEntitie.getId()) {
                        locArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }
        onAddLocationToDocFilterFinishedListener.locationToDocFilter(locArrayListToFilterGlobal);

    }

    @Override
    public void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal, ArrayList<LocationEntitie> allFilterLocList, OnAddLocationToFilterFinishedListener onAddLocationToFilterFinishedListener) {
        final ArrayList<LocationEntitie> locArrayList = new ArrayList<LocationEntitie>();

        for (LocationEntitie loc : addedLocListGlobal) {
            locArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), true));
        }

        for (LocationEntitie locAll : allFilterLocList) {
            boolean status = false;
            for (LocationEntitie lo : addedLocListGlobal) {
                if (locAll.getId() == lo.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                locArrayList.add(new LocationEntitie(locAll.getId(), locAll.getName(), false));

            }
        }

        onAddLocationToFilterFinishedListener.addLocationToFilter(locArrayList);
    }

    @Override
    public void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList, ArrayList<LocationEntitie> selectedLocList, OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener) {
        ArrayList<LocationEntitie> newLocList = new ArrayList<LocationEntitie>();

        if (selectedLocList.isEmpty()) {
            for (LocationEntitie l : totalLocList) {
                l.setSelect(false);
            }
            newLocList = totalLocList;
        } else {
            for (LocationEntitie added : selectedLocList) {
                newLocList.add(new LocationEntitie(added.getId(), added.getName(), true));
            }
            for (LocationEntitie added : totalLocList) {
                newLocList.add(new LocationEntitie(added.getId(), added.getName(), false));
            }
            for (int i = 0; i < newLocList.size(); i++) {
                for (int j = i + 1; j < newLocList.size(); j++) {
                    if (newLocList.get(i).getId() == newLocList.get(j).getId()) {
                        newLocList.remove(j);
                        j--;
                    }
                }
            }
        }
        onSetSelectedFilterLocationFinishedListener.showSelectedFilterLocation(newLocList);

    }

    @Override
    public void addSpecToDocFilterStart(Specialization specialization, boolean addOrRemove, OnAddSpecToDocFilterStartFinishedListener onAddSpecToDocFilterStartFinishedListener) {
        onAddSpecToDocFilterStartFinishedListener.specToDocFilterStart(specialization, addOrRemove);
    }

    @Override
    public void addSpecToDocFilter(ArrayList<Specialization> specArrayListToFilterGlobal, Specialization specialization, boolean addOrRemove, OnAddSpecToDocFilterFinishedListener onAddSpecToDocFilterFinishedListener) {

        if (addOrRemove) {
            if (specArrayListToFilterGlobal.isEmpty()) {
                specArrayListToFilterGlobal.add(new Specialization(specialization.getId(), specialization.getName(), false));
            } else {
                boolean isFound = false;

                for (Specialization spc : specArrayListToFilterGlobal) {
                    if (spc.getId() == specialization.getId()) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound)
                    specArrayListToFilterGlobal.add(new Specialization(specialization.getId(), specialization.getName(), false));
            }
        } else {
            if (specArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < specArrayListToFilterGlobal.size(); i++) {
                    if (specArrayListToFilterGlobal.get(i).getId() == specialization.getId()) {
                        specArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }

        onAddSpecToDocFilterFinishedListener.specToDocFilter(specArrayListToFilterGlobal);


    }

    @Override
    public void showAddSpecToFilter(ArrayList<Specialization> addedSpecListGlobal, ArrayList<Specialization> allFilterSpecist, OnShowSpecToFilterFinishedListener onShowSpecToFilterFinishedListener) {


        final ArrayList<Specialization> specArrayList = new ArrayList<Specialization>();


        for (Specialization spec : addedSpecListGlobal) {
            specArrayList.add(new Specialization(spec.getId(), spec.getName(), true));
        }

        for (Specialization specAll : allFilterSpecist) {
            boolean status = false;
            for (Specialization s : addedSpecListGlobal) {
                if (specAll.getId() == s.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                specArrayList.add(new Specialization(specAll.getId(), specAll.getName(), false));

            }
        }

        onShowSpecToFilterFinishedListener.addSpecToFilter(specArrayList);


    }

    @Override
    public void setSelectedFilterSpec(ArrayList<Specialization> totalSpecList, ArrayList<Specialization> selectedSpecList, OnSetSelectedFilterSpecFinishedListener onSetSelectedFilterSpecFinishedListener) {
        ArrayList<Specialization> newSpecList = new ArrayList<Specialization>();
        if (selectedSpecList.isEmpty()) {
            for (Specialization s : totalSpecList) {
                s.setSelect(false);
            }
            newSpecList = totalSpecList;
        } else {

            for (Specialization added : selectedSpecList) {
                newSpecList.add(new Specialization(added.getId(), added.getName(), true));
            }

            for (Specialization added : totalSpecList) {
                newSpecList.add(new Specialization(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newSpecList.size(); i++) {
                for (int j = i + 1; j < newSpecList.size(); j++) {
                    if (newSpecList.get(i).getId() == newSpecList.get(j).getId()) {
                        newSpecList.remove(j);
                        j--;
                    }
                }
            }

        }
        onSetSelectedFilterSpecFinishedListener.showSelectedFilterSpec(newSpecList);


    }

    @Override
    public void doctorsFilter(ArrayList<Doctor> docArrayList, ArrayList<String> statusList, ArrayList<LocationEntitie> locList, ArrayList<Specialization> specList, OnDoctorFilterFinishedListener onDoctorFilterFinishedListener) {
        ArrayList<Doctor> newDocArrayList=new ArrayList<>();
        newDocArrayList.addAll(docArrayList);

        if(statusList.isEmpty()){ }else {
            for (int i = 0; i < newDocArrayList.size(); i++) {
                boolean statusforStatus = false;
                for (String status : statusList) {
                    if (newDocArrayList.get(i).getStatus().equals(status)) {
                        statusforStatus = true;
                        break;
                    } else {
                        statusforStatus = false;
                    }
                }
                if (!statusforStatus) {
                    newDocArrayList.remove(i);
                    i--;
                }
            }
        }

        if(locList.isEmpty()){ }else {
            for (int i = 0; i < newDocArrayList.size(); i++) {
                boolean status = false;
                for (LocationEntitie docLoc : newDocArrayList.get(i).getLocationList()) {
                    if(status){
                    }else {
                        for (int j = 0; j < locList.size(); j++) {
                            if (docLoc.getId() == locList.get(j).getId()) {
                                status = true;
                                break;
                            } else {
                                status = false;
                            }
                        }
                    }
                }
                if (!status) {
                    newDocArrayList.remove(i);
                    i--;
                }
            }
        }


        if(specList.isEmpty()){ }else {

            for (int i = 0; i < newDocArrayList.size(); i++) {
                boolean status = false;
                for (Specialization docSpec : newDocArrayList.get(i).getSpecializationList()) {
                    if(status){
                    }else {
                        for (int j = 0; j < specList.size(); j++) {
                            if (docSpec.getId() == specList.get(j).getId()) {
                                status = true;
                                break;
                            } else {
                                status = false;
                            }
                        }
                    }
                }
                if (!status) {
                    newDocArrayList.remove(i);
                    i--;
                }
            }


        }




        onDoctorFilterFinishedListener.doctorsFilterList(newDocArrayList);
    }

    @Override
    public void searchLocationForFilter(ArrayList<LocationEntitie> locArrayList, String locationName, OnSearchLocationForFilterFinishedListener onSearchLocationForFilterFinishedListener) {


        final ArrayList<LocationEntitie> searchArrayList = new ArrayList<LocationEntitie>();


        if (locArrayList.isEmpty()) {
        } else {

            for (LocationEntitie loc : locArrayList) {
                if (loc.getName().equals(locationName)) {
                    searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), false));
                } else {
                }
            }

        }

        if ((searchArrayList.isEmpty()) && (!locArrayList.isEmpty())) {
            if (locationName.isEmpty() || locationName.equals("") || locationName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(locArrayList);
            } else {
                for (LocationEntitie loc : locArrayList) {
                    String text = loc.getName();
                    String patternString = locationName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), false));
                    } else {
                    }

                }

            }
        } else {
        }
        onSearchLocationForFilterFinishedListener.searchLocationList(searchArrayList);


    }

    @Override
    public void searchSpecForFilter(ArrayList<Specialization> specArrayList, String specName, OnSearchSpecForFilterFinishedListener onSearchSpecForFilterFinishedListener) {
        final ArrayList<Specialization> searchArrayList = new ArrayList<Specialization>();
        if (specArrayList.isEmpty()) {
        } else {

            for (Specialization spec : specArrayList) {
                if (spec.getName().equals(specName)) {
                    searchArrayList.add(new Specialization(spec.getId(), spec.getName(), false));
                } else {
                }
            }
        }
        if ((searchArrayList.isEmpty()) && (!specArrayList.isEmpty())) {
            if (specName.isEmpty() || specName.equals("") || specName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(specArrayList);
            } else {
                for (Specialization spec : specArrayList) {
                    String text = spec.getName();
                    String patternString = specName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Specialization(spec.getId(), spec.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchSpecForFilterFinishedListener.specListForFilter(searchArrayList);
    }
}


