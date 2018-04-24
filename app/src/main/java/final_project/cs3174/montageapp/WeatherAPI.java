package final_project.cs3174.montageapp;

import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shawn on 4/23/2018.
 */

public class WeatherAPI implements Response.Listener<String>, Response.ErrorListener
{
    private static final String requestURL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String apiKey = "524e7a9f4926ec7c019a9aa6d6050393";

    MainActivity mainActivity;
    RequestQueue requestQueue;

    public WeatherAPI(MainActivity ma)
    {
        this.mainActivity = ma;
        this.requestQueue = Volley.newRequestQueue(ma);
    }

    public void getWeatherData(Location location)
    {
        StringRequest request = new StringRequest(Request.Method.GET,
                getRequestQuery(location),this, this);
        this.requestQueue.add(request);
    }

    private String getRequestQuery(Location location)
    {
        return (requestURL + "?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + apiKey);
    }

    @Override
    public void onResponse(String response)
    {
        Log.d("Response", response);
        try
        {
            JSONObject jObj = new JSONObject(response);
            JSONArray weatherData = jObj.getJSONArray("weather");
            JSONObject jsonObject = weatherData.getJSONObject(0);
            String weather = jsonObject.getString("main");
            Log.d("Weather", weather);
            mainActivity.logWeather(weather);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        Log.e("VolleyError", error.getMessage());
    }
}
