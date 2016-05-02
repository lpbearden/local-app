package edu.apsu.csci.local_app.Activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.R;

/**
 * Created by lpbearden on 5/2/16.
 */
public class ViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        Locality locality = (Locality)intent.getSerializableExtra("Location");

        Log.i("LOCALITY NAME", "onCreate: " + locality.getName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(locality.getName());

        final IProfile profile1 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.test).withIdentifier(101);
        final IProfile profile2 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(R.drawable.nicholson).withIdentifier(102);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(true)
                .addProfiles(
                        profile1,
                        profile2
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
                .withSelectedItem(-1)
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
                        return true;
                    }
                })
                .build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.drawer_item_settings));


        //Set toolbar color in recent apps so logo doesn't blend in
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription("Local", BitmapFactory.decodeResource(getResources(), R.drawable.large_circle), Color.DKGRAY);
        ((Activity)this).setTaskDescription(taskDescription);



        String imageResource1 = locality.img_paths[0];
        String imageResource2 = locality.img_paths[1];
        String imageResource3 = locality.img_paths[2];
        setImageView(imageResource1, 0);
        setImageView(imageResource2, 1);
        setImageView(imageResource3, 2);





    }

    private void setImageView(String id, int index) {
        ImageView image_view1 = (ImageView) findViewById(R.id.image_view1);
        ImageView image_view2 = (ImageView) findViewById(R.id.image_view2);
        ImageView image_view3 = (ImageView) findViewById(R.id.image_view3);
        ImageView [] image_views = {image_view1, image_view2, image_view3};
        int resID = getResources().getIdentifier(id, "drawable", "edu.apsu.csci.local_app");
        image_views[index].setImageResource(resID);
    }
}
