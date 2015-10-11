package niceandroid.net.androidsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import org.wikispeedia.speedlimit.Box;
import org.wikispeedia.speedlimit.Signs;

import java.util.List;


public class DatabaseHandler {


    DatabaseHandler_east db_east= null;
    DatabaseHandler_west db_west= null;
    private final Context myContext;


    public DatabaseHandler(Context context) {
        //super(context, DB_PATH + DB_NAME_EAST, null, DATABASE_VERSION);
        this.myContext = context;

        db_east = new DatabaseHandler_east(myContext);
        db_west = new DatabaseHandler_west(myContext);

    }

    public void addContact(Signs contact) { db_east.addContact(contact); }

    public String getLastSync() { return db_east.getLastSync(); }

    public void onUpgrade() { db_east.onUpgrade(); }

    public List<Signs> getBox(Float _swlat,Float _swlon,Float _nelat,Float _nelon) {

        return db_east.getBox(_swlat,_swlon,_nelat,_nelon);

    }

    public void deleteSign(Signs contact) {   db_east.deleteSign(contact);    }

    public Box getExtremes() {  return db_east.getExtremes(); }

    public List<Signs> getJourneys() { return db_east.getJourneys(); }

    public void addJourney(Signs contact) { db_east.addJourney(contact); }

    public void setlastsync(String mydate) { db_east.setlastsync(mydate); }

    public void clearJourneys() { db_east.clearJourneys(); }

    public String getLatestDate() { return db_east.getLatestDate(); }

    public List<Signs> getAllofThemSince(String thedate) { return db_east.getAllofThemSince(thedate); }

    public void delete_dups() { db_east.delete_dups(); }

    public void deleteSignWithCOG(Signs contact,int coglow_i, int coghigh_i) {
        db_east.deleteSignWithCOG(      contact,    coglow_i,     coghigh_i);
    }


}

