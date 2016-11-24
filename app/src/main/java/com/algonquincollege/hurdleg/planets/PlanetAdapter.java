package com.algonquincollege.hurdleg.planets;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.algonquincollege.hurdleg.planets.model.Planet;

/**
 * Purpose: customize the Planet cell for each planet displayed in the ListActivity (i.e. MainActivity).
 * Usage:
 *   1) extend from class ArrayAdapter<YourModelClass>
 *   2) @override getView( ) :: decorate the list cell
 *
 * Based on the Adapter OO Design Pattern.
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 *
 * Reference: based on DisplayList in "Connecting Android Apps to RESTful Web Services" with David Gassner
 */
public class PlanetAdapter extends ArrayAdapter<Planet> {

    private Context context;
    private List<Planet> planetList;

    public PlanetAdapter(Context context, int resource, List<Planet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.planetList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_planet, parent, false);

        //Display planet name in the TextView widget
        Planet planet = planetList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(planet.getName());

        return view;
    }
}
