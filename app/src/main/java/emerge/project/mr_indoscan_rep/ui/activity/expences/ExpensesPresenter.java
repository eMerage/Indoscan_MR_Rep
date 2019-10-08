package emerge.project.mr_indoscan_rep.ui.activity.expences;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ExpensesPresenter {
    void getExpensesCategory(Context context);
    void getExpensesSubCategory(Context context);
    void postExpenses(Context context, String billdate, int catID, int subCatid, String ref, String description, Double billamount , ArrayList<Bitmap> imagelist);

}
