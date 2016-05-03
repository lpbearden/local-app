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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.Models.User;
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
        final Locality locality = (Locality) intent.getSerializableExtra("Location");
        final User setUser = (User) intent.getSerializableExtra("User");

        Log.i("LOCALITY NAME", "onCreate: " + locality.getName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(locality.getName());

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
                        if (position == 1) {
                            Intent details = new Intent(ViewActivity.this, MainActivity.class);
                            startActivity(details);
                        }
                        else if (position == 3) {
                            Toast toast = Toast.makeText(ViewActivity.this, "Senioritis got the better of us.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (position == 4) {
                            Toast toast = Toast.makeText(ViewActivity.this, "Senioritis again!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (position == 5) {
                            Intent details = new Intent(ViewActivity.this, UserListActivity.class);
                            details.putExtra("User", setUser);
                            startActivity(details);
                        }
                        else if (position == 2) {
                            Toast toast = Toast.makeText(ViewActivity.this, "This app has no settings.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
                })
                .build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.drawer_item_settings));


        //Set toolbar color in recent apps so logo doesn't blend in
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription("Local", BitmapFactory.decodeResource(getResources(), R.drawable.large_circle), Color.DKGRAY);
        ((Activity) this).setTaskDescription(taskDescription);


        String imageResource1 = locality.img_paths[0];
        String imageResource2 = locality.img_paths[1];
        String imageResource3 = locality.img_paths[2];
        setImageView(imageResource1, 0);
        setImageView(imageResource2, 1);
        setImageView(imageResource3, 2);

        TextView tv1 = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        TextView tv4 = (TextView) findViewById(R.id.textView4);
        tv1.setText(locality.name);
        tv2.setText(locality.street+ " " + locality.city + ", " + locality.state + " " + locality.zip);
        tv3.setText(locality.description);
        tv4.setText(locality.printTypes());

        Button like_button = (Button) findViewById(R.id.like_button);
        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO", "onItemClick: IN ON CLICK");
                if (v.getId() == R.id.like_button) {
                    if (setUser.user_list_count == 0) {
                        setUser.user_list_count+=1;
                        setUser.user_list[0] = locality;
                        Toast toast = Toast.makeText(ViewActivity.this, "Added to your list.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        for (int i = 0; i < setUser.user_list_count; i++) {
                            if (setUser.user_list[i].name.equals(locality.name)) {
                                Toast toast = Toast.makeText(ViewActivity.this, "Locality is already in your list.", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                setUser.user_list_count+= 1;
                                setUser.user_list[i] = locality;
                                Toast toast = Toast.makeText(ViewActivity.this, "Added to your list.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                }
            }
        });
    }

    private void setImageView(String id, int index) {
        ImageView image_view1 = (ImageView) findViewById(R.id.image_view1);
        ImageView image_view2 = (ImageView) findViewById(R.id.image_view2);
        ImageView image_view3 = (ImageView) findViewById(R.id.image_view3);
        ImageView[] image_views = {image_view1, image_view2, image_view3};
        int resID = getResources().getIdentifier(id, "drawable", "edu.apsu.csci.local_app");
        image_views[index].setImageResource(resID);
    }
}

