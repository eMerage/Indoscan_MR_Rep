package emerge.project.mr_indoscan_rep.ui.activity.expences;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ExpensesView {

        void expensesCategoryList(ArrayList<ExpencesCategorys> list);
        void expensesCategoryFail(String failMsg);
        void expensesCategoryNetworkFail();

        void expensesSubCategoryList(ArrayList<ExpencesCategorys> subList);
        void expensesSubCategoryFail(String failMsg);
        void expensesSubCategoryNetworkFail();

        void  postExpensesError(String msg);
        void  postExpensesSuccess();
        void  postExpensesFail(String failMsg);
        void  postExpensesNetworkFail();


}
