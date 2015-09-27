package org.wikispeedia.roadrage2;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import niceandroid.net.androidsqlite.DatabaseHandler;
import niceandroid.net.androidsqlite.DatabaseHandler_east;

import org.wikispeedia.roadrage2.R;
import org.wikispeedia.speedlimit.Signs;

import mzx.chn.lanedetection.LaneDetectActivity;


import es.pymasde.blueterm.BlueTerm;
import es.pymasde.blueterm.BluetoothSerialService;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;


import org.wikispeedia.backseatdriver.Global;
import org.wikispeedia.backseatdriver.Settings;
import org.wikispeedia.backseatdriver.Translate;
import org.wikispeedia.zombiepro.Canvastutorial;

import es.pymasde.blueterm.BlueTerm;
import es.pymasde.blueterm.BluetoothSerialService;
import es.pymasde.blueterm.DeviceListActivity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.*;



class AsyncTaskExample1 extends AsyncTask<Void, Integer, String>{   
	   protected void onPreExecute(){    
	    Log.d("Asyntask","On preExceute...");
	    }   
	   protected String doInBackground(Void...arg0) {    
	    Log.d("Asyntask","On doInBackground...");    
	      publishProgress(27);    
	      
	      Global.dobox();
  	    
	      
	      
	     return "You are at PostExecute";
	     }   
	   protected void onProgressUpdate(Integer...a){    
	    Log.d("Asyntask","You are in progress update ... " + a[0]);
	    }    
	   protected void onPostExecute(String result) {    
	    Log.d("Asyntask",result); 
	    }    
	  }

