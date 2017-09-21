package com.algonquincollege.hurdleg.planets.model;

/**
 * Model a planet of our solar system.
 *
 * A Planet has the following properties:
 *   planetId
 *   name
 *   overview
 *   image
 *   description
 *   distance from the Sun
 *   number of moons
 *
 * //TODO: compare this Java class to JSON object: https://planets.mybluemix.net/planets/2
 *
 * Pro-Tip: I used Android Studio IDE to generate the getters & setters. For details:
 *          Canvas > Modules > Resources > How to Generate Getters and Setters
 *
 * @author Gerald.Hurdle@AlgonquinCollege.com
 */
public class Planet {
    private int planetId;
    private String name;
    private String overview;
    private String image;
    private String description;
    private double distanceFromSun;
    private int numberOfMoons;

    public int getPlanetId() { return planetId; }
    public void setPlanetId(int planetId) { this.planetId = planetId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getDistanceFromSun() { return distanceFromSun; }
    public void setDistanceFromSun(double distanceFromSun) { this.distanceFromSun = distanceFromSun; }

    public int getNumberOfMoons() { return numberOfMoons;}
    public void setNumberOfMoons(int numberOfMoons) { this.numberOfMoons = numberOfMoons; }
}
