package emerge.project.mr_indoscan_rep.ui.activity.expences;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationInteractor;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationInteractorImpil;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationView;
import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class ExpensesPresenterImpli implements ExpensesPresenter,
        ExpensesInteractor.OnGetExpensesCategoryFinishedListener,
   ExpensesInteractor.OnGetExpensesSubCategoryFinishedListener,
ExpensesInteractor.OnPostExpensesFinishedListener{


    private ExpensesView expensesView;
    ExpensesInteractor expensesInteractor;


    public ExpensesPresenterImpli(ExpensesView expensesview) {
        this.expensesView = expensesview;
        this.expensesInteractor = new ExpensesInteractorImpil();
    }

    @Override
    public void expensesCategoryList(ArrayList<ExpencesCategorys> list) {
        expensesView.expensesCategoryList(list);
    }

    @Override
    public void expensesCategoryFail(String failMsg) {
        expensesView.expensesCategoryFail(failMsg);
    }

    @Override
    public void expensesCategoryNetworkFail() {
        expensesView.expensesCategoryNetworkFail();
    }

    @Override
    public void expensesSubCategoryList(ArrayList<ExpencesCategorys> subList) {
        expensesView.expensesSubCategoryList(subList);
    }

    @Override
    public void expensesSubCategoryFail(String failMsg) {
        expensesView.expensesSubCategoryFail(failMsg);
    }

    @Override
    public void expensesSubCategoryNetworkFail() {
        expensesView.expensesSubCategoryNetworkFail();
    }

    @Override
    public void postExpensesError(String msg) {
        expensesView.postExpensesError(msg);
    }

    @Override
    public void postExpensesSuccess() {
        expensesView.postExpensesSuccess();
    }

    @Override
    public void postExpensesFail(String failMsg) {
        expensesView.postExpensesFail(failMsg);
    }

    @Override
    public void postExpensesNetworkFail() {
        expensesView.postExpensesNetworkFail();
    }

    @Override
    public void getExpensesCategory(Context context) {
        expensesInteractor.getExpensesCategory(context,this);
    }

    @Override
    public void getExpensesSubCategory(Context context) {
        expensesInteractor.getExpensesSubCategory(context,this);
    }

    @Override
    public void postExpenses(Context context, String billdate, int catID, int subCatid, String ref, String description, Double billamount, ArrayList<Bitmap> imagelist) {
        expensesInteractor.postExpenses(context,billdate,catID,subCatid,ref,description,billamount,imagelist,this);
    }
}
