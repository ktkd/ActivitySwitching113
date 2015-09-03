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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class Deleter extends Activity implements OnClickListener {

	@Override
	public void onClick(View arg0) {
		
		
	}

	   
	   @Override
	   public void onCreate(Bundle savedInstanceState) { 
	      super.onCreate(savedInstanceState);
	      //setContentView(R.layout.main3);


		   if(Global.manualdelete) {

			   Global.manualdelete=false;

			   Signs contact = Global.deleter_contact;
			   AlertDialog.Builder builder = new AlertDialog.Builder(this);
			   // set title
			   builder.setTitle("(C) " + contact.tag);
			   builder.setMessage("Delete?").setPositiveButton("Yes", dialogClickListener)
					   .setNegativeButton("No", dialogClickListener).show();
			   Log.d("TAGG","TaggedSignFound1");

		   } else {

			   if (Global.andyDelete) {


			   } else {

				   Signs contact = Global.deleter_contact;
				   AlertDialog.Builder builder = new AlertDialog.Builder(this);
				   // set title
				   builder.setTitle("(C) " + contact.tag);
				   builder.setMessage("Delete?").setPositiveButton("Yes", dialogClickListener)
						   .setNegativeButton("No", dialogClickListener).show();
				   Log.d("TAGG", "TaggedSignFound1");
			   }

		   }

	      
	   }
	   

	    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            Signs contact;
				switch (which){
	            case DialogInterface.BUTTON_POSITIVE:
	                //Yes button clicked

	            	int coglow=0;
	            	int coghigh=0;
	            	
	            	contact= Global.deleter_contact;
	            	
	            	if(Global.myCog < (360-45) && Global.myCog > 45) {
	            		
	            		coglow=Global.myCog-45;
	            		coghigh=Global.myCog+45;            	
	            		Global.db.deleteSignWithCOG(contact,coglow,coghigh);
	            		
	            	} else if (Global.myCog <= 45){  
	            		
	            		//gotta do North-west ones in two parts....
	            		coglow=Global.myCog-45;
	            		coglow+=360;
	            		coghigh=360;
	            		Global.db.deleteSignWithCOG(contact,coglow,coghigh);
	            		
	            		coglow=0;
	            		coghigh=Global.myCog+45;            	
	            		Global.db.deleteSignWithCOG(contact,coglow,coghigh);
	            		
	            	} else {
	            		
	            		//gotta do North-west ones in two parts....
	            		coglow=Global.myCog-45;
	            		coghigh=360;
	            		Global.db.deleteSignWithCOG(contact,coglow,coghigh);
	            		
	            		coglow=0;
	            		coghigh=Global.myCog+45;            	
	            		coghigh-= 360;
	            		Global.db.deleteSignWithCOG(contact,coglow,coghigh);
	            	}
	        		
	                break;

	            case DialogInterface.BUTTON_NEGATIVE:
	                //No button clicked
	                break;
	            }
				 
				finish();
				
	        }
	       
	        
	    };
	    
    
    
	
}
    	

