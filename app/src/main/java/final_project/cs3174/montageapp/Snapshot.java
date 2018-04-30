package final_project.cs3174.montageapp;

/**
 * Created by Shawn on 4/19/2018.
 */

public class Snapshot {
    // The string of the photo's name so that it can be pulled from app's internal storage (also represent's the photo's date)
    String photoName;
    // The street address returned by the geocoder for this picture's location
    String location;
    // The string input by the user in the confirm photo screen
    String mood;
    // The weather during the time the photo was taken
    String weather;

    public Snapshot() {
        // initialize all fields
        this.photoName = "";
        this.location = "";
        this.mood = "";
        this.weather = "";
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
