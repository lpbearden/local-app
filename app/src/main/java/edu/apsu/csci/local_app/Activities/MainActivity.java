package edu.apsu.csci.local_app.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.apsu.csci.local_app.Adapters.LocalAdapter;
import edu.apsu.csci.local_app.Adapters.LocalCursorAdapter;
import edu.apsu.csci.local_app.Data.LocalDatabaseHelper;
import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Local"); // set the top title

        LocalDatabaseHelper helper = LocalDatabaseHelper.getsInstance(this);
        ListView list_view = (ListView) findViewById(R.id.listView);



        //SAMPLE DATA
        ArrayList<String> img_paths = new ArrayList<>();
        img_paths.add("bpub_brewery1.jpg");
        img_paths.add("bpub_brewery2.jpg");

        ArrayList<String> types = new ArrayList<>();
        types.add("bar");
        types.add("restaurant");

        Locality sampleLocality = new Locality();
        sampleLocality.name = "Blackhorse Pub & Brewery";
        sampleLocality.description = "Local pub with great brews and pub fare!";
        sampleLocality.street = "123 Somewhere Dr";
        sampleLocality.city = "Clarksville";
        sampleLocality.state = "TN";
        sampleLocality.zip = "37040";
        sampleLocality.img_paths = img_paths;
        sampleLocality.types = types;


        Log.i("DATABASE", "BEFORE ADD OR UPDATE CALL");
        helper.addOrUpdateLocality(sampleLocality);
        Log.i("DATABASE", "AFTER ADD OR UPDATE CALL");


        //create adapter instance and set arrayList to the adapter
        ArrayList<Locality> arrayOfLocalities = (ArrayList<Locality>)helper.getAllLocalities();
        LocalAdapter adapter = new LocalAdapter(this, arrayOfLocalities);

        list_view.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
