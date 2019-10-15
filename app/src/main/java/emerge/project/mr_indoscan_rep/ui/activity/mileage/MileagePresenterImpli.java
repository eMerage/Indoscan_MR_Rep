package emerge.project.mr_indoscan_rep.ui.activity.mileage;


import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesInteractor;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesInteractorImpil;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesView;
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class MileagePresenterImpli implements MileagePresenter,
        MileageInteractor.OnDayStartMileageFinishedListener,
MileageInteractor.OnpostDayStartMileageFinishedListener,
        MileageInteractor.OnPostDayEndMileageFinishedListener,
        MileageInteractor.OnDetailsSummaryFinishedListener{


    private MileageView mileageView;
    MileageInteractor mileageInteractor;


    public MileagePresenterImpli(MileageView mileageview) {
        this.mileageView = mileageview;
        this.mileageInteractor = new MileageInteractorImpil();
    }



    @Override
    public void checkDayStartMileage(Context context) {
        mileageInteractor.checkDayStartMileage(context,this);
    }

    @Override
    public void dayStartMileage() {
        mileageView.dayStartMileage();
    }

    @Override
    public void dayStartMileageFail(String failMsg) {
        mileageView.dayStartMileageFail(failMsg);
    }

    @Override
    public void dayStartMileageNetworkFail() {
        mileageView.dayStartMileageNetworkFail();
    }





    @Override
    public void postDayStartMileage(Context context, int currentOdometerReading, int currentDayOdometerReading, Double latitude, Double longitude) {
        mileageInteractor.postDayStartMileage(context,currentOdometerReading,currentDayOdometerReading,latitude,longitude,this);
    }



    @Override
    public void postDayStartMileageError(String msg) {
        mileageView.postDayStartMileageError(msg);
    }

    @Override
    public void postDayStartMileageSuccess() {
        mileageView.postDayStartMileageSuccess();
    }

    @Override
    public void postDayStartMileageFail(String failMsg) {
        mileageView.postDayStartMileageFail(failMsg);
    }

    @Override
    public void postDayStartMileageNetworkFail() {
        mileageView.postDayStartMileageNetworkFail();
    }





    @Override
    public void postDayEndMileage(Context context, int dayEndOdometerReading, int mileagePerDay, int privetMileagePerDay, Bitmap image, Double latitude, Double longitude) {
        mileageInteractor.postDayEndMileage(context,dayEndOdometerReading,mileagePerDay,privetMileagePerDay,image,latitude,longitude,this);
    }




    @Override
    public void postDayEndMileageError(String msg) {
        mileageView.postDayEndMileageError(msg);
    }

    @Override
    public void postDayEndMileageSuccess() {
        mileageView. postDayEndMileageSuccess();
    }

    @Override
    public void postDayEndMileageFail(String failMsg) {
        mileageView.postDayEndMileageFail( failMsg);
    }

    @Override
    public void postDayEndMileageNetworkFail() {
        mileageView.postDayEndMileageNetworkFail();
    }



    @Override
    public void getDetailsSummary() {
        mileageInteractor.getDetailsSummary(this);
    }

    @Override
    public void detailsSummaryList(DetailsSummary list) {
        mileageView.detailsSummaryList(list);
    }

    @Override
    public void detailsSummaryFail(String failMsg) {
        mileageView.detailsSummaryFail(failMsg);
    }

    @Override
    public void detailsSummaryNetworkFail() {
        mileageView.detailsSummaryNetworkFail();
    }
}
