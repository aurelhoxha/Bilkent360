package aurelhoxha.bilkent360.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import aurelhoxha.bilkent360.R;
import aurelhoxha.bilkent360.fragments.BuildingsFragment;
import aurelhoxha.bilkent360.fragments.CalendarFragment;
import aurelhoxha.bilkent360.fragments.ScheduleFragment;
import aurelhoxha.bilkent360.fragments.ShareFragment;
import aurelhoxha.bilkent360.fragments.TaskFragment;
import aurelhoxha.bilkent360.other.Building;


public class MenuActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private DatabaseReference mDatabase;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_SCHEDULE = "Schedule";
    private static final String TAG_SHARE = "Share";
    private static final String TAG_CALENDAR = "Calendar";
    private static final String TAG_TASK = "Tasks";
    private static final String TAG_BUILDINGS = "Buildings";
    public static String CURRENT_TAG = TAG_SCHEDULE;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_SCHEDULE;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Bilkent360");
        txtWebsite.setText("");

        // showing dot next to Share Bilkent label
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // Schedule
                ScheduleFragment scheduleFragment = new ScheduleFragment();
                return scheduleFragment;
            case 1:
                // Share
                ShareFragment shareFragment = new ShareFragment();
                return shareFragment;
            case 2:
                // Calendar fragment
                CalendarFragment calendarFragment = new CalendarFragment();
                return calendarFragment;
            case 3:
                // Task fragment
                TaskFragment taskFragment = new TaskFragment();
                return taskFragment;

            case 4:
                // Building fragment
                BuildingsFragment buildingsFragment = new BuildingsFragment();
                return buildingsFragment;
            default:
                return new ScheduleFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_schedule:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_SCHEDULE;
                        break;
                    case R.id.nav_share:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_SHARE;
                        break;
                    case R.id.nav_calendar:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CALENDAR;
                        break;
                    case R.id.nav_task:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_TASK;
                        break;
                    case R.id.nav_buildings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_BUILDINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MenuActivity.this, AboutUs.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_log_out:
                        // launch new intent instead of loading fragment
                        FirebaseAuth user = FirebaseAuth.getInstance();
                        user.signOut();
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_SCHEDULE;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        // show schedule menu when the Schedule is selected
        if (navItemIndex == 0 )
            getMenuInflater().inflate(R.menu.schedule_menu, menu);
        // show menu only when blog fragment is selected
        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.blog, menu);
        }
        // show menu only when calendar fragment is selected
        if (navItemIndex == 2) {
            getMenuInflater().inflate(R.menu.calendar, menu);
        }
        // show menu only when buildings fragment is selected
        if (navItemIndex == 4) {
            getMenuInflater().inflate(R.menu.buildings, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //ADD BLOG ON SHARE
        if (id == R.id.action_add_blogs) {
            Intent myIntent = new Intent(getApplicationContext(), AddNewBlog.class);
            startActivityForResult(myIntent,1);

        }

        //ADD EVENT ON CALENDAR
        if (id == R.id.action_add_event) {
            addEventMethod();

        }

        //ADD BUILDINGS ON BUILDINGS
        if (id == R.id.action_add_buildings) {
            addBuildingsMethod();
        }

        if(id == R.id.action_manage_schedule)
        {
            Intent myIntent = new Intent(getApplicationContext(), Schedule.class);
            startActivityForResult(myIntent,1);
        }

        return super.onOptionsItemSelected(item);
    }

    private void addEventMethod() {

        Intent myIntent = new Intent(getApplicationContext(), ProcessCalendar.class);
        startActivityForResult(myIntent,1);

    }


    private void addBuildingsMethod() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_dialog_buildings,null);

        final EditText mName = (EditText) mView.findViewById(R.id.etName);
        final EditText mLat = (EditText) mView.findViewById(R.id.etLatitude);
        final EditText mLong = (EditText) mView.findViewById(R.id.etLongitude);
        Button mAdd = (Button) mView.findViewById(R.id.btnAddBuilding);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String databaseName;
                double databaseLat;
                double databaseLong;
                mDatabase = FirebaseDatabase.getInstance().getReference("RequestedBuildings");
                if( !mName.getText().toString().isEmpty() && !mLat.getText().toString().isEmpty() && !mLong.getText().toString().isEmpty())
                {
                    databaseName = mName.getText().toString();
                    databaseLat  = Double.parseDouble(mLat.getText().toString());
                    databaseLong  = Double.parseDouble(mLong.getText().toString());

                    Building myBuilding = new Building(databaseName,databaseLat,databaseLong);

                    mDatabase.child(databaseName).setValue(myBuilding);

                    Toast.makeText(getApplicationContext(), "Building Requested Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Fill Fields Correct!", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
