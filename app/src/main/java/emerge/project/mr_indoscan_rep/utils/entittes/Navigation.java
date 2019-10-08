package emerge.project.mr_indoscan_rep.utils.entittes;




public class Navigation {
    public String mTitle;
    public  int mIcon;
//
    public Navigation(String title, int icon) {
        mTitle = title;
        mIcon = icon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }
}