public class Activity1 extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	

    BlueTerm bt= new BlueTerm();
    
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    
	public static final int ORIENTATION_SENSOR    = 0;
	public static final int ORIENTATION_PORTRAIT  = 1;
	public static final int ORIENTATION_LANDSCAPE = 2;

	final String DEBUG_TAG= "TAGG";
	

    /**
     * Set to true to add debugging code and logging.
     */
    public static final boolean DEBUG = true;

    
    private static BluetoothSerialService mSerialService = null;
    
    
    /**
     * The tag we use when logging, so that our messages can be distinguished
     * from other messages in the log. Public because it's used by several
     * classes.
     */
	public static final String LOG_TAG = "BlueTerm";

    private boolean mLocalEcho = false;
    
    // Message types sent from the BluetoothReadService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;	

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	
	private BluetoothAdapter mBluetoothAdapter = null;
	
    // Name of the connected device
    private String mConnectedDeviceName = null;
    
    private MenuItem mMenuItemConnect;
    
    
    private Dialog         mAboutDialog;
    

	
	Activity1 mythis;
	
	Timer mmTimer;
	
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout1);
    final Button switchact1 =(Button)findViewById(R.id.btn1);
    final Button switchact2= (Button)findViewById(R.id.btn2);
    final Button switchact3= (Button)findViewById(R.id.btn3);
    final Button switchact4= (Button)findViewById(R.id.btn4);



	  ConcurrentLinkedQueueExample clq = new ConcurrentLinkedQueueExample();


	  String[] arr = new String[0];

	  clq.main(arr);



    mythis = this;
    
	 
	 if(Global.width==0) {		//chicken egg workaround
		 
		 WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		 Display display = wm.getDefaultDisplay();
		 int width = display.getWidth();  // deprecated
	   	 int height = display.getHeight();  // deprecated		   		
	   	 Global.width= width;
	   	 Global.height= height;
	 }
	 
	 
    switchact1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
    	mmTimer.cancel();
    	
    	Global.andyDelete=false;
    	Global.andygrid = true;
    	
        Intent act2 = new Intent(view.getContext(),Canvastutorial.class);
        startActivity(act2);
      }
    });
    switchact2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          mmTimer.cancel();
          
          Global.andyDelete=false;
      	  Global.andygrid = false;
      	
          Intent act2 = new Intent(view.getContext(),LaneDetectActivity.class);
          startActivity(act2);
        }
      });

    switchact3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          mmTimer.cancel();
          
          Global.andyDelete=false;
      	  Global.andygrid = false;
      	
          Intent act3 = new Intent(view.getContext(),Translate.class);
          startActivity(act3);
        }
      });
    
    switchact4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {        	
          mmTimer.cancel();
          
          Global.andyDelete=true;
      	  Global.andygrid = false;
      	  
          Intent act4 = new Intent(view.getContext(),Canvastutorial.class);
          startActivity(act4);
        }
      });
    
		
    getPreferences();
    


    
    
    
	    
	
		//hardwire a Bluetooth BEE connection.
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
	        finishDialogNoBluetooth();
			return;
		}
	    mSerialService = new BluetoothSerialService(this, mHandlerBT, null);
		
	    // Cancel discovery because it will slow down the connection
	    mBluetoothAdapter.cancelDiscovery();
		String address= "10:14:06:16:14:40";
	    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
	    mSerialService.connect(device);       

	    
	    
    
 
    	Global.db = new DatabaseHandler(this);
    
    	
    	
    	if(Global.test) {    	 

			Signs sign= new Signs();
        	Global.db.addContact(sign);
        	
    	}
    	
	
    
		
		Global.lm= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		getMyLocation_oneTimeSetup(Global.lm);


	
    


	    //auto start system to help Andy
	    mycontext= this;
	    
	    Toast.makeText(this, "autostart andydelete in 1 minute", 200000).show();
	    Log.d("TAGG","autostart andydelete in 5 minutes");
        mmTimer = new Timer("andydeletetimer");

        TimerTask mmTimerTask = new TimerTask() {
            public void run() {
           	 
               
            	 mmTimer.cancel();
         
                 //Intent act3 = new Intent(mycontext,Translate.class);
                 //startActivity(act3);
            	
            }
        };
         
        mmTimer.schedule(mmTimerTask, 10000, 10000);


	  //voodoo to prevent error i got called    AndroidBlockGuardPolicy
	  //http://stackoverflow.com/questions/8706464/defaulthttpclient-to-androidhttpclient

	  if (android.os.Build.VERSION.SDK_INT > 9) {
		  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  StrictMode.setThreadPolicy(policy);
	  }




}
  
  

  static Context mycontext;
  
  
  @Override
  public void onDestroy() {
  	super.onDestroy();
  	
  	
  	Global.lm.removeUpdates(locationListener_distance); 	
  	//lm.removeUpdates(locationListener_time);			//time not used at present....

  	
  	
  	//save_junk();
  	
      if (mSerialService != null)
      	mSerialService.stop();
      
  }
  
  private int timeChanged;

	private void getMyLocation_oneTimeSetup(LocationManager lm) {

      locationListener_distance = new LocationListener() {
          public void onLocationChanged(Location newloc) {
            
          	//Log.d("TAGG","positionChanged= ");
        	  
        	    getMyLocation();
        	    
        	    
        	    
        	    
        	    new AsyncTaskExample1().execute();
        	    
        	    
             
             	//turncruiseonoroff();
             
        	    if(false) {
	     			Signs dasign = new Signs();
	     			dasign.lat= Global.myLatitude;       			
	     			dasign.lng= Global.myLongitude; 
	     			Global.db.addJourney(dasign);
        	    }

          }
          public void onProviderDisabled(String provider) {} 
          public void onProviderEnabled(String provider) {}
          public void onStatusChanged(String provider, int status,
          		Bundle extras) {}
      };  
      
      //wikipedia:  0.001 degrees	= 111.32 m
      //            0.004         = 445.28 m
      
      float meters= (float)800.0;
	 	
      Global.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, meters, locationListener_distance);

      

      locationListener_time = new LocationListener() {
          public void onLocationChanged(Location newloc) {
          
        		getMyLocation();
        		
				timeChanged++;
                //Log.d("TAGGG","timeChanged= " + Integer.toString(timeChanged));
          	
          }
          public void onProviderDisabled(String provider) {} 
          public void onProviderEnabled(String provider) {}
          public void onStatusChanged(String provider, int status,
          		Bundle extras) {}
      };          
      long ms= 30000; 
      Global.lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, ms, 0, locationListener_time);     
      
  }

	

  LocationListener locationListener_distance, locationListener_time = null;

	
	static float lattop,latbottom,lonleft,lonright;


	
  
    public static void getMyLocation() {

    	
 	   if(Global.lm != null){
 		   Location loc = Global.lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
 		   if (loc != null) {
 	       		
				
			   if(Global.mph_kph) {
					Global.mySpeed= loc.getSpeed() * 2.23693629;
 			   } else {
 				  Global.mySpeed= loc.getSpeed() * 3.6;	//1 meter per second = 3.6 kilometers per hour
 			   }
 			   Global.mySpeedString= Double.toString(Global.mySpeed);
 			   
 			   
 			   Global.mySpeed=20.0;

			   Global.myLatitude= loc.getLatitude();
			   Global.myLongitude= loc.getLongitude();	       	   
			   Global.myAltitude_meters= loc.getAltitude();	       	   
			   Global.myCog= (int) loc.getBearing();	       	   


			   
 	           Global.myLatitudeString = Double.toString(Global.myLatitude);
 	           Global.myLongitudeString = Double.toString(Global.myLongitude);
 	           Global.myCogString= Integer.toString(Global.myCog);
 	           
 	            
 	           Global.locFound= true;
 	       	   

				
				
 			   
 		   } else {
 			  Global.locFound= false;  //if false, dont trust the mySpeed, etc from above... Used as a lockout since I am a terrible hack!
 		   }
 		   
 	   }
 		   
    }	
    
    
    
    
    
	public int getConnectionState() {
		return mSerialService.getState();
	}
	

	// The Handler that gets information back from the BluetoothService
	private final Handler mHandlerBT = new Handler() {
		
	    @Override
	    public void handleMessage(Message msg) {        	
	        switch (msg.what) {
	        case MESSAGE_STATE_CHANGE:
	            if(DEBUG) Log.i(LOG_TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
	            switch (msg.arg1) {
	            case BluetoothSerialService.STATE_CONNECTED:
	            	if (mMenuItemConnect != null) {
	            		mMenuItemConnect.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
	            		mMenuItemConnect.setTitle(R.string.disconnect);
	            	}
	            	
	            	//mInputManager.showSoftInput(mEmulatorView, InputMethodManager.SHOW_IMPLICIT);
	            	
	                //mTitle.setText( R.string.title_connected_to );
	                //mTitle.append(" " + mConnectedDeviceName);
	                break;
	                
	            case BluetoothSerialService.STATE_CONNECTING:
	                //mTitle.setText(R.string.title_connecting);
	                break;
	                
	            case BluetoothSerialService.STATE_LISTEN:
	            case BluetoothSerialService.STATE_NONE:
	            	if (mMenuItemConnect != null) {
	            		mMenuItemConnect.setIcon(android.R.drawable.ic_menu_search);
	            		mMenuItemConnect.setTitle(R.string.connect);
	            	}

	        		//mInputManager.hideSoftInputFromWindow(mEmulatorView.getWindowToken(), 0);
	            	
	                //mTitle.setText(R.string.title_not_connected);

	                break;
	            }
	            break;
	        case MESSAGE_WRITE:
	        	if (mLocalEcho) {
	        		byte[] writeBuf = (byte[]) msg.obj;
	        		//mEmulatorView.write(writeBuf, msg.arg1);
	        	}
	            
	            break;
	/*                
	        case MESSAGE_READ:
	            byte[] readBuf = (byte[]) msg.obj;              
	            mEmulatorView.write(readBuf, msg.arg1);
	            
	            break;
	*/                
	        case MESSAGE_DEVICE_NAME:
	            // save the connected device's name
	            mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
	            Toast.makeText(getApplicationContext(), getString(R.string.toast_connected_to) + " "
	                           + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
	            
	            
	            //try {
				//	mapAndSend('f');
				//} catch (IOException e) {
				//	// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
	            
	            break;
	        case MESSAGE_TOAST:
	            Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
	            break;
	        }
	    }
	};    
	

    //relay closest to the BT daughter board jim....
	public static void	closestOn() {
		Log.d("TAGG","closestOn");
		byte[] mBuffer = new byte[1];	
    	mBuffer[0]= 101;
    	mSerialService.write(mBuffer);            
    	//101 is 'e'   relay1 position 1    ON closest to bluebee chip
    	//111 is 'o'   relay1 position 0        	
    	//102 is 'f'   relay2 position 1	ON	        	
    	//112 is 'p'   relay2 position 0
	}

	//relay closest to the BT daughter board jim....
	public static void	closestOFF() {
		Log.d("TAGG","closestOFF");
		byte[] mBuffer = new byte[1];	
	   	mBuffer[0]= 111;
	   	mSerialService.write(mBuffer);            
	   	//101 is 'e'   relay1 position 1    ON closest to bluebee chip
	   	//111 is 'o'   relay1 position 0        	
	   	//102 is 'f'   relay2 position 1	ON	        	
	   	//112 is 'p'   relay2 position 0
	}
		

	//relay furthest away from BT chip on the daughter board jim
	public static void	furthestON() {
		Log.d("TAGG","furthestON");
		byte[] mBuffer = new byte[1];	
	   	mBuffer[0]= 102;
	   	mSerialService.write(mBuffer);            
	   	//101 is 'e'   relay1 position 1    ON closest to bluebee chip
	   	//111 is 'o'   relay1 position 0        	
	   	//102 is 'f'   relay2 position 1	ON	        	
	   	//112 is 'p'   relay2 position 0
	}

	//relay furthest away from BT chip on the daughter board jim
	public static void	furthestOFF() {
		Log.d("TAGG","furthestOFF");
		byte[] mBuffer = new byte[1];	
	   	mBuffer[0]= 112;
	   	mSerialService.write(mBuffer);            
	   	//101 is 'e'   relay1 position 1    ON closest to bluebee chip
	   	//111 is 'o'   relay1 position 0        	
	   	//102 is 'f'   relay2 position 1	ON	        	
	   	//112 is 'p'   relay2 position 0
	}
	
		
		
	



public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(DEBUG) Log.d(LOG_TAG, "onActivityResult " + resultCode);
    switch (requestCode) {
    
    case REQUEST_CONNECT_DEVICE:

        // When DeviceListActivity returns with a device to connect
        if (resultCode == Activity.RESULT_OK) {
            // Get the device MAC address
            String address = data.getExtras()
                                 .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
            // Get the BLuetoothDevice object
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            // Attempt to connect to the device
            mSerialService.connect(device);                
        }
        break;

    case REQUEST_ENABLE_BT:
        // When the request to enable Bluetooth returns
        if (resultCode != Activity.RESULT_OK) {
            Log.d(LOG_TAG, "BT not enabled");
            
            finishDialogNoBluetooth();                
        }
    }
}
	

public void finishDialogNoBluetooth() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(R.string.alert_dialog_no_bt)
    .setIcon(android.R.drawable.ic_dialog_info)
    .setTitle(R.string.app_name)
    .setCancelable( false )
    .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   finish();            	
               }
           });
    AlertDialog alert = builder.create();
    alert.show(); 
}


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.connect:
      	
      	if (getConnectionState() == BluetoothSerialService.STATE_NONE) {
      		// Launch the DeviceListActivity to see devices and do scan
      		Intent serverIntent = new Intent(this, DeviceListActivity.class);
      		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
      		Log.d("TAGG","hi jim");
      	}
      	else
          	if (getConnectionState() == BluetoothSerialService.STATE_CONNECTED) {
          		mSerialService.stop();
		    		mSerialService.start();
          	}
          return true;
      case R.id.preferences:
      	doPreferences();
          return true;
      case R.id.menu_special_keys:
          //doDocumentKeys();
          return true;
      case R.id.menu_start_stop_save:
      	doSignActivity();
          return true;
          
      case R.id.menu_about:
      	showAboutDialog();
          return true;
      }
      return false;
  }


	private void showAboutDialog() {
		mAboutDialog = new Dialog(Activity1.this);  //Canvastutorial.this);
		mAboutDialog.setContentView(R.layout.about);
		mAboutDialog.setTitle( getString( R.string.app_name ) + " " + getString( R.string.app_version ));
		
		Button buttonOpen = (Button) mAboutDialog.findViewById(R.id.buttonDialog);
		buttonOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		 
				mAboutDialog.dismiss();
			}
		});		
		
		mAboutDialog.show();
  }

	

  private void doPreferences() {
      
      Intent act2 = new Intent(this,Settings.class);
      startActivity(act2);
  }

  private void doSignActivity() {

      
      Intent act2 = new Intent(this,LaneDetectActivity.class);
      startActivity(act2);
      
  }
  

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.option_menu, menu);
      bt.mMenuItemConnect = menu.getItem(0);
      bt.mMenuItemStartStopRecording = menu.getItem(3);        
      return true;
  }



