package emerge.project.mr_indoscan_rep.ui.activity.expences;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ExpensesInteractor {


    interface OnGetExpensesCategoryFinishedListener {
        void expensesCategoryList(ArrayList<ExpencesCategorys> list);
        void expensesCategoryFail(String failMsg);
        void expensesCategoryNetworkFail();
    }
    void getExpensesCategory(Context context, OnGetExpensesCategoryFinishedListener onGetExpensescategoryFinishedListener);


    interface OnGetExpensesSubCategoryFinishedListener {
        void expensesSubCategoryList(ArrayList<ExpencesCategorys> subList);
        void expensesSubCategoryFail(String failMsg);
        void expensesSubCategoryNetworkFail();
    }
    void getExpensesSubCategory(Context context, OnGetExpensesSubCategoryFinishedListener onGetExpensesSubcategoryFinishedListener);



    interface OnPostExpensesFinishedListener {
        void  postExpensesError(String msg);
        void  postExpensesSuccess();
        void  postExpensesFail(String failMsg);
        void  postExpensesNetworkFail();
    }
    void postExpenses(Context context, String billdate, int catID, int subCatid, String ref, String description, Double billamount , ArrayList<Bitmap> imagelist,
                      OnPostExpensesFinishedListener onPostExpensesFinishedListener);




}
