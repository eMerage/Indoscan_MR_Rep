package emerge.project.mr_indoscan_rep.services.api;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.LoginUser;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;
import emerge.project.mr_indoscan_rep.utils.entittes.TargetDetails;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("User/ValidateUser")
    Observable<LoginUser> validateUser(@Query("username") String username, @Query("password") String password, @Query("usertypeID") int usertypeID, @Query("pushtokenid") String pushtokenid);


    @GET("Visit/GetAllVisitsByMR")
    Observable<ArrayList<Visit>> getAllVisits(@Query("MRID") int mrID, @Query("LocationID") int LocationID, @Query("DoctorID") int DoctorID, @Query("Date") String Date);


    @GET("Doctor/GetApprovedDoctorsNearLocation")
    Observable<ArrayList<Doctor>> getApprovedDoctorsNearLocation(@Query("MRID") int mrID, @Query("Latitude") double latitude, @Query("Longitude") double longitude);



    @GET("Location/GetNearbyLocationsOfDoctor")
    Observable<ArrayList<LocationEntitie>> getNearbyLocationsOfDoctor(@Query("DoctorID") int docID, @Query("Latitude") double latitude, @Query("Longitude") double longitude);


    @GET("Product/GetAllProductsForMR")
    Observable<ArrayList<Products>> getAllProductsForMR(@Query("MRID") int mrID);




    @POST("Visit/SaveVisit")
    Observable<Visit> saveVisit(@Body JsonObject doctorInfo);


    @GET("Location/GetAllLocationsByMR")
    Observable<ArrayList<LocationEntitie>> getAllLocationsByMR(@Query("TokenID") String tokenID, @Query("MRID") int mrID);


    @GET("District/GetAllDistricts")
    Observable<ArrayList<District>> getAllDistricts(@Query("TokenID") String tokenID);

    @POST("Location/SaveLocation")
    Observable<ArrayList<LocationEntitie>> saveLocation(@Body JsonObject locationInfo);


    @GET("Doctor/GetAllDoctors")
    Observable<ArrayList<Doctor>> getAllDoctors(@Query("TokenID") String tokenID, @Query("MRID") int mrID);


    @GET("Specialization/GetApprovedSpecializations")
    Observable<ArrayList<Specialization>> getApprovedSpecializations(@Query("TokenID") String tokenID);



    @POST("Doctor/SaveDoctor")
    Observable<ArrayList<Doctor>> saveDoctor(@Body JsonObject doctorInfo);


    @POST("Visit/SaveVisitImage")
    Observable<Boolean> saveVisitImage(@Body JsonObject visitImage);


    @GET("TargetAchievement/GetTargetAchievement")
    Observable<TargetDetails> getTargetAchievement(@Query("mrid") int mrID);



    @GET("Expense/GetExpenseCategories")
    Observable<ArrayList<ExpencesCategorys>> getExpenseCategories(@Query("TokenID") String tokenID);

    @GET("Expense/GetExpenseSubCategories")
    Observable<ArrayList<ExpencesCategorys>> getExpenseSubCategories(@Query("TokenID") String tokenID);


    @POST("Expense/SaveExpense")
    Observable<Boolean> saveExpense(@Body JsonObject expenseInfo);

    @POST("Expense/SaveExpenseImages")
    Observable<Boolean> saveExpenseImages(@Body JsonObject expenseImageInfo);



}
