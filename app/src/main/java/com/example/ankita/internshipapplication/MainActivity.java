package com.example.ankita.internshipapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import bean.DriverBean;
import fragmentdrawer.AddBusFragment;
import fragmentdrawer.DriversListFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;
    String licenseid,picPath, school,name;
    DriverBean driverBean;
    TextView drivername;
    FragmentTransaction myFragmentTransaction;
    private Toolbar toolbar;
    private ImageView image;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("user_login", MODE_PRIVATE);
        licenseid=prefs.getString("licenseid",null);
        school=prefs.getString("school",null);
        picPath=prefs.getString("picPath",null);
        name=prefs.getString("name",null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater= (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.nav_header, null);
        image= (ImageView) view.findViewById (R.id.driverimage);
        drivername= (TextView) view.findViewById(R.id.tvdrivername);
        drivername.setText(name);
        try {
            File imgFile = new File(picPath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bus);
                bitmap = getCircularBitmapWithWhiteBorder(bitmap, 0);
                image.setImageBitmap(bitmap);
            } else {
                bitmap = Bitmap.createBitmap(bitmap);
                bitmap = getCircularBitmapWithWhiteBorder(bitmap, 0);
                image.setImageBitmap(bitmap);
            }
        }
        catch (Exception e)
        {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bus);
        }

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer);
        myNavigationView.addHeaderView(view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
            /**
             * Lets inflate the very first fragment
             * Here , we are inflating the HomeFragment as the first Fragment
             */

            myFragmentManager = getSupportFragmentManager();
            myFragmentTransaction = myFragmentManager.beginTransaction();
            myFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
            /**
             * Setup click events on the Navigation View Items.
             */
            myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                    myDrawerLayout.closeDrawers();


                    if (selectedMenuItem.getItemId() == R.id.nav_item_home) {
                        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

                    }
                    if (selectedMenuItem.getItemId() == R.id.nav_item_addbus) {
                        FragmentTransaction xfragmentTransaction = myFragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.containerView, new AddBusFragment(MainActivity.this, school)).commit();
                    }
                    if (selectedMenuItem.getItemId() == R.id.nav_item_listdriver) {
                        FragmentTransaction xfragmentTransaction = myFragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.containerView, new DriversListFragment(MainActivity.this)).commit();
                    }
                    return false;
                }

            });

            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.app_name,
                    R.string.app_name);

            myDrawerLayout.setDrawerListener(mDrawerToggle);

            mDrawerToggle.syncState();

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new HelpFragment()).commit();

        }
        if (id == R.id.action_log_out){
            SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
            editor.putBoolean("isLogin", false);
            editor.putString("username",null);
            editor.putString("type",null);
            editor.putString("name",null);
            editor.commit();
            startActivity(new Intent(MainActivity.this,OTPActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,
                                                          int borderWidth) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
        return canvasBitmap;
    }
}