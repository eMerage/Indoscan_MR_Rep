package emerge.project.mr_indoscan_rep.ui.activity.visits;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;

import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationActivity;

import emerge.project.mr_indoscan_rep.ui.activity.login.LoginActivity;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageActivity;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.products.ProductsUnavailabilityActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsDoctorAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsFilterDoctorsAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsFilterLocationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsFilterProductAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsLocationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsProductAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.ui.adapters.visits.VisitsAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import emerge.project.mr_indoscan_rep.utils.entittes.TargetDetails;
import emerge.project.mr_indoscan_rep.utils.entittes.Visit;


public class VisitsActivity extends Activity implements VisitsView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


    @BindView(R.id.relativeLayout_addvisits)
    RelativeLayout relativeLayoutAddvisits;

    @BindView(R.id.relativeLayout_visits)
    RelativeLayout relativeLayoutVisits;

    @BindView(R.id.recyclerView_visits)
    RecyclerView recyclerViewVisits;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;


    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;

    @BindView(R.id.fab)
    FloatingActionButton fab;


    @BindView(R.id.textView_date)
    TextView textViewDate;

    @BindView(R.id.textView_visitnumber)
    TextView textViewVisitNumber;

    @BindView(R.id.edittext_note)
    EditText edittextNote;


    @BindView(R.id.recyclerView_doc)
    RecyclerView recyclerViewDoc;

    @BindView(R.id.recyclerView_loc)
    RecyclerView recyclerViewLoc;


    @BindView(R.id.recyclerView_pro)
    RecyclerView recyclerViewPro;


    @BindView(R.id.autoCompleteTextView_doc)
    AutoCompleteTextView textViewDoc;

    @BindView(R.id.autoCompleteTextView_pro)
    AutoCompleteTextView textViewPro;

    @BindView(R.id.imageView_taken_photo)
    ImageView imageViewTakenPhoto;


    @BindView(R.id.text_name)
    TextView textName;

    @BindView(R.id.img_user)
    ImageView imgUser;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    private static final String USER_REMEMBER = "userRemember";
    private static final String USER_NAME = "userName";
    private static final String USER_IMAGE = "userImage";


    Dialog dialogFilter;

    RelativeLayout relativelayoutDateImage;
    ImageView imageViewLayoutDateImage;
    RelativeLayout relativelayoutDoctorImage;
    ImageView imageViewLayoutDoctorImage;

    TextView textviewSelectedProCount;
    TextView textviewSelectedLocationCount;
    TextView textviewSelectedDocCount;

    RecyclerView recyclerFilterDoctors;
    RecyclerView recyclerFilterLocation;
    RecyclerView recyclerFilterProduct;


    Animation animation, animation2;


    VisitsAdapter visitsAdapter;
    VisitsDoctorAdapter doctorsAdapter;
    VisitsLocationAdapter locationAdapter;
    VisitsProductAdapter visitsProductAdapter;

    VisitsPresenter visitsPresenter;

    LocationRequest mLocationRequest;
    private GoogleApiClient googleApiClient;


    static final int REQUEST_CHECK_SETTINGS = 2;
    static final int PICK_IMAGE_REQUEST = 3;

    private Location location;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 2;
    private boolean mLocationPermissionGranted;

    int selectDoctorID = 0;
    int selectLocationID = 0;
    String visitNumber;
    String imageCode = "";


    ArrayList<Visit> visitsList;

    ArrayList<Doctor> doctorsList;
    ArrayList<Products> productList;
    ArrayList<Products> addedproductsArrayList;

    ArrayList<Doctor> visitsDoctorsFilterList;
    ArrayList<String> visitsDoctorsNameFilterList;


    ArrayList<LocationEntitie> visitsLocationFilterList;
    ArrayList<String> visitsLocationNameFilterList;


    ArrayList<Products> visitsProductsFilterList;
    ArrayList<String> visitsProductsNameFilterList;


    ArrayList<Doctor> addedvisitsDoctorsFilterList;
    ArrayList<LocationEntitie> addedvisitsLocationFilterList;
    ArrayList<Products> addedvisitsProductsFilterList;


    int locationID = 0;
    int doctorID = 0;
    String date = "";

    String filterDateStart = "", filterDateEnd = "";

    Bitmap bitmap;


    int selectedUIOption = 2;


    int sdk;

    EncryptedPreferences encryptedPreferences;
    NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);
        ButterKnife.bind(this);

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();


        setVisitsRecycalView();
        setVisitsProductRecycalView();
        setVisitsLocationRecycalView();
        setVisitsDocRecycalView();

        setUserDetails();


        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_expanview);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.fade_out_expanview);


        visitsList = new ArrayList<Visit>();

        visitsDoctorsFilterList = new ArrayList<Doctor>();
        visitsLocationFilterList = new ArrayList<LocationEntitie>();
        visitsProductsFilterList = new ArrayList<Products>();

        doctorsList = new ArrayList<Doctor>();
        addedproductsArrayList = new ArrayList<Products>();
        addedvisitsDoctorsFilterList = new ArrayList<Doctor>();
        addedvisitsLocationFilterList = new ArrayList<LocationEntitie>();
        addedvisitsProductsFilterList = new ArrayList<Products>();


        visitsDoctorsNameFilterList = new ArrayList<String>();
        visitsLocationNameFilterList = new ArrayList<String>();
        visitsProductsNameFilterList = new ArrayList<String>();

        bottomNavigationBar.setSelectedItemId(R.id.navigation_visits);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        visitsPresenter = new VisitsPresenterImpli(this);
        visitsPresenter.setNavigation();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        googleApiClient = new GoogleApiClient.Builder(VisitsActivity.this).
                addApi(LocationServices.API).
                addConnectionCallbacks(VisitsActivity.this).
                addOnConnectionFailedListener(VisitsActivity.this).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getLocationPermission();
        } else {
            mLocationPermissionGranted = true;
            createLocationRequest();
        }


        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.getTargetDetails(this);
        visitsPresenter.getVisits(this, locationID, doctorID, date);


        setCurrentDate();


        textViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedDoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchDoctor(doctorsList, selectedDoc);
            }
        });


        textViewPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedPro = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchProduct(productList, selectedPro);

            }
        });



        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position==0){
                   Intent intent = new Intent(VisitsActivity.this, ExpensesActivity.class);
                   Bundle bndlanimation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                   startActivity(intent, bndlanimation);
                   finish();

               }else if(position==1){
                   Intent intent = new Intent(VisitsActivity.this, MileageActivity.class);
                   Bundle bndlanimation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                   startActivity(intent, bndlanimation);
                   finish();
               }
               else if(position==2){
                   Intent intent = new Intent(VisitsActivity.this, PharmacyVisitsActivity.class);
                   Bundle bndlanimation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                   startActivity(intent, bndlanimation);
                   finish();
               }  else if(position==3){
                   Intent intent = new Intent(VisitsActivity.this, ProductsUnavailabilityActivity.class);
                   Bundle bndlanimation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                   startActivity(intent, bndlanimation);
                   finish();
               }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @OnClick(R.id.imageView_logout)
    public void onClickLogout(View view) {
        encryptedPreferences.edit().putBoolean(USER_REMEMBER, false);

        Intent intent = new Intent(VisitsActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();


    }


    @OnClick(R.id.btn_add_visits)
    public void onAddVisits(View view) {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.addVisits(this, selectDoctorID, visitNumber, imageCode, selectLocationID, addedproductsArrayList, edittextNote.getText().toString(), mLastKnownLocation);
    }

    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.getVisits(this, locationID, doctorID, date);
    }

    @SuppressLint("RestrictedApi")
    @OnClick(R.id.fab)
    public void onClickAddVisits(View view) {


        if (!mLocationPermissionGranted) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VisitsActivity.this);
            alertDialogBuilder.setTitle("Location Permissions");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("Location Permissions has deny,Please allow the location permissions");
            alertDialogBuilder.setPositiveButton("Turn On",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getLocationPermission();
                        }
                    });
            alertDialogBuilder.show();
        } else if (mLastKnownLocation == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VisitsActivity.this);
            alertDialogBuilder.setTitle("Location");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("We're having trouble locating you.To find your location faster and more accurately");
            alertDialogBuilder.setPositiveButton("Try-Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            createLocationRequest();
                        }
                    });
            alertDialogBuilder.show();
        } else {
            fab.setVisibility(View.GONE);

            relativeLayoutVisits.startAnimation(animation2);
            relativeLayoutVisits.setVisibility(View.GONE);
            relativeLayoutAddvisits.startAnimation(animation);
            relativeLayoutAddvisits.setVisibility(View.VISIBLE);


            bloackUserInteraction();
            includeProgres.setVisibility(View.VISIBLE);
            visitsPresenter.getDoctors(this, mLastKnownLocation);
            visitsPresenter.createVisitNumber(this);


            selectedUIOption = 1;
        }


    }


    @OnClick(R.id.imageView_doc_search)
    public void onClickDocSearch(View view) {
        if (textViewDoc.getVisibility() == View.VISIBLE) {
            visitsPresenter.searchDoctor(doctorsList, textViewDoc.getText().toString());
        } else {
            textViewDoc.setVisibility(View.VISIBLE);
        }

    }


    @OnClick(R.id.imageView_pro_search)
    public void onClickProSearch(View view) {
        if (textViewPro.getVisibility() == View.VISIBLE) {
            visitsPresenter.searchProduct(productList, textViewPro.getText().toString());
        } else {
            textViewPro.setVisibility(View.VISIBLE);
        }

    }


    @OnClick(R.id.imageView_photo)
    public void onClickTackPhotos(View view) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, PICK_IMAGE_REQUEST);
            }

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_ACCESS_CAMERA);
        }


    }


    @OnClick(R.id.imageView_taken_photo)
    public void imageViewTakenPhoto(View view) {

        final Dialog dialogVisitsImage = new Dialog(this);
        dialogVisitsImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogVisitsImage.setContentView(R.layout.dialog_visits_photo);
        dialogVisitsImage.setCancelable(true);
        ImageView textName = dialogVisitsImage.findViewById(R.id.imageView_dialog_photo);
        try {
            textName.setImageBitmap(bitmap);
            dialogVisitsImage.show();
        } catch (Exception e) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Photography not captured properly, Please try again");
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            alertDialogBuilder.show();
        }


    }


    @OnClick(R.id.imageView_filter)
    public void onClickTackFilter(View view) {
        if (visitsList.isEmpty()) {
            Toast.makeText(this, "No Visits to filter", Toast.LENGTH_SHORT).show();
        } else {
            showFilterDialog();
        }

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

        if (relativeLayoutAddvisits.getVisibility() == View.VISIBLE) {
            relativeLayoutAddvisits.startAnimation(animation2);
            relativeLayoutAddvisits.setVisibility(View.GONE);

            relativeLayoutVisits.startAnimation(animation);
            relativeLayoutVisits.setVisibility(View.VISIBLE);

            fab.setVisibility(View.VISIBLE);

            bloackUserInteraction();
            includeProgres.setVisibility(View.VISIBLE);
            visitsPresenter.getVisits(this, locationID, doctorID, date);
            selectedUIOption = 2;

        } else {

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

    }

    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {

        navigationAdapter = new NavigationAdapter(this, navigationArrayList);
        listViewNavigation.setAdapter(navigationAdapter);


    }

    @Override
    public void visitsList(ArrayList<Visit> visitArrayList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        relativeLayoutVisits.setVisibility(View.VISIBLE);

        visitsList = visitArrayList;


        visitsAdapter = new VisitsAdapter(this, visitArrayList, this);
        recyclerViewVisits.setAdapter(visitsAdapter);


    }

    @Override
    public void visitsListNoItems() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Visits available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void visitsListFail(String failMsg, final int locationID, final int doctorID, final String date) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getVisits(VisitsActivity.this, locationID, doctorID, date);
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
    public void visitsListNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        relativeLayoutVisits.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
    }


    @Override
    public void visitsDoctorsList(ArrayList<Doctor> docListForFilter) {
        visitsDoctorsFilterList = docListForFilter;
    }

    @Override
    public void visitsDoctorsNameList(ArrayList<String> docNameListForFilter) {
        visitsDoctorsNameFilterList = docNameListForFilter;
    }

    @Override
    public void visitsLocationList(ArrayList<LocationEntitie> locListForFilter) {
        visitsLocationFilterList = locListForFilter;
    }

    @Override
    public void visitsLocationNameList(ArrayList<String> locNameListForFilter) {
        visitsLocationNameFilterList = locNameListForFilter;
    }

    @Override
    public void visitsProductsList(ArrayList<Products> productsListForFilter) {
        visitsProductsFilterList = productsListForFilter;
    }

    @Override
    public void visitsProductsNameList(ArrayList<String> productsNameList) {
        visitsProductsNameFilterList = productsNameList;
    }

    @Override
    public void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);

        doctorsAdapter = new VisitsDoctorAdapter(this, doctorArrayList, this);
        recyclerViewDoc.setAdapter(doctorsAdapter);

        doctorsList = doctorArrayList;

        ArrayAdapter<String> doctorsAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, doctorsNames);
        textViewDoc.setAdapter(doctorsAdapterList);


    }

    @Override
    public void doctorsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Doctors near this location");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();
    }

    @Override
    public void doctorsGetingFail(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getDoctors(VisitsActivity.this, mLastKnownLocation);
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
    public void doctorsGetingError(String msg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getDeviceLocation();
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getDoctors(VisitsActivity.this, mLastKnownLocation);
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
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void doctorsGetingNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("Try-Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bloackUserInteraction();
                        includeProgres.setVisibility(View.VISIBLE);
                        visitsPresenter.getDoctors(VisitsActivity.this, mLastKnownLocation);
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

    @Override
    public void searchDoctorList(ArrayList<Doctor> doctorArrayList) {
        if (doctorArrayList.isEmpty()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No Doctors are loaded,please check your location and try again");
            alertDialogBuilder.setPositiveButton("Try-Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getDoctors(VisitsActivity.this, mLastKnownLocation);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } else {
            doctorsAdapter = new VisitsDoctorAdapter(this, doctorArrayList, this);
            recyclerViewDoc.setAdapter(doctorsAdapter);
        }

    }

    @Override
    public void visitNumber(String visitnumber) {
        visitNumber = visitnumber;
        textViewVisitNumber.setText(visitnumber);
    }

    @Override
    public void locationList(ArrayList<LocationEntitie> locationArrayList) {
        locationAdapter = new VisitsLocationAdapter(this, locationArrayList, this);
        recyclerViewLoc.setAdapter(locationAdapter);
        visitsPresenter.getProducts(this, addedproductsArrayList);
    }

    @Override
    public void locationListEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No any Location approved or added yet");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();
    }

    @Override
    public void locationetingError(String msg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getDeviceLocation();
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getLocation(VisitsActivity.this, selectDoctorID, mLastKnownLocation);
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
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void locationFail(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getLocation(VisitsActivity.this, selectDoctorID, mLastKnownLocation);
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
    public void locationNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("Try-Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bloackUserInteraction();
                        includeProgres.setVisibility(View.VISIBLE);
                        visitsPresenter.getLocation(VisitsActivity.this, selectDoctorID, mLastKnownLocation);
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

    @Override
    public void selectedDoc(int docID) {

        selectDoctorID = docID;
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.getLocation(this, docID, mLastKnownLocation);
    }

    @Override
    public void selectedLocation(int locationID) {
        selectLocationID = locationID;
    }


    @Override
    public void productsList(ArrayList<Products> productArrayList, ArrayList<String> productNameList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        visitsProductAdapter = new VisitsProductAdapter(this, productArrayList, this);
        recyclerViewPro.setAdapter(visitsProductAdapter);

        productList = productArrayList;

        ArrayAdapter<String> doctorsAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, productNameList);
        textViewPro.setAdapter(doctorsAdapterList);

    }

    @Override
    public void productsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Products assign for you");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();

    }

    @Override
    public void productsGetingFail(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.getProducts(VisitsActivity.this, addedproductsArrayList);

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
    public void productsGetingNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("Try-Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bloackUserInteraction();
                        includeProgres.setVisibility(View.VISIBLE);
                        visitsPresenter.getProducts(VisitsActivity.this, addedproductsArrayList);
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

    @Override
    public void searchProductList(ArrayList<Products> productArrayList) {
        visitsProductAdapter = new VisitsProductAdapter(this, productArrayList, this);
        recyclerViewPro.setAdapter(visitsProductAdapter);
    }

    @Override
    public void productsToVisitsStart(Products products, boolean addOrRemove) {
        visitsPresenter.addProductsToVisits(addedproductsArrayList, products, addOrRemove);
    }

    @Override
    public void productsToVisits(ArrayList<Products> productsArrayList) {
        addedproductsArrayList = productsArrayList;

        visitsPresenter.showProductsToVisits(productsArrayList, productList);

    }

    @Override
    public void generateImageCode(String imagecode) {
        imageCode = imagecode;
    }

    @Override
    public void addVisitsSuccessful() {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        visitsPresenter.addVisitImage(this, bitmap, imageCode);


        Toast.makeText(this, "Visit added successfully", Toast.LENGTH_LONG).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Visit added successfully");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();


        addedproductsArrayList.clear();
        productList.clear();

        visitsPresenter.createVisitNumber(this);
        setCurrentDate();

        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.getDoctors(this, mLastKnownLocation);

        recyclerViewPro.setAdapter(null);
        recyclerViewLoc.setAdapter(null);

        imageViewTakenPhoto.setImageBitmap(null);
        edittextNote.setText("");

    }

    @Override
    public void addVisitsEmpty(String msg) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();

    }

    @Override
    public void addVisitsFail(String failMsg, int docid, String visitsNumber, final String imageCode, int locationID, ArrayList<Products> productsArrayList, String comment) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            visitsPresenter.addVisits(VisitsActivity.this, selectDoctorID, visitNumber, imageCode, selectLocationID, addedproductsArrayList, edittextNote.getText().toString(), mLastKnownLocation);
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
    public void addVisitsNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();
    }

    @Override
    public void showProducts(ArrayList<Products> addedArrayList) {
        visitsProductAdapter = new VisitsProductAdapter(this, addedArrayList, this);
        recyclerViewPro.setAdapter(visitsProductAdapter);
    }


    @Override
    public void doctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove) {
        visitsPresenter.addDoctorsToVisitsFilter(addedvisitsDoctorsFilterList, doctor, addOrRemove);

    }

    @Override
    public void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter) {
        addedvisitsDoctorsFilterList = docArrayListToFilter;
        textviewSelectedDocCount.setText(String.valueOf(addedvisitsDoctorsFilterList.size()));
        visitsPresenter.showAddDoctorsToFilter(docArrayListToFilter, visitsDoctorsFilterList);
    }

    @Override
    public void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter) {
        VisitsFilterDoctorsAdapter visitsFilterDoctorsAdapter = new VisitsFilterDoctorsAdapter(this, addedArrayListToFilter, this);
        recyclerFilterDoctors.setAdapter(visitsFilterDoctorsAdapter);
    }


    @Override
    public void locationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsPresenter.addLocationToVisitsFilter(addedvisitsLocationFilterList, locationEntitie, addOrRemove);
    }

    @Override
    public void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter) {
        addedvisitsLocationFilterList = locArrayListToFilter;
        textviewSelectedLocationCount.setText(String.valueOf(addedvisitsLocationFilterList.size()));
        visitsPresenter.showAddLocationToFilter(locArrayListToFilter, visitsLocationFilterList);
    }

    @Override
    public void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter) {
        VisitsFilterLocationAdapter visitsFilterLocationAdapter = new VisitsFilterLocationAdapter(this, addedArrayListToFilter, this);
        recyclerFilterLocation.setAdapter(visitsFilterLocationAdapter);
    }

    @Override
    public void productsToVisitsFilterStart(Products products, boolean addOrRemove) {
        visitsPresenter.addProductsToVisitsFilter(addedvisitsProductsFilterList, products, addOrRemove);
    }

    @Override
    public void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter) {
        addedvisitsProductsFilterList = productsArrayListToFilter;
        textviewSelectedProCount.setText(String.valueOf(addedvisitsProductsFilterList.size()));
        visitsPresenter.showAddProductsToFilter(productsArrayListToFilter, visitsProductsFilterList);
    }

    @Override
    public void addProductsToFilter(ArrayList<Products> addedArrayListToFilter) {
        VisitsFilterProductAdapter visitsFilterProductAdapter = new VisitsFilterProductAdapter(this, addedArrayListToFilter, this);
        recyclerFilterProduct.setAdapter(visitsFilterProductAdapter);
    }

    @Override
    public void showSelectedFilterDoctors(ArrayList<Doctor> filterList) {
        VisitsFilterDoctorsAdapter visitsFilterDoctorsAdapter = new VisitsFilterDoctorsAdapter(this, filterList, this);
        recyclerFilterDoctors.setAdapter(visitsFilterDoctorsAdapter);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList) {
        VisitsFilterLocationAdapter visitsFilterLocationAdapter = new VisitsFilterLocationAdapter(this, filterList, this);
        recyclerFilterLocation.setAdapter(visitsFilterLocationAdapter);
    }

    @Override
    public void showSelectedFilterProducts(ArrayList<Products> filterList) {
        VisitsFilterProductAdapter visitsFilterProductAdapter = new VisitsFilterProductAdapter(this, filterList, this);
        recyclerFilterProduct.setAdapter(visitsFilterProductAdapter);
    }

    @Override
    public void visitsFilterListEmpty(String msg, ArrayList<Visit> visitArrayList) {

        visitsAdapter = new VisitsAdapter(this, visitArrayList, this);
        recyclerViewVisits.setAdapter(visitsAdapter);
    }

    @Override
    public void visitsFilterList(ArrayList<Visit> visitArrayList) {

        visitsAdapter = new VisitsAdapter(this, visitArrayList, this);
        recyclerViewVisits.setAdapter(visitsAdapter);

        dialogFilter.dismiss();
    }


    @Override
    public void addVisitImageSuccessful() {
        Toast.makeText(this, "Image upload Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addVisitImageFail(String failMsg, final Bitmap img, final String imageCodee) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            visitsPresenter.addVisitImage(VisitsActivity.this, img, imageCodee);
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
    public void addVisitImageNetworkFail() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        visitsPresenter.addVisitImage(VisitsActivity.this, bitmap, imageCode);
                        return;
                    }
                });
        alertDialogBuilder.show();
    }

    @Override
    public void visitDetails(Visit visit) {
        Dialog dialogFullDetails = new Dialog(this);
        dialogFullDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFullDetails.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFullDetails.setContentView(R.layout.dialog_visits_details);
        dialogFullDetails.setCancelable(true);


        ImageView image = dialogFullDetails.findViewById(R.id.imageView_dialog_photo);
        TextView noimage = dialogFullDetails.findViewById(R.id.textView_noimage);
        TextView comments = dialogFullDetails.findViewById(R.id.textView_comments);

        final ProgressBar progressBar = dialogFullDetails.findViewById(R.id.progressBar);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_photo_camera);
        requestOptions.error(R.drawable.ic_photo_camera);


        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        };


        if (visit.getImageUrl().isEmpty() || visit.getImageUrl().equals("")) {
            image.setVisibility(View.GONE);
            noimage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            noimage.setVisibility(View.GONE);


            Glide.with(this)
                    .asBitmap()
                    .load(visit.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(image);
        }


        if (visit.getComment().isEmpty() || visit.getComment().equals("")) {
            comments.setText("No Comments");
        } else {
            comments.setText(visit.getComment());
        }

        dialogFullDetails.show();

    }

    @Override
    public void docListForFilter(ArrayList<Doctor> docArrayList) {
        VisitsFilterDoctorsAdapter visitsFilterDoctorsAdapter = new VisitsFilterDoctorsAdapter(this, docArrayList, this);
        recyclerFilterDoctors.setAdapter(visitsFilterDoctorsAdapter);
    }

    @Override
    public void locListForFilter(ArrayList<LocationEntitie> locArrayList) {
        VisitsFilterLocationAdapter visitsFilterLocationAdapter = new VisitsFilterLocationAdapter(this, locArrayList, this);
        recyclerFilterLocation.setAdapter(visitsFilterLocationAdapter);
    }

    @Override
    public void productsListForFilter(ArrayList<Products> proArrayList) {
        VisitsFilterProductAdapter visitsFilterProductAdapter = new VisitsFilterProductAdapter(this, proArrayList, this);
        recyclerFilterProduct.setAdapter(visitsFilterProductAdapter);
    }

    @Override
    public void targetDetails(TargetDetails target) {
        visitTargetDialog(target);
    }

    @Override
    public void targetDetailsError(String failMsg) {
        Toast.makeText(this, failMsg, Toast.LENGTH_LONG).show();
    }


    public void visitTargetDialog(TargetDetails target) {
        Dialog dialogTarget = new Dialog(this);
        dialogTarget.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTarget.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogTarget.setContentView(R.layout.dialog_target_infor);
        dialogTarget.setCancelable(true);


        NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
        ((DecimalFormat) nf2).applyPattern("#,###,###");


        TextView ytdtarget = dialogTarget.findViewById(R.id.textview_dialogtarget_ytdtarget);
        if (!target.getYearlyTarget().equals("")) {
            Double value = Double.parseDouble(target.getYearlyTarget());
            ytdtarget.setText(String.valueOf(nf2.format(value)));
        } else {
            ytdtarget.setText("0.00");
        }

        TextView ytdachievment = dialogTarget.findViewById(R.id.textview_dialogtarget_ytdachievment);
        if (!target.getYearlyAchievement().equals("")) {
            Double value = Double.parseDouble(target.getYearlyAchievement());
            ytdachievment.setText(String.valueOf(nf2.format(value)));

        } else {
            ytdachievment.setText("0.00");
        }


        TextView ytdpresentage = dialogTarget.findViewById(R.id.textview_dialogtarget_ytdpresentage);
        if (!target.getYearlyPercentage().equals("")) {

            Double value = Double.parseDouble(target.getYearlyPercentage());
            ytdpresentage.setText(String.valueOf(nf2.format(value)) + " %");

        } else {
            ytdpresentage.setText("0.00 %");
        }


        TextView monthlytarget = dialogTarget.findViewById(R.id.textview_dialogtarget_monthlytarget);
        if (!target.getMonthlyTarget().equals("")) {
            Double value = Double.parseDouble(target.getMonthlyTarget());
            monthlytarget.setText(String.valueOf(nf2.format(value)));
        } else {
            monthlytarget.setText("0.00");
        }


        TextView monthlychievment = dialogTarget.findViewById(R.id.textview_dialogtarget_monthlychievment);
        if (!target.getMonthlyAchievement().equals("")) {
            Double value = Double.parseDouble(target.getMonthlyAchievement());
            monthlychievment.setText(String.valueOf(nf2.format(value)));
        } else {
            monthlychievment.setText("0.00");
        }

        TextView monthlypresentage = dialogTarget.findViewById(R.id.textview_dialogtarget_monthlypresentage);
        if (!target.getMonthlyPercentage().equals("")) {

            Double value = Double.parseDouble(target.getMonthlyPercentage());
            monthlypresentage.setText(String.valueOf(nf2.format(value)) + " %");

        } else {
            monthlypresentage.setText("0.00 %");
        }


        TextView targetPredictionvalue = dialogTarget.findViewById(R.id.textview_dialogtarget_predictionvalue);
        if (!target.getTargetBalance().equals("")) {

            Double value = Double.parseDouble(target.getTargetBalance());
            targetPredictionvalue.setText(String.valueOf(nf2.format(value)));

        } else {
            targetPredictionvalue.setText("0.00");
        }


        TextView targetPredictionpresentage = dialogTarget.findViewById(R.id.textview_dialogtarget_predictionpresentage);
        if (!target.getTotalCurrentMonthTarget().equals("")) {

            Double value = Double.parseDouble(target.getTotalCurrentMonthTarget());
            targetPredictionpresentage.setText(String.valueOf(nf2.format(value)) + " %");

        } else {
            targetPredictionpresentage.setText("0.00 %");
        }


        ImageView promobalance = dialogTarget.findViewById(R.id.imageview_dialogtarget_promobalance);
        if (!target.getPromotionBalance().equals("")) {
            Double valuex = Double.parseDouble(target.getPromotionBalance());
            if (valuex < 0.0) {
                promobalance.setImageResource(R.drawable.ic_brightness_red);
            } else {
                promobalance.setImageResource(R.drawable.ic_brightness_green);
            }
        } else {
            promobalance.setImageResource(R.drawable.ic_brightness_yellow);
        }


        ImageView promonext = dialogTarget.findViewById(R.id.imageview_dialogtarget_promonext);
        if (!target.getNextMonthPromotionBudget().equals("")) {
            Double valuex = Double.parseDouble(target.getNextMonthPromotionBudget());
            if (valuex < 0.0) {
                promonext.setImageResource(R.drawable.ic_brightness_red);
            } else {
                promonext.setImageResource(R.drawable.ic_brightness_green);
            }
        } else {
            promonext.setImageResource(R.drawable.ic_brightness_yellow);
        }




        ImageView promototal = dialogTarget.findViewById(R.id.imageview_dialogtarget_promototal);
        if (!target.getTotalPromotionBudget().equals("")) {
            Double valuex = Double.parseDouble(target.getTotalPromotionBudget());
            if (valuex < 0.0) {
                promototal.setImageResource(R.drawable.ic_brightness_red);
            } else {
                promototal.setImageResource(R.drawable.ic_brightness_green);
            }
        } else {
            promototal.setImageResource(R.drawable.ic_brightness_yellow);
        }



        TextView doccount = dialogTarget.findViewById(R.id.textview_dialogtarget_doccount);
        if (!target.getTotalApprovedDoctors().equals("")) {

            Double value = Double.parseDouble(target.getTotalApprovedDoctors());
            doccount.setText(String.valueOf(nf2.format(value)));

        } else {
            doccount.setText("0.00");
        }


        TextView visitcount = dialogTarget.findViewById(R.id.textview_dialogtarget_visitcount);
        if (!target.getTotalVisitedDoctors().equals("")) {
            Double value = Double.parseDouble(target.getTotalVisitedDoctors());
            visitcount.setText(String.valueOf(nf2.format(value)));
        } else {
            visitcount.setText("0.00");
        }


        TextView visitpresantage = dialogTarget.findViewById(R.id.textview_dialogtarget_visitpresantage);
        if (!target.getDoctorVisitingPercentage().equals("")) {

            Double value = Double.parseDouble(target.getDoctorVisitingPercentage());
            visitpresantage.setText(String.valueOf(nf2.format(value)) + " %");

        } else {
            visitpresantage.setText("0.00 %");
        }


        TextView avgperdoc = dialogTarget.findViewById(R.id.textview_dialogtarget_avgperdoc);
        if (!target.getAverageSalePerDoctor().equals("")) {

            Double value = Double.parseDouble(target.getAverageSalePerDoctor());
            avgperdoc.setText(String.valueOf(nf2.format(value)));

        } else {
            avgperdoc.setText("0.00");
        }


        dialogTarget.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        createLocationRequest();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Location Features no longer available", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;

            case PICK_IMAGE_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        try {
                            Bundle extras = data.getExtras();
                            bitmap = (Bitmap) extras.get("data");
                            imageViewTakenPhoto.setImageBitmap(bitmap);
                            visitsPresenter.generateImageCode(this, visitNumber);

                        } catch (Exception e) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            alertDialogBuilder.setMessage("Photography not captured properly, Please try again");
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            return;
                                        }
                                    });
                            alertDialogBuilder.show();
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Photography not captured properly, Please try again", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }


        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedUIOption", selectedUIOption);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedUIOption = savedInstanceState.getInt("selectedUIOption");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createLocationRequest();
                    mLocationPermissionGranted = true;
                } else {
                    mLocationPermissionGranted = false;
                    Toast.makeText(this, "Location Features no longer available", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case PERMISSIONS_REQUEST_ACCESS_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, PICK_IMAGE_REQUEST);
                    }

                } else {

                }

                break;
            }

        }
    }


    private void checkLocationSettings() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if (mLocationRequest.getPriority() == 100) {
                    getDeviceLocation();
                } else {
                    if (!mLocationPermissionGranted) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VisitsActivity.this);
                        alertDialogBuilder.setTitle("Location Permissions");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setMessage("Location Permissions has deny,Please allow the location permissions");
                        alertDialogBuilder.setPositiveButton("Turn On",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getLocationPermission();
                                    }
                                });
                        alertDialogBuilder.show();

                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VisitsActivity.this);
                        alertDialogBuilder.setTitle("Location");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setMessage("We're having trouble locating you.To find your location faster and more accurately,turn on your device's high-accuracy mode");
                        alertDialogBuilder.setPositiveButton("Turn On",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkLocationSettings();
                                    }
                                });
                        alertDialogBuilder.show();
                    }


                }

            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(VisitsActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });

    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setSmallestDisplacement(200);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        checkLocationSettings();

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            createLocationRequest();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void getDeviceLocation() {
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful() && task.isComplete()) {
                        mLastKnownLocation = (Location) task.getResult();
                        if (mLastKnownLocation == null) {
                        } else {
                        }
                    } else {

                    }


                }
            });


        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void setVisitsRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVisits.setLayoutManager(layoutManager);
        recyclerViewVisits.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVisits.setNestedScrollingEnabled(false);

    }

    private void setVisitsDocRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDoc.setLayoutManager(layoutManager);
        recyclerViewDoc.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDoc.setNestedScrollingEnabled(false);

    }


    private void setVisitsLocationRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLoc.setLayoutManager(layoutManager);
        recyclerViewLoc.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLoc.setNestedScrollingEnabled(false);

    }


    private void setVisitsProductRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPro.setLayoutManager(layoutManager);
        recyclerViewPro.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPro.setNestedScrollingEnabled(false);

    }


    private void setVisitsFilterDocRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterDoctors.setLayoutManager(layoutManager);
        recyclerFilterDoctors.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterDoctors.setNestedScrollingEnabled(false);

    }


    private void setVisitsFilterProductRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterProduct.setLayoutManager(layoutManager);
        recyclerFilterProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterProduct.setNestedScrollingEnabled(false);

    }


    private void setVisitsFilterLocationRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterLocation.setLayoutManager(layoutManager);
        recyclerFilterLocation.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterLocation.setNestedScrollingEnabled(false);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:


                    return true;
                case R.id.navigation_location:
                    Intent intent = new Intent(VisitsActivity.this, LocationActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoc = new Intent(VisitsActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoc = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoc, bndlanimationDoc);
                    finish();
                    return true;

            }
            return false;
        }
    };


    private void setCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String currentDate = sdfDate.format(new Date(System.currentTimeMillis()));
        textViewDate.setText(currentDate);
    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void setUserDetails() {
        textName.setText(encryptedPreferences.getString(USER_NAME, ""));
        String userImgURL = encryptedPreferences.getString(USER_IMAGE, "");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_profile_users);
        requestOptions.error(R.drawable.ic_profile_users);

        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        };

        if (userImgURL.isEmpty() || userImgURL.equals("")) {
        } else {

            Glide.with(this)
                    .asBitmap()
                    .load(userImgURL)
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(imgUser);
        }


    }

    private void showFilterDialog() {

        dialogFilter = new Dialog(this);
        dialogFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFilter.setContentView(R.layout.dialog_visit_filter);
        dialogFilter.setCancelable(true);

        final DateRangeCalendarView calendarView = (DateRangeCalendarView) dialogFilter.findViewById(R.id.calendarView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");
        calendarView.setFonts(typeface);


        relativelayoutDateImage = dialogFilter.findViewById(R.id.relativelayout_date_bar_image);
        imageViewLayoutDateImage = dialogFilter.findViewById(R.id.imageView_layout_date_image);

        relativelayoutDoctorImage = dialogFilter.findViewById(R.id.relativelayout_doctor_bar_image);
        imageViewLayoutDoctorImage = dialogFilter.findViewById(R.id.imageView_layout_doctor_image);


        RelativeLayout relativelayoutLocationImage = dialogFilter.findViewById(R.id.relativeLayout_location_bar);
        final ImageView imageViewLayoutLocationImage = dialogFilter.findViewById(R.id.imageView_layout_location_image);

        RelativeLayout relativelayoutProductBarImage = dialogFilter.findViewById(R.id.relativelayout_product_bar_image);
        final ImageView imageViewLayoutProductImage = dialogFilter.findViewById(R.id.imageView_layout_product_image);


        recyclerFilterDoctors = dialogFilter.findViewById(R.id.recyclerView_doctos);
        recyclerFilterLocation = dialogFilter.findViewById(R.id.recyclerView_location);
        recyclerFilterProduct = dialogFilter.findViewById(R.id.recyclerView_product);


        final TextView textviewSelectedDate = dialogFilter.findViewById(R.id.textview_selected_date);

        textviewSelectedProCount = dialogFilter.findViewById(R.id.textview_selected_pro_count);
        textviewSelectedLocationCount = dialogFilter.findViewById(R.id.textview_selected_location_count);
        textviewSelectedDocCount = dialogFilter.findViewById(R.id.textview_selected_doc_count);


        Button btnFilter = dialogFilter.findViewById(R.id.btn_filter);
        Button btnReset = dialogFilter.findViewById(R.id.btn_reset);


        if (addedvisitsProductsFilterList.size() == 0) {
        } else {
            textviewSelectedProCount.setText(String.valueOf(addedvisitsProductsFilterList.size()));
        }

        if (addedvisitsLocationFilterList.size() == 0) {
        } else {
            textviewSelectedLocationCount.setText(String.valueOf(addedvisitsLocationFilterList.size()));
        }

        if (addedvisitsDoctorsFilterList.size() == 0) {
        } else {
            textviewSelectedDocCount.setText(String.valueOf(addedvisitsDoctorsFilterList.size()));
        }

        setVisitsFilterLocationRecycalView();
        setVisitsFilterDocRecycalView();
        setVisitsFilterProductRecycalView();


        visitsPresenter.setSelectedFilterDoctors(visitsDoctorsFilterList, addedvisitsDoctorsFilterList);
        visitsPresenter.setSelectedFilterLocation(visitsLocationFilterList, addedvisitsLocationFilterList);
        visitsPresenter.setSelectedFilterProducts(visitsProductsFilterList, addedvisitsProductsFilterList);


        final RelativeLayout layoutRecyclerViewDoctos = dialogFilter.findViewById(R.id.layout_recyclerView_doctos);
        final AutoCompleteTextView autoTextViewDoc = dialogFilter.findViewById(R.id.autoCompleteTextView_doc);
        final ImageView imageViewDocSearch = dialogFilter.findViewById(R.id.imageView_doc_search);


        ArrayAdapter<String> docAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsDoctorsNameFilterList);
        autoTextViewDoc.setAdapter(docAdapterList);

        autoTextViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchDocForFilter(visitsDoctorsFilterList, selectedLoc);
            }
        });

        imageViewDocSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchDocForFilter(visitsDoctorsFilterList, autoTextViewDoc.getText().toString());
            }
        });


        final RelativeLayout layoutRecyclerViewLocation = dialogFilter.findViewById(R.id.layout_recyclerView_location);
        final AutoCompleteTextView autoTextViewLocation = dialogFilter.findViewById(R.id.autoCompleteTextView_loc);
        final ImageView imageViewLocationSearch = dialogFilter.findViewById(R.id.imageView_loc_search);


        ArrayAdapter<String> locAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsLocationNameFilterList);
        autoTextViewLocation.setAdapter(locAdapterList);

        autoTextViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchLocForFilter(visitsLocationFilterList, selectedLoc);
            }
        });

        imageViewLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchLocForFilter(visitsLocationFilterList, autoTextViewLocation.getText().toString());
            }
        });


        final RelativeLayout layoutRecyclerViewProduct = dialogFilter.findViewById(R.id.layout_recyclerView_product);
        final AutoCompleteTextView autoTextViewProduct = dialogFilter.findViewById(R.id.autoCompleteTextView_pro);
        final ImageView imageViewProductSearch = dialogFilter.findViewById(R.id.imageView_pro_search);


        ArrayAdapter<String> proAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsProductsNameFilterList);
        autoTextViewProduct.setAdapter(proAdapterList);

        autoTextViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchProductsForFilter(visitsProductsFilterList, selectedLoc);
            }
        });

        imageViewProductSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchProductsForFilter(visitsProductsFilterList, autoTextViewProduct.getText().toString());
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.visitsFilter(visitsList, filterDateStart, filterDateEnd, addedvisitsDoctorsFilterList, addedvisitsLocationFilterList, addedvisitsProductsFilterList);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDateStart = "";
                filterDateEnd = "";

                addedvisitsDoctorsFilterList.clear();
                addedvisitsLocationFilterList.clear();
                addedvisitsProductsFilterList.clear();


                visitsPresenter.visitsFilter(visitsList, filterDateStart, filterDateEnd, addedvisitsDoctorsFilterList, addedvisitsLocationFilterList, addedvisitsProductsFilterList);
            }
        });


        relativelayoutProductBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewProduct.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewProduct.setVisibility(View.GONE);
                    imageViewLayoutProductImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewProduct.setVisibility(View.VISIBLE);
                    imageViewLayoutProductImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });


        relativelayoutDoctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewDoctos.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewDoctos.setVisibility(View.GONE);
                    imageViewLayoutDoctorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewDoctos.setVisibility(View.VISIBLE);
                    imageViewLayoutDoctorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });


        relativelayoutDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getVisibility() == View.VISIBLE) {
                    calendarView.setVisibility(View.GONE);
                    imageViewLayoutDateImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    calendarView.setVisibility(View.VISIBLE);
                    imageViewLayoutDateImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });


        relativelayoutLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewLocation.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewLocation.setVisibility(View.GONE);
                    imageViewLayoutLocationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewLocation.setVisibility(View.VISIBLE);
                    imageViewLayoutLocationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });


        String date = "Dates :";

        if (filterDateStart.isEmpty()) {
            date = "Date Not Selected";
        } else if (filterDateEnd.isEmpty()) {
            date = date + " " + filterDateStart;
        } else {
            date = date + " " + filterDateStart + " To " + filterDateEnd;
        }

        final SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        textviewSelectedDate.setText(date);


        Date dateS = null;
        Date dateE = null;

        try {
            dateS = targetFormat.parse(filterDateStart);
            dateE = targetFormat.parse(filterDateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (filterDateStart.isEmpty()) {

        } else if (filterDateEnd.isEmpty()) {
            Calendar startSelectionDate = Calendar.getInstance();
            startSelectionDate.setTime(dateS);
            calendarView.setSelectedDateRange(startSelectionDate, startSelectionDate);

        } else {
            Calendar startSelectionDate = Calendar.getInstance();
            startSelectionDate.setTime(dateS);
            Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
            endSelectionDate.setTime(dateE);
            calendarView.setSelectedDateRange(startSelectionDate, endSelectionDate);
        }


        calendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                filterDateStart = targetFormat.format(startDate.getTime());
                textviewSelectedDate.setText("Date : " + filterDateStart);

            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                filterDateStart = targetFormat.format(startDate.getTime());
                filterDateEnd = targetFormat.format(endDate.getTime());
                textviewSelectedDate.setText("Dates : " + filterDateStart + " To " + filterDateEnd);


            }
        });


        dialogFilter.show();


    }


    @Override
    public void onLocationChanged(Location location) {
        location = location;
        mLastKnownLocation = location;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        mLastKnownLocation = location;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
