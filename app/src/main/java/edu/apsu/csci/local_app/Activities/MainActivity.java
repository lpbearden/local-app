package edu.apsu.csci.local_app.Activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.apsu.csci.local_app.Adapters.LocalAdapter;
//import edu.apsu.csci.local_app.Adapters.LocalCursorAdapter;
//import edu.apsu.csci.local_app.Data.LocalDatabaseHelper;
import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.Models.User;
import edu.apsu.csci.local_app.R;

public class MainActivity extends AppCompatActivity  {
    private ArrayList<Locality> arrayOfLocalities = new ArrayList();
    private User mainUser = new User(1, "lpbearden", "Lucas", "Bearden", "password", "lpbearden@gmail.com", "test");
    private User testUser = new User(2, "janicholson", "John", "Nicholson", "password", "nicholsonja@apsu.edu", "nicholson");
    public User setUser = mainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Localities"); // set the top title


        final IProfile profile1 = new ProfileDrawerItem().withName("Lucas Bearden").withEmail("lpbearden@gmail.com").withIcon(R.drawable.test).withIdentifier(101);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(true)
                .addProfiles(
                        profile1
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                       return false;
                    }
                })
                .build();


        new DrawerBuilder().withActivity(this).build();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withSelectedItem(0)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_search),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_cities),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_list)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 3) {
                            Toast toast = Toast.makeText(MainActivity.this, "Senioritis got the better of us.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (position == 4) {
                            Toast toast = Toast.makeText(MainActivity.this, "Senioritis again!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (position == 5) {
                            Intent details = new Intent(MainActivity.this, UserListActivity.class);
                            details.putExtra("User", setUser);
                            startActivity(details);
                        }
                        else if (position == 2) {
                            Toast toast = Toast.makeText(MainActivity.this, "This app has no settings.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
                })
                .build();



        result.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.drawer_item_settings));

        //Set toolbar color in recent apps so logo doesn't blend in
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription("Local", BitmapFactory.decodeResource(getResources(), R.drawable.large_circle), Color.DKGRAY);
        ((Activity)this).setTaskDescription(taskDescription);



        //region Sample Data
        //SAMPLE DATA
        String [] img_paths = new String[5];
        img_paths[0] = "bpub_brewery1";
        img_paths[1] = "bpub_brewery2";
        img_paths[2] = "bpub_brewery3";

        String [] types = new String[5];
        types[0] = "bar";
        types[1] = "restaurant";

        Locality sampleLocality = new Locality("Blackhorse Pub & Brewery", "Local pub with great brews and pub fare!", "123 Somewhere Dr.", "Clarksville", "TN", "37040", img_paths, types);
        arrayOfLocalities.add(sampleLocality);

        addLocality("Miss Lucille's Marketplace", "Antiques & Thrift locally sourced in Clarksville, with a delicious cafe!", "123 Somewhere Dr.", "Clarksville", "TN", "37040", "miss_lucilles", "miss_lucilles1", "miss_lucilles2", "restaurant", "shop");
        addLocality("The Gilroy Neighborhood Pub", "College bar with trivia every Thursday! Normal pub far.", "123 Somewhere Dr.", "Clarksville", "TN", "37040", "gilroy1", "gilroy2", "gilroy3", "restaurant", "bar");
        addLocality("Section 125", "A great place to grab a beer and eat some wings.", "125 Franklin St.", "Clarksville", "TN", "37040", "section_125_1", "section_125_2", "section_125_3", "restaurant", "bar");
        addLocality("Freeze Pleeze", "A local ice cream joint that makes homemade ice cream right in front of you with liquid nitrogen and fresh ingredients.", "652 N. Riverside Dr.", "Clarksville", "TN", "37040", "freeze_pleeze_1", "freeze_pleeze_2", "freeze_pleeze_3", "restaurants", "");
        addLocality("Elite Wine and Spirits", "Just that.", "1875 Madison St.", "Clarksville, TN", "37040", "elite_1", "elite_2", "elite_3", "shops", "alcohol", "");
        addLocality("Johnny’s Big Burger", "Best burger in town and you can’t beat a honey bun and ice cream.", "428 College St.", "Clarksville", "TN", "37040", "johnnys_1", "johnnys_2", "johnnys_3", "restaurants", "");
        //endregion

        //set ListView for localities up
        final ListView list_view = (ListView) findViewById(R.id.listView);

        //populate adapter with data
        LocalAdapter adapter = new LocalAdapter(this, arrayOfLocalities);

        //set listView adapter
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                     int position, long id) {
                    Locality locality = arrayOfLocalities.get(position);
                    Log.i("LOCALITY NAME", "onItemClick: " + locality.getName());
                    Intent details = new Intent(MainActivity.this, ViewActivity.class);
                    details.putExtra("Location", locality);
                    details.putExtra("User", setUser);
                    startActivity(details);
            }
        });

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
