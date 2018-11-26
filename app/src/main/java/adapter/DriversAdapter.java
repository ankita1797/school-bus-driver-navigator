package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import databases.DatabaseDriver;

import com.example.ankita.internshipapplication.R;

import java.io.File;
import java.util.List;


import bean.DriverBean;

public class DriversAdapter extends BaseAdapter implements Filterable {
    List<DriverBean> listDrivers;
    private Context con;
    private static LayoutInflater inflater;
    private  Bitmap bitmap;
    private DatabaseDriver databaseDriver;

    public DriversAdapter(List<DriverBean> listDrivers, Context con) {
        this.listDrivers = listDrivers;
        this.con = con;
        inflater =(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public int getCount() {
        return listDrivers.size();
    }

    @Override
    public Object getItem(int i) {
        return listDrivers.get(i) ;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=inflater.inflate(R.layout.driver_listview,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_license= (TextView) view.findViewById(R.id.tv_license);
        TextView tv_contact= (TextView) view.findViewById(R.id.tv_contact);
        ImageView profile= (ImageView) view.findViewById(R.id.profile);

        tv_name.setText("Name: "+ listDrivers.get(i).getName());
        tv_license.setText("License Id: "+ listDrivers.get(i).getLicenseId());
        tv_contact.setText("Contact Number: "+ listDrivers.get(i).getContactnumber());
        databaseDriver= new DatabaseDriver(con);
        String path= listDrivers.get(i).getPROFILE_PIC_PATH();
        try
        {
            File imgFile = new  File(path);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),bmOptions);
            if(bitmap==null)
            {
                bitmap=BitmapFactory.decodeResource(con.getResources(),R.drawable.user_default);
                bitmap= getCircularBitmapWithWhiteBorder(bitmap,0);
                profile.setImageBitmap(bitmap);
            }
            else {
                bitmap = Bitmap.createBitmap(bitmap);
                bitmap= getCircularBitmapWithWhiteBorder(bitmap,0);
                profile.setImageBitmap(bitmap);
            }
        }
        catch (Exception e)
        {
            bitmap=BitmapFactory.decodeResource(con.getResources(),R.drawable.user_default);

        }
        return  view;

    }

    @Override
    public Filter getFilter() {
        return null;
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


