package iti.team.tablia.others;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaceApi {

  public PlaceApi() {
  }

  private FusedLocationProviderClient fusedLocationProviderClient;

  public ArrayList<String> autocomplete(String input) {

    ArrayList<String> arrayList = new ArrayList();
    HttpURLConnection connection = null;
    StringBuilder jsonResult = new StringBuilder();

    try {
      StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
      sb.append("input=" + input);
      sb.append("&key=AIzaSyDFhO6SEcewKE7jQUjyE-XwqbhlODfObEA");
      URL url = new URL(sb.toString());
      connection = (HttpURLConnection) url.openConnection();
      InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
      int read;
      char[] buff = new char[1024];
      while ((read = inputStreamReader.read(buff)) != -1) {
        jsonResult.append(buff, 0, read);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
        ;
      }
    }

    try {
      JSONObject jsonObject = new JSONObject(jsonResult.toString());
      JSONArray predicions = jsonObject.getJSONArray("predictions");
      for (int i = 0; i < predicions.length(); i++) {
        arrayList.add(predicions.getJSONObject(i).getString("description"));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return arrayList;
  }

  private Address getAddressFromCoordinates(Context context, LatLng latLng) {


    Geocoder geocoder = new Geocoder(context);

    List<Address> addresses;

    try {
      addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
      if (addresses != null) {
        Address address = addresses.get(0);
        return address;
      } else {
        return null;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }

    /*
    public ArrayList<Double> getCurrentLocation(Context context){

        final ArrayList<Double> arr = new ArrayList<>();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        Task location = fusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful()){
                    Log.d("Debug", "Location found");
                    Location currentLocation = (Location) task.getResult();

                    arr.add(currentLocation.getLatitude());
                    arr.add(currentLocation.getLongitude());


                }else{

                    Log.d("Debug", "Location NOT found");
                }
            }
        });

        return arr;
    }



    public LatLng getCoordinatesFromAddress(Context context, String address){

        Geocoder geocoder = new Geocoder(context);

        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address,1);
            if(address != null){
                Address singleAddress = addressList.get(0);
                LatLng latLng = new LatLng(singleAddress.getLatitude(), singleAddress.getLongitude());
                return latLng;
            }else {

                return  null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


//Try permissions



     */


}
