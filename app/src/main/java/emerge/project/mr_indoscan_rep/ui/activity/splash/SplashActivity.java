package emerge.project.mr_indoscan_rep.ui.activity.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.ui.activity.login.LoginActivity;
import emerge.project.mr_indoscan_rep.ui.activity.visits.VisitsActivity;

public class SplashActivity extends Activity {


    public Handler mHandler;


    private Runnable myRunnableActivityLogin;
    private Runnable myRunnableActivityHome;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    private static final String USER_REMEMBER = "userRemember";


        @BindView(R.id.textView_version)
        TextView textViewVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();

        textViewVersion.setText("Indoscan Medical Rep - v"+ getApplicationVersion());

        mHandler = new Handler();
        runnable(encryptedPreferences.getBoolean(USER_REMEMBER,false));
        try {
            mHandler.postDelayed(myRunnableActivityLogin, 2000);
        } catch (Exception ex) {

        }



    }

    @Override
    protected void onResume() {
        try {
            mHandler.postDelayed(myRunnableActivityLogin, 2000);
        } catch (Exception ex) {

        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(myRunnableActivityLogin);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(myRunnableActivityLogin);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(myRunnableActivityLogin);
        super.onStop();
    }


    private void runnable(final boolean isRemember) {

        myRunnableActivityLogin = new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(isRemember){
                    intent = new Intent(SplashActivity.this, VisitsActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SplashActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(intent, bndlanimation);
                finish();
            }
        };


    }

    public String getApplicationVersion() {
        String version;
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            version = "1.0.0";
            e.printStackTrace();
        }
        version = pInfo.versionName;
        return version;
    }

}
