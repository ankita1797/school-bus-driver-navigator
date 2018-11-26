package databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import bean.BusBean;
import bean.DriverBean;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "BUS_DETAILS";

	// Contacts table name
	private static final String USERS = "Users";

	// Contacts Table Columns names
	private static final String BUS_NUMBER = "BUS_NUMBER";
	private static final String SCHOOL = "SCHOOL";
	private static final String ADDRESS = "ADDRESS";

	 private static final String CREATE_USERS_TABLE = "CREATE TABLE  " + USERS + "("
			+ BUS_NUMBER + " TEXT," + SCHOOL + " TEXT,"
			+ ADDRESS + " TEXT" + ");";
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USERS_TABLE);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + USERS);

		// Create tables again
		onCreate(db);
	}
	 public void addBus (BusBean busBean) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BUS_NUMBER, busBean.getBusNumber());
		values.put(SCHOOL, busBean.getSchool());
		values.put(ADDRESS, busBean.getAddress());
		// Inserting Row
		db.insert(USERS, null, values);
		db.close(); // Closing database connection
	}

	public BusBean getBus (String busNumber) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(USERS, new String[] { BUS_NUMBER,
						SCHOOL, ADDRESS }, BUS_NUMBER +" =?" , new String[] {busNumber}, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
	    BusBean busBean= new BusBean();
		busBean.setBusNumber(cursor.getString(0));
		busBean.setSchool(cursor.getString(1));
		busBean.setAddress(cursor.getString(2));
		// return contact
		return busBean;
	}
	public ArrayList<BusBean> getAllBuses() {
		ArrayList<BusBean> userList = new ArrayList<BusBean>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + USERS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BusBean bus = new BusBean();
				bus.setBusNumber(cursor.getString(0));
				bus.setSchool(cursor.getString(1));
				bus.setAddress(cursor.getString(2));
				// Adding contact to list
				userList.add(bus);
			} while (cursor.moveToNext());
		}
		// return contact list
		return userList;
	}
	public int updateBus(BusBean user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BUS_NUMBER, user.getBusNumber());
		values.put(SCHOOL, user.getSchool());
		values.put(ADDRESS, user.getAddress());

		// updating row
		return db.update(USERS, values, BUS_NUMBER + " = ?",
				new String[] { user.getBusNumber() });
	}
}
