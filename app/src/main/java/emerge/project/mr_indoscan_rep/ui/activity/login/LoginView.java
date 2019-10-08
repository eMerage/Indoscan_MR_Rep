package emerge.project.mr_indoscan_rep.ui.activity.login;



public interface LoginView {
    void userNameEmpty();
    void passwordEmpty();
    void loginSuccessful();
    void loginFail(String msg);
    void loginError(String msg);
    void loginNetworkFail();


    void eligiblToUse();
    void notEligiblToUse(String msg);





}
