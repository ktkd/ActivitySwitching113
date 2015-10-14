package niceandroid.net.androidsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import org.wikispeedia.backseatdriver.Global;
import org.wikispeedia.speedlimit.Box;
import org.wikispeedia.speedlimit.Signs;

import java.util.List;


public class DatabaseHandler {


    DatabaseHandler_east db_east= null;
    DatabaseHandler_west db_west= null;
    private final Context myContext;
    static public boolean busy = false;


    public DatabaseHandler(Context context) {
        //super(context, DB_PATH + DB_NAME_EAST, null, DATABASE_VERSION);
        this.myContext = context;

        db_east = new DatabaseHandler_east(myContext);
        db_west = new DatabaseHandler_west(myContext);

    }

    public void addContact(Signs contact) {
        if(busy) {
        } else {
            busy=true;
            if(Global.east_west) {
                db_east.addContact(contact);
            } else {
                db_west.addContact(contact);
            }
            busy=false;
        }
    }

    public String getLastSync() {
        String retval = "";
        if (busy) {
        } else {
            busy = true;
            if(Global.east_west) {
                retval = db_east.getLastSync();
            } else {
                retval = db_west.getLastSync();
            }
            busy = false;
        }
        return retval;
    }


    public void onUpgrade() {
        if(busy) {
        } else {
            busy=true;
            if(Global.east_west) {
                db_east.onUpgrade();
            } else {
                db_west.onUpgrade();
            }
            busy=false;
        }
    }

    public List<Signs> getBox(Float _swlat,Float _swlon,Float _nelat,Float _nelon)
    {
        List<Signs> retval= null;
        if(busy) {
            return null;
        } else {
            busy=true;
            if(Global.east_west) {
                retval = db_east.getBox(_swlat, _swlon, _nelat, _nelon);
            } else {
                retval = db_west.getBox(_swlat, _swlon, _nelat, _nelon);
            }
            busy=false;
            return retval;
        }

    }

    public void deleteSign(Signs contact) {
        if(busy) {
        } else {
            busy=true;
            if(Global.east_west) {
                db_east.deleteSign(contact);
            } else {
                db_west.deleteSign(contact);
            }
            busy=false;
        }
    }

    public Box getExtremes() {
        Box retval= null;
        if(busy) {
            return null;
        } else {
            busy=true;
            if(Global.east_west) {
                retval = db_east.getExtremes();
            } else {
                retval = db_west.getExtremes();
            }
            busy=false;
            return retval;
        }
    }

    public List<Signs> getJourneys() {
        List<Signs> retval = null;
        if(busy) {
            return null;
        } else {
            busy=true;
            if(Global.east_west) {
                retval = db_east.getJourneys();
            } else {
                retval = db_west.getJourneys();
            }
            busy=false;
            return retval;
        }
    }

    public void addJourney(Signs contact) {
        if(busy) {
        } else {
            busy=true;
            if(Global.east_west) {
                db_east.addJourney(contact);
            } else {
                db_west.addJourney(contact);
            }
            busy=false;
        }
    }

    public void setlastsync(String mydate) {
        if(busy) {
        } else {
            busy = true;
            if(Global.east_west) {
                db_east.setlastsync(mydate);
            } else {
                db_west.setlastsync(mydate);
            }
            busy = false;
        }
    }

    public void clearJourneys() {
        if(busy) {
        } else {
            busy = true;
            if(Global.east_west) {
                db_east.clearJourneys();
            } else {
                db_west.clearJourneys();
            }
            busy=false;
        }
    }

    public String getLatestDate() {
        String retval="";
        if(busy) {
        } else {
            busy=true;
            if(Global.east_west) {
                retval = db_east.getLatestDate();
            } else {
                retval = db_west.getLatestDate();
            }
            busy=false;
        }
        return retval;
    }

    public List<Signs> getAllofThemSince(String thedate) {
        List<Signs> retval = null;
        if(busy) {
        } else {
            busy = true;
            if(Global.east_west) {
                retval = db_east.getAllofThemSince(thedate);
            } else {
                retval = db_west.getAllofThemSince(thedate);
            }
            busy = false;
        }
        return retval;
    }

    public void delete_dups() {
        if(busy) {
        } else {
            busy=true;
            if(Global.east_west) {
                db_east.delete_dups();
            } else {
                db_west.delete_dups();
            }
            busy=false;
        }
    }

    public void deleteSignWithCOG(Signs contact,int coglow_i, int coghigh_i) {
        if(busy) {
        } else {
            busy = true;
            if(Global.east_west) {
                db_east.deleteSignWithCOG(contact, coglow_i, coghigh_i);
            } else {
                db_west.deleteSignWithCOG(contact, coglow_i, coghigh_i);
            }
            busy = false;
        }
    }


}

