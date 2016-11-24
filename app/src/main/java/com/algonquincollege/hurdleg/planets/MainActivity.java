package com.algonquincollege.hurdleg.planets;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.algonquincollege.hurdleg.planets.model.Planet;
import com.algonquincollege.hurdleg.planets.parsers.PlanetJSONParser;
import com.algonquincollege.hurdleg.planets.utils.HttpManager;
import com.algonquincollege.hurdleg.planets.utils.HttpMethod;
import com.algonquincollege.hurdleg.planets.utils.RequestPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Sending POST parameters in JSON format.
 *
 * @see {util.HttpManager}
 * @see {util.HttpMethod}
 * @see {util.RequestPackage}
 * @see {PlanetAdapter}
 * @see {res.layout.item_planet.xml}
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 *
 * Reference: based on JSONParams in "Connecting Android Apps to RESTful Web Services" with David Gassner
 */
public class MainActivity extends ListActivity {

    private static final Boolean LOCALHOST = false;
    private static final String REST_URI;

    private ProgressBar pb;
    private List<GetTask> tasks;

    private List<Planet> planetList;

    static {
        REST_URI = LOCALHOST ? "http://10.0.2.2:3000/planets" : "https://planets-hurdleg.mybluemix.net/planets";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_get_data) {
            if (isOnline()) {
                getPlanets( REST_URI );
            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.action_post_data) {
            if (isOnline()) {
                createPlanet( REST_URI );
            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.action_put_data) {
            if (isOnline()) {
                updatePlanet( REST_URI );
            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.action_delete_data) {
            if (isOnline()) {
                deletePlanet( REST_URI );
            } else {
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void createPlanet(String uri) {
        Planet planet = new Planet();
        planet.setPlanetId( 0 );
        planet.setName( "Pluto" );
        planet.setOverview( "I miss Pluto!" );
        planet.setImage( "images/neptune.png" );
        planet.setDescription( "Pluto was stripped of planet status :(" );
        planet.setDistanceFromSun( 3.67f );
        planet.setNumberOfMoons( 0 );

        RequestPackage pkg = new RequestPackage();
        pkg.setMethod( HttpMethod.POST );
        pkg.setUri( uri );
        pkg.setParam("planetId", planet.getPlanetId() + "");
        pkg.setParam("name", planet.getName() );
        pkg.setParam("overview", planet.getOverview() );
        pkg.setParam("image", planet.getImage() );
        pkg.setParam("description", planet.getDescription() );
        pkg.setParam("distance_from_sun", planet.getDistanceFromSun() + "");
        pkg.setParam("number_of_moons", planet.getNumberOfMoons() + "" );

        DoTask postTask = new DoTask();
        postTask.execute( pkg );
    }

    private void deletePlanet(String uri) {
        RequestPackage pkg = new RequestPackage();
        pkg.setMethod( HttpMethod.DELETE );
        // DELETE the planet with Id 8
        pkg.setUri( uri + "/8" );
        DoTask deleteTask = new DoTask();
        deleteTask.execute( pkg );
    }

    private void getPlanets(String uri) {
        RequestPackage getPackage = new RequestPackage();
        getPackage.setMethod( HttpMethod.GET );
        getPackage.setUri( uri );
        GetTask getTask = new GetTask();
        getTask.execute( getPackage );
    }

    private void updatePlanet(String uri) {
        Planet planet = new Planet();
        planet.setPlanetId( 8 );
        planet.setName( "hurdleg" );
        planet.setOverview( "hurdleg" );
        planet.setImage( "images/neptune.png" );
        planet.setDescription( "hurdleg" );
        planet.setDistanceFromSun( 0f );
        planet.setNumberOfMoons( 0 );

        RequestPackage pkg = new RequestPackage();
        pkg.setMethod( HttpMethod.PUT );
        pkg.setUri( uri + "/8" );
        pkg.setParam("planetId", planet.getPlanetId() + "");
        pkg.setParam("name", planet.getName() );
        pkg.setParam("overview", planet.getOverview() );
        pkg.setParam("image", planet.getImage() );
        pkg.setParam("description", planet.getDescription() );
        pkg.setParam("distance_from_sun", planet.getDistanceFromSun() + "");
        pkg.setParam("number_of_moons", planet.getNumberOfMoons() + "" );

        DoTask putTask = new DoTask();
        putTask.execute( pkg );
    }

    protected void updateDisplay() {
        //Use PlanetAdapter to display data
        PlanetAdapter adapter = new PlanetAdapter(this, R.layout.item_planet, planetList);
        setListAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class GetTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(RequestPackage ... params) {

            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null) {
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }

            planetList = PlanetJSONParser.parseFeed(result);
            updateDisplay();
        }
    }

    private class DoTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(RequestPackage ... params) {

            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            pb.setVisibility(View.INVISIBLE);

            if (result == null) {
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }
}
