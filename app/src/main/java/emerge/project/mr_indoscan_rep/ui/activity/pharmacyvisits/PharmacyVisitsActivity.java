package emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationActivity;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageActivity;
import emerge.project.mr_indoscan_rep.ui.activity.products.ProductsUnavailabilityActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.pharmacy.PharmacyAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.pharmacy.PharmacyDoctorAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.pharmacy.ProductAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PharmacyVisitsActivity extends Activity implements PharmacyVisitsView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    @BindView(R.id.recyclerview_product)
    RecyclerView recyclerviewProduct;


    @BindView(R.id.recyclerview_doc)
    RecyclerView recyclerviewDoc;


    @BindView(R.id.recyclerview_pharmacy)
    RecyclerView recyclerviewPharmacy;


    @BindView(R.id.autoCompleteTextView_pha)
    AutoCompleteTextView autoTextViewPha;


    @BindView(R.id.autoCompleteTextView_pro)
    AutoCompleteTextView autoTextViewPro;


    @BindView(R.id.autoCompleteTextView_doc)
    AutoCompleteTextView autoTextViewDoc;


    @BindView(R.id.include_progres)
    View includeProgres;


    @BindView(R.id.editText2)
    EditText editTextMonthly;

    @BindView(R.id.editText3)
    EditText editTextWeekly;


    @BindView(R.id.editText4)
    EditText editTextDaily;


    @BindView(R.id.editText_com_montly)
    EditText editTextComMonthly;

    @BindView(R.id.editText_com_weekly)
    EditText editTextComWeekly;


    @BindView(R.id.editText_com_daily)
    EditText editTextComDaily;

    @BindView(R.id.editText_competitor_product)
    EditText editTextCompetitorProduct;


    NavigationAdapter navigationAdapter;
    public static String tokenID;
    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";

    ApiInterface apiService;


    ArrayList<Products> allProductsList = new ArrayList<Products>();
    ArrayList<Pharmacy> allPharmacyList = new ArrayList<Pharmacy>();
    ArrayList<Doctor> allDoctorsList = new ArrayList<Doctor>();


    PharmacyVisitsPresenter pharmacyVisitsPresenter;


    int selectedPharmacy = 0;
    int selectedProduct = 0;
    int selectedDoctor = 0;

    String prescriptionType = "";
    int prescriptionCount = 0;

    String competitorPrescriptionType = "";
    int competitorPrescriptionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_visits);

        ButterKnife.bind(this);


        bottomNavigationBar.setSelectedItemId(R.id.navigation_visits);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Expenses", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Mileage", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Pharmacy Visits", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Products", R.drawable.ic_product_defult_small));


        tokenID = BuildConfig.API_TOKEN_ID;
        apiService = ApiClient.getClient().create(ApiInterface.class);

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();


        navigationAdapter = new NavigationAdapter(this, navigationItems);
        listViewNavigation.setAdapter(navigationAdapter);

        pharmacyVisitsPresenter = new PharmacyVisitsPresenterImpli(this);


        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(PharmacyVisitsActivity.this, ExpensesActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PharmacyVisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                } else if (position == 1) {
                    Intent intent = new Intent(PharmacyVisitsActivity.this, MileageActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PharmacyVisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 2) {

                } else if (position == 3) {
                    Intent intent = new Intent(PharmacyVisitsActivity.this, ProductsUnavailabilityActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PharmacyVisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        includeProgres.setVisibility(View.VISIBLE);
        pharmacyVisitsPresenter.getPharmacy(PharmacyVisitsActivity.this);

    }


    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @OnClick(R.id.imageView_pha_search)
    public void onClickPhaSerach(View view) {
        pharmacyVisitsPresenter.searchPharmacy(allPharmacyList, autoTextViewPha.getText().toString());
    }

    @OnClick(R.id.imageView_pro_search)
    public void onClickProductSerach(View view) {
        pharmacyVisitsPresenter.searchProduct(allProductsList, autoTextViewPro.getText().toString());
    }

    @OnClick(R.id.imageView_doc_search)
    public void onClickDoctorSerach(View view) {
        pharmacyVisitsPresenter.searchDoctors(allDoctorsList, autoTextViewDoc.getText().toString());
    }


    @OnClick(R.id.btn_save_pha_visits)
    public void onClickAdd(View view) {
        includeProgres.setVisibility(View.VISIBLE);

        if(!editTextMonthly.getText().toString().equals("")){
            try {
                prescriptionCount = Integer.parseInt(editTextMonthly.getText().toString());
                prescriptionType = "M";
            } catch (NumberFormatException num) {

            }
        }else if(!editTextWeekly.getText().toString().equals("")){
            try {
                prescriptionCount = Integer.parseInt(editTextWeekly.getText().toString());
                prescriptionType = "W";
            } catch (NumberFormatException num) {

            }

        }else if(!editTextDaily.getText().toString().equals("")){
            try {
                prescriptionCount = Integer.parseInt(editTextDaily.getText().toString());
                prescriptionType = "D";
            } catch (NumberFormatException num) {

            }
        }else {
            prescriptionCount = 0;
        }



        if(!editTextComMonthly.getText().toString().equals("")){
            try {
                competitorPrescriptionCount = Integer.parseInt(editTextComMonthly.getText().toString());
                competitorPrescriptionType = "M";
            } catch (NumberFormatException num) {

            }
        }else if(!editTextComWeekly.getText().toString().equals("")){
            try {
                competitorPrescriptionCount = Integer.parseInt(editTextComWeekly.getText().toString());
                competitorPrescriptionType = "W";
            } catch (NumberFormatException num) {

            }

        }else if(!editTextComDaily.getText().toString().equals("")){
            try {
                competitorPrescriptionCount = Integer.parseInt(editTextComDaily.getText().toString());
                competitorPrescriptionType = "D";
            } catch (NumberFormatException num) {

            }
        }else {
            competitorPrescriptionCount = 0;
        }



        pharmacyVisitsPresenter.postPharmacyVisits(this,selectedPharmacy,selectedProduct,selectedDoctor,prescriptionCount,
                prescriptionType,editTextCompetitorProduct.getText().toString(),competitorPrescriptionCount,competitorPrescriptionType);
    }


    @OnTextChanged(R.id.editText2)
    protected void onTextChangedMonthly(CharSequence text) {
        int montlyRate = 0;
        try {
            montlyRate = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }

        if(montlyRate<=0){

        }else {
            editTextWeekly.setText("");
            editTextDaily.setText("");
        }
    }

    @OnTextChanged(R.id.editText3)
    protected void onTextChangedWeekly(CharSequence text) {
        int weeklyRate = 0;
        try {
            weeklyRate = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }

        if(weeklyRate<=0){

        }else {
            editTextMonthly.setText("");
            editTextDaily.setText("");
        }
    }


    @OnTextChanged(R.id.editText4)
    protected void onTextChangedDaily(CharSequence text) {
        int dailyRate = 0;
        try {
            dailyRate = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }

        if(dailyRate<=0){

        }else {
            editTextMonthly.setText("");
            editTextWeekly.setText("");
        }
    }




    @OnTextChanged(R.id.editText_com_montly)
    protected void onTextChangedCommMontly(CharSequence text) {
        int montlyRate = 0;
        try {
            montlyRate = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }

        if(montlyRate<=0){
        }else {
            editTextComDaily.setText("");
            editTextComWeekly.setText("");
        }
    }


    @OnTextChanged(R.id.editText_com_weekly)
    protected void onTextChangedCommWeekly(CharSequence text) {
        int weeklyRate = 0;
        try {
            weeklyRate = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }

        if(weeklyRate<=0){
        }else {
            editTextComMonthly.setText("");
            editTextComDaily.setText("");
        }
    }


    @OnTextChanged(R.id.editText_com_daily)
    protected void onTextChangedCommDaily(CharSequence text) {
        int dailyRate = 0;
        try {
            dailyRate = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }
        if(dailyRate<=0){

        }else {
            editTextComMonthly.setText("");
            editTextComWeekly.setText("");
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Do you really want to exit ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    Intent intent = new Intent(PharmacyVisitsActivity.this, VisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PharmacyVisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_location:
                    Intent intent2 = new Intent(PharmacyVisitsActivity.this, LocationActivity.class);
                    Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(PharmacyVisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent2, bndlanimation2);
                    finish();

                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoc = new Intent(PharmacyVisitsActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoc = ActivityOptions.makeCustomAnimation(PharmacyVisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoc, bndlanimationDoc);
                    finish();
                    return true;

            }
            return false;
        }
    };


    @Override
    public void pharmacyList(ArrayList<Pharmacy> pharmacyList, ArrayList<String> pharmacyNAmeList) {
        includeProgres.setVisibility(View.GONE);

        allPharmacyList = pharmacyList;

        PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(this, pharmacyList, this);
        recyclerviewPharmacy.setAdapter(pharmacyAdapter);


        ArrayAdapter<String> phaAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pharmacyNAmeList);
        autoTextViewPha.setAdapter(phaAdapterList);


        autoTextViewPha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedPha = parent.getItemAtPosition(pos).toString();

                pharmacyVisitsPresenter.searchPharmacy(allPharmacyList, selectedPha);

            }
        });


        pharmacyVisitsPresenter.getProduct(PharmacyVisitsActivity.this);


    }

    @Override
    public void pharmacyFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            pharmacyVisitsPresenter.getPharmacy(PharmacyVisitsActivity.this);

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void pharmacyNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchPharmacyList(ArrayList<Pharmacy> pharmacyList) {

        PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(this, pharmacyList, this);
        recyclerviewPharmacy.setAdapter(pharmacyAdapter);

    }

    @Override
    public void selectedPharmacyID(int selectedPharmacyId) {
        selectedPharmacy = selectedPharmacyId;
    }

    @Override
    public void productList(ArrayList<Products> productList, ArrayList<String> productNameList) {

        includeProgres.setVisibility(View.GONE);

        allProductsList = productList;

        ProductAdapter productAdapter = new ProductAdapter(this, productList, this);
        recyclerviewProduct.setAdapter(productAdapter);


        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, productNameList);
        autoTextViewPro.setAdapter(adapterList);

        autoTextViewPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedItem = parent.getItemAtPosition(pos).toString();
                pharmacyVisitsPresenter.searchProduct(allProductsList, selectedItem);

            }
        });


        pharmacyVisitsPresenter.getDoctors(this);


    }

    @Override
    public void productFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            pharmacyVisitsPresenter.getProduct(PharmacyVisitsActivity.this);

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void productNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void searchProductList(ArrayList<Products> productList) {
        ProductAdapter productAdapter = new ProductAdapter(this, productList, this);
        recyclerviewProduct.setAdapter(productAdapter);
    }

    @Override
    public void selectedProductID(int selectedProductId) {
        selectedProduct = selectedProductId;
    }


    @Override
    public void doctorsList(ArrayList<Doctor> doctorsList, ArrayList<String> doctorsNameList) {


        includeProgres.setVisibility(View.GONE);

        allDoctorsList = doctorsList;


        PharmacyDoctorAdapter pharmacyDoctorAdapter = new PharmacyDoctorAdapter(this, doctorsList, this);
        recyclerviewDoc.setAdapter(pharmacyDoctorAdapter);


        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, doctorsNameList);
        autoTextViewDoc.setAdapter(adapterList);

        autoTextViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedItem = parent.getItemAtPosition(pos).toString();
                pharmacyVisitsPresenter.searchDoctors(allDoctorsList, selectedItem);

            }
        });

    }

    @Override
    public void doctorsFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            pharmacyVisitsPresenter.getDoctors(PharmacyVisitsActivity.this);

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void doctorsNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchDoctorsList(ArrayList<Doctor> doctorsList) {
        PharmacyDoctorAdapter pharmacyDoctorAdapter = new PharmacyDoctorAdapter(this, doctorsList, this);
        recyclerviewDoc.setAdapter(pharmacyDoctorAdapter);
    }

    @Override
    public void selectedDoctorID(int selectedDoctorId) {
        selectedDoctor = selectedDoctorId;
    }




    @Override
    public void postPharmacyVisitsError(String msg) {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postPharmacyVisitsSuccess() {

        editTextComMonthly.setText("");
        editTextComWeekly.setText("");
        editTextComDaily.setText("");

        editTextWeekly.setText("");
        editTextDaily.setText("");
        editTextMonthly.setText("");


        includeProgres.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Success");
            alertDialogBuilder.setMessage("Pharmacy Visits adding success");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Pharmacy Visits adding success", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void postPharmacyVisitsFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            pharmacyVisitsPresenter.postPharmacyVisits(PharmacyVisitsActivity.this,selectedPharmacy,selectedProduct,selectedDoctor,prescriptionCount,
                                    prescriptionType,editTextCompetitorProduct.getText().toString(),competitorPrescriptionCount,competitorPrescriptionType);

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void postPharmacyVisitsNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }
}
