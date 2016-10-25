package mx.foodstack.spring;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {
    private Intent mMenuServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMenuServiceIntent = new Intent(this, MenuService.class);
        mMenuServiceIntent.setData(Uri.parse("http://sample.com/"));
        this.startService(mMenuServiceIntent);
        //if (savedInstanceState == null) {
        //    getSupportFragmentManager().beginTransaction()
        //            .add(R.id.container, new PlaceholderFragment())
        //            .commit();
        //}
    }

    @Override
    protected void onStart() {
        super.onStart();
        //new HttpRequestTask().execute();
        //new HttpRequestRestaurantTask().execute();
        new HttpRequestMenuTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();
    //    if (id == R.id.action_refresh) {
    //        new HttpRequestTask().execute();
    //        return true;
    //    }
    //    return super.onOptionsItemSelected(item);
    //}

    /**
     * A placeholder fragment containing a simple view.
     */
    //public static class PlaceholderFragment extends Fragment {

    //    public PlaceholderFragment() {
    //    }

    //    @Override
    //    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    //                             Bundle savedInstanceState) {
    //        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    //        return rootView;
    //    }
    //}


    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            if (greeting == null)
            return;

            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(greeting.getId());
            greetingContentText.setText(greeting.getContent());
        }
    }

    private class HttpRequestMenuTask extends AsyncTask<Void, Void, mx.foodstack.spring.Menu[]> {
        @Override
        protected mx.foodstack.spring.Menu[] doInBackground(Void... params) {
            try {
                final String url = "https://preview.c9users.io/eddiemachete/mixpanel/apple/menus.json";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                mx.foodstack.spring.Menu[] menus = restTemplate.getForObject(url, mx.foodstack.spring.Menu[].class);
                return menus;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return new mx.foodstack.spring.Menu[0];
        }

        @Override
        protected void onPostExecute(mx.foodstack.spring.Menu[] menus) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            greetingIdText.setText(Integer.toString(menus.length));

            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingContentText.setText(Integer.toString(menus[0].getItems().size()));
            //greetingContentText.setText(menus[0].getId());
        }
    }

    private class HttpRequestRestaurantTask extends AsyncTask<Void, Void, Restaurant[]> {
        @Override
        protected Restaurant[] doInBackground(Void... params) {
            try {
                final String url = "https://preview.c9users.io/eddiemachete/mixpanel/apple/restaurants.json";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Restaurant[] restaurants = restTemplate.getForObject(url, Restaurant[].class);
                return restaurants;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return new Restaurant[0];
        }

        @Override
        protected void onPostExecute(Restaurant[] restaurants) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            greetingIdText.setText(Integer.toString(restaurants.length));
        }
    }
}