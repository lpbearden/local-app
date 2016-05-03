package edu.apsu.csci.local_app.Activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.fastadapter.adapters.FooterAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;

import edu.apsu.csci.local_app.Adapters.LocalAdapter;
import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.Models.User;
import edu.apsu.csci.local_app.R;

/**
 * Created by PC on 5/2/2016.
 */
public class UserListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Intent intent = getIntent();
        final User user = (User)intent.getSerializableExtra("User");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My List"); // set the top title


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
                .withSelectedItem(5)
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
                        if (position == 1) {
                            Intent details = new Intent(UserListActivity.this, MainActivity.class);
                            startActivity(details);
                        }
                        else if (position == 3) {
                            Toast toast = Toast.makeText(UserListActivity.this, "Senioritis got the better of us.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (position == 4) {
                            Toast toast = Toast.makeText(UserListActivity.this, "Senioritis again!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (position == 2) {
                            Toast toast = Toast.makeText(UserListActivity.this, "This app has no settings.", Toast.LENGTH_LONG);
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



        //set ListView for localities up
        ListView list_view = (ListView) findViewById(R.id.user_list_view);
        //populate adapter with data



        //set listView adapter
        if (user.user_list_count == 0) {
            Toast toast = Toast.makeText(UserListActivity.this, "No Localities favorited!", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            ArrayList<Locality> arrayList = new ArrayList<Locality>();
            for (int i = 0; i < user.user_list_count; i++) {
                Locality newLocality = new Locality(user.user_list[i].name, user.user_list[i].description, user.user_list[i].street, user.user_list[i].city, user.user_list[i].state, user.user_list[i].zip, user.user_list[i].img_paths, user.user_list[i].types);
                arrayList.add(newLocality);
            }
            LocalAdapter adapter = new LocalAdapter(this, arrayList);
            list_view.setAdapter(adapter);
        }


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Locality locality = user.user_list[position];
                Log.i("LOCALITY NAME", "onItemClick: " + locality.getName());
                Intent details = new Intent(UserListActivity.this, ViewActivity.class);
                details.putExtra("Location", locality);
                details.putExtra("User", user);
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

}
