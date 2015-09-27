package niceandroid.net.androidsqlite;

        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.Date;
        import java.text.DecimalFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;
        import java.util.Locale;
        import java.util.TimeZone;

        import org.wikispeedia.backseatdriver.AppPreferences;
        import org.wikispeedia.backseatdriver.Global;
        import org.wikispeedia.backseatdriver.Settings;
        import org.wikispeedia.speedlimit.Box;
        import org.wikispeedia.speedlimit.Signs;

        import android.annotation.SuppressLint;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.text.format.DateFormat;
        import android.util.Log;

/**
 *
 * @author 9android.net
 *
 */
@SuppressLint("SimpleDateFormat")
public class DatabaseHandler_east extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    // All Static variables
    // Database Version
    //private static final int DATABASE_VERSION = 1;

    // Database Name
    private static String DB_PATH = "/mnt/sdcard/";
    //private static String DB_PATH= "/mnt/extSdCard/";
    //private static String DB_PATH = "/data/data/org.wikispeedia.roadrage2/databases/";


    private static final String DB_NAME_EAST = "east";


    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_JOURNEY = "journey";

    //another table
    private static final String TABLE_LASTSYNC= "lastsync";

    // Contacts Table Columns names
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";
    private static final String KEY_COG = "cog";
    private static final String KEY_MPH = "mph";
    private static final String KEY_LIM = "limit";
    private static final String KEY_DELETEDON= "deletedOn";
    private static final String KEY_SUBMITTEDON= "submittedOn";
    private static final String KEY_VISITTIME= "visittime";
    private static final String KEY_KPH = "kph";
    private static final String KEY_SUTC = "sutc";
    private static final String KEY_DUTC = "dutc";
    private static final String KEY_TAG = "tag";
    private static final String KEY_WEB_LOCAL= "web_local";

    //different table
    private static final String KEY_LASTSYNC= "date";

    private final Context myContext;


    private static DatabaseHandler_east mDBConnection;




    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHandler_east(Context context) {
        super(context, DB_PATH + DB_NAME_EAST, null, DATABASE_VERSION);
        this.myContext = context;
        //DB_PATH = "/data/data/"+ context.getApplicationContext().getPackageName()+ "/databases/";
        //DB_PATH = "/mnt/sdcard/";
        try {
            createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // The Android's default system path of your application database is
        // "/data/data/mypackagename/databases/"
    }


    /**
     * Creates an empty database on the system and rewrites it with your own database.
     **/
    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if (dbExist)
        {
            // do nothing - database already exist
        } else {
            // By calling following method
            // 1) an empty database will be created into the default system path of your application
            // 2) than we overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME_EAST;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e)
        {
            // database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }


    public static synchronized DatabaseHandler_east getDBAdapterInstance(Context context)
    {
        if (mDBConnection == null)
        {
            mDBConnection = new DatabaseHandler_east(context);
        }
        return mDBConnection;
    }


    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        //InputStream myInput = myContext.getAssets().open(DB_NAME);
        InputStream myInput = myContext.getAssets().open("acontactsManager.png");

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME_EAST;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_LAT + " REAL,"
            + KEY_LON + " REAL,"
            + KEY_COG + " INTEGER,"
            + KEY_MPH + " INTEGER,"
            + KEY_DELETEDON   + " TEXT,"
            + KEY_SUBMITTEDON + " TEXT, "
            + KEY_KPH + " INTEGER, "
            + KEY_SUTC + " TEXT,"
            + KEY_DUTC + " TEXT "
            + KEY_TAG + " TEXT "
            + KEY_WEB_LOCAL + " INTEGER "
            + ")";

    //only gets here if you have uninstalled the app..... Jim
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_CONTACTS_TABLE);
    }



    public String getLatestDate() {

        String sutcmax= null;


        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT max(sutc) FROM contacts ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        int n= cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                sutcmax= cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        if (sutcmax == null) {
            sutcmax="2000-01-01 01:01:01";
        }

        return sutcmax;

    }







    public // Adding new contact
    void addJourney(Signs contact) {

        String mydate = Global.getGmtDateAsString();

        SQLiteDatabase db = this.getWritableDatabase();

        //todo redundant remove sometime: This is also done in getJourneys().
        //change 35.12345678 to 35.123  so its easier to sort
        String smalllat= new DecimalFormat("##.###").format( contact.lat);
        String smalllng= new DecimalFormat("##.###").format(contact.lng);

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, smalllat);
        values.put(KEY_LON, smalllng);
        values.put(KEY_VISITTIME, mydate);

        //Inserting Row
        db.insert(TABLE_JOURNEY, null, values);
        db.close(); // Closing database connection

    }





    public void onUpgrade() {


        //if new load, force lastsync date to be highest value in the DB
        //This keeps us from syncing the whole kit and kabootle
        //String sutcmax=null;
        //sutcmax= Global.db.getLatestDate();







        //db too big. Stuff below can't be done. Cursor too large.
        //I TOOK OUT A BUNCH OF FIX IT UPGRADE STUFF HERE....




        SQLiteDatabase db = this.getWritableDatabase();


        try {
            db.execSQL(" SELECT * FROM JOURNEY LIMIT 1 ");
        }
        catch(Exception e) {
            try {
                db.execSQL("CREATE TABLE journey(lat REAL,lon REAL,visittime TEXT )");
            }
            catch(Exception f) {
                Log.d("TAGG","");
            }
        }



        try {
            db.execSQL(" SELECT * FROM EXTREMES LIMIT 1 ");
        }
        catch(Exception e) {
            try {
                db.execSQL("CREATE TABLE extremes(swlat REAL,swlon REAL, nelat REAL, nelon REAL )");

                Thread.sleep(100);

                db.execSQL("INSERT INTO extremes  (swlat,swlon,nelat,nelon) SELECT min(lat),min(lon),max(lat),max(lon) FROM contacts");



            }
            catch(Exception f) {
                Log.d("TAGG","");
            }
        }




        db.close();


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        //onCreate(db);



    }





    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */




    public // Adding new contact quickly (Async)
    void addContactAsync(Signs contact) {

        Global.signs_s.add(contact);

    }



    public // Adding new contact
    void addContact(Signs contact) {

        String mydate;
        mydate= Global.getGmtDateAsString();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, contact.lat);
        values.put(KEY_LON, contact.lng);
        values.put(KEY_COG, contact.cog);
        values.put(KEY_MPH, contact.mph);
        values.put(KEY_KPH, contact.kph);
        values.put(KEY_TAG, contact.tag);

        int web_local;
        if(contact.web_local) {
            web_local=1;
        } else {
            web_local=0;
        }
        values.put(KEY_WEB_LOCAL,web_local);


        String whatever= contact.sutc;

        if(whatever!=null) {
            values.put(KEY_SUTC,contact.sutc);
        } else {
            values.put(KEY_SUTC,mydate);
        }

        whatever= contact.submittedOn;
        if(whatever!=null && whatever.contains("20") ) {
            values.put(KEY_SUBMITTEDON, contact.submittedOn);
        } else {
            //values.put(KEY_SUBMITTEDON,mydate);
        }

        whatever= contact.deletedOn;
        if(whatever!=null && whatever.contains("20") ) {
            values.put(KEY_DELETEDON,  contact.deletedOn);
        } else {
            //values.put(KEY_DELETEDON,  mydate);
        }

        //Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public void deleteSignAsync(Signs contact) {


        Global.signs_d.add(contact);

    }

    // Deleting single contact
    public void deleteSign(Signs contact) {

        double blat = contact.lat;
        double nelat= blat+0.000001;
        double swlat= blat-0.000001;
        String snelat = Double.toString(nelat);
        String sswlat = Double.toString(swlat);

        double blon= contact.lng;
        double nelon= blon+0.000001;
        double swlon= blon-0.000001;
        String snelon= Double.toString(nelon);
        String sswlon= Double.toString(swlon);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();

        String mydate = Global.getGmtDateAsString();

        args.put(KEY_DUTC, mydate);

        db.update(TABLE_CONTACTS, args,
                KEY_LAT + " >= ? and " + KEY_LAT + " <= ? and " +
                        KEY_LON + " >= ? and " + KEY_LON + " <= ? ",
                new String[] { sswlat,
                        snelat,
                        sswlon,
                        snelon
                }
        );



        db.close();
    }


    // Deleting single contact
    public void deleteSignWithCOG(Signs contact,int coglow_i, int coghigh_i) {

        double blat = contact.lat;
        double nelat= blat+0.000001;
        double swlat= blat-0.000001;
        String snelat = Double.toString(nelat);
        String sswlat = Double.toString(swlat);

        double blon= contact.lng;
        double nelon= blon+0.000001;
        double swlon= blon-0.000001;
        String snelon= Double.toString(nelon);
        String sswlon= Double.toString(swlon);

        String coglow= Integer.toString(coglow_i);
        String coghigh=Integer.toString(coghigh_i);


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();

        String mydate = Global.getGmtDateAsString();

        args.put(KEY_DUTC, mydate);


        //todo I want to put LIMIT 1, in here, but it doesn't work!!!!!!!!!!!!!!!!!!!!!!!!!! ugh   Find direct QUERY replacement to update().

        db.update(TABLE_CONTACTS, args,
                KEY_LAT + " >= ? and " + KEY_LAT + " <= ? and " +
                        KEY_LON + " >= ? and " + KEY_LON + " <= ? and " +
                        KEY_COG + " >= ? and " + KEY_COG + " <= ? ",
                new String[] { sswlat,
                        snelat,
                        sswlon,
                        snelon,
                        coglow,
                        coghigh
                }
        );



        db.close();
    }



    // Deleting single contact
    public void deleteSignREALLYVAPORIZEIT(Signs contact) {

        double blat = contact.lat;
        double nelat= blat+0.000001;
        double swlat= blat-0.000001;
        String snelat = Double.toString(nelat);
        String sswlat = Double.toString(swlat);

        double blon= contact.lng;
        double nelon= blon+0.000001;
        double swlon= blon-0.000001;
        String snelon= Double.toString(nelon);
        String sswlon= Double.toString(swlon);

        String sutc= contact.sutc;


        SQLiteDatabase db = this.getWritableDatabase();

        if(sutc!=null) {

            db.delete(TABLE_CONTACTS, KEY_LAT + " >= ? and " + KEY_LAT + " <= ? and " +
                            KEY_LON + " >= ? and " + KEY_LON + " <= ? and " +
                            KEY_SUTC + " == ? ",
                    new String[] { sswlat,
                            snelat,
                            sswlon,
                            snelon,
                            sutc
                    });

        } else {

            db.delete(TABLE_CONTACTS, KEY_LAT + " >= ? and " + KEY_LAT + " <= ? and " +
                            KEY_LON + " >= ? and " + KEY_LON + " <= ? ",
                    new String[] { sswlat,
                            snelat,
                            sswlon,
                            snelon
                    });
        }


        db.close();
    }




    public List<Signs> getBox(Float _swlat,Float _swlon,Float _nelat,Float _nelon) {
        List<Signs> contactList = new ArrayList<Signs>();



        String swlat=  _swlat.toString();
        String nelat=  _nelat.toString();
        String swlon=  _swlon.toString();
        String nelon=  _nelon.toString();
        String cogcrap="";

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM contacts where lat >= " + swlat +
                " and lat <= " + nelat +
                " and lon >= " + swlon +
                " and lon <= " + nelon;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        //Log.d("TAGG",selectQuery);

        int n= cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                Signs contact = new Signs();

                contact.lat= cursor.getDouble(0);
                contact.lng= cursor.getDouble(1);
                contact.cog= cursor.getInt(2);
                contact.mph= cursor.getInt(3);

                contact.deletedOn= cursor.getString(4);
                contact.submittedOn= cursor.getString(5);

                contact.kph= cursor.getInt(6);
                contact.sutc=  cursor.getString(7);
                contact.dutc=  cursor.getString(8);

                contact.tag= cursor.getString(9);

                String dutc=contact.dutc;
                String deletedOn=contact.deletedOn;

                if(dutc!=null && dutc.contains("20")) {
                    //Log.d("TAGG","dont add to list");
                } else if(deletedOn!=null && deletedOn.contains("20")) {
                    //Log.d("TAGG","dont add to list");
                } else if(dutc != null) {
                    Log.d("TAGG","hi jim");
                } else {
                    // Adding contact to list
                    contactList.add(contact);
                    //Log.d("TAGG","add to list");
                }


            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        // return contact list
        return contactList;
    }



    public List<Signs> getBoxALL(Float _swlat,Float _swlon,Float _nelat,Float _nelon) {
        List<Signs> contactList = new ArrayList<Signs>();

        String format = "yyyy-MM-dd-H-m";

        String swlat=  _swlat.toString();
        String nelat=  _nelat.toString();
        String swlon=  _swlon.toString();
        String nelon=  _nelon.toString();
        String cogcrap="";

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM contacts where lat >= " + swlat +
                " and lat <= " + nelat +
                " and lon >= " + swlon +
                " and lon <= " + nelon;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        //Log.d("TAGG",selectQuery);

        int n= cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                Signs contact = new Signs();

                contact.lat= cursor.getDouble(0);
                contact.lng= cursor.getDouble(1);
                contact.cog= cursor.getInt(2);
                contact.mph= cursor.getInt(3);

                contact.deletedOn= cursor.getString(4);
                contact.submittedOn= cursor.getString(5);

                contact.kph= cursor.getInt(6);
                contact.sutc=  cursor.getString(7);
                contact.dutc=  cursor.getString(8);

                //if(contact.deletedOn!=null) {
                //	Log.d("TAGG","dont add to list");
                //} else {
                //	// Adding contact to list
                contactList.add(contact);
                //Log.d("TAGG","add to list");
                //}


            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        // return contact list
        return contactList;
    }


    public List<Signs> getAllofThemSince(String thedate) {

        List<Signs> contactList = new ArrayList<Signs>();

        SQLiteDatabase db = this.getWritableDatabase();
        thedate= "'" + thedate + "'";

        //String selectQuery = "SELECT * FROM contacts where sutc>" + thedate + " or dutc>" + thedate + "order by mydate";


        String selectQuery=
                "SELECT lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag,web_local, " +
                        "sutc as mydate from contacts " +
                        "where sutc>" + thedate + " " +
                        "union all " +
                        "SELECT lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag,web_local, " +
                        "dutc as mydate from contacts " +
                        "where dutc>" + thedate + " " +
                        "order by mydate " +  " " +
                        "limit 1000";




        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        //Log.d("TAGG",selectQuery);

        int n= cursor.getCount();
        Integer howmany=0;

        if (cursor.moveToFirst()) {
            do {
                Signs contact = new Signs();

                try {
                    contact.lat= cursor.getDouble(0);
                    contact.lng= cursor.getDouble(1);
                    contact.cog= cursor.getInt(2);
                    contact.mph= cursor.getInt(3);

                } catch(Exception e) {
                    Log.d("TAGG","crap data, should delete it  but oh well...");
                    continue;
                }

                contact.deletedOn= cursor.getString(4);
                contact.submittedOn= cursor.getString(5);

                contact.kph= cursor.getInt(6);
                contact.sutc=  cursor.getString(7);
                contact.dutc=  cursor.getString(8);

                int val = cursor.getInt(10);			//9 is tag which is unused...
                Boolean bval= (val>0)?true:false;
                contact.web_local= bval;
                //Adding contact to list
                contactList.add(contact);



            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        // return contact list
        return contactList;
    }


    public int getAllofThemSinceN(String thedate) {


        SQLiteDatabase db = this.getWritableDatabase();
        thedate= "'" + thedate + "'";
        String selectQuery = "SELECT count(*) FROM contacts where sutc>" + thedate + " or dutc>" + thedate;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int n= cursor.getCount();
        Integer howmany=0;

        if (cursor.moveToFirst()) {
            do {

                try {
                    howmany= cursor.getInt(0);

                } catch(Exception e) {
                    Log.d("TAGG","crap data, should delete it  but oh well...");
                    continue;
                }

            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        // return contact list
        return howmany;
    }



    public List<Signs> getAllofThem() {
        List<Signs> contactList = new ArrayList<Signs>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM contacts where 1=1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        //Log.d("TAGG",selectQuery);

        int n= cursor.getCount();
        Integer howmany=0;

        if (cursor.moveToFirst()) {
            do {
                Signs contact = new Signs();

                try {
                    contact.lat= cursor.getDouble(0);
                    contact.lng= cursor.getDouble(1);
                    contact.cog= cursor.getInt(2);
                    contact.mph= cursor.getInt(3);

                } catch(Exception e) {
                    Log.d("TAGG","crap data, should delete it  but oh well...");
                    continue;
                }

                contact.deletedOn= cursor.getString(4);
                contact.submittedOn= cursor.getString(5);

                contact.kph= cursor.getInt(6);
                contact.sutc=  cursor.getString(7);
                contact.dutc=  cursor.getString(8);


                //Adding contact to list
                contactList.add(contact);



            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        // return contact list
        return contactList;
    }



    public List<Signs> getAlljourneys() {
        List<Signs> contactList = new ArrayList<Signs>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM journey where 1=1";
        Cursor cursor = db.rawQuery(selectQuery, null);

        int n= cursor.getCount();
        Integer howmany=0;

        if (cursor.moveToFirst()) {
            do {
                Signs contact = new Signs();

                try {
                    contact.lat= cursor.getDouble(0);
                    contact.lng= cursor.getDouble(1);
                    contact.sutc= cursor.getString(2);

                } catch(Exception e) {
                    Log.d("TAGG","crap in journey DB");
                    continue;
                }

                contactList.add(contact);

            } while (cursor.moveToNext());
        }
        cursor.close(); //jim added
        db.close();

        // return contact list
        return contactList;
    }


    // Updating single contact
    public int updateContact(Signs contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT,   Double.toString(contact.lat));
        values.put(KEY_LON,   Double.toString(contact.lng));
        values.put(KEY_COG,   Integer.toString(contact.cog));
        values.put(KEY_MPH,   Integer.toString(contact.mph));
        values.put(KEY_KPH,   Integer.toString(contact.kph));
        values.put(KEY_TAG,   contact.tag);

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_LAT + " = ?",
                new String[] { Double.toString(contact.lat) });
    }


    // Getting contacts Count
    public int getContactsCount() {
        int n;

        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        n= cursor.getCount();
        cursor.close();

        // return count
        return n;
    }




    public void delete_dups() {

        SQLiteDatabase db = this.getWritableDatabase();


        try {
            db.execSQL("DROP TABLE rnd_killer");
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }


        db.execSQL("CREATE TABLE rnd_killer( lat REAL,lon REAL,cog INTEGER,mph INTEGER,deletedOn TEXT,submittedOn TEXT, kph INTEGER, sutc TEXT, dutc TEXT, tag TEXT )");
        //db.execSQL("CREATE INDEX indx on rnd_killer (lat,lon)");


        db.execSQL("INSERT INTO rnd_killer  (lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag) SELECT distinct round(lat,8),round(lon,8),cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag FROM contacts");


        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }

        db.execSQL("DELETE FROM contacts;");

        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }


        db.execSQL("DROP TABLE contacts;");


        try {
            Thread.sleep(1000);
            db.execSQL("ALTER TABLE rnd_killer rename to contacts");
            db.execSQL("CREATE INDEX indx on contacts (lat,lon)");
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }





		/*todo gotta get this working jim

		//remove dups only different by sutc dates.  I can't seem to do this with query, so using loop... Oh well..
		List<Signs> signsin= getAllofThem();
		int n= signsin.size();
   	 	Integer total=n;

   	 	Signs sign1,sign2;

   	 	for(int i=0;i<n;i++) {
   	 		sign1= signsin.get(i);

   	 		for(int j=i+1;j<n;j++) {
   	 			sign2= signsin.get(j);

   	 			//find identical except for date
   	 			if(sign1.lat==sign2.lat && sign1.lng==sign2.lng && sign1.cog==sign2.cog && sign1.mph==sign2.mph && sign1.kph==sign2.kph &&
   	 					sign1.sutc!=sign2.sutc) {

   	 				this.deleteSignREALLYVAPORIZEIT(sign2);

   	 			}

   	 		}
   	 	}
   	 	*/


        Log.d("TAGG","after delete_dups");

    }



    public void delete_dupsold() {

        SQLiteDatabase db = this.getWritableDatabase();


        try {
            db.execSQL("DROP TABLE web_set");
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }

        db.execSQL("CREATE TABLE web_set( lat REAL,lon REAL,cog INTEGER,mph INTEGER,deletedOn TEXT,submittedOn TEXT, kph INTEGER, sutc TEXT, dutc TEXT, tag TEXT )");

        db.execSQL("INSERT INTO web_set  (lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag) SELECT lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag FROM contacts where web_local=true");

        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }



        try {
            db.execSQL("DROP TABLE local_set");
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }

        db.execSQL("CREATE TABLE local_set( lat REAL,lon REAL,cog INTEGER,mph INTEGER,deletedOn TEXT,submittedOn TEXT, kph INTEGER, sutc TEXT, dutc TEXT, tag TEXT )");

        db.execSQL("INSERT INTO local_set  (lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag) SELECT lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag FROM contacts where web_local=false and ");

        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }




        //db.execSQL("INSERT INTO web_set  (lat,lon,cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag) SELECT distinct round(lat,8),round(lon,8),cog,mph,deletedOn,submittedOn,kph,sutc,dutc,tag FROM contacts");
        //db.execSQL("CREATE INDEX indx on rnd_killer (lat,lon)");

        db.execSQL("DELETE FROM contacts;");

        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }


        db.execSQL("DROP TABLE contacts;");


        try {
            Thread.sleep(1000);
            db.execSQL("ALTER TABLE rnd_killer rename to contacts");
            db.execSQL("CREATE INDEX indx on contacts (lat,lon)");
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }





		/*todo gotta get this working jim

		//remove dups only different by sutc dates.  I can't seem to do this with query, so using loop... Oh well..
		List<Signs> signsin= getAllofThem();
		int n= signsin.size();
   	 	Integer total=n;

   	 	Signs sign1,sign2;

   	 	for(int i=0;i<n;i++) {
   	 		sign1= signsin.get(i);

   	 		for(int j=i+1;j<n;j++) {
   	 			sign2= signsin.get(j);

   	 			//find identical except for date
   	 			if(sign1.lat==sign2.lat && sign1.lng==sign2.lng && sign1.cog==sign2.cog && sign1.mph==sign2.mph && sign1.kph==sign2.kph &&
   	 					sign1.sutc!=sign2.sutc) {

   	 				this.deleteSignREALLYVAPORIZEIT(sign2);

   	 			}

   	 		}
   	 	}
   	 	*/


        Log.d("TAGG","after delete_dups");

    }


    public List<Signs> getJourneys() {

        List<Signs> signsout = new ArrayList<Signs>();


        SQLiteDatabase db = this.getWritableDatabase();



        //drop dup_killer just in case it exists. Just in case.
        try {
            db.execSQL("DROP TABLE journey_dup_killer");
        }
        catch(Exception e) {
        }

        try {
            db.execSQL("CREATE TABLE journey_dup_killer(lat REAL,lon REAL,visittime TEXT )");
        }
        catch(Exception f) {
            Log.d("TAGG","");
        }



        db.execSQL("INSERT INTO journey_dup_killer (lat,lon) SELECT distinct round(lat,3),round(lon,3) FROM journey");


        db.execSQL("DROP table journey");

        //voodoo
        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }

        db.execSQL("ALTER TABLE journey_dup_killer rename to journey");

        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }


        try {
            Cursor cursor = db.rawQuery("SELECT * FROM journey where 1=1", null);
            if (cursor.moveToFirst()) {
                do {
                    Signs sign = new Signs();
                    sign.lat= cursor.getDouble(0);
                    sign.lng= cursor.getDouble(1);
                    signsout.add(sign);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch(Exception g) {

        }






        db.close();


        Log.d("TAGG","after getJourneys");

        return signsout;

    }



    public void clearJourneys() {


        SQLiteDatabase db = this.getWritableDatabase();



        //drop dup_killer just in case it exists. Just in case.
        try {
            db.execSQL("DELETE FROM JOURNEY");
        }
        catch(Exception e) {
        }


        db.close();


        Log.d("TAGG","after clearJourney");



    }


    public String getLastSync() {

        String mydate = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT date from lastsync", null);
            if (cursor.moveToFirst()) {
                mydate= cursor.getString(0);
            }
            cursor.close();
        } catch(Exception g) {

            mydate= getLatestDate();
            db.close();
            db = this.getWritableDatabase();
            db.execSQL("CREATE TABLE lastsync( date TEXT )");
            String magic= "insert into lastsync (date) values ('" + mydate + "')";
            db.execSQL(magic);

        }
        db.close();
        return mydate;


    }



    public Box getExtremes() {

        Double swlat, swlon, nelat, nelon;
        Box mybox= new Box();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT swlat,swlon,nelat,nelon from extremes", null);
            if (cursor.moveToFirst()) {
                mybox.swlat= cursor.getDouble(0);
                mybox.swlon= cursor.getDouble(1);
                mybox.nelat= cursor.getDouble(2);
                mybox.nelon= cursor.getDouble(3);
            }
            cursor.close();
        } catch(Exception g) {
            Log.d("TAGG","ERROR: extremes table busted...");
        }
        db.close();

        return mybox;

    }


    public void setlastsync(String mydate) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {

            db = this.getWritableDatabase();
            String magic= "update lastsync set date='"+ mydate + "'";
            db.execSQL(magic);

        } catch(Exception g) {

            Log.d("TAGG","bug, fix me jim");


        }
        db.close();


    }





}
