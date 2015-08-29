/* Any questions contact speedup@wikispeedia.org  No warranty. Have fun. */


package org.wikispeedia.backseatdriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;








import org.wikispeedia.speedlimit.Box;
import org.wikispeedia.speedlimit.Signs;
import org.wikispeedia.speedlimit.SpeedlimitManager3;
import org.wikispeedia.zombiepro.Panel;






import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.wikispeedia.roadrage2.R;

public class Settings extends Activity {
	
	private static AppPreferences _appPrefs;
	
	
	   public static final String PREFS_NAME = "MyPrefsFile";

	   private Spinner fromSpinner;
	   private Spinner fromSpinner2;
	   private Spinner fromSpinner3;
	   
	   private TextWatcher emailWatcher;
	   private TextWatcher usernameWatcher;
	   private TextWatcher allWatcher;
	   private TextWatcher overWatcher;
	   private TextWatcher rgbWatcher;
	   private OnItemSelectedListener itemListener;
	   private OnItemSelectedListener itemListener2;	
	   private OnItemSelectedListener itemListener3;	
	   private EditText usernameText;
	   private EditText allText;
	   private EditText overText;
	   private EditText rgbText;
	   private EditText origText;
	   private EditText syncText_n;
  
	   private static Context theoldcontext=null;
	   
    @Override
    public void onCreate(Bundle savedInstanceState) {         
        super.onCreate(savedInstanceState);

        _appPrefs = new AppPreferences(getApplicationContext());
        
        //multipurpose button.
        if(Global.delete_settings==true) {
        	onCreate_delete();
        } else {
        	onCreate_settings();
        }       
        
        theoldcontext= this;
    }
    
  

        
       
   
    

    //multipurpose button
    public void onCreate_settings() {
	        setContentView(R.layout.settings);
	               
	        //initThreading();
	        findViews_settings(); 
	        setAdapters_settings(); 
	        setListeners_settings(); 
	
	        //initialize username and email EditText boxes
	        usernameText.setText(Global.mUsernameString);
	             allText.setText(Global.all);	            				
				 String over = Integer.toString(Global.over);
	             overText.setText(over);
	             rgbText.setText(Global.rgb);
	        origText.setText(Global.mEmailString);	        
    }
        
    
    //multipurpose button            
    public void onCreate_delete() {
	        setContentView(R.layout.delete);        
	        
	        //TODO, need a 10 second timeout and window closer...
	
	        //jim re add this. I just stubbed it OFF for ZOMBIE testing
	        //Global.slm.deleteNearestSpeedLimitSign();
	        
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    }
    
    @Override
    protected void onStop(){
       super.onStop();
   
       
       //multipurpose button.
       if(Global.delete_settings==true) {
       		onStop_delete();
       } else {
       		onStop_settings();
       }       
    }


    

    public  void onStop_settings(){
       super.onStop();
           
    }


    public void onStop_delete(){
       super.onStop();
       
    }

    
    /** Get a handle to all user interface elements */
    private void findViews_settings() {
       fromSpinner = (Spinner) findViewById(R.id.mph_kph_language);
       fromSpinner3= (Spinner) findViewById(R.id.syncnow_language);
       usernameText = (EditText) findViewById(R.id.username_field);
          origText  = (EditText) findViewById(R.id.url_field);
            allText = (EditText) findViewById(R.id.all_field);
            overText= (EditText) findViewById(R.id.over_field);
            rgbText = (EditText) findViewById(R.id.rgb_field);
            
       		String todayAsString2=null;
       		try {
       			todayAsString2 = Global.db.getLastSync();						        			
       		} catch (Exception e) {
       			Log.d("TAGG","shouldnt get here. if it does, fix it jim");
       		}
       		//Global.nSignsNotSynced =  Global.db.getAllofThemSinceN(todayAsString2);
       		if(Global.nSignsNotSynced >0) {
            	TextView myTextView= (TextView) findViewById(R.id.mytextview); 
            	myTextView.setText(Integer.toString(Global.nSignsNotSynced) + " not synced. " +
                       Integer.toString(Global.nSignsNotSynced_wentoutlasttime) + "=lasttime."
                );
            }
            
            
            
    }
    
        
    
    /** Define data source for the spinners */
    private void setAdapters_settings() {
    	
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
             this, R.array.languages,
             android.R.layout.simple_spinner_item);
       
       adapter.setDropDownViewResource(
             android.R.layout.simple_spinner_dropdown_item);
       
       fromSpinner.setAdapter(adapter);
       
