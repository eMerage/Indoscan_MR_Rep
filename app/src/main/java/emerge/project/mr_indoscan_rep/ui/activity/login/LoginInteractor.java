package emerge.project.mr_indoscan_rep.ui.activity.login;


import android.content.Context;

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void userNameEmpty();
        void passwordEmpty();
        void loginSuccessful();
        void loginFail(String msg);
        void loginError(String msg);
        void loginNetworkFail();
    }
    void checkLogin(Context context, String userName, String password, boolean rememberMe,OnLoginFinishedListener onLoginFinishedListener);


    interface OnCheckFingerPrintFinishedListener {
        void eligiblToUse();
        void notEligiblToUse(String msg);
    }
    void checkIsEligiblUseFingerPrint(Context context,OnCheckFingerPrintFinishedListener onCheckFingerPrintFinishedListener);




}
