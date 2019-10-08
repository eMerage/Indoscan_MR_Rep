package emerge.project.mr_indoscan_rep.ui.activity.login;


import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import emerge.project.mr_indoscan_rep.services.api.ApiClient;
import emerge.project.mr_indoscan_rep.services.api.ApiInterface;
import emerge.project.mr_indoscan_rep.services.network.NetworkAvailability;
import emerge.project.mr_indoscan_rep.utils.entittes.LoginUser;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginInteractorImpil implements LoginInteractor {

    EncryptedPreferences encryptedPreferences;
    private static final String PUSH_TOKEN = "pushToken";
    private static final String USER_ID = "userID";
    private static final String USER_NAME = "userName";
    private static final String USER_IMAGE = "userImage";
    private static final String USER_REMEMBER = "userRemember";


    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    LoginUser loginUserObject;

    @Override
    public void checkLogin(final Context context, final String userName, String password, final boolean rememberMe, final OnLoginFinishedListener onLoginFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onLoginFinishedListener.loginNetworkFail();
        } else if (userName.isEmpty() || userName.equals("") || userName == null) {
            onLoginFinishedListener.userNameEmpty();
        } else if (password.isEmpty() || password.equals("") || password == null) {
            onLoginFinishedListener.passwordEmpty();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                String userPushTokenId = encryptedPreferences.getString(PUSH_TOKEN, "");
                apiService.validateUser(userName, password, 2, userPushTokenId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<LoginUser>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(LoginUser loginuser) {
                                loginUserObject = loginuser;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onLoginFinishedListener.loginFail("Something went wrong, Please try again"+e);
                            }

                            @Override
                            public void onComplete() {
                                if (!loginUserObject.getUserTypeID().equals("0")) {
                                    if (rememberMe) {
                                        encryptedPreferences.edit().putBoolean(USER_REMEMBER, true);
                                    } else {
                                        encryptedPreferences.edit().putBoolean(USER_REMEMBER, false);
                                    }
                                    encryptedPreferences.edit().putInt(USER_ID, loginUserObject.getUserId()).apply();
                                    encryptedPreferences.edit().putString(USER_NAME, loginUserObject.getUserName()).apply();
                                    if (loginUserObject.getImageUrl() == null) {
                                        encryptedPreferences.edit().putString(USER_IMAGE, "").apply();
                                    } else {
                                        encryptedPreferences.edit().putString(USER_IMAGE, loginUserObject.getImageUrl()).apply();
                                    }
                                    onLoginFinishedListener.loginSuccessful();
                                } else {
                                    onLoginFinishedListener.loginError(loginUserObject.getError().getDescription());
                                }
                            }
                        });
            } catch (Exception ex) {
                onLoginFinishedListener.loginFail("Something went wrong, Please try again");
            }
        }
    }

    @Override
    public void checkIsEligiblUseFingerPrint(Context context, OnCheckFingerPrintFinishedListener onCheckFingerPrintFinishedListener) {
        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        int userId = encryptedPreferences.getInt(USER_ID, 0);

        if (userId == 0) {
            onCheckFingerPrintFinishedListener.notEligiblToUse("You need to log manually at the initial login. The finger print scan will be active at the next login");
        } else {
            onCheckFingerPrintFinishedListener.eligiblToUse();
        }
    }

}

