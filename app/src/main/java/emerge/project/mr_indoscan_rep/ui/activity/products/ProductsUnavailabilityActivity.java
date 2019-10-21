package emerge.project.mr_indoscan_rep.ui.activity.products;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mr_indoscan_rep.BuildConfig;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.ui.activity.doctors.DoctorsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.expences.ExpensesActivity;
import emerge.project.mr_indoscan_rep.ui.activity.locations.LocationActivity;
import emerge.project.mr_indoscan_rep.ui.activity.mileage.MileageActivity;
import emerge.project.mr_indoscan_rep.ui.activity.pharmacyvisits.PharmacyVisitsActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;
import emerge.project.mr_indoscan_rep.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.pharmacy.ProductAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.products.ProductExpAdapter;
import emerge.project.mr_indoscan_rep.ui.adapters.products.ProductUnavilableAdapter;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;
import emerge.project.mr_indoscan_rep.utils.entittes.Pharmacy;
import emerge.project.mr_indoscan_rep.utils.entittes.Products;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductsUnavailabilityActivity extends Activity implements ProductsUnavailabilityView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;

    @BindView(R.id.recyclerview_product)
    RecyclerView recyclerviewProduct;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.recyclerview_expiryproduct)
    RecyclerView recyclerviewExpiryproduct;


    @BindView(R.id.include_progres)
    View includeProgres;


    @BindView(R.id.textView24)
    TextView textViewselectedDate;


    @BindView(R.id.autoCompleteTextView_unavailability)
    AutoCompleteTextView autoTextViewUnPro;


    @BindView(R.id.autoCompleteTextView_exp)
    AutoCompleteTextView autoTextViewExpDoc;


    NavigationAdapter navigationAdapter;
    public static String tokenID;
    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";

    ApiInterface apiService;

    Dialog dialogCalander;
    String filterDateStart = "";
    String selectedReason = "";

    ArrayList<Products> allProductsList = new ArrayList<Products>();

    ProductsUnavailabilityPresenter productsUnavailabilityPresenter;


    int selectedUnProduct = 0;
    int selectedExpProduct = 0;


    ProductUnavilableAdapter productUnavilableAdapter;
    ProductExpAdapter productExpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_unavailability);
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

        productsUnavailabilityPresenter = new ProductsUnavailabilityPresenterImpli(this);

        navigationAdapter = new NavigationAdapter(this, navigationItems);
        listViewNavigation.setAdapter(navigationAdapter);

        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(ProductsUnavailabilityActivity.this, ExpensesActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ProductsUnavailabilityActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                } else if (position == 1) {
                    Intent intent = new Intent(ProductsUnavailabilityActivity.this, MileageActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ProductsUnavailabilityActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 2) {
                    Intent intent = new Intent(ProductsUnavailabilityActivity.this, PharmacyVisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ProductsUnavailabilityActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();
                } else if (position == 3) {

                }
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        includeProgres.setVisibility(View.VISIBLE);
        productsUnavailabilityPresenter.getProduct(this);


        List<String> list = new ArrayList<String>();
        list.add("Reason 1");
        list.add("Reason 2");
        list.add("Reason 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_bg_spinner, list);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReason = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @OnClick(R.id.autoCompleteTextView_unavailability)
    public void onClickUnProSerach(View view) {
        productsUnavailabilityPresenter.searchUnProduct(allProductsList, autoTextViewUnPro.getText().toString());
    }

    @OnClick(R.id.autoCompleteTextView_exp)
    public void onClickExpProSerach(View view) {
        productsUnavailabilityPresenter.searchUnProduct(allProductsList, autoTextViewExpDoc.getText().toString());
    }


    @OnClick(R.id.btn_save_unproduct)
    public void onClickAddUnPro(View view) {
        includeProgres.setVisibility(View.VISIBLE);

        productsUnavailabilityPresenter.postUnProduct(this, selectedUnProduct, selectedReason);
    }


    @OnClick(R.id.btn_save_exproduct)
    public void onClickAddExPro(View view) {
        includeProgres.setVisibility(View.VISIBLE);
        productsUnavailabilityPresenter.postExProduct(this, selectedExpProduct,filterDateStart);

    }

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

        textViewselectedDate.setText(date);


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
                textViewselectedDate.setText(filterDateStart);
                dialogCalander.dismiss();

            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {


            }
        });


        dialogCalander.show();
    }


    @OnClick(R.id.imageView1)
    public void onClickCalanderIcon(View view) {
        showCalanderDilog();
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
                    Intent intent = new Intent(ProductsUnavailabilityActivity.this, VisitsActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ProductsUnavailabilityActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent, bndlanimation);
                    finish();

                    return true;
                case R.id.navigation_location:
                    Intent intent2 = new Intent(ProductsUnavailabilityActivity.this, LocationActivity.class);
                    Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(ProductsUnavailabilityActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intent2, bndlanimation2);
                    finish();

                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoc = new Intent(ProductsUnavailabilityActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoc = ActivityOptions.makeCustomAnimation(ProductsUnavailabilityActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoc, bndlanimationDoc);
                    finish();
                    return true;

            }
            return false;
        }
    };


    @Override
    public void productList(ArrayList<Products> productList, ArrayList<String> productNameList) {
        includeProgres.setVisibility(View.GONE);

        allProductsList = productList;

         productUnavilableAdapter = new ProductUnavilableAdapter(this, productList, this);
        recyclerviewProduct.setAdapter(productUnavilableAdapter);


         productExpAdapter = new ProductExpAdapter(this, productList, this);
        recyclerviewExpiryproduct.setAdapter(productExpAdapter);


        ArrayAdapter<String> phaAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, productNameList);
        autoTextViewUnPro.setAdapter(phaAdapterList);
        autoTextViewExpDoc.setAdapter(phaAdapterList);


        autoTextViewUnPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedPha = parent.getItemAtPosition(pos).toString();
                productsUnavailabilityPresenter.searchUnProduct(allProductsList, selectedPha);

            }
        });


        autoTextViewExpDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedPha = parent.getItemAtPosition(pos).toString();
                productsUnavailabilityPresenter.searchExpProduct(allProductsList, selectedPha);

            }
        });


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
                            productsUnavailabilityPresenter.getProduct(ProductsUnavailabilityActivity.this);

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
    public void searchUnProductList(ArrayList<Products> productList) {
        ProductUnavilableAdapter productUnavilableAdapter = new ProductUnavilableAdapter(this, productList, this);
        recyclerviewProduct.setAdapter(productUnavilableAdapter);

    }

    @Override
    public void searchExpProductList(ArrayList<Products> productList) {
        ProductExpAdapter productExpAdapter = new ProductExpAdapter(this, productList, this);
        recyclerviewExpiryproduct.setAdapter(productExpAdapter);
    }

    @Override
    public void selectedUnProductID(int selectedProductId) {
        selectedUnProduct = selectedProductId;
    }

    @Override
    public void selectedExpProductID(int selectedProductId) {
        selectedExpProduct = selectedProductId;
    }


    @Override
    public void postUnProductError(String msg) {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postUnProductSuccess() {
        includeProgres.setVisibility(View.GONE);
        for (Products products : allProductsList) {
            products.setSelect(false);
        }
        productUnavilableAdapter.notifyDataSetChanged();


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Success");
            alertDialogBuilder.setMessage("Unavailable Products adding success");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Unavailable Products adding success", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void postUnProductFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            productsUnavailabilityPresenter.postUnProduct(ProductsUnavailabilityActivity.this, selectedUnProduct, selectedReason);

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
    public void postUnProductNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }







    @Override
    public void postExProductError(String msg) {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postExProductSuccess() {
        includeProgres.setVisibility(View.GONE);


        for (Products products : allProductsList) {
            products.setSelect(false);
        }
        productExpAdapter.notifyDataSetChanged();


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Success");
            alertDialogBuilder.setMessage("Short Expiry Products adding success");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Short Expiry Products adding success", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void postExProductFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            productsUnavailabilityPresenter.postExProduct(ProductsUnavailabilityActivity.this, selectedExpProduct,filterDateStart);

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
    public void postExProductNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, "No Internet access,Please try again", Toast.LENGTH_SHORT).show();
    }
}
