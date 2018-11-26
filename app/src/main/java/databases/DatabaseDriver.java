package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import bean.DriverBean;

public class DatabaseDriver extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DRIVER_DETAILS";

    private static final String DRIVERS = "DRIVERS";
    private static final String NAME = "NAME";
    private static final String LICENSEID = "LICENSEID";
    private static final  String CONTACT_NO = "CONTACT_NO";
    private static final String PROFILE_PIC_PATH = "PROFILE_PIC_PATH";

    public DatabaseDriver(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DRIVERS_TABLE = "CREATE TABLE  " + DRIVERS + "("
                + NAME + " TEXT," + LICENSEID + " TEXT," + CONTACT_NO + " TEXT," + PROFILE_PIC_PATH + " TEXT "+");";

        sqLiteDatabase.execSQL(CREATE_DRIVERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DRIVERS);

        // Create tables again
        onCreate(sqLiteDatabase);

    }
    public void addDriver (DriverBean driverBean) {
        SQLiteDatabase db1 = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, driverBean.getName());
        values.put(LICENSEID, driverBean.getLicenseId());
        values.put(CONTACT_NO, driverBean.getContactnumber());
        values.put(PROFILE_PIC_PATH, driverBean.getPROFILE_PIC_PATH());


        // Inserting Row
        db1.insert(DRIVERS , null, values);

        db1.close(); // Closing database connection
    }

    public DriverBean getDriver (String licenseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DRIVERS, new String[] { NAME,
                LICENSEID, CONTACT_NO, PROFILE_PIC_PATH}, LICENSEID +" =?" , new String[] {licenseId}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DriverBean driverBean = new DriverBean(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return driverBean;

    }
    public ArrayList<DriverBean> getAllDrivers() {
        ArrayList<DriverBean> driverList = new ArrayList<DriverBean>();
        String selectQuery = "SELECT  * FROM " + DRIVERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DriverBean driverBean = new DriverBean();
                driverBean.setName(cursor.getString(0));
                driverBean.setLicenseId(cursor.getString(1));
                driverBean.setContactnumber(cursor.getString(2));
                driverBean.setPROFILE_PIC_PATH(cursor.getString(3));

                driverList.add(driverBean);
            } while (cursor.moveToNext());
        }
        return driverList;
    }
   /*/ public int updateBus(BusBean user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BUS_NUMBER, user.getBusNumber());
        values.put(SCHOOL, user.getSchool());
        values.put(ADDRESS, user.getAddress());

        // updating row
        return db.update(USERS, values, BUS_NUMBER + " = ?",
                new String[] { user.getBusNumber() });
    }/*/


}
