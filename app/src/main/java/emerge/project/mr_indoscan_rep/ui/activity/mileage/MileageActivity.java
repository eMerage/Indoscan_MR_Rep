package emerge.project.mr_indoscan_rep.ui.activity.mileage;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesPresenter;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesPresenterImpli;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationActivity;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.mileage.TownsCoverdAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.mileage.VisitsDocsAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.mileage.VisitsPharmacyAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Towns;

public class MileageActivity extends Activity implements MileageView {

    static final int PICK_IMAGE_REQUEST = 3;

    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 2;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    @BindView(R.id.relativelayout_day_start)
    RelativeLayout relativelayoutDayStart;


    @BindView(R.id.relativelayout_day_end)
    RelativeLayout relativelayoutDayEnd;


    Bitmap bitmap;

    @BindView(R.id.imageView_payment_image)
    ImageView imageViewimage;


    @BindView(R.id.include_progres)
    View includeProgres;



    @BindView(R.id.editText22)
    View editTextDayStartODMeterReading;


    @BindView(R.id.editText1)
    View editTextCurrentDayODMeterReading;


    @BindView(R.id.editText)
    View editTextDayEndODMeterReading;


    @BindView(R.id.editText3)
    View editTextMileageForDay;

    @BindView(R.id.editText4)
    View editTextPrivertMileageForDay;




    MileagePresenter mileagePresenter;


    NavigationAdapter navigationAdapter;
    LocationRequest request;

    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LatLng currentLocation = new LatLng(0.0, 0.0);






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);

        ButterKnife.bind(this);


        bottomNavigationBar.setSelectedItemId(R.id.navigation_visits);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        request = new LocationRequest();




        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Expenses", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Mileage", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Pharmacy Visits", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Products", R.drawable.ic_product_defult_small));


        mileagePresenter = new MileagePresenterImpli(this);

        navigationAdapter = new NavigationAdapter(this, navigationItems);
        listViewNavigation.setAdapter(navigationAdapter);

        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MileageActivity.this, ExpensesActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(MileageActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 1) {

                } else if(position==2){
                    Intent intent = new Intent(MileageActivity.this, PharmacyVisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(MileageActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }
            }
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                makeRequest();

            } else {
                createLocationRequest();
            }

        } else {

            createLocationRequest();
        }




/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
            ) {
                makeRequest()
            } else {
                createLocationRequest()
            }
        } else {
            createLocationRequest()
        }*/


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    currentLocation =  new LatLng(location.getLatitude(),location.getLongitude());
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        includeProgres.setVisibility(View.VISIBLE);
        mileagePresenter.checkDayStartMileage(this);

    }

    @OnClick(R.id.btn_save_end)
    public void onClickDayEndSave(View view) {
        todaySummery();

    }


    @OnClick(R.id.imageView_payment_image_cam_icon)
    public void onClickCamIcon(View view) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, PICK_IMAGE_REQUEST);
            }

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_ACCESS_CAMERA);
        }


    }


    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
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
                    Intent intent = new Intent(MileageActivity.this, VisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(MileageActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_location:
                    Intent intent2 = new Intent(MileageActivity.this, LocationActivity.class);
                    Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(MileageActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent2, bndlanimation2);
                    finish();

                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoc = new Intent(MileageActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoc = ActivityOptions.makeCustomAnimation(MileageActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoc, bndlanimationDoc);
                    finish();
                    return true;

            }
            return false;
        }
    };

    protected void createLocationRequest() {
        request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(request,
                locationCallback,
                Looper.getMainLooper());
    }

    private void todaySummery(){

        Dialog dialogFullDetails = new Dialog(this);
        dialogFullDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFullDetails.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFullDetails.setContentView(R.layout.dialog_today_summery_infor);
        dialogFullDetails.setCancelable(true);


        ImageView ImageView1 = dialogFullDetails.findViewById(R.id.ImageView1);
        final RelativeLayout relativeLayout2 = dialogFullDetails.findViewById(R.id.relativeLayout2);
        RecyclerView recyclerviewTowns = dialogFullDetails.findViewById(R.id.recyclerview_towns);


        ImageView ImageView2 = dialogFullDetails.findViewById(R.id.ImageView2);
        final RelativeLayout relativeLayout3 = dialogFullDetails.findViewById(R.id.relativeLayout3);
        RecyclerView recyclerviewDocs = dialogFullDetails.findViewById(R.id.recyclerview_docs);



        ImageView ImageView3 = dialogFullDetails.findViewById(R.id.ImageView3);
        final RelativeLayout relativeLayout4 = dialogFullDetails.findViewById(R.id.relativeLayout4);
        RecyclerView recyclerviewPharmacy = dialogFullDetails.findViewById(R.id.recyclerview_pharmacy);







        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayout2.getVisibility() == View.VISIBLE) {
                    relativeLayout2.setVisibility(View.GONE);
                }else {
                    relativeLayout2.setVisibility(View.VISIBLE);
                }
            }
        });


        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayout3.getVisibility() == View.VISIBLE) {
                    relativeLayout3.setVisibility(View.GONE);
                }else {
                    relativeLayout3.setVisibility(View.VISIBLE);
                }
            }
        });



        ImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relativeLayout4.getVisibility() == View.VISIBLE) {
                    relativeLayout4.setVisibility(View.GONE);
                }else {
                    relativeLayout4.setVisibility(View.VISIBLE);
                }
            }
        });




        ArrayList<Towns> townsItems =  new ArrayList<>();
        for(int a =1;a<10;a++){
            String tw = "Colombo "+a;
            townsItems.add(new Towns(a,tw));
        }
        TownsCoverdAdapter townsCoverdAdapter =  new TownsCoverdAdapter(this,townsItems);
        recyclerviewTowns.setAdapter(townsCoverdAdapter);


        ArrayList<Doctor> doctorItems =  new ArrayList<>();
        for(int a =1;a<10;a++){
            String tw = "Doctor "+a;
            doctorItems.add(new Doctor(a,tw));
        }
        VisitsDocsAdapter visitsDocsAdapter =  new VisitsDocsAdapter(this,doctorItems);
        recyclerviewDocs.setAdapter(visitsDocsAdapter);



        ArrayList<Pharmacy> pharmacyItems =  new ArrayList<>();
        for(int a =1;a<10;a++){
            String tw = "Pharmacy "+a;
            pharmacyItems.add(new Pharmacy(a,tw));
        }
        VisitsPharmacyAdapter visitsPharmacyAdapter =  new VisitsPharmacyAdapter(this,pharmacyItems);
        recyclerviewPharmacy.setAdapter(visitsPharmacyAdapter);


        dialogFullDetails.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        try {
                            Bundle extras = data.getExtras();
                            bitmap = (Bitmap) extras.get("data");
                            imageViewimage.setImageBitmap(bitmap);


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
    public void dayStartMileage() {
        includeProgres.setVisibility(View.GONE);
        relativelayoutDayEnd.setVisibility(View.GONE);

    }

    @Override
    public void dayStartMileageFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        relativelayoutDayEnd.setVisibility(View.VISIBLE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            mileagePresenter.checkDayStartMileage(MileageActivity.this);

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
    public void dayStartMileageNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        relativelayoutDayEnd.setVisibility(View.VISIBLE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void postDayStartMileageError(String msg) {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void postDayStartMileageSuccess() {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Success");
            alertDialogBuilder.setMessage("Day start mileage adding success");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Day start mileage adding success", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void postDayStartMileageFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                           // mileagePresenter.dsds

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
    public void postDayStartMileageNetworkFail() {

        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }
}
