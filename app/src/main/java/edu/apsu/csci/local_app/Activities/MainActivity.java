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
//import edu.apsu.csci.local_app.Adapters.LocalCursorAdapter;
//import edu.apsu.csci.local_app.Data.LocalDatabaseHelper;
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

        //LocalDatabaseHelper helper = LocalDatabaseHelper.getsInstance(this);
        ListView list_view = (ListView) findViewById(R.id.listView);
        ArrayList<Locality> arrayOfLocalities = new ArrayList();


        //SAMPLE DATA
        String [] img_paths = new String[5];
        img_paths[0] = "bpub_brewery1";
        img_paths[1] = "bpub_brewery2";

        String [] types = new String[5];
        types[0] = "bar";
        types[1] = "restaurant";

        Locality sampleLocality = new Locality("Blackhorse Pub & Brewery", "Local pub with great brews and pub fare!", "123 Somewhere Dr.", "Clarksville", "TN", "37040", img_paths, types);
        arrayOfLocalities.add(sampleLocality);

        String [] img_paths1 = new String[5];
        img_paths1[0] = "miss_lucilles";
        img_paths1[1] = "miss_lucilles1";
        img_paths1[2] = "miss_lucilles2";

        String [] types1 = new String[5];
        types1[0] = "shop";
        types1[1] = "restaurant";

        sampleLocality = new Locality("Miss Lucille's Marketplace", "Antiques & Thrift locally sourced in Clarksville, with a delicious cafe!", "123 Somewhere Dr.", "Clarksville", "TN", "37040", img_paths1, types1);
        arrayOfLocalities.add(sampleLocality);

        String [] img_paths2 = new String[5];
        img_paths2[0] = "gilroy1";
        img_paths2[1] = "gilroy2";
        img_paths2[2] = "gilroy3";


        String [] types2 = new String[5];
        types2[0] = "bar";
        types2[1] = "restaurant";

        sampleLocality = new Locality("The Gilroy Neighborhood Pub", "College bar with trivia every Thursday! Normal pub far.", "123 Somewhere Dr.", "Clarksville", "TN", "37040", img_paths2, types2);
        arrayOfLocalities.add(sampleLocality);

        LocalAdapter adapter = new LocalAdapter(this, arrayOfLocalities);
        list_view.setAdapter(adapter);


        //helper.addOrUpdateLocality(sampleLocality);
        //create adapter instance and set arrayList to the adapter
        //ArrayList<Locality> arrayOfLocalities = (ArrayList<Locality>)helper.getAllLocalities();
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
