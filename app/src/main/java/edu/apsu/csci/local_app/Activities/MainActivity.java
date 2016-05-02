package edu.apsu.csci.local_app.Activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import edu.apsu.csci.local_app.Adapters.LocalAdapter;
//import edu.apsu.csci.local_app.Adapters.LocalCursorAdapter;
//import edu.apsu.csci.local_app.Data.LocalDatabaseHelper;
import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.Models.User;
import edu.apsu.csci.local_app.R;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Locality> arrayOfLocalities = new ArrayList();
    private User mainUser = new User(1, "lpbearden", "Lucas", "Bearden", "password", "test");
    private User testUser = new User(2, "janicholson", "John", "Nicholson", "password", "nicholson");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Local"); // set the top title

        new DrawerBuilder().withActivity(this).build();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home);
        PrimaryDrawerItem item2 = new SecondaryDrawerItem().withName(R.string.drawer_item_settings);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return true;
                    }
                })
                .build();

        //Set toolbar color in recent apps so logo doesn't blend in
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription("Local", BitmapFactory.decodeResource(getResources(), R.drawable.large_circle), Color.DKGRAY);
        ((Activity)this).setTaskDescription(taskDescription);



        //region Sample Data
        //SAMPLE DATA
        String [] img_paths = new String[5];
        img_paths[0] = "bpub_brewery1";
        img_paths[1] = "bpub_brewery2";

        String [] types = new String[5];
        types[0] = "bar";
        types[1] = "restaurant";

        Locality sampleLocality = new Locality("Blackhorse Pub & Brewery", "Local pub with great brews and pub fare!", "123 Somewhere Dr.", "Clarksville", "TN", "37040", img_paths, types);
        arrayOfLocalities.add(sampleLocality);

        addLocality("Miss Lucille's Marketplace", "Antiques & Thrift locally sourced in Clarksville, with a delicious cafe!", "123 Somewhere Dr.", "Clarksville", "TN", "37040", "miss_lucilles", "miss_lucilles1", "miss_lucilles2", "restaurant", "shop");
        addLocality("The Gilroy Neighborhood Pub", "College bar with trivia every Thursday! Normal pub far.", "123 Somewhere Dr.", "Clarksville", "TN", "37040", "gilroy1", "gilroy2", "gilroy3", "restaurant", "bar");
        //endregion

        //set ListView for localities up
        ListView list_view = (ListView) findViewById(R.id.listView);
        //populate adapter with data
        LocalAdapter adapter = new LocalAdapter(this, arrayOfLocalities);
        //set listView adapter
        list_view.setAdapter(adapter);

        //region Database Stuff
        //LocalDatabaseHelper helper = LocalDatabaseHelper.getsInstance(this);
        //helper.addOrUpdateLocality(sampleLocality);
        //create adapter instance and set arrayList to the adapter
        //ArrayList<Locality> arrayOfLocalities = (ArrayList<Locality>)helper.getAllLocalities();
        //endregion
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

    private void addLocality(String name, String description, String street, String city, String state, String zip, String img1, String img2, String img3, String type1, String type2) {
        String [] img_paths = new String[5];
        img_paths[0] = img1;
        img_paths[1] = img2;
        img_paths[2] = img3;

        String [] types = new String[5];
        types[0] = type1;
        types[1] = type2;

        Locality sampleLocality = new Locality(name, description, street, city, state, zip, img_paths, types);
        arrayOfLocalities.add(sampleLocality);
    }


}
