package emerge.project.mr_indoscan_rep.ui.activity.locations;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;

import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
import emerge.project.mr_indoscan_rep.ui.activity.login.LoginActivity;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageActivity;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.products.ProductsUnavailabilityActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.locations.LocationTypeSpnninerAdaptar;
import emerge.project.mr_indoscan_rep.ui.adapters.locations.LocationsDuplicateAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.District;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationType;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationView ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.relativeLayout_currentlocation)
    RelativeLayout relativeLayoutCurrentlocation;

    @BindView(R.id.relativeLayout_addlocation)
    RelativeLayout relativeLayoutAddlocation;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;


    @BindView(R.id.edittext_name)
    EditText edittextName;

    @BindView(R.id.edittext_addres)
    EditText edittextAddres;


    @BindView(R.id.edittext_area)
    EditText edittextArea;


    @BindView(R.id.edittext_town)
    EditText edittextTown;


    @BindView(R.id.autoComplete_district)
    AutoCompleteTextView autoCompleteDistrict;


    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;


    @BindView(R.id.text_name)
    TextView textName;

    @BindView(R.id.img_user)
    ImageView imgUser;


    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;



    @BindView(R.id.spinner_location_type)
    Spinner spinnerLocationType;

    private GoogleMap mMap;

    Marker mapMarker;
    LocationRequest mLocationRequest;
    static final int REQUEST_CHECK_SETTINGS = 2;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation = null;
    private Location location;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private GoogleApiClient googleApiClient;

    private static final String USER_REMEMBER = "userRemember";
    private static final String USER_NAME = "userName";
    private static final String USER_IMAGE = "userImage";

    boolean isLocationAddUi = false;
    boolean isLocationDialogUi = false;
    boolean isLocationAddStart = false;

    private Location locationDialog;


    double locationDialogLan, locationDialogLon;

    int selectedDistricId;
    int selectedTypeId=0;

    Animation animation, animation2;


    ArrayList<District> districtList;
    LocationPresenter locationPresenter;

    ArrayList<LocationEntitie> allLocation;

    LocationEntitie selectLocation;
    EncryptedPreferences encryptedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();

        bottomNavigationBar.setSelectedItemId(R.id.navigation_location);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        locationPresenter = new LocationPresenterImpli(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setUserDetails();

        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_expanview);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.fade_out_expanview);

        locationPresenter.getDistrict(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getLocationPermission();
        } else {

            mLocationPermissionGranted=true;
            createLocationRequest();
        }


        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();


        SupportMapFragment mapAlllocations = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_alllocations);
        mapAlllocations.getMapAsync(this);


        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        locationPresenter.getLocation(this);


        autoCompleteDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedDistrict = parent.getItemAtPosition(pos).toString();
                locationPresenter.getSelectedDistrictID(districtList, selectedDistrict);
            }
        });




        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Expenses", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Mileage", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Pharmacy Visits", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Products", R.drawable.ic_product_defult_small));

        NavigationAdapter navigationAdapter = new NavigationAdapter(this, navigationItems);
        listViewNavigation.setAdapter(navigationAdapter);

        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(LocationActivity.this, ExpensesActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 1) {
                    Intent intent = new Intent(LocationActivity.this, MileageActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if(position==2){
                    Intent intent = new Intent(LocationActivity.this, PharmacyVisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }else if(position==3){
                    Intent intent = new Intent(LocationActivity.this, ProductsUnavailabilityActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
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

        Intent intent = new Intent(LocationActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();


    }

    @OnTextChanged(value = R.id.autoComplete_district, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedDistric(CharSequence text) {
        if (text.length() == 0) {
            selectedDistricId = 0;
        } else {
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        if (isLocationAddUi) {

            SupportMapFragment mapAlllocations = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_alllocations);
            mapAlllocations.getMapAsync(this);

            fab.setVisibility(View.VISIBLE);

            relativeLayoutAddlocation.startAnimation(animation2);
            relativeLayoutAddlocation.setVisibility(View.GONE);

            relativeLayoutCurrentlocation.startAnimation(animation);
            relativeLayoutCurrentlocation.setVisibility(View.VISIBLE);

            getDeviceLocation();

            bloackUserInteraction();
            includeProgres.setVisibility(View.VISIBLE);
            locationPresenter.getLocation(this);

            isLocationAddUi = false;


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


    @OnClick(R.id.btn_addlocation)
    public void onClickAddLocation(View view) {


        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);

        LocationEntitie locationEntitie = new LocationEntitie();
        locationEntitie.setName(edittextName.getText().toString());
        locationEntitie.setAddress(edittextAddres.getText().toString());
        locationEntitie.setArea(edittextArea.getText().toString());
        locationEntitie.setTown(edittextTown.getText().toString());
        locationEntitie.setDistrictID(selectedDistricId);
        locationEntitie.setLatitude(mLastKnownLocation.getLatitude());
        locationEntitie.setLongitude(mLastKnownLocation.getLongitude());
        locationEntitie.setLocationTypeID(selectedTypeId);

        locationPresenter.postLocation(this, locationEntitie, 0);


    }

    @SuppressLint("RestrictedApi")
    @OnClick(R.id.fab)
    public void onClickAddNewLocationFab(View view) {
        isLocationAddStart = true;

        if (!mLocationPermissionGranted) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Location");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setMessage("We're having trouble locating you.To find your location faster and more accurately,turn on your device's high-accuracy mode");
            alertDialogBuilder.setPositiveButton("Turn On",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            createLocationRequest();
                        }
                    });
            alertDialogBuilder.show();

        } else {
            fab.setVisibility(View.GONE);

            SupportMapFragment mapCurrentloctations = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_currentloctations);
            mapCurrentloctations.getMapAsync(this);

            relativeLayoutCurrentlocation.startAnimation(animation2);
            relativeLayoutCurrentlocation.setVisibility(View.GONE);

            relativeLayoutAddlocation.startAnimation(animation);
            relativeLayoutAddlocation.setVisibility(View.VISIBLE);

            isLocationAddStart = false;
            isLocationAddUi = true;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Location");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setMessage("Please make sure your current location is show on the map,if it's not, Please restart the app");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.show();



            includeProgres.setVisibility(View.VISIBLE);
           locationPresenter.getLocationType(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createLocationRequest();
                    mLocationPermissionGranted=true;
                } else {
                    mLocationPermissionGranted = false;
                    Toast.makeText(this, "Location Features no longer available", Toast.LENGTH_SHORT).show();
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LocationActivity.this);
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LocationActivity.this);
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
                        resolvable.startResolutionForResult(LocationActivity.this, REQUEST_CHECK_SETTINGS);
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
        mLocationRequest.setSmallestDisplacement(0);

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


                        if (isLocationAddUi) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 15));
                            mapMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())));
                        } else {
                        }


                        if (mLastKnownLocation == null) {

                            Toast.makeText(LocationActivity.this, "Location not Fond", Toast.LENGTH_SHORT).show();
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 12));
                        }
                    } else {
                    }
                }
            });


        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    Intent intent = new Intent(LocationActivity.this, VisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_location:


                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoctors = new Intent(LocationActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoctors = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoctors, bndlanimationDoctors);
                    finish();
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        googleMap.setOnMarkerClickListener(this);

        LatLng locationMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));

        if (mLastKnownLocation == null) {

        } else {

            if (isLocationDialogUi) {

                boolean success2 = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_silver));


                locationMap = new LatLng(locationDialogLan, locationDialogLon);

                if (selectLocation.getApproved()) {
                    mMap.addMarker(new MarkerOptions()
                            .position(locationMap)
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_green))
                    );
                } else if (selectLocation.getRejected()) {
                    mMap.addMarker(new MarkerOptions()
                            .position(locationMap)
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_red))
                    );

                } else {
                    mMap.addMarker(new MarkerOptions()
                            .position(locationMap)
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_gold))
                    );


                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMap, 15));

                isLocationDialogUi = false;

            } else {

                if (isLocationAddUi) {


                    locationMap = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(locationMap).title(""));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMap, 15));

                    isLocationDialogUi = false;

                } else {

                }


            }


        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        final Dialog dialogLocation = new Dialog(this);
        dialogLocation.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLocation.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLocation.setContentView(R.layout.dialog_location);
        dialogLocation.setCancelable(true);


        TextView textviewAddress = dialogLocation.findViewById(R.id.textview_address);
        TextView textviewLocation = dialogLocation.findViewById(R.id.textview_location);
        TextView textviewRep = dialogLocation.findViewById(R.id.textview_rep);
        TextView textviewStatus = dialogLocation.findViewById(R.id.textview_status);


        MapView mapView = dialogLocation.findViewById(R.id.mapView);


        try {
            for (LocationEntitie loc : allLocation) {
                if (loc.getId() == Integer.parseInt(marker.getSnippet())) {
                    textviewAddress.setText(loc.getAddress());
                    textviewLocation.setText(loc.getName());
                    textviewRep.setText(loc.getCreatedByName());
                    textviewStatus.setText(loc.getStatus());

                    selectLocation = loc;
                    isLocationDialogUi = true;

                    locationDialogLan = loc.getLatitude();
                    locationDialogLon = loc.getLongitude();

                    if (mapView != null) {
                        mapView.onCreate(null);
                        mapView.onResume();
                        mapView.getMapAsync(this);
                    }

                } else {
                }
            }

            dialogLocation.show();

        } catch (NullPointerException nulex) {
            Toast.makeText(this, "This is your current location", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException num) {
            Toast.makeText(this, "This is your current location", Toast.LENGTH_SHORT).show();
        }


        return false;
    }


    @Override
    public void locationList(ArrayList<LocationEntitie> locationArrayList) {

        allLocation = locationArrayList;
        for (LocationEntitie locationEntitie : locationArrayList) {
            if (locationEntitie.getApproved()) {
                mapMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(locationEntitie.getLatitude(), locationEntitie.getLongitude()))
                        .title(locationEntitie.getName())
                        .snippet(String.valueOf(locationEntitie.getId()))
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_green)));

            } else if (locationEntitie.getRejected()) {
                mapMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(locationEntitie.getLatitude(), locationEntitie.getLongitude()))
                        .title(locationEntitie.getName())
                        .snippet(String.valueOf(locationEntitie.getId()))
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_red)));
            } else {
                mapMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(locationEntitie.getLatitude(), locationEntitie.getLongitude()))
                        .title(locationEntitie.getName())
                        .snippet(String.valueOf(locationEntitie.getId()))
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker_gold)));

            }
            mapMarker.showInfoWindow();
        }


    }


    @Override
    public void noLocation() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Location approved or added yet");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();

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
                            locationPresenter.getLocation(LocationActivity.this);
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
        includeNointernt.setVisibility(View.VISIBLE);
    }


    @Override
    public void districtList(ArrayList<District> districtArrayList, ArrayList<String> districtNameList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        districtList = districtArrayList;

        ArrayAdapter<String> productAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, districtNameList);
        autoCompleteDistrict.setAdapter(productAdapterList);


    }

    @Override
    public void noDistrict() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Districts added yet,please contact administrator");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();

    }

    @Override
    public void districtFail(String failMsg) {
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
                            locationPresenter.getDistrict(LocationActivity.this);
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
    public void districtNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("Try-Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bloackUserInteraction();
                        includeProgres.setVisibility(View.VISIBLE);
                        locationPresenter.getDistrict(LocationActivity.this);
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
    public void selectedDistrictID(int selectedDsistrictId) {
        selectedDistricId = selectedDsistrictId;
    }


    @Override
    public void postLocationError(String msg) {
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
    public void postLocationSuccess() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Location added successfully");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();


        Toast.makeText(this, "Location add successful", Toast.LENGTH_LONG).show();

        edittextName.setText("");
        edittextAddres.setText("");
        edittextArea.setText("");
        edittextTown.setText("");
        selectedDistricId = 0;

    }

    @Override
    public void postLocationFail(String failMsg, final LocationEntitie location, final int isAfterSuggestion) {
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
                            locationPresenter.postLocation(LocationActivity.this, location, isAfterSuggestion);
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
    public void postLocationDuplicate(ArrayList<LocationEntitie> duplicateLocationList, final LocationEntitie locationEntitie, int isAfterSuggestion) {


        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        LocationsDuplicateAdapter locationsDuplicateAdapter = new LocationsDuplicateAdapter(this, duplicateLocationList);

        final Dialog dialogDuplicate = new Dialog(this);
        dialogDuplicate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDuplicate.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDuplicate.setContentView(R.layout.dialog_location_duplicate);
        dialogDuplicate.setCancelable(true);


        RecyclerView recyclerDuplicate = dialogDuplicate.findViewById(R.id.recycler_duplicate);

        TextView textviewBtnNo = dialogDuplicate.findViewById(R.id.text_btn_approve);
        TextView textviewBtnYes = dialogDuplicate.findViewById(R.id.text_btn_reject);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerDuplicate.setLayoutManager(layoutManager);
        recyclerDuplicate.setItemAnimator(new DefaultItemAnimator());
        recyclerDuplicate.setNestedScrollingEnabled(false);

        recyclerDuplicate.setAdapter(locationsDuplicateAdapter);


        textviewBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDuplicate.dismiss();
            }
        });


        textviewBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDuplicate.dismiss();
                includeNointernt.setVisibility(View.GONE);
                includeProgres.setVisibility(View.VISIBLE);
                unBloackUserInteraction();
                locationPresenter.postLocation(LocationActivity.this, locationEntitie, 1);

            }
        });


        dialogDuplicate.show();


    }

    @Override
    public void postLocationNetworkFail() {
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
    public void locationTypeList(ArrayList<LocationType> locationTypeList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        LocationTypeSpnninerAdaptar adapter = new LocationTypeSpnninerAdaptar(this, R.layout.textview_spinner, locationTypeList);
        spinnerLocationType.setAdapter(adapter); // this will set list of values to spinner

        spinnerLocationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                LocationType ex = (LocationType) parent.getItemAtPosition(pos);
                selectedTypeId = ex.getId();

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void locationTypeListgetingFail(String failMsg) {

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
                            locationPresenter.getLocationType(LocationActivity.this);
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
    public void locationTypeListNetworkFail() {
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


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        location =location;
        mLastKnownLocation=location;
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        mLastKnownLocation=location;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
