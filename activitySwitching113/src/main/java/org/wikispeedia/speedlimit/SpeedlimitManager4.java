package org.wikispeedia.speedlimit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.wikispeedia.backseatdriver.Global;


import com.thebuzzmedia.sjxp.XMLParser;
import com.thebuzzmedia.sjxp.XMLParserException;
import com.thebuzzmedia.sjxp.rule.DefaultRule;
import com.thebuzzmedia.sjxp.rule.IRule;
import com.thebuzzmedia.sjxp.rule.IRule.Type;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


public class SpeedlimitManager4 {

    Thread t5;

    static String myname, myemail;
    private static SpeedlimitListener myspeedlimitListener;
    static int mymph, mykph;
    static int pegMph, pegKph;
    static Float myCog;
    static Float pegCog;
    static double myLatitude;
    static double pegLatitude;
    static double myLongitude;
    static double pegLongitude;
    static double  myAltitude_meters;
    static double pegAltitude_meters;
    static String rval1;
    static Boolean locFound=false;
    static double theSpeedLimitIs_latitude=0.0;
    static double theSpeedLimitIs_longitude=0.0;
    static double theSpeedLimitIs_cog=0.0;
    static int theSpeedLimitIs_i= -1;
    static double theSpeedLimitIsDouble=0;
    static Integer theSpeedLimitIs_Integer=0;
    static String  theCopyrightIs;
    static double min_distance=2000.0;
    static double Global_min_distanceFt=2000.0;
    private int positionChanged;
    private int timeChanged, fivetimer;
    private static Timer mmTimer_submit;
    private static Timer mmTimer_instantSL;
    final Handler mHandler_time = new Handler();
    final static Handler mHandler_distance = new Handler();
    final static Handler mHandler_submit = new Handler();
    private static double mySpeed;
    static Boolean mph_kph=true;	//not sure how to toggle this yet...
    static Boolean test=false;

    private CountDownTimer mCountDownTimer;
    static String pegName="";
    static String pegEmail="";
    static String pegBitcoin="";

    public static Integer imph;
    public static Integer ikph;
    static CharSequence stuff;


    static int xresol_10;
    static int xresol_2;
    static int yresol_10;
    static int yresol_2;

    public SpeedlimitManager4(Context baseContext) {

        Global.lm= (LocationManager) baseContext.getSystemService(Context.LOCATION_SERVICE);

        getMyLocation_oneTimeSetup();


        latheighthalf=latheight/2.0f;
        lonwidthhalf=lonwidth/2.0f;



        xresol= Global.width;
        yresol= Global.height;
        if(xresol==0) {		//chicken egg workaround
            Log.d("TAGGG","bug jim");
        }
        xresol_10= xresol/10;
        xresol_2= xresol/2;
        yresol_10= yresol/10;
        yresol_2= yresol/2;


    }


    public void stopListener() {

        Log.d("TAGG","SpeedlimitManager stopListener  TODO");

        getMyLocation_oneTimeTeardown();

    }

    public void requestChanges(SpeedlimitListener speedlimitListener) {

        //save for later
        myspeedlimitListener= speedlimitListener;

    }


    public String getVal1() {
        return theString;
    }

    static Boolean lockout=false;


    public void getMyLocation_oneTimeTeardown() {

        Log.d("TAGG","getMyLocation_oneTimeTearDown");

        Global.lm.removeUpdates(locationListener_distance);

        Global.lm.removeUpdates(locationListener_time);

        Log.d("TAGG","not sure if this is right to make lm=null. jim todo test");
        Global.lm= null;


        Log.d("TAGGG","delete five timer");
        try{

            t5.interrupt();
            t5.stop();
        } catch(Exception e) {
            Log.d("TAGG","Exception onetimeteardown");

        }



        //t5.destroy();


    }

    LocationListener locationListener_distance, locationListener_time;

    private void getMyLocation_oneTimeSetup() {
        //float meters;



        locationListener_distance = new LocationListener() {
            public void onLocationChanged(Location newloc) {
                positionChanged++;
                Log.d("TAGGG","positionChanged= " + Integer.toString(positionChanged));
                startLongRunningOperation_distance();

                if(Global.radius>0) {
                    //jim turn back on later   org.wikispeedia.zombiepro.Panel.cruiseControl2();
                    Global.radius=0;
                }

            }
            public void onProviderDisabled(String provider) {}
            public void onProviderEnabled(String provider) {}
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {}
        };

        float meters= (float)800.0;


        Global.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, meters, locationListener_distance);


