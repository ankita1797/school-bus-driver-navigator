package com.example.ankita.internshipapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.DriversAdapter;
import bean.DriverBean;
import databases.DatabaseDriver;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView tv_signup, tv_login;
    EditText driverName, driverLicense, driverMobile;
    Button btn_addprofile_pic, btn_signup, btn_login;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private DriverBean driverBean = new DriverBean();
    String pinview,school;
    private List<DriverBean> listDrivers;
    private Bitmap bitmap;
    private DatabaseDriver databaseHandler;
    int c;
    ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pinview = getIntent().getExtras().getString("pinview");
        driverName = findViewById(R.id.driverName);
        driverMobile = findViewById(R.id.driverMobile);
        driverLicense = findViewById(R.id.driverLicense);
        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);
        btn_addprofile_pic = findViewById(R.id.btn_profile_pic);
        profilepic = findViewById(R.id.profile_pic);
        tv_signup = findViewById(R.id.tv_signup);
        tv_login = findViewById(R.id.tv_login);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        if(pinview.equals("1234")) {
            List<String> categories = new ArrayList<String>();
            categories.add("Royal Global School");
            categories.add("Army Public School Narangi");
            categories.add("Clay and Crayons");
            categories.add("Delhi Public School");
            categories.add("Maria's Public School");
            categories.add("Holy Child School ");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }
      else if(pinview.equals("1235")) {
            List<String> categories = new ArrayList<String>();
            categories.add("KV Khanapara");
            categories.add("Sarla Birla");
            categories.add("Srimanta Sankardev Academy");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }


        tv_signup.setOnClickListener(this);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_addprofile_pic.setVisibility(View.GONE);
                profilepic.setVisibility(View.GONE);
                btn_signup.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                tv_signup.setVisibility(View.VISIBLE);
                tv_login.setVisibility(View.GONE);
            }
        });
        btn_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_addprofile_pic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        school = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onClick(View view) {
        String s1 = driverName.getText().toString();
        String s2 = driverLicense.getText().toString();
        String s3 = driverMobile.getText().toString();
        if (view.getId() == R.id.tv_signup) {
            btn_addprofile_pic.setVisibility(View.VISIBLE);
            profilepic.setVisibility(View.VISIBLE);
            btn_signup.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
            tv_signup.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);
        }
        if (view.getId() == R.id.btn_signup) {

            if (s1.equals("") || s2.equals("") || s3.equals("")) {
                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_LONG).show();
            }
            databaseHandler = new DatabaseDriver(LoginActivity.this);
            listDrivers = databaseHandler.getAllDrivers();
            if (listDrivers.size() > 0) {
                for (int i = 0; i < listDrivers.size(); i++) {
                    if (s2.equals(listDrivers.get(i).getLicenseId().toString())) {
                        Toast.makeText(LoginActivity.this, "Your liense id is already logged in", Toast.LENGTH_LONG).show();
                        c++;
                    }
                }
                if (c == 0) {
                    driverBean.setName(s1);
                    driverBean.setLicenseId(s2);
                    driverBean.setContactnumber(s3);

                    try {
                        databaseHandler.addDriver(driverBean);
                        Toast.makeText(LoginActivity.this, "Sucessfully added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                        editor.putBoolean("isLogin", true);
                        editor.putString("licenseid", driverBean.getLicenseId());
                        editor.putString("picPath", driverBean.getPROFILE_PIC_PATH());
                        editor.putString("school", school);
                        editor.putString("name", driverBean.getName());
                        editor.commit();
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG).show();
                    }

                }
                listDrivers = databaseHandler.getAllDrivers();
            } else {
                driverBean.setName(s1);
                driverBean.setLicenseId(s2);
                driverBean.setContactnumber(s3);

                try {
                    databaseHandler.addDriver(driverBean);
                    Toast.makeText(LoginActivity.this, "Sucessfully added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("licenseid", driverBean.getLicenseId());
                    editor.putString("picPath", driverBean.getPROFILE_PIC_PATH());
                    editor.putString("school", school);
                    editor.putString("name", driverBean.getName());
                    editor.commit();
                    startActivity(intent);
                    finish();


                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG).show();
                }
                listDrivers = databaseHandler.getAllDrivers();
            }
        }
        if(view.getId()==R.id.btn_login)
        {if (s1.equals("") || s2.equals("")|| s3.equals("")) {
            Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_LONG).show();
        } else {
             databaseHandler = new DatabaseDriver(LoginActivity.this);
            try {
                driverBean= databaseHandler.getDriver(s2);
                if (s1.equals(driverBean.getName())&&s3.equals(driverBean.getContactnumber())) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("licenseid", driverBean.getLicenseId());
                    editor.putString("picPath", driverBean.getPROFILE_PIC_PATH());
                    editor.putString("school", school);
                    editor.putString("name", driverBean.getName());
                    editor.commit();
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "User InValid", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            }
        }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(LoginActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        String pic_path = destination.getAbsolutePath();
        driverBean.setPROFILE_PIC_PATH(pic_path);
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File imgFile = new File(driverBean.getPROFILE_PIC_PATH());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(imgFile.getPath(), bmOptions);
        } catch (Exception e) {
            bitmap = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.user_default);

        }

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.user_default);
            bitmap = getCircularBitmapWithWhiteBorder(bitmap, 0);
            profilepic.setImageBitmap(bitmap);
        } else {
            bitmap = Bitmap.createBitmap(bitmap);
            bitmap = getCircularBitmapWithWhiteBorder(bitmap, 0);
            profilepic.setImageBitmap(bitmap);
        }
        System.out.println(pic_path);

    }

    private void onSelectFromGalleryResult(Intent data) {

        Uri uri = data.getData();
        File imageFile = new File(getRealPathFromURI(uri));
        String pic_path = imageFile.getAbsolutePath();
        driverBean.setPROFILE_PIC_PATH(pic_path);
        try {
            File imgFile = new File(driverBean.getPROFILE_PIC_PATH());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
        } catch (Exception e) {
            bitmap = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.user_default);

        }

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.user_default);
            bitmap = getCircularBitmapWithWhiteBorder(bitmap, 0);
            profilepic.setImageBitmap(bitmap);
        } else {
            bitmap = Bitmap.createBitmap(bitmap);
            bitmap = getCircularBitmapWithWhiteBorder(bitmap, 0);
            profilepic.setImageBitmap(bitmap);
        }


    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;

        Cursor cursor = LoginActivity.this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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

