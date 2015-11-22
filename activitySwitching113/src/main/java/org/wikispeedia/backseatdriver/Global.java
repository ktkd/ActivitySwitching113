package org.wikispeedia.backseatdriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;





import org.wikispeedia.speedlimit.Signs;
import org.wikispeedia.speedlimit.SpeedlimitListener;
import org.wikispeedia.speedlimit.SpeedlimitManager4;
import org.wikispeedia.zombiepro.CanvasThread;
import org.wikispeedia.zombiepro.Canvastutorial;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import niceandroid.net.androidsqlite.DatabaseHandler;
import niceandroid.net.androidsqlite.DatabaseHandler_east;


public class Global {



	public static List<Signs> duplist= new ArrayList<Signs>();


	public static boolean test=  false;



	public static boolean andygrid= false;
	public static boolean andyDelete= false;
	public static boolean andyDoubleDeleter= true;
    public static boolean manualdelete= false;
	public static boolean east_west= true;
	public static int sdlocation= 0;


	public static String getGmtDateAsString() {
		Date gmt= new Date();
		String mydate;
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		mydate = sdf.format(Calendar.getInstance().getTime());
	    return mydate;
	}

	public static Date getGmtDate() {	
		Date gmt= new Date();
		String mydate;
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		mydate = sdf.format(Calendar.getInstance().getTime());
		SimpleDateFormat sdff = new SimpleDateFormat(format);
		try {
			gmt = sdff.parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return gmt;
	}


		public static DatabaseHandler db = null;
		public static List<Signs> thecontactList=null;

		public static boolean mph_kph= true;
		public static double mySpeed=20.0;
		public static double myLatitude,myLongitude;
		public static double myAltitude_meters;
		public static int myCog;
		public static boolean locFound=false;

		public static String myLatitudeString="";
		public static String myLongitudeString="";
		public static String myCogString="";
		public static String mySpeedString="";
		
		
		
		
		
		
 	  
		public static Boolean weAreSpeeding= false;
		
    
		public static String all="";
		public static String rgb="77,119,67,77,119,87";
	    public static String mUsernameString= "";
	    public static String mEmailString="";	    
        public static String mOverString=""; 
	    public static Boolean delete_settings= false;  //multi-purpose button on bottom right... 
		public static boolean iAmSpeedingAlot= false;	
		public static double theSpeedLimitIsDouble= 	127;

		public static int xtap;
		public static int ytap;
		public static boolean tapped= false;

		public static int tappedSignFound= -1;

		
		

		

		public static String mBitcoin="";
		
		public static String msutc="";
		public static String mdutc="";

		public static boolean dbCreated= false;

		public static int width;

		public static int height;

		public static double theSpeedLimitIsDoubleOld;

	

		public static boolean taunts;

		public static int over;

		public static boolean threadisrunning= false;

		public static int radius=-1;

		public static SpeedlimitListener speedlimitListener;

		public static SpeedlimitManager4 slm;
		
		public static boolean thecontactlistbusy = false;

		private static Float nelon;

		private static Float swlat;

		private static Float swlon;

		private static Float nelat;

		static float latheighthalf,lonwidthhalf;
				
		public static Canvastutorial panelthis;

		public static Signs contact;

		public static Signs deleter_contact;

		public static Signs visible_contact = null;
		
		

		public static boolean spinlocked= false;

		public static int thecontactListindex;

		public static boolean no_wifi_found;
	
		static float latheight=0.01f;
		static float lonwidth=0.01f;
		
		public static void dobox() {
		
			

			Float swlat;
			Float nelat;
			Float swlon;
			Float nelon;

			//Note: Hail Marry, I doubled this so screen doesn't show half missing...			
			//latheighthalf=latheight/2.0f;
			//lonwidthhalf=lonwidth/2.0f;
			
			swlat=(float) (Global.myLatitude-latheight);	//latheighthalf);
			nelat=(float) (Global.myLatitude+latheight);	//latheighthalf);
			
			swlon=(float) (Global.myLongitude-lonwidth); 	//lonwidthhalf);
			nelon=(float) (Global.myLongitude+lonwidth);	//lonwidthhalf);
		
			
			Global.thecontactlistbusy=true;

			Global.thecontactList= Global.db.getBox(swlat,swlon,nelat,nelon);
			Log.d("TAGG","After getBox, Lat,Lon= " + Global.myLatitudeString + " "  + Global.myLongitudeString);





            Global.thecontactlistbusy=false;


        }



		public static LocationManager lm;
		
		public static int nSignsNotSynced;
		public static int nSignsNotSynced_wentoutlasttime;


		public static Settings.AsyncTaskExample2 mytask2;




}

	
	