        locationListener_time = new LocationListener() {
            public void onLocationChanged(Location newloc) {
                timeChanged++;
                //Log.d("TAGGG","timeChanged= " + Integer.toString(timeChanged));      

                startLongRunningOperation_time();
            }
            public void onProviderDisabled(String provider) {}
            public void onProviderEnabled(String provider) {}
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {}
        };
        long ms= 10000; //every 5seconds. So if you are at a red light, speed heads to zero
        //this doesnt work. It always hits at 1 second no matter what value I give for ms
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long)10000, 0, locationListener_time);   






    }










    private void updateResultsInUi_time() {
    }

    private static void updateResultsInUi_distance() {
    }

    private static void updateResultsInUi_submit() {
    }

    // Create runnable for posting
    final Runnable mUpdateResults_time = new Runnable() {
        public void run() {
            updateResultsInUi_time();
        }
    };

    final static Runnable mUpdateResults_distance = new Runnable() {
        public void run() {
            updateResultsInUi_distance();
        }
    };

    final static Runnable mUpdateResults_submit = new Runnable() {
        public void run() {
            updateResultsInUi_submit();
        }
    };
    private static double Global_min_distance = 0;





    void startLongRunningOperation_time() {



        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread("startLongRunningOperation_time") {
            public void run() {
                doSomethingExpensive_time();
                mHandler_time.post(mUpdateResults_time);
            }

            private void doSomethingExpensive_time() {


                //getMyLocation();

            }
        };
        t.start();
    }

    static int ngrabs;


    public static void startLongRunningOperation_distance() {

        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread("startLongRunningOperation_distance") {
            public void run() {
                doSomethingExpensive_distance();
                mHandler_distance.post(mUpdateResults_distance);
            }

            private void doSomethingExpensive_distance() {

                getMyLocation();
            }
        };
        t.start();
    }




    private static int instant_counter=0;






    static Integer fail=0;
    static Integer pass=0;



    static Signs mySign;
    static String label="";
    static String lat="";

    static String lng="";

    static String mph="";

    static String kph="";

    static String cog="";

    /** Queue of Speed Limit Signs */
    static ArrayList<Signs> signs = new ArrayList<Signs>();


    @SuppressLint("WrongCall")
    public static void getMyLocation() {

        Log.d("TAGGG","getMyLocation");

        if(Global.lm != null){
            Location loc = Global.lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {


                if(Global.mph_kph) {
                    Global.mySpeed= loc.getSpeed() * 2.23693629;
                } else {
                    Global.mySpeed= loc.getSpeed() * 3.6;	//1 meter per second = 3.6 kilometers per hour
                }
                Global.mySpeedString= Double.toString(Global.mySpeed);


                Global.myLatitude= loc.getLatitude();
                Global.myLongitude= loc.getLongitude();
                Global.myAltitude_meters= loc.getAltitude();
                Global.myCog= (int) loc.getBearing();





                //todo jim testing testing remove remove!!!!!!!!!!!!!!
                //Global.myLatitude=35.048929;
                //Global.myLongitude=-89.881882;



                Global.myLatitudeString = Double.toString(Global.myLatitude);
                Global.myLongitudeString = Double.toString(Global.myLongitude);
                Global.myCogString= Integer.toString(Global.myCog);


                Global.locFound= true;

                //if(Global.test) {
                //	   Log.d("TAGG","TEST!!!!!!!!!!!!!");
                //	   Global.mySpeed= 100;
                //	   Global.myAltitude_meters= 8527.12;
                //  }

                Float swlat;
                Float nelat;
                Float swlon;
                Float nelon;

                swlat=(float) (Global.myLatitude-latheighthalf);
                nelat=(float) (Global.myLatitude+latheighthalf);

                swlon=(float) (Global.myLongitude-lonwidthhalf);
                nelon=(float) (Global.myLongitude+lonwidthhalf);


                Global.thecontactList= Global.db.getBox(swlat,swlon,nelat,nelon);
                Log.d("TAGG","after getbox. Before onDraw()");
                onDraw();



            } else {
                Global.locFound= false;  //if false, dont trust the mySpeed, etc from above... Used as a lockout since I am a terrible hack!
            }

        }

    }




    int ii=0;

    static float latheighthalf,lonwidthhalf;
    static float lattop,latbottom,lonleft,lonright;

    //fill these in per phone.



    static float latheight=0.01f;
    static float lonwidth=0.01f;

    static boolean busy=false;

    static int mycog= 0;

    static int spin=20;
    static int oldspin;
    static int pthreshold,mthreshold=-2;	//trying to prevent jitter




    static Float close_b=0f;
    static Float close_m=0f;
    static int close_x=0;
    static int close_y=0;
    static Float close_d;
    static Float close_numerator;
    static Float close_denomanator=1f;
    static Float close_inverseden=1f;
    static Float close_guts;
    static Double close_dguts;


    static int xresol;
    static int yresol;


    public static void onDraw() {

        int isRusty = 1;

        busy=true;


        if(!Global.locFound) {
            busy=false;
            return;
        }






        if(Global.thecontactList==null) {
            busy=false;
            return;
        }

        int n= Global.thecontactList.size();

        if(n==0) {
            busy=false;
            return;
        }





        lattop= (float) (Global.myLatitude+latheighthalf);
        latbottom= lattop-latheight;

        lonleft= (float) (Global.myLongitude-lonwidthhalf);
        lonright= (float)(Global.myLongitude+lonwidthhalf);


        mycog = Global.myCog;



        Float latf, lonf;
        Float xf,yf;
        int xi,yi;
        Float dx;
        Float xd;

        Float xs;
        Float dy;
        Float ys;
        Float yd;

        dx= lonright-lonleft;
        dy= lattop-latbottom;

        Integer signcog=0;
        int dcog = 0;
        int absdcog = 0;

        int i;
        Integer result=0;
        Boolean showcog=false;
        Boolean showdistance=false;



        Signs val;

        for(i=0;i<n;i++) {
            try{
                val = Global.thecontactList.get(i);	//trying to prevent ANR's reported on this line!!
            } catch(Exception e) {
                Log.d("TAGG","Exception thecontactlist.get()  n= " + Integer.toString(i));
                break;
            }

            lonf = (float) val.lng;

            //only show signs in my direction of travel. Prevents deleting too many at a time....
            signcog = val.cog;
            dcog = mycog-signcog;
            absdcog = Math.abs(dcog);
            if(absdcog>180) {
                result= 360-absdcog;
            } else {
                result=absdcog;
            }
            showcog=false;
            if(result<45) {			//accept up to 45degrees off direction... Which is 90 total window. Might need adjusted...
                showcog= true;
            }



            if(!showcog) {
                //Log.d("TAGG","cog1=" + mycog.toString() + " cog2= " + signcog.toString() + " delta=" + result.toString());
            }






            xf= lonf-lonleft;

            xd= xf/dx;

            xs= xd*xresol;



            latf= (float) val.lat;


            yf= lattop-latf;

            yd= yf/dy;

            ys= yd*yresol;




            xi= (int) (double)xs;
            yi= (int) (double)ys;



            lonf = (float) val.lng;




            //only show signs closest to the line.
            //reference: http://math.ucsd.edu/~wgarner/math4c/derivations/distance/distptline.htm
            //d= |y-xm-b|/ sqrt(m^2+1)
            //only doing this if needed based on spin
            close_x = xi;
            close_y = yi;
            if(spin != oldspin) {
                oldspin= spin;
                double tmp= spin+90;
                close_m= (float) Math.tan(Math.toRadians(tmp));
                close_b= yresol/2 - close_m*xresol/2;		//use center point in m,b calculation y=mx+b or b= y-mx
                close_guts = (close_m*close_m+1f);
                close_dguts = (double)close_guts;
                close_denomanator = (float) Math.sqrt(close_dguts);
                close_inverseden= 1.0f/close_denomanator;		   //pre doing division to speed it up...
            }
            close_numerator = Math.abs(close_y-close_x*close_m-close_b);
            close_d= close_numerator * close_inverseden;
            showdistance=false;
            if(close_d<50) {
                showdistance= true;
                //Log.d("TAGG","close_d= " + Float.toString(close_d));
            }







            int speed= (Global.mph_kph)?val.mph:val.kph;


            int cog= val.cog;



            if(showdistance && showcog) {

                //if sign wanders thru middle, light it up.
                int dxx = Math.abs(xi - (xresol_2));
                int dyy = Math.abs(yi - (yresol_2));
                if (dxx<(xresol_10) && dyy<(yresol_10)) {


                    if(Global.theSpeedLimitIsDouble != speed) {

                        theSpeedLimitIs_Integer= speed;
                        theCopyrightIs= val.tag;

                        myspeedlimitListener.onSpeedLimitChanged(theSpeedLimitIs_Integer, theCopyrightIs);

                        Global.theSpeedLimitIsDouble= speed;

                        //Log.d("TAGG","sign nearby, new psl= " + Integer.toString(speed) );
                    }

                    if(Global.mySpeed > (Global.theSpeedLimitIsDouble + Global.over)) {

                        if(false) {
                            org.wikispeedia.zombiepro.Panel.cruiseControl();
                        }

                    }



                }



            }



        }



        Global.tapped=false;	//tap not found, so keep it from hanging


        busy=false;

    }




    public static double computeLatLongDistance( double startLat, double startLong, double endLat, double endLong )
    {
        double returnDouble = 0;


        double dLat= endLat-startLat;
        double dLon= endLong-startLong;

        if(dLat<0.0) dLat= 0.0 - dLat;
        if(dLon<0.0) dLon= 0.0 - dLon;

        double d= (dLat) + (dLon);

        //this should be scaled and square-rooted, but WHY, just a waste of battery ;-)

        returnDouble= d;

        //System.out.println("Returning distance: " + returnDouble + " meters");
        return returnDouble;
    }



    public static void deletePointAt(double pointLat, double pointLon) {


        //ck for garbage, cases to ignore...
        if(pointLat>=0.0 && pointLat<0.000001) {
            theString= "pointLat=0  delete-IGNORED";
            return;
        }


        StringBuffer response = new StringBuffer("");

			/*
			 *    START SIGN QUERY
			 * 
			 */
        URL url = null;

        //crude way to identify point, but I hate to resort to using the "index"....
        try {
            url = new URL("http://www.wikispeedia.org/a/delete_bb2.php" +
                    "?name=all" +
                    "&nelat=" + ( pointLat + .00001) +
                    "&swlat=" + ( pointLat - .00001) +
                    "&nelng=" + ( pointLon + .00001) +
                    "&swlng=" + ( pointLon - .00001));
            //System.out.println(url.toString());
        } catch (MalformedURLException e) {
            //Log.e(e.getMessage(), null);
        }

        if (url != null) {
            try {
                HttpURLConnection urlConn = (HttpURLConnection) url
                        .openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    response.append( inputLine );
                }
                in.close();
                urlConn.disconnect();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
	        
	        /*
	         *     END SIGN QUERY 
	         * 
	         */



        zeroOutEverything();

    }


    public static void zeroOutEverything() {
        //fake out everything in sight to ignore the DELETED sign...
        Global_min_distanceFt= 1000.0;		//was 2000.0;

    }

    public static void clearSigns(int tagval) {

        int n= signs.size();


        signs.clear();
        theString= "7";

    }


    public static void clearSigns() {

        int n= signs.size();
        int nn;

        signs.clear();



        //Log.d("TAGG","All " + Integer.toString(n) + " signs deleted. ");

        nn= signs.size();

        //Log.d("TAGG", Integer.toString(nn) + " signs remaining after clearing them all....");
    }


    static int min_distanceFt=0;

    public static int min_distanceFt() {
        //gives distance to closest

        return min_distanceFt;
    }


    static double old_min_latDouble= 99;
    static String theString="";




    //computes angle difference using two course-over-ground's in degrees and also returning degree difference 0...360-1
    public static double cogDiff(double cog1, double cog2) {

        //code borrowed from my half/angle stuff,
        //   so compute half-angles here
        double cogByTwo_x= cog1 / 2.0;
        double cogByTwo  = cog2 / 2.0;
        double i16;
        double diff;

        //simple algorithm to compute angle error.

        if(cogByTwo_x >= cogByTwo) {
            i16= (cogByTwo_x - cogByTwo);
        } else {
            i16= (cogByTwo   - cogByTwo_x);
        }

        if(i16 > 90.0) {
            if(i16 >= 180.0) {
                i16= i16 - 180.0;
            } else {
                i16= 180.0 - i16;
            }
        }

        //convert half-difference into full-difference [0-359]
        diff= i16 * 2.0;

        //Log.d("TAG", "angle: cog1 cog1 diff= " + Double.toString(cog1) + " " + Double.toString(cog2) + " " + Double.toString(diff) );

        return diff;	//difference in degrees
    }





    public static double haversign(double lat1, double lat2, double lon1, double lon2 ) {
        //Haversign formula courtesy:
        //http://www.movable-type.co.uk/scripts/latlong.html

        double R = 6371; // km
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a1 = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1-a1));
        double d = R * c;
        if(mph_kph){
            d= d * 0.621371192;    //miles= km * 0.62miles/km
        } else {
            //already in km
        }
        return d;
    }




}

