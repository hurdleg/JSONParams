package com.algonquincollege.hurdleg.planets.parsers;

import com.algonquincollege.hurdleg.planets.model.Planet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse a JSON object for a Planet.
 *
 * //TODO: compare this parser to JSON array: https://planets-hurdleg.mybluemix.net/planets
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 *
 * Reference: FlowerJSONParser in "Connecting Android Apps to RESTful Web Services" with David Gassner
 */
public class PlanetJSONParser {

    public static List<Planet> parseFeed(String content) {

        try {
            JSONArray planetArray = new JSONArray(content);
            List<Planet> planetList = new ArrayList<>();

            for (int i = 0; i < planetArray.length(); i++) {

                JSONObject obj = planetArray.getJSONObject(i);
                Planet planet = new Planet();

                planet.setPlanetId(obj.getInt("planetId"));
                planet.setName(obj.getString("name"));
                planet.setOverview(obj.getString("overview"));
                planet.setImage(obj.getString("image"));
                planet.setDescription(obj.getString("description"));
                planet.setDistanceFromSun(obj.getDouble("distance_from_sun"));
                planet.setNumberOfMoons(obj.getInt("number_of_moons"));

                planetList.add(planet);
            }

            return planetList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
