package emerge.project.mr_indoscan_rep.ui.activity.expences;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;

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
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationInteractor;
import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class ExpensesInteractorImpil implements ExpensesInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    ArrayList<ExpencesCategorys> list;
    ArrayList<ExpencesCategorys> subList;

    Boolean saveExpeenses = true;
    Boolean saveExpeensesImages = true;


    @Override
    public void getExpensesCategory(Context context, final OnGetExpensesCategoryFinishedListener onGetExpensescategoryFinishedListener) {


        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetExpensescategoryFinishedListener.expensesCategoryNetworkFail();
        }else {
            try {
                list = new ArrayList<ExpencesCategorys>();

                apiService.getExpenseCategories(tokenID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ExpencesCategorys>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<ExpencesCategorys> doctors) {
                                list = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetExpensescategoryFinishedListener.expensesCategoryFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {
                                onGetExpensescategoryFinishedListener.expensesCategoryList(list);
                            }
                        });
            } catch (Exception ex) {
                onGetExpensescategoryFinishedListener.expensesCategoryFail("Something went wrong, Please try again");
            }

        }




    }

    @Override
    public void getExpensesSubCategory(Context context, final OnGetExpensesSubCategoryFinishedListener onGetExpensesSubcategoryFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetExpensesSubcategoryFinishedListener.expensesSubCategoryNetworkFail();
        }else {
            subList = new ArrayList<ExpencesCategorys>();
            try {
                apiService.getExpenseSubCategories(tokenID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ExpencesCategorys>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<ExpencesCategorys> doctors) {
                                subList = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetExpensesSubcategoryFinishedListener.expensesSubCategoryFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {
                                onGetExpensesSubcategoryFinishedListener.expensesSubCategoryList(subList);
                            }
                        });
            } catch (Exception ex) {
                onGetExpensesSubcategoryFinishedListener.expensesSubCategoryFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void postExpenses(Context context, String billdate, int catID, int subCatid, String ref, String description, Double billamount, final ArrayList<Bitmap> imagelist,
                             final OnPostExpensesFinishedListener onPostExpensesFinishedListener) {



        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostExpensesFinishedListener.postExpensesNetworkFail();
        } else if ((billdate.isEmpty()) || (billamount.equals(""))) {
            onPostExpensesFinishedListener.postExpensesError("Please set the Bill Date");
        } else if (catID == 0) {
            onPostExpensesFinishedListener.postExpensesError("Please select the Category");
        } else if (subCatid == 0) {
            onPostExpensesFinishedListener.postExpensesError("Please select the Sub Category");
        } else if (billamount == 0.0) {
            onPostExpensesFinishedListener.postExpensesError("Please add bill value");
        } else if (imagelist.size() == 0) {
            onPostExpensesFinishedListener.postExpensesError("Please add reference image");
        } else {
            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);
            final JsonObject jsonObject = new JsonObject();
            final String code = genarateExpenseCode(String.valueOf(userId));

            jsonObject.addProperty("MobileCode", code);
            jsonObject.addProperty("UserID",userId);
            jsonObject.addProperty("BillDate",billdate);
            jsonObject.addProperty("CategoryID", catID);
            jsonObject.addProperty("SubCategoryID", subCatid);
            jsonObject.addProperty("References", ref);
            jsonObject.addProperty("Description", description);
            jsonObject.addProperty("Amount", billamount);


            try {
                apiService.saveExpense(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean expen) {
                                saveExpeenses = expen;
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("xxxxxxxxxxxxxxxxxxxx : 1 "+e.toString());

                                onPostExpensesFinishedListener.postExpensesFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {
                                new AddImages(imagelist,code,onPostExpensesFinishedListener).execute();

                            }
                        });
            } catch (Exception ex) {
                System.out.println("xxxxxxxxxxxxxxxxxxxx : 2 "+ex.toString());
                onPostExpensesFinishedListener.postExpensesFail("Something went wrong, Please try again");
            }

        }

    }

    public void addExpenseImage(JsonObject jsonObject, final OnPostExpensesFinishedListener onPostExpensesFinishedListener) {
        try {
            apiService.saveExpenseImages(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Boolean respond) {
                            saveExpeensesImages = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("xxxxxxxxxxxxxxxxxxxx : 3 "+e.toString());
                            onPostExpensesFinishedListener.postExpensesFail("Something went wrong, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (saveExpeensesImages) {
                                onPostExpensesFinishedListener.postExpensesSuccess();
                            } else {
                                onPostExpensesFinishedListener.postExpensesFail("Something went wrong, Please try again");

                            }
                        }
                    });

        } catch (Exception ex) {
            System.out.println("xxxxxxxxxxxxxxxxxxxx : 4 "+ex.toString());
            onPostExpensesFinishedListener.postExpensesFail("Something went wrong, Please try again");
        }


    }

    private class AddImages extends AsyncTask<Void, Void, Void> {

        ArrayList<Bitmap> listBitmap;
        ArrayList<String> listStringImages;
        String mCode;
        OnPostExpensesFinishedListener onPostExpensesFinishedListener;


        final JsonObject jsonObject ;

        public AddImages(ArrayList<Bitmap> list,String code,OnPostExpensesFinishedListener onpostExpensesFinishedListener) {
            listBitmap = list;
            mCode = code;
            onPostExpensesFinishedListener = onpostExpensesFinishedListener;

            listStringImages= new ArrayList<String>();
            jsonObject = new JsonObject();
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObject.addProperty("ImageCode", mCode);
            JsonArray cartJsonArr = new JsonArray();
            for(int a = 0 ;a<listBitmap.size();a++){
                JsonObject ob = new JsonObject();
                ob.addProperty("ImageBase64", bitMapToString(listBitmap.get(a)));
                cartJsonArr.add(ob);

            }
            jsonObject.add("ImageList", cartJsonArr);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            addExpenseImage(jsonObject,onPostExpensesFinishedListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }





    public String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public String genarateExpenseCode(String userID) {
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
        String expCode ="EXP"+userID + numberFromTime;
        return expCode;
    }
}


