package emerge.project.mr_indoscan_rep.ui.adapters.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.utils.entittes.Navigation;


/**
 * Created by Himanshu on 01/05/2017.
 */

public class NavigationAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Navigation> mNavItems;

    public NavigationAdapter(Context context, ArrayList<Navigation> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_navigation, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.txtView__listnavigation_title);
        titleView.setText(mNavItems.get(position).mTitle);


        return view;
    }
}