       if(Global.mph_kph == true){
    	   fromSpinner.setSelection(0);  //0=Mph  
       } else {
    	   fromSpinner.setSelection(1);  //1=Kph
       }
       
       
       
       

       ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
             this, R.array.languagessss,
             android.R.layout.simple_spinner_item);
             
       adapter3.setDropDownViewResource(
             android.R.layout.simple_spinner_dropdown_item);
       
       fromSpinner3.setAdapter(adapter3);
      
    	   //fromSpinner3.setSelection(1);  //1= yes sync now
    	   fromSpinner3.setSelection(0);  //0= no
           
       
       
       
       
       
       
       
       
       
    }
    
    

    /** Setup user interface event handlers */
    private void setListeners_settings() {
       emailWatcher = new TextWatcher() {
          public void beforeTextChanged(CharSequence s, int start,
                int count, int after) {
          }
          public void onTextChanged(CharSequence s, int start,
                int before, int count) {
        	  Global.mEmailString= s.toString();        	  
          }
          public void afterTextChanged(Editable s) {
          }
       };
  
       usernameWatcher = new TextWatcher() {
           public void beforeTextChanged(CharSequence s, int start,
                 int count, int after) {
           }
           public void onTextChanged(CharSequence s, int start,
                 int before, int count) {
         	  Global.mUsernameString= s.toString();        	  
           }
           public void afterTextChanged(Editable s) {
           }
        };   

        allWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start,
                  int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                  int before, int count) {
          	  Global.all= s.toString();        	  
            }
            public void afterTextChanged(Editable s) {
            }
         };   
         

         overWatcher = new TextWatcher() {
             public void beforeTextChanged(CharSequence s, int start,
                   int count, int after) {
             }
             public void onTextChanged(CharSequence s, int start,
                   int before, int count) {
              String tmps="";
           	  tmps= s.toString();  	
	          try {	        	  
	        	    int tmpi;
	           		tmpi = Integer.parseInt(tmps);
	           		Global.over=tmpi;
	          } catch(Exception e) {
	        	  Log.d("TAGG","ERROR: OVER must be an integer");
	          }
           	
           	
           	
           	  
           	
             }
             public void afterTextChanged(Editable s) {
             }
          };   
          
          
         rgbWatcher = new TextWatcher() {
             public void beforeTextChanged(CharSequence s, int start,
                   int count, int after) {
             }
             public void onTextChanged(CharSequence s, int start,
                   int before, int count) {
            	 
            	 String tmp = s.toString();
                 String[] parts = tmp.split(",");        
                 try {
                	 //litmus tests to see if they are numbers        
                     Integer.parseInt(parts[0]);
                     Integer.parseInt(parts[1]);
                     Integer.parseInt(parts[2]);
                     Integer.parseInt(parts[3]);
                     Integer.parseInt(parts[4]);
                     Integer.parseInt(parts[5]);
                     Log.d("TAGG","Global.rgb set to " + tmp);
                     Global.rgb= tmp;
                 } catch(Exception e) {
                 }
             }
             public void afterTextChanged(Editable s) {
             }
          };   
          
       itemListener = new OnItemSelectedListener() {
          public void onItemSelected(AdapterView parent, View v,
                int position, long id) {
        	  
        	  if(id==0) {
        		  Global.mph_kph= true;		//mph
        	  } else {
        		  Global.mph_kph= false;	//kph
        	  }
          }
          
          public void onNothingSelected(AdapterView parent) {
          } 
       };


         
       

       itemListener3 = new OnItemSelectedListener() {
          public void onItemSelected(AdapterView parent, View v,
              int position, long id) {
        	  
        	  if(id==0) {
        	  } else {
        		  syncnow();        		  
        		  Log.d("TAGG","calling finish()");   
        		  finish();        		  
        	  }        	  
          }                    
          public void onNothingSelected(AdapterView parent) {
          } 
       };
         
       
       // Set listeners on graphical user interface widgets
       usernameText.addTextChangedListener(usernameWatcher);
       origText.addTextChangedListener(emailWatcher);
       fromSpinner.setOnItemSelectedListener(itemListener);
          
       fromSpinner3.setOnItemSelectedListener(itemListener3);        
       allText.addTextChangedListener(allWatcher);
       overText.addTextChangedListener(overWatcher);
       rgbText.addTextChangedListener(rgbWatcher);
       
    }
    

	public static void getsigns(Boolean firsttime) {
		
		
		
    	Double lat;
    	Double lon;
    	String journeyOnAsString="";
    	Date journeyOnAsDate=null;
    	Double mylat,mylon;
    	Signs myguy= null;
    	Date targetAsDate = null;
    	String oldDateString = null;
		String newDateString = null;
		
    	Integer n;
    	Integer error=0;
    	List<Signs> thecontactList= new ArrayList<Signs>();
    	List<Signs> signs= new ArrayList<Signs>();
        	
    	
    	
    	
    	

    	if(false){
    		//gets 5000 signs method
    		String since= Global.db.getLastSync();
    		Box b= new Box();
    		b= Global.db.getExtremes();
			try {
				signs= org.wikispeedia.speedlimit.SpeedlimitManager3.doTranslate2(b.swlat, b.swlon, b.nelat, b.nelon, since);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} 			
			
			int nn=0;
			try {
				nn= signs.size();
			}catch(Exception e) {
				Log.d("TAGG","Settings n=0");
			}
			if(nn>0) {			    			
	    			for(int j=0;j<nn;j++) {
	    				Signs myguy2= signs.get(j);	    				
						final String OLD_FORMAT = "yyyy-MM-dd";	
						final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
						oldDateString = myguy2.sutc;
						myguy2.sutc= oldDateString;
	    				if(myguy2.dutc != null) {
		    				Log.d("TAGG","dutc true");
	    				} else if (myguy2.deletedOn != null) {			    					
	    					Log.d("TAGG","deletedOn true");
		    			}	else {
		    				myguy2.web_local = true;	//web
		    				Global.db.addContact(myguy2);
		    			}
	    			}   	
			}
			
			thecontactList.clear();  //trying to get rid of GC_FOR_MALLOC's I see in log. 
    	}
    	
    	
    	if(false) {
    		//breadcrumb method
    		
       	 	thecontactList= Global.db.getJourneys();
       	 	n= thecontactList.size();	    	   
       	 	
       	 	for (int i = 0; i < n; i++) {
       	 		myguy = thecontactList.get(i);
       	 		mylat = myguy.lat;
       	 		mylon = myguy.lng;
       	 		journeyOnAsString= myguy.sutc;
       			try{
       				signs.clear();	//perhaps this will get rid of all the GC_FOR_MALLOC's junk I see in log......
       			} catch(Exception e){       				
       			}
       			String since= Global.db.getLastSync();
    			signs= org.wikispeedia.speedlimit.SpeedlimitManager3.doTranslate(mylat, mylon, since); 
    			    			
    			
    			
    			int nn=0;
    			try {
    				nn= signs.size();
    			}catch(Exception e) {
    				Log.d("TAGG","Settings n=0");
    			}
    			if(nn>0) {			    			
		    			for(int j=0;j<nn;j++) {
		    				Signs myguy2= signs.get(j);	    				
							final String OLD_FORMAT = "yyyy-MM-dd";	
							final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
							oldDateString = myguy2.sutc;
							myguy2.sutc= oldDateString;
		    				if(myguy2.dutc != null) {
			    				Log.d("TAGG","dutc true");
		    				} else if (myguy2.deletedOn != null) {			    					
		    					Log.d("TAGG","deletedOn true");
			    			}	else {
			    				myguy2.web_local = true;	//web
			    				Global.db.addContact(myguy2);
			    			}
		    			}   	
    			}
       	 	}
       	 	thecontactList.clear();  //trying to get rid of GC_FOR_MALLOC's I see in log. 
			 
    	}
    	     


       	 	    		
	    	 
	}
	    	 

    
	public static  void addExtraJourneys() {
		Log.d("TAGG","in addExtraJourneys()");
		
		if(!Global.locFound) {
			Log.d("TAGG","addExtraJourneys(): no gps. can't do it, oh well.");
			return; 
		}
		
    	Signs contact= new Signs();
    	
    	Integer n=10;
    	
    	//n
   	 	for (int i = 1; i < n; i++) {
   	 		contact.lat = Global.myLatitude + (0.004 * i);
   	 		contact.lng  = Global.myLongitude ;
   	 		Global.db.addJourney(contact);   	 		
   	 	}
   				
   	 	//s
   	 	for (int i = 1; i < n; i++) {
   	 		contact.lat = Global.myLatitude - (0.004 * i);
   	 		contact.lng = Global.myLongitude;
   	 		Global.db.addJourney(contact);   	 
   	 	}

   	 	//e
   	 	for (int i = 1; i < n; i++) {
   	 		contact.lat = Global.myLatitude ;
   	 		contact.lng = Global.myLongitude + (0.004 * i);
   	 		Global.db.addJourney(contact); 
   	 	}

   	 	//w
   	 	for (int i = 1; i < n; i++) {
   	 		contact.lat = Global.myLatitude;
   	 		contact.lng = Global.myLongitude - (0.004 * i);
   	 		Global.db.addJourney(contact); 
   	 	}
   	 	
	
    	      	 	
	}
    
    
    public static void syncnow() {
    	Log.d("TAGG","SYNC NOW");

  
    

    	//Log.d("TAGG","TODO Jim.  Getsigns removed for Andy USA HWY version");
    	if(true){
    	getsigns(false);    	
    	}
    	
    	//still need this but not really today
    	//delete_dups();
    	

    	putsigns();
    	
    	
    	
    	//need some kind of dup deleter here jim
    	
    	
    	

	    	
	    if(true){
	    	if(Global.no_wifi_found) {
	    		Log.d("TAGG","no wifi found");
	    	} else {
	        	String mydate5= org.wikispeedia.backseatdriver.Global.getGmtDateAsString();
	    		Global.db.setlastsync(mydate5);        	   	
	    	}
	    }
	    
   	         	        	
    	
    	Global.db.clearJourneys();
    	Log.d("TAGG","after clearJourneys");
	
    }



	public static void putsigns()  {
		    	
		Global.no_wifi_found=false;
		
    	
   	 	Date subOn=null;
   	 	Date delOn=null;
   	

		
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatterx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formattery = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String someString2="";
		Date lastsyncAsDate= null;
		String todayAsString2=null;
		try {
			someString2 = Global.db.getLastSync();						
			lastsyncAsDate = formatter.parse(someString2);
			todayAsString2= someString2;
		} catch (Exception e) {
			Log.d("TAGG","shouldnt get here. if it does, fix it jim");
		}
		
		//JIM BANDAID!!! turn back on.
		if(false){
			//On first time app install, or if DB is sideloaded, this kludge keeps from syncing too much, just force in max value from db.
			if (todayAsString2.equals("2000-01-01 01:01:01")) {
				todayAsString2= Global.db.getLatestDate();
				try {
					lastsyncAsDate = formatterx.parse(todayAsString2);
				} catch  (Exception ef) {
				}
				try {
	        		Global.db.setlastsync(todayAsString2);		
	        	} catch (Exception ee) {        		
	        	}			
			}
		}
		
		
		
		
		List<Signs> signsinx = new ArrayList<Signs>();
		
		
		//trick to reset date 
		//if(false ) {
			signsinx= Global.db.getAllofThemSince(todayAsString2);
		//}
		
			
		int isize= 0;
		isize= signsinx.size();

		Global.nSignsNotSynced= isize;


				
		//jim, turn off phpmyadmin for everyone before sync!!! You have been warned!!!!
		//jim, make sure phone browser works to get to internet. It will fix it!!!!

		
		Integer ix= 0;	//used to tip toe the data into website...  2475;

		Global.no_wifi_found= false;
		
		
		Signs sign = null;
		
		mainLoop:			
        for(; ix < isize; ix++) {
        	
        		        		        		
        	if(ix==350) {
        		int i=ix;
        		i=ix;
        		
        	}
        	
        
        	sign= signsinx.get(ix);
    		
    		Log.d("TAGG","ix= " + Integer.toString(ix));
    		
			try {
				 subOn = formatter.parse(sign.sutc);
				} catch (Exception e) {
					e.printStackTrace();
				}

			delOn=null;
			if(sign.dutc!=null) {						 
				 try {
					 delOn = formatter.parse(sign.dutc);
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
    		}

	    	Boolean dosub= false;
	    	if(subOn==null) {
	    		dosub=true;			//this means its fresh new submitted by me and not synced in yet.
	    	} else {
		    	if(subOn.compareTo(lastsyncAsDate)>0){
		    		System.out.println("subOn is after lastsyncAsDate");
		    		dosub=true;
		    	}else if(subOn.compareTo(lastsyncAsDate)<0){
		    		System.out.println("subOn is before lastsyncAsDate");
		    		dosub=false;
		    	}else if(subOn.compareTo(lastsyncAsDate)==0){
		    		System.out.println("subOn is equal to lastsyncAsDate");
		    		dosub=false;
		    	}else{
		    		System.out.println("How to get here?");
		    		dosub=false;
		    	}
	    	}
	    	Log.d("TAGG","");

		    	
		    	Boolean dodel= false;
		    	if(delOn==null) {
		    		dodel=false;
		    	} else {
			    	if(delOn.compareTo(lastsyncAsDate)>0){
			    		System.out.println("delOn is after lastsyncAsDate");
			    		dodel=true;
			    	}else if(delOn.compareTo(lastsyncAsDate)<0){
			    		System.out.println("delOn is before lastsyncAsDate");
			    		dodel=false;
			    	}else if(delOn.compareTo(lastsyncAsDate)==0){
			    		System.out.println("delOn is equal to lastsyncAsDate");
			    		dodel=true;
			    	}else{
			    		System.out.println("How to get here?");
			    		dodel=false;
			    	}
		    	}
		    	
		    	
		    	sign.markedforspeedydeletion =false;		//do this here to be safe....
		    	
		    	if(dosub && dodel) {
		    		
		    		//kill these again for good luck
		    		sign.markedforspeedydeletion=true;
		    		
		    		if(sign.web_local) {    
		    			//we should mark them as all local here, but I don't think it will
		    			//matter since the sync date will exclude them in future syncs.
		    			//They can just lay in the database at web when in reality they are now
		    			//local....
		    		} else {
		    			


		    	   		SpeedlimitManager3.doSomethingExpensive_submitAndDelete(sign,
		    	   				Global.all,
		    	   				Global.mEmailString,
		    	   				Global.mBitcoin,
		    	   				Global.mUsernameString
		    	   				);	
		    	   				
		    	   		
		    		}			    	
			    	//remove it from sqlite too....
		    		sign.sutc= null;	//kludge to make this not hang on the call below.... ugh
		    		//Global.db.deleteSignREALLYVAPORIZEIT(sign);	
		    		Global.db.deleteSign(sign);
			    	
		    	} else {
			    	if(dosub) {
			    		
			    		
			    		if(sign.web_local) {    
			    			//we should mark them as all local here, but I don't think it will
			    			//matter since the sync date will exclude them in future syncs.
			    			//They can just lay in the database at web when in reality they are now
			    			//local....
			    		} else {
			    						    			
			    			
			    	   		SpeedlimitManager3.doSomethingExpensive_submitAndDelete(sign,
			    	   				Global.all,
			    	   				Global.mEmailString,
			    	   				Global.mBitcoin,
			    	   				Global.mUsernameString
			    	   				);	
			    	   				
			    	   		
			    		}
			    		
			    		
			    	} else if (dodel) {
				    	sign.markedforspeedydeletion=true;
				  				    	
				    	
				    	if(sign.web_local) {    
			    			//we should mark them as all local here, but I don't think it will
			    			//matter since the sync date will exclude them in future syncs.
			    			//They can just lay in the database at web when in reality they are now
			    			//local....
			    		} else {
			    			


			    	   		SpeedlimitManager3.doSomethingExpensive_submitAndDelete(sign,
			    	   				Global.all,
			    	   				Global.mEmailString,
			    	   				Global.mBitcoin,
			    	   				Global.mUsernameString
			    	   				);	
			    	   				
			    	   		
			    		}
				    	
				    	
				    	//remove it from sqlite too....
			    		sign.sutc= null;	//kludge to make this not hang on the call below.... ugh
			    		
			    		//Global.db.deleteSignREALLYVAPORIZEIT(sign);	
			    		Global.db.deleteSign(sign);
			    		
			    	}			    	
		    	}
	 
		    	//this could be part of dup cleanup, but I just do it here...
		    	if(sign.dutc!=null) {
		    		sign.sutc= null;	//kludge to make this not hang on the call below.... ugh
		    		
		    		//Global.db.deleteSignREALLYVAPORIZEIT(sign);
		    		Global.db.deleteSign(sign);
		    	}

		    	if(false) {
		    		if(ix==100) {
		    			Global.no_wifi_found=true;
		    		}
		    	}

	        	if(Global.no_wifi_found) {
        		
        			String ss= sign.sutc;
        			String ds= sign.dutc;
        		
        			String mydate5="";
        		
        			if(ss != null) {
        				mydate5=ss;
        			}
        			if(ds != null) {
        				mydate5=ds;
        			}
        			Global.db.setlastsync(mydate5);

					Global.nSignsNotSynced_wentoutlasttime=ix;
	        		
	        		break mainLoop;
	        	}
	        	

    	 }
    	

		
		//dunno if garbage collection does this for me or not...
    	signsinx.clear();
		
			
			
				 
   	 
		
		
	}
    

	

	

	public static void delete_dups() {
		
		Global.db.delete_dups();
			
	}

	
	
}