public void send(byte[] out) {
	
	if ( out.length == 1 ) {
		
		//if ( out[0] == 0x0D ) {
		//	out = handleEndOfLineChars( mOutgoingEoL_0D );
		//}
		//else {
    	//	if ( out[0] == 0x0A ) {
    	//		out = handleEndOfLineChars( mOutgoingEoL_0A );
    	//	}
		//}
	}
	
	if ( out.length > 0 ) {
		mSerialService.write( out );
	}
}

  void getPreferences() {

	     // Save user preferences as persistent. 
	        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	        SharedPreferences.Editor editor = settings.edit();

	        
	   	   // Restore persistence variables
	        // SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);  //already open above
	   	   Global.mUsernameString = settings.getString("Username", "Andy Cranford");
	   	   Global.mEmailString = settings.getString("Email", "");
	   	   Global.mph_kph = settings.getBoolean("MphKph", true);
	   	   Global.taunts  = settings.getBoolean("Taunts", true);
	   	   Global.all= settings.getString("all", "all");
	   	   Global.over= settings.getInt("over", 7);
	   	   Global.dbCreated= settings.getBoolean("db", false);
	   	   String rgbtmp= settings.getString("Rgb", "77,119,67,77,119,87");
	   	   
	   	   

	       String string = rgbtmp;
	       String[] parts = string.split(",");         
	       try {
	      	 //litmus tests to see if they are numbers        
	           Integer.parseInt(parts[0]);
	           Integer.parseInt(parts[1]);
	           Integer.parseInt(parts[2]);
	           Integer.parseInt(parts[3]);
	           Integer.parseInt(parts[4]);
	           Integer.parseInt(parts[5]);
	           Global.rgb= rgbtmp;
	       } catch(Exception e) {
	      	 Global.rgb= "77,119,67,77,119,87";			//just force it to something so we don't get exceptions all day long
	       }
	       
	   	   
	   	   
  }




}