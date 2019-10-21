package emerge.project.mr_indoscan_rep.ui.adapters.locations;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import emerge.project.mr_indoscan_rep.R;
import emerge.project.mr_indoscan_rep.utils.entittes.ExpencesCategorys;
import emerge.project.mr_indoscan_rep.utils.entittes.LocationType;

public class LocationTypeSpnninerAdaptar extends ArrayAdapter<String> {


    private final LayoutInflater mInflater;
    private final Context mContext;
    private final ArrayList<LocationType> items;
    private final int mResource;

    public LocationTypeSpnninerAdaptar(@NonNull Context context, @LayoutRes int resource,
                                       @NonNull ArrayList objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView offTypeTv = (TextView) view.findViewById(R.id.text1);


        LocationType offerData = items.get(position);

        offTypeTv.setText(offerData.getName());


        return view;
    }
}
