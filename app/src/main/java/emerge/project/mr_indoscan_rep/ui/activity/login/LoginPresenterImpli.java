package emerge.project.mr_indoscan_rep.ui.activity.login;


import android.content.Context;

public class LoginPresenterImpli implements LoginPresenter, LoginInteractor.OnLoginFinishedListener,
        LoginInteractor.OnCheckFingerPrintFinishedListener {


    private LoginView loginView;
    LoginInteractor loginInteractor;


    public LoginPresenterImpli(LoginView loginview) {
        this.loginView = loginview;
        this.loginInteractor = new LoginInteractorImpil();

    }


    @Override
    public void checkLogin(Context context, String userName, String password,boolean rememberMe) {
        loginInteractor.checkLogin(context, userName, password,rememberMe ,this);
    }


    @Override
    public void userNameEmpty() {
        loginView.userNameEmpty();
    }

    @Override
    public void passwordEmpty() {
        loginView.passwordEmpty();
    }

    @Override
    public void loginSuccessful() {
        loginView.loginSuccessful();
    }

    @Override
    public void loginFail(String msg) {
        loginView.loginFail(msg);
    }

    @Override
    public void loginError(String msg) {
        loginView.loginError(msg);
    }

    @Override
    public void loginNetworkFail() {
        loginView.loginNetworkFail();
    }


    @Override
    public void checkIsEligiblUseFingerPrint(Context context) {
        loginInteractor.checkIsEligiblUseFingerPrint(context, this);
    }

    @Override
    public void eligiblToUse() {
        loginView.eligiblToUse();
    }

    @Override
    public void notEligiblToUse(String msg) {
        loginView.notEligiblToUse(msg);
    }
}
