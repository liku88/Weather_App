package in.mangaldeep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  TextView result;
  EditText cityname;
  Button button;
    private RequestQueue requestQueue;

//    https://api.openweathermap.org/data/2.5/weather?q=Bhubaneswar&appid=108b491584680c467961749f8c9cbec0

  String BaseUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
  String API =  "&appid=108b491584680c467961749f8c9cbec0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        cityname = findViewById(R.id.cityName);
        button = findViewById(R.id.button);



//        String mainUrl = BaseUrl + city + API;

        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendApiRequest();
            }
        });

    }
    private  void sendApiRequest(){
        String city = cityname.getText().toString();
        String realApi = BaseUrl + city + API;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                realApi,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("weather");
                            for (int i=0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String weather = jsonObject.getString("main");
                                String description = jsonObject.getString("description");
                                result.setText(weather+", "+description);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}