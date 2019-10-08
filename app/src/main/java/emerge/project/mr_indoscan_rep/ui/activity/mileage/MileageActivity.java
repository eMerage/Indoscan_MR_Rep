package emerge.project.mr_indoscan_rep.ui.activity.mileage;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
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

public class MileageActivity extends Activity {

    static final int PICK_IMAGE_REQUEST = 3;

    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 2;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    Bitmap bitmap;

    @BindView(R.id.imageView_payment_image)
    ImageView imageViewimage;

    NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);



        ButterKnife.bind(this);



        bottomNavigationBar.setSelectedItemId(R.id.navigation_visits);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Expenses", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Mileage", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Pharmacy Visits", R.drawable.ic_product_defult_small));
        navigationItems.add(new Navigation("Products", R.drawable.ic_product_defult_small));



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

}
