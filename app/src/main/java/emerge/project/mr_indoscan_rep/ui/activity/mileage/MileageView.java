package emerge.project.mr_indoscan_rep.ui.activity.mileage;


import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface MileageView {

    void dayStartMileage();

    void dayStartMileageFail(String failMsg);

    void dayStartMileageNetworkFail();


    void postDayStartMileageError(String msg);

    void postDayStartMileageSuccess();

    void postDayStartMileageFail(String failMsg);

    void postDayStartMileageNetworkFail();


    void postDayEndMileageError(String msg);

    void postDayEndMileageSuccess();

    void postDayEndMileageFail(String failMsg);

    void postDayEndMileageNetworkFail();


    void detailsSummaryList(DetailsSummary list);

    void detailsSummaryFail(String failMsg);

    void detailsSummaryNetworkFail();


}
