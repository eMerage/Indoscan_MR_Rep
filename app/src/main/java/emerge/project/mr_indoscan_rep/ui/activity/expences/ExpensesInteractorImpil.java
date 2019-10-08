package emerge.project.mr_indoscan_rep.ui.activity.expences;


import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;

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

/**
 * Created by Himanshu on 4/5/2017.
 */

public class ExpensesInteractorImpil implements ExpensesInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);




    @Override
    public void getExpensesCategory(Context context, OnGetExpensesCategoryFinishedListener onGetExpensescategoryFinishedListener) {


        ArrayList<ExpencesCategorys> list = new ArrayList<ExpencesCategorys>();



       list.add(new ExpencesCategorys(1,"Cat 1"));
        list.add(new ExpencesCategorys(2,"Cat 2"));
        list.add(new ExpencesCategorys(5,"Cat 5"));
        list.add(new ExpencesCategorys(7,"Cat 7"));
        list.add(new ExpencesCategorys(8,"Cat 8"));


        onGetExpensescategoryFinishedListener.expensesCategoryList(list);

    }

    @Override
    public void getExpensesSubCategory(Context context, OnGetExpensesSubCategoryFinishedListener onGetExpensesSubcategoryFinishedListener) {


        ArrayList<ExpencesCategorys> list = new ArrayList<ExpencesCategorys>();



        list.add(new ExpencesCategorys(1,"Sub Cat 1"));
        list.add(new ExpencesCategorys(2,"Sub Cat 2"));
        list.add(new ExpencesCategorys(5,"Sub Cat 5"));
        list.add(new ExpencesCategorys(7,"Sub Cat 7"));
        list.add(new ExpencesCategorys(8,"Sub Cat 8"));


        onGetExpensesSubcategoryFinishedListener.expensesSubCategoryList(list);
    }

    @Override
    public void postExpenses(Context context, String billdate, int catID, int subCatid, String ref, String description, Double billamount, ArrayList<Bitmap> imagelist,
                             OnPostExpensesFinishedListener onPostExpensesFinishedListener) {



        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostExpensesFinishedListener.postExpensesNetworkFail();
        } else if ((billdate.isEmpty())  ||  (billamount.equals(""))) {
            onPostExpensesFinishedListener.postExpensesError("Please set the Bill Date");
        } else if (catID == 0) {
            onPostExpensesFinishedListener.postExpensesError("Please select the Category");
        } else if (subCatid == 0) {
            onPostExpensesFinishedListener.postExpensesError("Please select the Sub Category");
        } else if (billamount == 0.0) {
            onPostExpensesFinishedListener.postExpensesError("Please add bill value");
        }else if (imagelist.size()==0) {
            onPostExpensesFinishedListener.postExpensesError("Please add reference image");
        } else {







        }








        }
}


