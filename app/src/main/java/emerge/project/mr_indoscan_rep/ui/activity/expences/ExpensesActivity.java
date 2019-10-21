package emerge.project.mr_indoscan_rep.ui.activity.expences;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationActivity;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageActivity;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.products.ProductsUnavailabilityActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.expenses.ExpensesCategorySpnninerAdaptar;
import emerge.project.mr_indoscan_rep.ui.adapters.expenses.ExpensesImagesAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;

public class ExpensesActivity extends Activity implements ExpensesView {


    static final int PICK_IMAGE_REQUEST = 3;

    private static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 2;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    @BindView(R.id.spinner2)
    Spinner spinnerMaincat;

    @BindView(R.id.spinner3)
    Spinner spinnerSubcat;


    @BindView(R.id.montserratRegular2)
    TextView textDate;


    @BindView(R.id.imageView_payment_image)
    ImageView imageViewimage;


    @BindView(R.id.editText_ref)
    EditText editTextRef;


    @BindView(R.id.editText_des)
    EditText editTextDes;


    @BindView(R.id.editText_amount)
    EditText editTextAmount;


    Bitmap bitmap;


    Dialog dialogCalander;


    String filterDateStart = "";

    NavigationAdapter navigationAdapter;

    ExpensesPresenter expensesPresenter;

    int selectedCatID = 0;
    int selectedSubCatID = 0;
    ArrayList<Bitmap> imagelist = new ArrayList<Bitmap>();


    @BindView(R.id.recyclerView_iamges)
    RecyclerView recyclerViewIamges;

    @BindView(R.id.include_progres)
    View includeProgres;


    Bundle imageExtras;

    ExpensesImagesAdapter expensesImagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expences);
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


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewIamges.setLayoutManager(layoutManager);
        recyclerViewIamges.setItemAnimator(new DefaultItemAnimator());
        recyclerViewIamges.setNestedScrollingEnabled(false);


        expensesPresenter = new ExpensesPresenterImpli(this);

        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if (position == 1) {
                    Intent intent = new Intent(ExpensesActivity.this, MileageActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ExpensesActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 2) {
                    Intent intent = new Intent(ExpensesActivity.this, PharmacyVisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ExpensesActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 3) {
                    Intent intent = new Intent(ExpensesActivity.this, ProductsUnavailabilityActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ExpensesActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                }
            }
        });

        includeProgres.setVisibility(View.VISIBLE);
        expensesPresenter.getExpensesCategory(this);

    }


    @Override
    protected void onStart() {
        super.onStart();




    }

    @OnClick(R.id.btn_add_expen)
    public void onClickAddExpenses(View view) {

        includeProgres.setVisibility(View.VISIBLE);

        Double billAmount = 0.0;
        try {
            billAmount = Double.valueOf(editTextAmount.getText().toString());
        } catch (NumberFormatException num) {

        }

        expensesPresenter.postExpenses(this, filterDateStart, selectedCatID, selectedSubCatID, editTextRef.getText().toString(), editTextDes.getText().toString(),
                billAmount,imagelist);

    }


    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.imageView5)
    public void onClickCalender(View view) {
        showCalanderDilog();
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
                    Intent intent = new Intent(ExpensesActivity.this, VisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ExpensesActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_location:
                    Intent intent2 = new Intent(ExpensesActivity.this, LocationActivity.class);
                    Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(ExpensesActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent2, bndlanimation2);
                    finish();

                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoc = new Intent(ExpensesActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoc = ActivityOptions.makeCustomAnimation(ExpensesActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoc, bndlanimationDoc);
                    finish();
                    return true;

            }
            return false;
        }
    };


    private void showCalanderDilog() {

        dialogCalander = new Dialog(this);
        dialogCalander.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCalander.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogCalander.setContentView(R.layout.dialog_data_calande);
        dialogCalander.setCancelable(true);


        final DateRangeCalendarView calendarView = (DateRangeCalendarView) dialogCalander.findViewById(R.id.calendarView);


        String date = "";

        if (filterDateStart.isEmpty()) {
            date = "Date not selected yet";
        } else {
            date = date + " " + filterDateStart;
        }

        final SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        textDate.setText(date);


        Date dateS = null;
        Date dateE = null;

        try {
            dateS = targetFormat.parse(filterDateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (filterDateStart.isEmpty()) {
        } else {
            Calendar startSelectionDate = Calendar.getInstance();
            startSelectionDate.setTime(dateS);
            calendarView.setSelectedDateRange(startSelectionDate, startSelectionDate);

        }


        calendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                filterDateStart = targetFormat.format(startDate.getTime());
                textDate.setText(filterDateStart);
                dialogCalander.dismiss();

            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {


            }
        });


        dialogCalander.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        try {
                            bitmap = (Bitmap) data.getExtras().get("data");
                            imagelist.add(bitmap);
                            imageViewimage.setImageBitmap(bitmap);

                            showImages();

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




    private void showImages() {
        expensesImagesAdapter = new ExpensesImagesAdapter(this, imagelist);
        recyclerViewIamges.setAdapter(expensesImagesAdapter);

    }


    @Override
    public void expensesCategoryList(ArrayList<ExpencesCategorys> list) {

        includeProgres.setVisibility(View.GONE);

        expensesPresenter.getExpensesSubCategory(this);

        ExpensesCategorySpnninerAdaptar adapter = new ExpensesCategorySpnninerAdaptar(this, R.layout.textview_spinner, list);
        spinnerMaincat.setAdapter(adapter); // this will set list of values to spinner

        spinnerMaincat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ExpencesCategorys ex = (ExpencesCategorys) parent.getItemAtPosition(pos);
                selectedCatID = ex.getId();

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void expensesCategoryFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            expensesPresenter.getExpensesCategory(ExpensesActivity.this);
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
    public void expensesCategoryNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void expensesSubCategoryList(ArrayList<ExpencesCategorys> subList) {

        includeProgres.setVisibility(View.GONE);

        ExpensesCategorySpnninerAdaptar adapter = new ExpensesCategorySpnninerAdaptar(this, R.layout.textview_spinner, subList);
        spinnerSubcat.setAdapter(adapter); // this will set list of values to spinner

        spinnerSubcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ExpencesCategorys ex = (ExpencesCategorys) parent.getItemAtPosition(pos);
                selectedSubCatID = ex.getId();

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void expensesSubCategoryFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            expensesPresenter.getExpensesSubCategory(ExpensesActivity.this);
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
    public void expensesSubCategoryNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postExpensesError(String msg) {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postExpensesSuccess() {
        includeProgres.setVisibility(View.GONE);
        filterDateStart = "";
        editTextRef.setText("");
        editTextDes.setText("");
        editTextAmount.setText("");
        imagelist.clear();
        showImages();

        imageViewimage.setImageDrawable(getResources().getDrawable(R.drawable.noimage));

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Success");
            alertDialogBuilder.setMessage("Expenses adding success");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Expenses adding success", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void postExpensesFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            includeProgres.setVisibility(View.VISIBLE);
                            Double billAmount = 0.0;
                            try {
                                billAmount = Double.valueOf(editTextAmount.getText().toString());
                            } catch (NumberFormatException num) {

                            }

                            expensesPresenter.postExpenses(ExpensesActivity.this, filterDateStart, selectedCatID, selectedSubCatID, editTextRef.getText().toString(), editTextDes.getText().toString(),
                                    billAmount,imagelist);
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
    public void postExpensesNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }
}
