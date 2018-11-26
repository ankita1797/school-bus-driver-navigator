package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.util.ArrayList;

import bean.DriverBean;
import bean.StoppageBean;

public class DatabaseStoppage extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "STOPPAGE_DETAILS";

    private static final String STOPPAGE = "STOPPAGE";
    private static final String PLACE = "PLACE";
    private static final String ADDRESS = "ADDRESS";


    public DatabaseStoppage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DRIVERS_TABLE = "CREATE TABLE  " + STOPPAGE + "("
                + PLACE + " TEXT," + ADDRESS + " TEXT "+");";

        sqLiteDatabase.execSQL(CREATE_DRIVERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STOPPAGE);

        // Create tables again
        onCreate(sqLiteDatabase);

    }
    public void addStoppage (StoppageBean stoppageBean) {
        SQLiteDatabase db1 = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLACE, stoppageBean.getPlace());
        values.put(ADDRESS, stoppageBean.getAddress());
        // Inserting Row
        db1.insert(STOPPAGE , null, values);

        db1.close(); // Closing database connection
    }

    public StoppageBean getStoppage (String place) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(STOPPAGE, new String[] { PLACE,
                ADDRESS}, PLACE +" =?" , new String[] {place}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        StoppageBean stoppageBean= new StoppageBean();
        stoppageBean.setPlace(cursor.getString(0));
        stoppageBean.setAddress(cursor.getString(1));

        return stoppageBean;

    }
    public ArrayList<StoppageBean> getAllStoppages() {
        ArrayList<StoppageBean> stoppageList = new ArrayList<StoppageBean>();
        String selectQuery = "SELECT  * FROM " + STOPPAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                StoppageBean stoppageBean= new StoppageBean();
                stoppageBean.setPlace(cursor.getString(0));
                stoppageBean.setAddress(cursor.getString(1));


               stoppageList.add(stoppageBean);
            } while (cursor.moveToNext());
        }
        return stoppageList;
    }
    public void deleteStoppage(StoppageBean stoppageBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STOPPAGE,  PLACE+ " = ?",
                new String[] { String.valueOf(stoppageBean.getPlace()) });
        db.close();
    }
}
