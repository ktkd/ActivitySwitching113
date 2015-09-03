/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/
   
package org.wikispeedia.backseatdriver;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.wikispeedia.roadrage2.R;



import org.wikispeedia.speedlimit.Signs;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class Translate extends Activity implements OnClickListener {
   //public static final String PREFS_NAME = "MyPrefsFile";
    
   private TextView transText;
   private TextView retransText;
   private TextView retransText2;

   private TextWatcher textWatcher;
   private OnItemSelectedListener itemListener;
  
   private Handler guiThread;
   private ExecutorService transThread;
   private Runnable updateTask;
   private Future transPending;
  
   
   
   @Override
   public void onCreate(Bundle savedInstanceState) { 
      super.onCreate(savedInstanceState);


      // Save user preferences as persistent. 
      //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
      //SharedPreferences.Editor editor = settings.edit();
      //editor.putBoolean("NormalRadar", true);	//Global.normal_radar);      
      //to see your stuff go into DDMS->File Explorer->data->data->org.wikispeedia.backseatdriver->shared_prefs
      // Don't forget to commit your edits!!!
      //editor.commit();   
      //editor.apply();
      
      
      
	   onCreate_normal(savedInstanceState);

	   
   }

   
 	
   
   public void onCreate_normal(Bundle savedInstanceState) { 
      
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.main1);
            
      initThreading();
      findViews(); 
      setAdapters(); 
      setListeners(); 
      

   }
   
 	
   
	
   public void onClick(View v) {
	
	  String email= Global.mEmailString;
	  String name=  Global.mUsernameString;
			  
	  Signs contact= new Signs();
	  
	  contact.tag= Global.mUsernameString;
	  
	  boolean andygrid= Global.andygrid;
	   
	  
      switch (v.getId()) {
/*
      case R.id.settings_button:
    	  Intent isettings = new Intent(this,Settings.class);
          startActivity(isettings);
          break;
       */
      
      
      case R.id.about_button1:     
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph= 25;
    	  } else {
    		  contact.kph= 30;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         
         
      case R.id.about_button2:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=30;
    	  } else {
    		  contact.kph= 40;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;

      case R.id.about_button3:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=35;
    	  } else {
    		  contact.kph= 50;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;

      case R.id.about_button4:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=40;
    	  } else {
    		  contact.kph= 60;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         

      case R.id.about_button5:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=45;
    	  } else {
    		  contact.kph= 70;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         
      case R.id.about_button6:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=50;
    	  } else {
    		  contact.kph= 80;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         

      case R.id.about_button7:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=55;
    	  } else {
    		  contact.kph= 90;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         

      case R.id.about_button8:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=60;
    	  } else {
    		  contact.kph= 100;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         

      case R.id.about_button9:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=65;
    	  } else {
    		  contact.kph= 110;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
         

      case R.id.about_button10:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=70;
    	  } else {
    		  contact.kph= 120;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;

      case R.id.about_button11:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=75;
    	  } else {
    		  contact.kph= 130;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;

      case R.id.about_button12:
    	  contact.lat= Global.myLatitude;
    	  contact.lng= Global.myLongitude;
    	  contact.cog= Global.myCog;
    	  if(Global.mph_kph) {
    		  contact.mph=80;
    	  } else {
    		  contact.kph= 140;
    	  }
    	  Global.db.addContactAsync(contact);
    	  if(!andygrid) finish();
    	  
          break;
          
      }
      
   }

   
   
   @Override
   protected void onDestroy() {
	   super.onDestroy();
	   
	   
	   
       save_preferences();
           
   }


   public void save_preferences(){
	   
	      // Save user preferences as persistent. 
	      //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	      //SharedPreferences.Editor editor = settings.edit();
	      
	      //to see your stuff go into DDMS->File Explorer->data->data->org.wikispeedia.backseatdriver->shared_prefs
	      // Don't forget to commit your edits!!!
	      //editor.commit();      
	      //editor.apply();
   }
   
   
   /** Get a handle to all user interface elements */
   private void findViews() {                          
      
	      if(Global.mph_kph == false) {
		      ((Button) findViewById(R.id.about_button1)).setText("30 Kph");
		      ((Button) findViewById(R.id.about_button2)).setText("40 Kph");
		      ((Button) findViewById(R.id.about_button3)).setText("50 Kph");
		      ((Button) findViewById(R.id.about_button4)).setText("60 Kph");
		      ((Button) findViewById(R.id.about_button5)).setText("70 Kph");
		      ((Button) findViewById(R.id.about_button6)).setText("80 Kph");
		      ((Button) findViewById(R.id.about_button7)).setText("90 Kph");
		      ((Button) findViewById(R.id.about_button8)).setText("100 Kph");
		      ((Button) findViewById(R.id.about_button9)).setText("110 Kph");
		      ((Button) findViewById(R.id.about_button10)).setText("120 Kph");
		      ((Button) findViewById(R.id.about_button11)).setText("130 Kph");
		      ((Button) findViewById(R.id.about_button12)).setText("140 Kph");	      
	      }
	   
	      /*
	      // Set up click listeners for all the buttons
	      View settingsButton = findViewById(R.id.settings_button);	      
	      settingsButton.setOnClickListener(this);*/
	      
	      

	      // Set up click listeners for all the buttons
	      View aboutButton1 = findViewById(R.id.about_button1);	      
	      aboutButton1.setOnClickListener(this);
	      
	      View aboutButton2 = findViewById(R.id.about_button2);
	      aboutButton2.setOnClickListener(this);

	      View aboutButton3 = findViewById(R.id.about_button3);
	      aboutButton3.setOnClickListener(this);
	      
	      View aboutButton4 = findViewById(R.id.about_button4);
	      aboutButton4.setOnClickListener(this);
	      
	      View aboutButton5 = findViewById(R.id.about_button5);
	      aboutButton5.setOnClickListener(this);

	      View aboutButton6 = findViewById(R.id.about_button6);
	      aboutButton6.setOnClickListener(this);
	      
	      View aboutButton7 = findViewById(R.id.about_button7);
	      aboutButton7.setOnClickListener(this);
	      
	      View aboutButton8 = findViewById(R.id.about_button8);
	      aboutButton8.setOnClickListener(this);
	      
	      View aboutButton9 = findViewById(R.id.about_button9);
	      aboutButton9.setOnClickListener(this);
	      
	      View aboutButton10 = findViewById(R.id.about_button10);
	      aboutButton10.setOnClickListener(this);
	      
	      View aboutButton11 = findViewById(R.id.about_button11);
	      aboutButton11.setOnClickListener(this);
	      
	      View aboutButton12 = findViewById(R.id.about_button12);
	      aboutButton12.setOnClickListener(this);
	      
	   
	   
//      transText = (TextView) findViewById(R.id.translated_text);
      //retransText = (TextView) findViewById(R.id.retranslated_text);
//      retransText2 = (TextView) findViewById(R.id.retranslated_text2);
   }
   

   
   /** Define data source for the spinners */
   private void setAdapters() {
   
	   
   }
   
       
   
   
   
   /** Setup user interface event handlers */
   private void setListeners() {
      // Define event listeners
      textWatcher = new TextWatcher() {
         public void beforeTextChanged(CharSequence s, int start,
               int count, int after) {
            /* Do nothing */
         }
         public void onTextChanged(CharSequence s, int start,
               int before, int count) {
            queueUpdate(1000 /* milliseconds */);
         }
         public void afterTextChanged(Editable s) {
            /* Do nothing */
         }
      };
      itemListener = new OnItemSelectedListener() {
         @SuppressWarnings("unchecked")
		public void onItemSelected(AdapterView parent, View v,
               int position, long id) {
            queueUpdate(200 /* milliseconds */);
         }
         @SuppressWarnings("unchecked")
		public void onNothingSelected(AdapterView parent) {
            /* Do nothing */
         }
      };

   }
   

   
   /**
    * Initialize multi-threading. There are two threads: 1) The main
    * graphical user interface thread already started by Android,
    * and 2) The translate thread, which we start using an executor.
    */
   private void initThreading() {
      guiThread = new Handler();
      transThread = Executors.newSingleThreadExecutor();

      // This task does a translation and updates the screen
      updateTask = new Runnable() { 
         public void run() {
        	 
       
         	 
            // Cancel previous translation if there was one
            if (transPending != null)
               transPending.cancel(true); 

            
            //getMyLocation();


//            	
//            if(Global.mph_kph) {
//            	setRetranslated2("Current Speed: " + Integer.toString((int)Global.mySpeed), false);
//            } else {
//            	setRetranslated2("Current Speed: " + Integer.toString((int)Global.mySpeed), false);
//            }
//            
//
           /* //allow changing-SETTINGS if stopped, allow DELETES if moving
            if(Global.mySpeed>10 || Global.mySpeed>10) {
            	Global.delete_settings= true;
		      ((Button) findViewById(R.id.settings_button)).setText("Delete");
            } else {
            	Global.delete_settings= false;
            	((Button) findViewById(R.id.settings_button)).setText("Settings");
            }*/
//
//            
//            //put in TEST text if driving slow
//            if(Global.mySpeed<20 || Global.mySpeed<10) {
//		      ((Button) findViewById(R.id.about_button1)).setText("TEST");
//         	} else {
//         		if(Global.mph_kph) {
//         			((Button) findViewById(R.id.about_button1)).setText("25 Mph");
//         		} else {
//         			((Button) findViewById(R.id.about_button1)).setText("30 Kph");
//         		}
//            }
//            
            
         }
      };
   }
   
   
   /** Request an update to start after a short delay */
   private void queueUpdate(long delayMillis) {
   	
      // Cancel previous update if it hasn't started yet
      guiThread.removeCallbacks(updateTask);
      // Start an update if nothing happens after a few milliseconds
      guiThread.postDelayed(updateTask, delayMillis);
   }

   /** Modify text on the screen (called from another thread) */
   public void setTranslated(String text, Boolean red) {
      guiSetText(transText, text, red);
   }
   
   /** Modify text on the screen (called from another thread) */
   public void setRetranslated(String text, Boolean red) {
      guiSetText(retransText, text, red);
   }
   
   /** Modify text on the screen (called from another thread) */
   public void setRetranslated2(String text, Boolean red) {
      guiSetText(retransText2, text, red);
   }

   /** All changes to the GUI must be done in the GUI thread */
   private void guiSetText(final TextView view, final String text, final Boolean red) {
      guiThread.post(new Runnable() {
         public void run() {

            view.setText(text);            
//
//            if(red) {
//            	view.setTextColor(getResources().getColor(R.color.solid_red));
//            } else {
//            	view.setTextColor(getResources().getColor(R.color.solid_black)); 
//            }
            	

            
         }
      });
   }
   
   
   	
	
	
}
    	

