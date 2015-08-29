package org.wikispeedia.zombiepro;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.wikispeedia.roadrage2.R;


//
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
import org.wikispeedia.backseatdriver.Deleter;
import org.wikispeedia.backseatdriver.Global;
import org.wikispeedia.backseatdriver.Translate;
import org.wikispeedia.speedlimit.Signs;

import es.pymasde.blueterm.TermPreferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Activity;



public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	private CanvasThread canvasthread;
	
	static LocationManager lm;

	Bitmap kangoo25 = null;
	Bitmap kangoo30 = null;
	Bitmap kangoo35 = null;
	Bitmap kangoo40 = null;
	Bitmap kangoo45 = null;
	Bitmap kangoo50 = null;
	Bitmap kangoo55 = null;
	Bitmap kangoo60 = null;
	Bitmap kangoo65 = null;
	Bitmap kangoo70 = null;
	Bitmap kangoo75 = null;
	Bitmap kangoo80 = null;
	Bitmap kangoo85 = null;
	Bitmap kangoo90 = null;
	Bitmap kangoo100 = null;
	Bitmap kangoo110 = null;
	Bitmap kangoo120 = null;
	Bitmap kangoo130 = null;
	Bitmap kangoo140 = null;
	
	Bitmap red0 = null;
	Bitmap red45 = null;
	Bitmap red90 = null;
	Bitmap red135 = null;
	Bitmap red180 = null;
	Bitmap red225 = null;
	Bitmap red270 = null;
	Bitmap red315 = null;
	Bitmap red123 = null;
	
	Bitmap bluecog= null;

    int xresol_10;
    int xresol_2;
    int yresol_10;
    int yresol_2;

    
	Path myrect=null;
	 
	int xresol;
	int yresol;
	
	static Timer mmTimer;
	 
	static int nDoubles;

    //[ . . . ]
    // Need handler for callbacks to the UI thread
    final static Handler mHandler = new Handler();
  
    // Create runnable for posting
    final static Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };
    
    private static void updateResultsInUi() {
  
        Log.d("TAGG","Back in the UI thread -- update our UI elements based on the data in mResults");
         
        //Log.d("TAGG","finish");
        //finish();
      //  [ . . . ]
    }
    
    public Panel(Context context, AttributeSet attrs) {
		super(context, attrs); 
		
		
		
		// TODO Auto-generated constructor stub
	    getHolder().addCallback(this);
	    canvasthread = new CanvasThread(getHolder(), this);
	    setFocusable(true);
	    

		kangoo25 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo25);
		kangoo30 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo30);
		kangoo35 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo35);
		kangoo40 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo40);
		kangoo45 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo45);
		kangoo50 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo50);
		kangoo55 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo55);
		kangoo60 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo60);
		kangoo65 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo65);
		kangoo70 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo70);
		kangoo75 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo75);
		kangoo80 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo80);
		kangoo85 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo85);
		kangoo90 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo90);
		kangoo100 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo100);
		kangoo110 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo110);
		kangoo120 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo120);
		kangoo130 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo130);
		kangoo140 = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo140);
		

		red0 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red0);
		red45 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red45);
		red90 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red90);
		red135 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red135);
		red180 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red180);
		red225 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red225);
		red270 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red270);
		red315 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red315);
		
		red123 = BitmapFactory.decodeResource(getResources(),
				R.drawable.red123);
		
		
		bluecog = BitmapFactory.decodeResource(getResources(),
				R.drawable.bluecog);
	
		

		
		

		 latheighthalf=latheight/2.0f;
		 lonwidthhalf=lonwidth/2.0f;
		

		 xresol= Global.width;
		 yresol= Global.height;
		 if(xresol==0) {		//chicken egg workaround
			Log.d("TAGGG","bug jim");
		 }
			
	    
		 myrect = new Path();
		 myrect.moveTo(30, 0-yresol); 
		 myrect.lineTo(30, yresol);
		 myrect.lineTo(-30, yresol);
		 myrect.lineTo(-30, 0-yresol);
		 myrect.close();
		 myrect.offset(xresol/2, yresol/2);
		

	     xresol_10= xresol/10;
	     xresol_2= xresol/2;
	     yresol_10= 39;			//jim was   yresol/10;
	     yresol_2= yresol/2;

			
			
		 
         String string = Global.rgb;
         String[] parts = string.split(",");         
         try {
        	 //litmus tests to see if they are numbers        
             line_red = Integer.parseInt(parts[0]);
             line_green = Integer.parseInt(parts[1]);
             line_blue = Integer.parseInt(parts[2]);
             bullseye_red = Integer.parseInt(parts[3]);
             bullseye_green = Integer.parseInt(parts[4]);
             bullseye_blue = Integer.parseInt(parts[5]);
         } catch(Exception e) {
        	 Global.rgb= "77,119,67,77,119,87";			//just force it to something so we don't get exceptions all day long
             line_red = Integer.parseInt("77");
             line_green = Integer.parseInt("119");
             line_blue = Integer.parseInt("67");
             bullseye_red = Integer.parseInt("77");
             bullseye_green = Integer.parseInt("119");
             bullseye_blue = Integer.parseInt("87");      
         }
         	        
		 
	}

  int line_red,line_green,line_blue;
  int bullseye_red,bullseye_green,bullseye_blue;
   
    
    
    static boolean cruiseon=false;
    
    Integer t=0;
    
    
    


	 public Panel(Context context) {
		   super(context);
		    getHolder().addCallback(this);
		    canvasthread = new CanvasThread(getHolder(), this);
		    setFocusable(true);

	    }

	int ii=0;

	static float latheighthalf,lonwidthhalf;
	static float lattop,latbottom,lonleft,lonright;

	//fill these in per phone.

	
	
	//static float xresol=330f;
	//static float yresol=430f;
	
	
	

	static float latheight=0.01f;
	static float lonwidth=0.01f;
	
	static boolean busy=false;

	int mycog= 0;
	
	static int spin;
	static int oldspin=361; //trick to force first distance denominator calculation
	static int pthreshold,mthreshold=-2;	//trying to prevent jitter
	

	
	
	   Float close_b=0f;
	   Float close_m=0f;
	   int close_x=0;
	   int close_y=0;
	   Float close_d;
	   Float close_numerator;
	   Float close_denomanator=1f;
	   Float close_inverseden=1f;
	   Float close_guts;
	   Double close_dguts;
	   
	   

		 
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
						
			//if(!Global.threadisrunning) {
				canvasthread.setRunning(true);		    
		    	canvasthread.start();
			//}
			
			//Global.threadisrunning=true;
			
		}
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			boolean retry = true;
			canvasthread.setRunning(false);
			while (retry) {
				try {
					canvasthread.join();
					retry = false;
				} catch (InterruptedException e) {
					// we will try it again and again...
				}
			}
			
	        
	        

		}
	    
		

	static Boolean doslowdown=false;
	
	static Signs val;
	
	Date thisdate=null, lastdate=null;  
	   
		 
	@Override
	public void onDraw(Canvas canvas) {
		//Log.d("ondraw", "lefutott");
		Paint paint = new Paint();
		int isRusty = 1;
		
		busy=true;
		try {
			Thread.sleep(100);   //jp was 100  jim todo   feb 22 2015
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(!Global.locFound) {
			busy=false;
			return;
		}
		
		if(Global.thecontactlistbusy) {
			return;
		}

		try {
			canvas.drawColor(Color.BLACK);
		} catch (Exception e) {
			e.printStackTrace(); 
			return;
		}
		

		 xresol= Global.width;
		 yresol= Global.height;
		 if(xresol==0) {		//chicken egg workaround
			 xresol=480;	//320;
			 yresol=320;	//480;
		 }

		 //if(true) {
		//	 Log.d("TAGG","TESTTEST");
		//	 Global.mySpeed=10;
		 //}
		 //Global.mySpeed=10.0;
		  	
		 
		//draw cog green rectangle
		Global.spinlocked=false;
		if(Global.mySpeed >= 10 ) {			
			int diff;
			diff= Global.myCog - spin;
			if(diff < -180) {
				diff+=360;
			}
			if(diff>pthreshold) {
				pthreshold=5;
				mthreshold=0;
				spin+=1;					
			} else if(diff<mthreshold) {
				mthreshold=-5;
				pthreshold=0;
				spin-=1;
			} else {
				Global.spinlocked=true;
			}
			
			//Log.d("TAGG","Global.myCog= " + Global.myCogString + " spin=" + Integer.toString(spin));
			
			
			canvas.rotate(spin,xresol/2, yresol/2);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
	        paint.setStrokeWidth(2);
	        
	        
        	paint.setColor(Color.rgb(line_red, line_green, line_blue)); //Color.rgb(144,238,144));	//Color.rgb(0, 38, 0));	//r,g,b
        	canvas.drawPath(myrect, paint);    
        	canvas.rotate(0-spin,xresol/2, yresol/2);
		}
		
        //draw bullseye
        paint.setColor(Color.rgb(bullseye_red, bullseye_green, bullseye_blue));	//r,g,b
        canvas.drawCircle(xresol/2, yresol/2, 40, paint);
		

		
		
		
		if(Global.thecontactList==null) {
			busy=false;
			return;
		}
		
		int n= Global.thecontactList.size();
		
		if(n==0) {
			
			if(Global.tapped) {
				int i=1;
			//	Log.d("TAGG","test");
				stuff();
			//	Global.tapped=false;
			}
			
			busy=false;
			return;
		}
		
		/*
		if(n>10) {
			Log.d("TAGG","Limiting n to 10= " + Integer.toString(n));
			n=10;
		}*/
		
		
		
		
		
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
		
		

		//Log.d("TAGG","n=" + Integer.toString(n));
		
		nDoubles= 0;
		int oldmph= 0;
		
		Global.visible_contact= null;
		Global.thecontactListindex= -1;
		
		int index=0;
		
		for(i=0;i<n;i++) {
			
		   index=i;
			
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
	       Bitmap bp;
	       switch (speed) {
	            case 25:  bp = kangoo25;
	                     break;
	            case 30:  bp = kangoo30;
	                     break;
	            case 35:  bp = kangoo35;
	                     break;
	            case 40:  bp = kangoo40;
	                     break;
	            case 45:  bp = kangoo45;
	                     break;
	            case 50:  bp = kangoo50;
	                     break;
	            case 55:  bp = kangoo55;
	                     break;
	            case 60:  bp = kangoo60;
	                     break;
	            case 65:  bp = kangoo65;
	                     break;
	            case 70: bp = kangoo70;
	                     break;
	            case 75: bp = kangoo75;
	                     break;
	            case 80: bp = kangoo80;
	                     break;
	            case 85: bp = kangoo85;
	            		 break;
	            case 90: bp = kangoo90;
	                     break;
	            case 100: bp = kangoo100;
	                     break;
	            case 110: bp = kangoo110;
	                     break;
	            case 120: bp = kangoo120;
	                     break;
	            case 130: bp = kangoo130;
        				break;
	            case 140: bp = kangoo140;
        				break;
	            default: bp = kangoo85;
                 		break;
	       }
	        
	       
	       int cog= val.cog;
	       Bitmap bpcog= bluecog;
	    		   
	       Bitmap bp2=null;
	       switch ((int)(cog/45)) {
           		case 0:  bp2 = red0;
                    	break;
           		case 1:  bp2 = red45;
           				break;
       			case 2:  bp2 = red90;
           				break;
       			case 3:  bp2 = red135;
           				break;
       			case 4:  bp2 = red180;
           				break;
       			case 5:  bp2 = red225;
           				break;
       			case 6:  bp2 = red270;
           				break;
       			case 7:  bp2 = red315;
                    	break;
       			default: 
           				break;
	       }

	       
	       if(showdistance && showcog) {
	    	   
		       //if sign wanders thru middle, light it up.
		       int dxx = Math.abs(xi - (xresol_2));
		       int dyy = Math.abs(yi - (yresol_2));
		       if (dxx<(xresol_10) && dyy<(yresol_10)) {
		    	   bp2= red123;
		    	   
			       if(Global.theSpeedLimitIsDouble != speed) {
			    	   Global.theSpeedLimitIsDouble= speed;
			    	   //Log.d("TAGG","sign nearby, new psl= " + Integer.toString(speed) );
			       }
			       
		    	   if(Global.mySpeed > (Global.theSpeedLimitIsDouble + Global.over)) {
		    		   cruiseControl();
		    	   }

		    	   //if multi signs, delete newer ones
		    	   if(Global.andyDoubleDeleter) {
		    		   
		    		   thisdate= getdate(val);
		    		   
			    	   if(val.mph == oldmph) {		    		   
			    		   			    		   
			    		   if(thisdate.compareTo(lastdate)>0){
					    		//System.out.println("left is after right");  		
					    		Global.visible_contact= val;
				    			Global.thecontactListindex= index;	      					   				    		
					    	}  
			    	  	
			    	   }
			    	   oldmph= val.mph;
			    	   lastdate= thisdate;
		    	   }  
		    	   
		    	   
		    		

		       }	    	  
		       

		       canvas.drawBitmap(bp, xi-18, yi-18, null);
			   if(bp2!=null) {
				   canvas.drawBitmap(bp2, xi-18, yi-18, null);
			   }    	   
			   if(Global.tapped) {
				   //tap in middle of 36x37bitmap sign, so translate to top left corner which is 1/2= 18
				   int xdiff= (Global.xtap - 18) - xi;		
				   int ydiff= Global.ytap - 18 - yi;
				   int delta= Math.abs(xdiff) + Math.abs(ydiff);
		
				  
				   if (delta<50) {
					   Log.d("TAGG","JIM TODO take out winner, i= " + Integer.toString(i) );
					   //Global.tapped=false;			   
					   
					   Global.tappedSignFound = i;					   
				   	}
				   					   
			   }
			   			 
			   
			   //just add one each Panel. That way not too much burden....
			   if (val.tag.equals("Joseph Warner") || val.tag.equals("Ben de Waal") || val.tag.equals("John Carr")) {
    			   Global.visible_contact= val;
    			   Global.thecontactListindex= index;	       			   
			   }
			   
			   
	       } else {
	    	   canvas.drawBitmap(bpcog, xi, yi,null);	    	   
	       }
		   
		   
		   //Log.d("TAGG","delta= " + Integer.toString(delta));
	       
	       
		}
		
	
		if(Global.andyDelete && Global.spinlocked) {			

			
			if(Global.thecontactListindex > -1) {
			  
   			   	   Global.db.deleteSignAsync(Global.visible_contact);   
   			   	   
   			   	   Global.thecontactList.remove(Global.thecontactListindex);
   			    

   				Global.visible_contact= null;
   				Global.thecontactListindex= -1;
   				
			}
	    }
		
		
		
        if(Global.tapped) {
        	stuff();    	  
    	   Global.tapped=false;
        }
       
		
		//Global.tapped=false;	//tap not found, so keep it from hanging
		
		busy=false;
		
	}
	 


	Date getdate(Signs val) {

		Date thedate = null;

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	  	
		
		//create thedate as submit date
			try {
				thedate = formatter1.parse("2000-01-01 00:00:00");
			} catch (Exception e1) {	
			}
			 			
		try {
			   thedate = formatter1.parse(val.sutc);    			
		} catch (Exception e) {
   		    try {
    			   thedate = formatter2.parse(val.submittedOn);
    		} catch (Exception ee) {        		
    		}
		}
		
   		Log.d("TAGG","");
   		return thedate;
   		
		
	}
	
	
	
	
	
	void stuff() {
		    	
		Global.tapped= false;
		
    	if(Global.tappedSignFound >= 0) {
    		
    		int i = Global.tappedSignFound;
    		Global.deleter_contact = Global.thecontactList.get(i);
    		
    		Global.tappedSignFound=-1;
    		
    		Global.panelthis.startActivity(new Intent(Global.panelthis, Deleter.class));
    		
    	   
    	} else {
    		Log.d("TAGG", "no sign tapped, so do SETTINGS");
    	
    		//Log.d(DEBUG_TAG,"Action was DOWN");
	  		
    		
    		Global.panelthis.startActivity(new Intent(Global.panelthis, Translate.class));
    		
    		//Global.tappedSignFound=-1;
    		
	  			
    	}
    	
		
	}

	
    
    static Signs contact;
    
	public static void cruiseControl() {
		startLongRunningOperation();
	}
	

	public static void cruiseControl2() {
		startLongRunningOperation2();
	}
	
	
	
	public static void startLongRunningOperation() {

		if(doslowdown) {
			return;
		}
		doslowdown=true;
		
	    // Fire off a thread to do some work that we shouldn't do directly in the UI thread
	    Thread t = new Thread("SlowDownThread") {
	        public void run() {
	            Object mResults = doSomethingExpensive();
	            mHandler.post(mUpdateResults);
	        }
	    };
	    t.start();
	}
	  

	public static void startLongRunningOperation2() {

		if(doslowdown) {
			return;
		}
		doslowdown=true;
		
	    // Fire off a thread to do some work that we shouldn't do directly in the UI thread
	    Thread t = new Thread("SlowDownThread") {
	        public void run() {
	            Object mResults = doSomethingExpensive2();
	            mHandler.post(mUpdateResults);
	        }
	    };
	    t.start();
	}
	  
	




    
    public static Object doSomethingExpensive() {
         
        Log.d("TAGG","doSomethingExpensive");
          
 
            //before http, set off a failsafe timer to end it all so we dont get ANR hangs!!!
            if(true) {
                 Log.d("TAGG","in thread: Setup 30 second timer!");
                 mmTimer = new Timer("slowDownFailsafeTimer");
  
                 TimerTask mmTimerTask = new TimerTask() {
                     public void run() {
                    	 
                            mmTimer.cancel();
                            
                            org.wikispeedia.roadrage2.Activity1.furthestOFF();
                            Log.d("TAGG","30sec elapsed done....  decel=OFF");
                            doslowdown=false;   
                          
                     }
                 };
                  
                 mmTimer.schedule(mmTimerTask, 20000, 20000);         
                        
            }         
         
            Log.d("TAGG","decel=ON");
            org.wikispeedia.roadrage2.Activity1.furthestON();
          
            Boolean keepgoing=true;
            while(keepgoing) {
            	
	            if(Global.mySpeed < (Global.theSpeedLimitIsDouble + Global.over) ) {
	            	keepgoing=false;
	            }
	            
            	try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
            }
                        
            Log.d("TAGG","decel=OFF   Finished before 30s, calling timerCancel.  ");
            org.wikispeedia.roadrage2.Activity1.furthestOFF();            
            mmTimer.cancel();
            doslowdown=false; 
            
        return null;
    }
    
    

    public static Object doSomethingExpensive2() {
         
        Log.d("TAGG","doSomethingExpensive2");
          
 
            //before http, set off a failsafe timer to end it all so we dont get ANR hangs!!!
            if(true) {
                 Log.d("TAGG","in thread: Setup 30 second timer!");
                 mmTimer = new Timer("slowDownFailsafeTimer2");
  
                 TimerTask mmTimerTask = new TimerTask() {
                     public void run() {
                    	 
                            mmTimer.cancel();
                            org.wikispeedia.roadrage2.Activity1.furthestOFF();
                            Log.d("TAGG","30sec elapsed done....  decel=OFF");
                            doslowdown=false;   
                          
                     }
                 };
                  
                 mmTimer.schedule(mmTimerTask, 20000, 20000);         
                        
            }         
         
            Log.d("TAGG","decel=ON");
            org.wikispeedia.roadrage2.Activity1.furthestON();
          
            Boolean keepgoing=true;
            while(keepgoing) {
            	
	            if(Global.mySpeed < (Global.theSpeedLimitIsDouble - 10) ) {
	            	keepgoing=false;
	            }
	            
            	try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
            }
                        
            Log.d("TAGG","decel=OFF   Finished before 30s, calling timerCancel.  ");
            org.wikispeedia.roadrage2.Activity1.furthestOFF();            
            mmTimer.cancel();
            doslowdown=false; 
            
        return null;
    }
    
     
     
    void turncruiseonoroff() {

        if(Global.mySpeed > (Global.theSpeedLimitIsDouble + Global.over)) {
     	               	   
     	   if(cruiseon) {
     		   
     	   } else {     		   
 		   	   //hit set button (which is also the set/decel button...)
 		   	   cruiseon=true;

 		  	    // Fire off a thread to do some work that we shouldn't do directly in the UI thread
 		  	    Thread t = new Thread("SlowDownThread2") {
 		  	        public void run() {
 		  	        	org.wikispeedia.roadrage2.Activity1.furthestON();
 		  	        	 try {
 		  					Thread.sleep(500);
 		  				} catch (InterruptedException e) {
 		  				}
 		  	        	org.wikispeedia.roadrage2.Activity1.furthestOFF();
 		  	        }
 		  	    };
 		  	    t.start(); 		  	
     	   } 
     	   
        }
        
        if(Global.mySpeed < 25) {
        	//turn cruise off since you apparently hit the brakes....
        	cruiseon=false;
	        //org.wikispeedia.zombiepro.Canvastutorial.furthestOFF();             
        }
        
    }

    
   
    
    

}   