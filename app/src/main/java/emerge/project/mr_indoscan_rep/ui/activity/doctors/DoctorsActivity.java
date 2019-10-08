package emerge.project.mr_indoscan_rep.ui.activity.doctors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationActivity;
import emerge.project.mr_indoscan_rep.ui.activity.login.LoginActivity;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageActivity;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.products.ProductsUnavailabilityActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.doctors.DoctorsAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.doctors.DoctorsDuplicateAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.doctors.DoctorsFilterLocationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.doctors.DoctorsFilterSpecializationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.doctors.SpecializationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.Doctor;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationEntitie;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Specialization;

public class DoctorsActivity extends Activity implements DoctorsView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


    @BindView(R.id.relativeLayout_add_doctors)
    RelativeLayout relativeLayoutAddDoctors;

    @BindView(R.id.relativeLayout_doctors)
    RelativeLayout relativeLayoutDoctors;


    @BindView(R.id.recyclerView_doctors)
    RecyclerView recyclerViewDoctors;


    @BindView(R.id.recyclerView_spec)
    RecyclerView recyclerViewSpec;

    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;


    @BindView(R.id.autoCompleteTextView_doc)
    AutoCompleteTextView textViewDoc;

    @BindView(R.id.autoCompleteTextView_spec)
    AutoCompleteTextView textViewSpec;


    @BindView(R.id.edittext_name)
    EditText edittextName;

    @BindView(R.id.edittext_contatct_number)
    EditText edittextContatctNumber;

    @BindView(R.id.edittext_reg_number)
    EditText edittextRegNumber;

    @BindView(R.id.edittext_qualification)
    EditText edittextQualification;


    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.text_name)
    TextView textName;

    @BindView(R.id.img_user)
    ImageView imgUser;


    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    TextView textviewSelectedLocationCount;
    TextView textviewSelectedSpecCount;

    Dialog dialogFilter;
    RecyclerView recyclerFilterLocation;
    RecyclerView recyclerFilterSpec;



    private static final String USER_REMEMBER = "userRemember";
    private static final String USER_NAME = "userName";
    private static final String USER_IMAGE = "userImage";




    DoctorsAdapter doctorsAdapter;
    SpecializationAdapter specializationAdapter;

    ArrayList<Doctor> doctorList;
    ArrayList<Specialization> specList;
    ArrayList<Specialization> addedSpecList;


    DoctorsPresenter doctorsPresenter;


    Animation animation, animation2;





    ArrayList<LocationEntitie> doctorsLocationFilterList;
    ArrayList<Specialization> doctorsSpecFilterList;

    ArrayList<String> locationNameListForFilter;
    ArrayList<String> specNameListForFilter;


    ArrayList<LocationEntitie> addedDoctorsLocationFilterList;
    ArrayList<Specialization> addedDoctorsSpecFilterList;


    ArrayList<String> addedStatusList ;

    EncryptedPreferences encryptedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        ButterKnife.bind(this);


        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();


        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_expanview);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.fade_out_expanview);



        doctorList = new ArrayList<>();

        doctorsLocationFilterList = new ArrayList<>();
        doctorsSpecFilterList = new ArrayList<>();


        addedSpecList = new ArrayList<Specialization>();
        addedDoctorsLocationFilterList = new ArrayList<LocationEntitie>();
        addedDoctorsSpecFilterList = new ArrayList<Specialization>();
        addedStatusList = new ArrayList<>();

        locationNameListForFilter = new ArrayList<>();
        specNameListForFilter = new ArrayList<>();



        setDoctorsRecycalView();
        setSpecRecycalView();

        setUserDetails();

        doctorsPresenter = new DoctorsPresenterImpli(this);

        bottomNavigationBar.setSelectedItemId(R.id.navigation_doctors);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        unBloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        doctorsPresenter.getDoctor(this);


        textViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedDoc = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchDoctor(doctorList, selectedDoc);
            }
        });


        textViewSpec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedSpec = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchSpec(specList, selectedSpec);

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
                    Intent intent = new Intent(DoctorsActivity.this, ExpensesActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 1) {
                    Intent intent = new Intent(DoctorsActivity.this, MileageActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }else if(position==2){
                    Intent intent = new Intent(DoctorsActivity.this, PharmacyVisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }else if(position==3){
                    Intent intent = new Intent(DoctorsActivity.this, ProductsUnavailabilityActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }
            }
        });

    }


    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @OnClick(R.id.imageView_logout)
    public void onClickLogout(View view) {
        encryptedPreferences.edit().putBoolean(USER_REMEMBER,false);
        Intent intent = new Intent(DoctorsActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();


    }

    @OnClick(R.id.btn_adddoc)
    public void onAddDoctors(View view) {
        includeNointernt.setVisibility(View.GONE);
        includeProgres.setVisibility(View.VISIBLE);
        unBloackUserInteraction();

        doctorsPresenter.postDoctor(this, edittextName.getText().toString(),edittextContatctNumber.getText().toString(),edittextRegNumber.getText().toString(),
                edittextQualification.getText().toString(), 0, addedSpecList);

      //  String name,String tpNumber,String regNumber,String qualification
    }


    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        includeNointernt.setVisibility(View.GONE);
        unBloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        doctorsPresenter.getDoctor(this);
    }


    @OnClick(R.id.imageView_doc_search)
    public void onClickDocSearch(View view) {
        if (textViewDoc.getVisibility() == View.VISIBLE) {
            doctorsPresenter.searchDoctor(doctorList, textViewDoc.getText().toString());
        } else {
            textViewDoc.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.imageView_spec_search)
    public void onClickSpecSearch(View view) {
        doctorsPresenter.searchSpec(specList, textViewSpec.getText().toString());
    }


    @SuppressLint("RestrictedApi")
    @OnClick(R.id.fab)
    public void onClickFab(View view) {

        fab.setVisibility(View.GONE);

        relativeLayoutDoctors.startAnimation(animation2);
        relativeLayoutDoctors.setVisibility(View.GONE);
        relativeLayoutAddDoctors.startAnimation(animation);
        relativeLayoutAddDoctors.setVisibility(View.VISIBLE);


        unBloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        doctorsPresenter.getSpecialization(this);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        if (relativeLayoutAddDoctors.getVisibility() == View.VISIBLE) {
            fab.setVisibility(View.VISIBLE);

            relativeLayoutAddDoctors.startAnimation(animation2);
            relativeLayoutAddDoctors.setVisibility(View.GONE);

            relativeLayoutDoctors.startAnimation(animation);
            relativeLayoutDoctors.setVisibility(View.VISIBLE);

            unBloackUserInteraction();
            includeProgres.setVisibility(View.VISIBLE);
            doctorsPresenter.getDoctor(this);


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

    @OnClick(R.id.imageView_filter)
    public void onClickTackFilter(View view) {
        if(doctorList.isEmpty()){
            Toast.makeText(this, "No Doctors to filter", Toast.LENGTH_SHORT).show();
        }else {
            showFilterDialog();
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:

                    Intent intent = new Intent(DoctorsActivity.this, VisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_location:

                    Intent intentLocation = new Intent(DoctorsActivity.this, LocationActivity.class);
                    Bundle bndlanimationLocation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentLocation, bndlanimationLocation);
                    finish();

                    return true;
                case R.id.navigation_doctors:


                    return true;

            }
            return false;
        }
    };


    @Override
    public void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorNAmeList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        relativeLayoutDoctors.setVisibility(View.VISIBLE);

        doctorList = doctorArrayList;
        doctorsAdapter = new DoctorsAdapter(this, doctorArrayList);
        recyclerViewDoctors.setAdapter(doctorsAdapter);

        ArrayAdapter<String> productAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, doctorNAmeList);
        textViewDoc.setAdapter(productAdapterList);




    }

    @Override
    public void doctorsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Doctors available");
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
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            doctorsPresenter.getDoctor(DoctorsActivity.this);
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
    public void doctorsGetingNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        relativeLayoutDoctors.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);

    }

    @Override
    public void visitsLocationList(ArrayList<LocationEntitie> locationListForFilter) {
      doctorsLocationFilterList=locationListForFilter;

    }

    @Override
    public void visitsLocationNameList(ArrayList<String> locationNameList) {
        locationNameListForFilter=locationNameList;
    }

    @Override
    public void visitsSpecList(ArrayList<Specialization> specListForFilter) {
        doctorsSpecFilterList = specListForFilter;
    }

    @Override
    public void visitsSpecNameList(ArrayList<String> specNameList) {
        specNameListForFilter=specNameList;
    }

    @Override
    public void searchDoctorList(ArrayList<Doctor> doctorArrayList) {
        doctorsAdapter = new DoctorsAdapter(this, doctorArrayList);
        recyclerViewDoctors.setAdapter(doctorsAdapter);
    }


    @Override
    public void SpecializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        specList = specArrayList;

        specializationAdapter = new SpecializationAdapter(this, specArrayList, this);
        recyclerViewSpec.setAdapter(specializationAdapter);


        ArrayAdapter<String> productAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, specNameList);
        textViewSpec.setAdapter(productAdapterList);


    }

    @Override
    public void SpecializationEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Specialization approved or added yet");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();


    }

    @Override
    public void SpecializationGetingFail(String failMsg) {
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
                            doctorsPresenter.getSpecialization(DoctorsActivity.this);
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
    public void SpecializationGetingNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("Try-Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bloackUserInteraction();
                        includeProgres.setVisibility(View.VISIBLE);
                        doctorsPresenter.getSpecialization(DoctorsActivity.this);
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
    public void addedSpecStart(Specialization specialization, boolean addOrRemove) {
        doctorsPresenter.addSpecToDoc(addedSpecList, specialization, addOrRemove);

    }

    @Override
    public void addedSpec(ArrayList<Specialization> specArrayList) {
        addedSpecList = specArrayList;
        doctorsPresenter.showAddSpec(specArrayList, specList);

    }

    @Override
    public void showAddedSpec(ArrayList<Specialization> addedArrayList) {

        specializationAdapter = new SpecializationAdapter(this, addedArrayList, this);
        recyclerViewSpec.setAdapter(specializationAdapter);

    }

    @Override
    public void searchSpecList(ArrayList<Specialization> specArrayList) {
        specializationAdapter = new SpecializationAdapter(this, specArrayList, this);
        recyclerViewSpec.setAdapter(specializationAdapter);
        textViewSpec.setText("");

    }


    @Override
    public void postDoctorError(String msg) {
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
    public void postDoctorSuccess() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        edittextName.setText("");
        edittextContatctNumber.setText("");
        edittextRegNumber.setText("");
        edittextQualification.setText("");

        addedSpecList.clear();

        specializationAdapter = new SpecializationAdapter(this, specList, this);
        recyclerViewSpec.setAdapter(specializationAdapter);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Doctor added successfully");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();


        Toast.makeText(this, "Doctor added successful", Toast.LENGTH_LONG).show();

    }

    @Override
    public void postDoctorDuplicate(ArrayList<Doctor> duplicateDoctorsList, final String name, final String tpNumber, final String regNumber, final String qualification, int isAfterSuggestion, ArrayList<Specialization> specArrayList) {

        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();


        DoctorsDuplicateAdapter doctorsDuplicateAdapter = new DoctorsDuplicateAdapter(this, duplicateDoctorsList);

        final Dialog dialogDuplicate = new Dialog(this);
        dialogDuplicate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDuplicate.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogDuplicate.setContentView(R.layout.dialog_doctor_duplicate);
        dialogDuplicate.setCancelable(true);


        RecyclerView recyclerDuplicate = dialogDuplicate.findViewById(R.id.recycler_duplicate);

        TextView textviewBtnNo = dialogDuplicate.findViewById(R.id.text_btn_approve);
        TextView textviewBtnYes = dialogDuplicate.findViewById(R.id.text_btn_reject);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerDuplicate.setLayoutManager(layoutManager);
        recyclerDuplicate.setItemAnimator(new DefaultItemAnimator());
        recyclerDuplicate.setNestedScrollingEnabled(false);

        recyclerDuplicate.setAdapter(doctorsDuplicateAdapter);


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
                doctorsPresenter.postDoctor(DoctorsActivity.this,  name, tpNumber, regNumber, qualification, 1, addedSpecList);
            }
        });

        dialogDuplicate.show();


    }

    @Override
    public void postDoctorFail(String failMsg, final String name, final String tpNumber, final String regNumber, final String qualification, final int isAfterSuggestion, final ArrayList<Specialization> specArrayList) {
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
                            doctorsPresenter.postDoctor(DoctorsActivity.this,  name, tpNumber, regNumber, qualification, isAfterSuggestion, specArrayList);
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
    public void postDoctorNetworkFail() {
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
    public void locationToDocFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        doctorsPresenter.addLocationToDocFilter(addedDoctorsLocationFilterList,locationEntitie,addOrRemove);
    }

    @Override
    public void locationToDocFilter(ArrayList<LocationEntitie> locArrayListToFilter) {
        addedDoctorsLocationFilterList = locArrayListToFilter;
        textviewSelectedLocationCount.setText(String.valueOf(addedDoctorsLocationFilterList.size()));
        doctorsPresenter.showAddLocationToFilter(locArrayListToFilter,doctorsLocationFilterList);

    }

    @Override
    public void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter) {
        DoctorsFilterLocationAdapter doctorsFilterLocationAdapter = new DoctorsFilterLocationAdapter(this,addedArrayListToFilter,this);
        recyclerFilterLocation.setAdapter(doctorsFilterLocationAdapter);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList) {
        DoctorsFilterLocationAdapter doctorsFilterLocationAdapter = new DoctorsFilterLocationAdapter(this,filterList,this);
        recyclerFilterLocation.setAdapter(doctorsFilterLocationAdapter);
    }



    @Override
    public void specToDocFilterStart(Specialization specialization, boolean addOrRemove) {
        doctorsPresenter.addSpecToDocFilter(addedDoctorsSpecFilterList,specialization,addOrRemove);
    }

    @Override
    public void specToDocFilter(ArrayList<Specialization> specListToFilter) {
        addedDoctorsSpecFilterList =specListToFilter;
        textviewSelectedSpecCount.setText(String.valueOf(addedDoctorsSpecFilterList.size()));
        doctorsPresenter.showAddSpecToFilter(specListToFilter,doctorsSpecFilterList);
    }

    @Override
    public void addSpecToFilter(ArrayList<Specialization> addedArrayListToFilter) {
        DoctorsFilterSpecializationAdapter doctorsFilterSpecializationAdapter = new DoctorsFilterSpecializationAdapter(this,addedArrayListToFilter,this);
        recyclerFilterSpec.setAdapter(doctorsFilterSpecializationAdapter);
    }

    @Override
    public void showSelectedFilterSpec(ArrayList<Specialization> filterList) {
        DoctorsFilterSpecializationAdapter doctorsFilterSpecializationAdapter = new DoctorsFilterSpecializationAdapter(this,filterList,this);
        recyclerFilterSpec.setAdapter(doctorsFilterSpecializationAdapter);
    }



    @Override
    public void doctorsFilterListEmpty(String msg, ArrayList<Doctor> doctorsArrayList) {

    }

    @Override
    public void doctorsFilterList(ArrayList<Doctor> doctorsArrayList) {
        doctorsAdapter = new DoctorsAdapter(this, doctorsArrayList);
        recyclerViewDoctors.setAdapter(doctorsAdapter);
        dialogFilter.dismiss();
    }

    @Override
    public void searchLocationList(ArrayList<LocationEntitie> locationArrayList) {

        DoctorsFilterLocationAdapter doctorsFilterLocationAdapter = new DoctorsFilterLocationAdapter(this,locationArrayList,this);
        recyclerFilterLocation.setAdapter(doctorsFilterLocationAdapter);
    }

    @Override
    public void specListForFilter(ArrayList<Specialization> specArrayList) {

        DoctorsFilterSpecializationAdapter doctorsFilterSpecializationAdapter = new DoctorsFilterSpecializationAdapter(this,specArrayList,this);
        recyclerFilterSpec.setAdapter(doctorsFilterSpecializationAdapter);
    }


    private void setDoctorsRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDoctors.setLayoutManager(layoutManager);
        recyclerViewDoctors.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDoctors.setNestedScrollingEnabled(false);

    }

    private void setSpecRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSpec.setLayoutManager(layoutManager);
        recyclerViewSpec.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSpec.setNestedScrollingEnabled(false);

    }


    private void setFilterLocationRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterLocation.setLayoutManager(layoutManager);
        recyclerFilterLocation.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterLocation.setNestedScrollingEnabled(false);

    }

    private void setFilterSpecRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterSpec.setLayoutManager(layoutManager);
        recyclerFilterSpec.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterSpec.setNestedScrollingEnabled(false);

    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void setUserDetails(){
        textName.setText(encryptedPreferences.getString(USER_NAME,""));
        String userImgURL = encryptedPreferences.getString(USER_IMAGE,"");

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


        if(userImgURL.isEmpty() || userImgURL.equals("")){
        }else {
            Glide.with(this)
                    .asBitmap()
                    .load(userImgURL)
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(imgUser);
        }

    }

    private void showFilterDialog(){
        dialogFilter = new Dialog(this);
        dialogFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFilter.setContentView(R.layout.dialog_doctor_filter);
        dialogFilter.setCancelable(true);

        View mView = getLayoutInflater().inflate(R.layout.dialog_doctor_filter, null);

        RelativeLayout layoutStatusBar = dialogFilter.findViewById(R.id.relativelayout_status_bar_image);
        final ImageView imageViewLayoutStatusImage = dialogFilter.findViewById(R.id.imageView_layout_status_image);
        final RelativeLayout relativeLayoutStatus = dialogFilter.findViewById(R.id.relativeLayout_status);

        RelativeLayout layoutLocationBar = dialogFilter.findViewById(R.id.relativelayout_location_bar_image);
        final ImageView imageViewLayoutLocationImage = dialogFilter.findViewById(R.id.imageView_layout_location_image);


        RelativeLayout layoutSpecBar = dialogFilter.findViewById(R.id.relativelayout_spec_bar_image);
        final ImageView imageViewLayoutSpecImage = dialogFilter.findViewById(R.id.imageView_layout_spec_image);





        Button btnFilter = dialogFilter.findViewById(R.id.btn_filter);
        Button btnReset = dialogFilter.findViewById(R.id.btn_reset);



        final RelativeLayout layoutRecyclerViewLocation = dialogFilter.findViewById(R.id.layout_recyclerView_location);
        final AutoCompleteTextView autoTextViewLocation =dialogFilter.findViewById(R.id.autoCompleteTextView_loc);
        final ImageView imageViewLocationSearch = dialogFilter.findViewById(R.id.imageView_loc_search);



        recyclerFilterSpec = dialogFilter.findViewById(R.id.recyclerView_spec);
        recyclerFilterLocation = dialogFilter.findViewById(R.id.recyclerView_location);

        setFilterLocationRecycalView();
        setFilterSpecRecycalView();




        ArrayAdapter<String> locAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locationNameListForFilter);
        autoTextViewLocation.setAdapter(locAdapterList);

        autoTextViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchLocationForFilter(doctorsLocationFilterList,selectedLoc);
            }
        });

        imageViewLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorsPresenter.searchLocationForFilter(doctorsLocationFilterList,autoTextViewLocation.getText().toString());
            }
        });



        final RelativeLayout layoutRecyclerViewSpec = dialogFilter.findViewById(R.id.relativelayout_recyclerView_spec);
        final AutoCompleteTextView autoTextViewSpec =dialogFilter.findViewById(R.id.autoCompleteTextView_spec);
        final ImageView imageViewSpecSearch = dialogFilter.findViewById(R.id.imageView_spec_search);



        ArrayAdapter<String> specAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, specNameListForFilter);
        autoTextViewSpec.setAdapter(specAdapterList);


        autoTextViewSpec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedSpec = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchSpecForFilter(doctorsSpecFilterList,selectedSpec);

            }
        });

        imageViewSpecSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorsPresenter.searchSpecForFilter(doctorsSpecFilterList,autoTextViewSpec.getText().toString());
            }
        });




        textviewSelectedLocationCount = dialogFilter.findViewById(R.id.textview_selected_loc_count);
        textviewSelectedSpecCount = dialogFilter.findViewById(R.id.textview_selected_spec_count);

        if(addedDoctorsLocationFilterList.size()==0){ }else {
            textviewSelectedLocationCount.setText(String.valueOf(addedDoctorsLocationFilterList.size()));
        }

        if(addedDoctorsSpecFilterList.size()==0){ }else {
            textviewSelectedSpecCount.setText(String.valueOf(addedDoctorsSpecFilterList.size()));
        }






        doctorsPresenter.setSelectedFilterLocation(doctorsLocationFilterList,addedDoctorsLocationFilterList);
        doctorsPresenter.setSelectedFilterSpec(doctorsSpecFilterList,addedDoctorsSpecFilterList);


        layoutStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relativeLayoutStatus.getVisibility() == View.VISIBLE) {
                    relativeLayoutStatus.setVisibility(View.GONE);
                    imageViewLayoutStatusImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    relativeLayoutStatus.setVisibility(View.VISIBLE);
                    imageViewLayoutStatusImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });


        layoutLocationBar.setOnClickListener(new View.OnClickListener() {
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




        layoutSpecBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewSpec.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewSpec.setVisibility(View.GONE);
                    imageViewLayoutSpecImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewSpec.setVisibility(View.VISIBLE);
                    imageViewLayoutSpecImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });






        final CheckBox checkBoxApp = dialogFilter.findViewById(R.id.checkBox_approved);
        final CheckBox checkBoxPending = dialogFilter.findViewById(R.id.checkBox_pending);
        final CheckBox checkBoxReject = dialogFilter.findViewById(R.id.checkBox_reject);




        for(String s :addedStatusList){
            if(s.equals("Approved")){
                checkBoxApp.setChecked(true);
            }else if(s.equals("Pending")){
                checkBoxPending.setChecked(true);
            }else {
                checkBoxReject.setChecked(true);
            }


        }


        checkBoxApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxApp.isChecked()) {
                    addedStatusList.add("Approved");
                }else {
                    addedStatusList.remove("Approved");
                }
            }
        });

        checkBoxPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxPending.isChecked()) {
                    addedStatusList.add("Pending");
                }else {
                    addedStatusList.remove("Pending");
                }
            }
        });


        checkBoxReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxReject.isChecked()) {
                    addedStatusList.add("Rejected");
                }else {
                    addedStatusList.remove("Rejected");
                }
            }
        });




        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorsPresenter.doctorsFilter(doctorList, addedStatusList, addedDoctorsLocationFilterList, addedDoctorsSpecFilterList);

            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addedStatusList.clear();
                addedDoctorsLocationFilterList.clear();
                addedDoctorsSpecFilterList.clear();


                doctorsPresenter.doctorsFilter(doctorList, addedStatusList, addedDoctorsLocationFilterList, addedDoctorsSpecFilterList);
            }
        });

        dialogFilter.show();

    }

}
