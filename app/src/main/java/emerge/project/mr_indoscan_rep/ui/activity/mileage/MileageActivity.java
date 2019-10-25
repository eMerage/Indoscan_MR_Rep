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
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import butterknife.OnTextChanged;
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
import emerge.project.mr_indoscan_rep.utils.entittes.DetailsSummary;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.Mileage;
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
    EditText editTextDayStartODMeterReading;


    @BindView(R.id.editText15)
    TextView textDayStartPrivertMilage;



    @BindView(R.id.editText1)
    EditText editTextCurrentDayODMeterReading;


    @BindView(R.id.editText)
    EditText editTextDayEndODMeterReading;


    @BindView(R.id.editText3)
    EditText editTextMileageForDay;

    @BindView(R.id.editText4)
    EditText editTextPrivertMileageForDay;

    MileagePresenter mileagePresenter;
    NavigationAdapter navigationAdapter;
    LocationRequest request;



    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LatLng currentLocation = new LatLng(0.0, 0.0);

    int codmr = 0;
    int cdayodmr = 0;


    int dayendreading = 0;
    int dayendmileage = 0;
    int dayendprivertmileage = 0;

    Bundle imageExtras;


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

                } else if (position == 2) {
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

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
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


    @OnTextChanged(R.id.editText1)
    protected void onTextChangedCurrentDay(CharSequence text) {
        int reading = 0;

        try {
            reading = Integer.parseInt(text.toString());
        } catch (NumberFormatException num) {

        }

        int readingPrives = 0;

        try {
            readingPrives = Integer.parseInt(editTextDayStartODMeterReading.getText().toString());
        } catch (NumberFormatException num) {

        }

        int verians = reading - readingPrives;

        textDayStartPrivertMilage.setText(String.valueOf(verians));


    }

    @OnClick(R.id.btn_save_stat)
    public void onClickDayStartSave(View view) {
        includeProgres.setVisibility(View.VISIBLE);

        try {
            codmr = Integer.parseInt(editTextDayStartODMeterReading.getText().toString());
        } catch (NumberFormatException num) {

        }
        try {
            cdayodmr = Integer.parseInt(editTextCurrentDayODMeterReading.getText().toString());
        } catch (NumberFormatException num) {

        }
        mileagePresenter.postDayStartMileage(this, codmr, cdayodmr, currentLocation.latitude, currentLocation.longitude);
    }


    @OnClick(R.id.btn_save_end)
    public void onClickDayEndSave(View view) {
        includeProgres.setVisibility(View.VISIBLE);
        try {
            dayendreading = Integer.parseInt(editTextDayEndODMeterReading.getText().toString());
        } catch (NumberFormatException num) {

        }
        try {
            dayendmileage = Integer.parseInt(editTextMileageForDay.getText().toString());
        } catch (NumberFormatException num) {

        }
        try {
            dayendprivertmileage = Integer.parseInt(editTextPrivertMileageForDay.getText().toString());
        } catch (NumberFormatException num) {

        }
        mileagePresenter.postDayEndMileage(this, dayendreading, dayendmileage, dayendprivertmileage, bitmap, currentLocation.latitude, currentLocation.longitude);

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

    private void todaySummery(DetailsSummary list) {

        Dialog dialogFullDetails = new Dialog(this);
        dialogFullDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFullDetails.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFullDetails.setContentView(R.layout.dialog_today_summery_infor);
        dialogFullDetails.setCancelable(true);


        ImageView ImageView1 = dialogFullDetails.findViewById(R.id.ImageView1);
        final RelativeLayout relativeLayout2 = dialogFullDetails.findViewById(R.id.relativeLayout2);
        RecyclerView recyclerviewTowns = dialogFullDetails.findViewById(R.id.recyclerview_towns);


        TownsCoverdAdapter townsCoverdAdapter = new TownsCoverdAdapter(this, list.getTownList());
        recyclerviewTowns.setAdapter(townsCoverdAdapter);


        ImageView ImageView2 = dialogFullDetails.findViewById(R.id.ImageView2);
        final RelativeLayout relativeLayout3 = dialogFullDetails.findViewById(R.id.relativeLayout3);
        RecyclerView recyclerviewDocs = dialogFullDetails.findViewById(R.id.recyclerview_docs);


        VisitsDocsAdapter visitsDocsAdapter = new VisitsDocsAdapter(this, list.getDoctorList());
        recyclerviewDocs.setAdapter(visitsDocsAdapter);


        ImageView ImageView3 = dialogFullDetails.findViewById(R.id.ImageView3);
        final RelativeLayout relativeLayout4 = dialogFullDetails.findViewById(R.id.relativeLayout4);
        RecyclerView recyclerviewPharmacy = dialogFullDetails.findViewById(R.id.recyclerview_pharmacy);

        VisitsPharmacyAdapter visitsPharmacyAdapter = new VisitsPharmacyAdapter(this, list.getPharmacyList());
        recyclerviewPharmacy.setAdapter(visitsPharmacyAdapter);


        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relativeLayout2.getVisibility() == View.VISIBLE) {
                    relativeLayout2.setVisibility(View.GONE);
                } else {
                    relativeLayout2.setVisibility(View.VISIBLE);
                }
            }
        });


        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relativeLayout3.getVisibility() == View.VISIBLE) {
                    relativeLayout3.setVisibility(View.GONE);
                } else {
                    relativeLayout3.setVisibility(View.VISIBLE);
                }
            }
        });


        ImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relativeLayout4.getVisibility() == View.VISIBLE) {
                    relativeLayout4.setVisibility(View.GONE);
                } else {
                    relativeLayout4.setVisibility(View.VISIBLE);
                }
            }
        });


        TextView textTowncount = dialogFullDetails.findViewById(R.id.MontserratMedium2);
        textTowncount.setText(String.valueOf(list.getTownCount()));

        TextView textDocVistscount = dialogFullDetails.findViewById(R.id.MontserratMedium4);
        textDocVistscount.setText(String.valueOf(list.getDoctorCount()));

        TextView textPhaVistscount = dialogFullDetails.findViewById(R.id.MontserratMedium6);
        textPhaVistscount.setText(String.valueOf(list.getPharmacyCount()));


        TextView textOrderCollectscount = dialogFullDetails.findViewById(R.id.MontserratMedium8);
        textOrderCollectscount.setText(String.valueOf(list.getCollectedOrdersCount()));

        TextView textOfficalMileage = dialogFullDetails.findViewById(R.id.MontserratMedium10);
        textOfficalMileage.setText(String.valueOf(list.getOfficialMileage()) + " KM");

        TextView textPrivertMileage = dialogFullDetails.findViewById(R.id.MontserratMedium12);
        textPrivertMileage.setText(String.valueOf(list.getOfficialMileage()) + " KM");

        TextView textDailyExpenses = dialogFullDetails.findViewById(R.id.MontserratMedium14);
        textDailyExpenses.setText(String.valueOf(list.getDailyExpenses()) + " LKR");


        TextView textAchievementTowns = dialogFullDetails.findViewById(R.id.MontserratMedium16);
        textAchievementTowns.setText(String.valueOf(list.getTownAchievement()) + " %");

        TextView textAchievementUpToDate = dialogFullDetails.findViewById(R.id.MontserratMedium18);
        textAchievementUpToDate.setText(String.valueOf(list.getUptodateAchievement()) + " %");

        TextView textBalTarget = dialogFullDetails.findViewById(R.id.MontserratMedium20);
        textBalTarget.setText(String.valueOf(list.getBalanceTarget()));


        TextView textNotVisitedCustomersToday = dialogFullDetails.findViewById(R.id.MontserratMedium22);
        textNotVisitedCustomersToday.setText(String.valueOf(list.getNotVisitedCustomers()));


        TextView textNotVisitedCustomersAll = dialogFullDetails.findViewById(R.id.MontserratMedium24);
        textNotVisitedCustomersAll.setText(String.valueOf(list.getNotVisitedCustomersAll()));


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
                            imageExtras = data.getExtras();

                            new AddImages().execute();


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


    private class AddImages extends AsyncTask<Void, Void, Void> {

        public AddImages() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            bitmap = (Bitmap) imageExtras.get("data");
            imageViewimage.setImageBitmap(bitmap);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            includeProgres.setVisibility(View.GONE);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            includeProgres.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void dayStartMileage(Mileage availability) {
        includeProgres.setVisibility(View.GONE);

        if (!availability.getDayEndOdometerReading().equals("0")) {
            relativelayoutDayStart.setVisibility(View.VISIBLE);
            relativelayoutDayEnd.setVisibility(View.GONE);
        } else {
            relativelayoutDayStart.setVisibility(View.GONE);
            relativelayoutDayEnd.setVisibility(View.VISIBLE);
        }

        int priDay = 0;
        try {
            priDay = Integer.parseInt(availability.getDayEndOdometerReading());
        }catch (NumberFormatException num){

        }


        if(priDay==0){
            editTextDayStartODMeterReading.setEnabled(true);
        }else {
            editTextDayStartODMeterReading.setEnabled(false);
        }


        editTextDayStartODMeterReading.setText(availability.getDayEndOdometerReading());



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
        relativelayoutDayEnd.setVisibility(View.VISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void postDayStartMileageSuccess() {


        editTextDayStartODMeterReading.setText("");
        editTextCurrentDayODMeterReading.setText("");


        includeProgres.setVisibility(View.GONE);
        relativelayoutDayStart.setVisibility(View.GONE);
        relativelayoutDayEnd.setVisibility(View.VISIBLE);
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
                            mileagePresenter.postDayStartMileage(MileageActivity.this, codmr, cdayodmr, currentLocation.latitude, currentLocation.longitude);

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

    @Override
    public void postDayEndMileageError(String msg) {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postDayEndMileageSuccess() {


        editTextDayEndODMeterReading.setText("");
        editTextMileageForDay.setText("");
        editTextPrivertMileageForDay.setText("");


        bitmap = null;
        imageViewimage.setImageDrawable(getResources().getDrawable(R.drawable.noimage));



        includeProgres.setVisibility(View.GONE);
        relativelayoutDayStart.setVisibility(View.VISIBLE);
        relativelayoutDayEnd.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Success");

            alertDialogBuilder.setMessage("Day end mileage adding success");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //includeProgres.setVisibility(View.VISIBLE);
                    //  mileagePresenter.getDetailsSummary();
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Day end mileage adding success", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void postDayEndMileageFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            mileagePresenter.postDayEndMileage(MileageActivity.this, dayendreading, dayendmileage, dayendprivertmileage, bitmap, currentLocation.latitude, currentLocation.longitude);

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
    public void postDayEndMileageNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void detailsSummaryList(DetailsSummary list) {
        includeProgres.setVisibility(View.GONE);
        todaySummery(list);

    }

    @Override
    public void detailsSummaryFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            mileagePresenter.getDetailsSummary();

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
    public void detailsSummaryNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }
}
