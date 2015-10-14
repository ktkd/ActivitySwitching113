package org.wikispeedia.zombiepro;

import java.lang.Object;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.wikispeedia.backseatdriver.AppPreferences;
import org.wikispeedia.backseatdriver.Global;
import org.wikispeedia.backseatdriver.Settings;
import org.wikispeedia.backseatdriver.Translate;
import org.wikispeedia.speedlimit.Signs;



import org.wikispeedia.roadrage2.R;

import android.app.Activity;


import android.content.Context;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.preference.PreferenceManager;

import android.util.Log;

import android.view.KeyEvent;

import android.view.MenuItem;
import android.view.MotionEvent;

import android.view.inputmethod.InputMethodManager;

import android.widget.TextView;



public class Canvastutorial extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private static AppPreferences _appPrefs;
	
	private static Boolean firstJourney= true;
	
    private static TextView mTitle;



    /**
     * Set to true to log each character received from the remote process to the
     * android log, which makes it easier to debug some kinds of problems with
     * emulating escape sequences and control codes.
     */
   
    /**
     * Set to true to log unknown escape sequences.
     */

    /**
     * Our main view. Displays the emulated terminal screen.
     */
    //private EmulatorView mEmulatorView;

    /**
     * A key listener that tracks the modifier keys and allows the full ASCII
     * character set to be entered.
     */
    //private TermKeyListener mKeyListener;
		
	
	private static InputMethodManager mInputManager;
	
	private boolean mEnablingBT;

    private int mFontSize = 9;
    private int mColorId = 2;
    private int mControlKeyId = 0;
    private boolean mAllowInsecureConnections = true;
    private int mIncomingEoL_0D = 0x0D;
    private int mIncomingEoL_0A = 0x0A;
    private int mOutgoingEoL_0D = 0x0D;
    private int mOutgoingEoL_0A = 0x0A;
    
    private int mScreenOrientation = 0;

    private static final String LOCALECHO_KEY = "localecho";
    private static final String FONTSIZE_KEY = "fontsize";
    private static final String COLOR_KEY = "color";
    private static final String CONTROLKEY_KEY = "controlkey";
    private static final String ALLOW_INSECURE_CONNECTIONS_KEY = "allowinsecureconnections";
    private static final String INCOMING_EOL_0D_KEY = "incoming_eol_0D";
    private static final String INCOMING_EOL_0A_KEY = "incoming_eol_0A";
    private static final String OUTGOING_EOL_0D_KEY = "outgoing_eol_0D";
    private static final String OUTGOING_EOL_0A_KEY = "outgoing_eol_0A";
    private static final String SCREENORIENTATION_KEY = "screenorientation";

    public static final int WHITE = 0xffffffff;
    public static final int BLACK = 0xff000000;
    public static final int BLUE = 0xff344ebd;

    private static final int[][] COLOR_SCHEMES = {
        {BLACK, WHITE}, {WHITE, BLACK}, {WHITE, BLUE}};

    private static final int[] CONTROL_KEY_SCHEMES = {
        KeyEvent.KEYCODE_DPAD_CENTER,
        KeyEvent.KEYCODE_AT,
        KeyEvent.KEYCODE_ALT_LEFT,
        KeyEvent.KEYCODE_ALT_RIGHT,
        KeyEvent.KEYCODE_VOLUME_DOWN,
        KeyEvent.KEYCODE_VOLUME_UP
    };
//    private static final String[] CONTROL_KEY_NAME = {
//        "Ball", "@", "Left-Alt", "Right-Alt"
//    };
    private static String[] CONTROL_KEY_NAME;

    private int mControlKeyCode;

    private SharedPreferences mPrefs;
	
    private MenuItem mMenuItemStartStopRecording;


	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.d("TAGG","do i get here");
		save_junk();
		Log.d("TAGG","after save_junk");
	}
	

    

    
    
	
	
		
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
		






    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
          
        _appPrefs = new AppPreferences(getApplicationContext());

        Global.panelthis= this;
        
        
       
        
        
        
        /*
        // Save user preferences as persistent. 
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        
   	   // Restore persistence variables
        // SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);  //already open above
   	   Global.mUsernameString = settings.getString("Username", "all");
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
*/       
	
       
       
        //Global.db = new DatabaseHandler(this);
        
	    //Global.mySpeedLimitManager3= new SpeedlimitManager3();
	    	   
  	    
  	   //check: just put anything in sync date since it was empty....
  	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  	   String someString2="";
	   Date lastsyncAsDate= null;
	   try {
			//someString2 = _appPrefs.getSmsBody();
			
			someString2 =  Global.db.getLastSync();
			
			
			lastsyncAsDate = formatter.parse(someString2);
	   } catch (Exception e) {
			Log.d("TAGG","might need some default code here  Not sure...");
	   }
		
  	   //Jim TODO put this in a version number somehow...
  	   //you need this to add columns to Tommys old version.... Its safe to call over and over, but unnecessary...
  	   Global.db.onUpgrade();
  	   
  	   
  	   
  	   
       mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
       //readPrefs();

       CONTROL_KEY_NAME = getResources().getStringArray(R.array.entries_controlkey_preference);

   		mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);		
		
       // Set up the window layout
       //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       //setContentView(R.layout.main);
       //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

       // Set up the custom title
       //mTitle = (TextView) findViewById(R.id.title_left_text);
       //mTitle.setText(R.string.app_name);
       //mTitle = (TextView) findViewById(R.id.title_right_text);
       
		
       //setContentView(R.layout.term_activity);

       //mEmulatorView = (EmulatorView) findViewById(R.id.emulatorView);

       //mEmulatorView.initialize( this );

       //mKeyListener = new TermKeyListener();

       //mEmulatorView.setFocusable(true);
       //mEmulatorView.setFocusableInTouchMode(true);
       //mEmulatorView.requestFocus();
       //mEmulatorView.register(mKeyListener);


       
        //moved to Acivity1
		//if(Global.test) {
		//	Toast.makeText(this, "DEBUG JIM!", 200000).show();
		//	org.wikispeedia.backseatdriver.Settings.syncnow();	         
        //}  
		
		
        		   
    }
 
		


  	   
    private void StoreDatabase() {
    	
    	//create directory since it wont exist...
    	getDir("databases",Context.MODE_PRIVATE);
    	
    	
        File DbFile = new File(
                "data/data/org.wikispeedia.zombiepro/databases/contactsManager");
        if (DbFile.exists()) {
            System.out.println("file already exist ,No need to Create");
        } else {
            try {
                DbFile.createNewFile();
                System.out.println("File Created successfully");
                InputStream is = this.getAssets().open("acontactsManager.png");
                FileOutputStream fos = new FileOutputStream(DbFile);
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                System.out.println("File succesfully placed on sdcard");
                // Close the streams
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
void copyFile(File src, File dst) throws IOException {
    FileChannel inChannel = new FileInputStream(src).getChannel();
    FileChannel outChannel = new FileOutputStream(dst).getChannel();
    try {
        inChannel.transferTo(0, inChannel.size(), outChannel);
    } finally {
        if (inChannel != null)
            inChannel.close();
        if (outChannel != null)
            outChannel.close();
    }
}

  
  	   
    
    
    static int i;
	static Signs contact;

	
    
    @Override
    public boolean onTouchEvent(MotionEvent event){ 
              	

    	 
    	int action= event.getActionMasked();
    
    	int x= (int) event.getRawX()- 18;		//empirical jim goofy side bezzel offset.
    	int y= (int) event.getRawY()- 40;
    	//Log.d("TAGG","x,y= " + Integer.toString(x) + " " + Integer.toString(y));
    	Global.xtap= x;
    	Global.ytap= y;
    	
    	
    	
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
            	
            	Global.tapped= true;
            	Global.tappedSignFound=-1;
			   
	            
            	
          
                return true;
            case (MotionEvent.ACTION_MOVE) :
                //Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                //Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                //Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                //Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                //        "of current screen element");
                return true;      
            default : 
                return super.onTouchEvent(event);
        }      
    }
    
    

   
    
    
    @Override
    public void onStop() {
    	super.onStop();

    	//save_junk();
    }

    @Override
    public void onDestroy() {
    	super.onDestroy();
    	
    
        
    }
    
    public void save_junk() {

        // Save user preferences as persistent. 
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("MphKph", Global.mph_kph);
        editor.putBoolean("Taunts", Global.taunts);
        
        editor.putString("Username", Global.mUsernameString);
        editor.putString("Rgb", Global.rgb);
        editor.putString("Email", Global.mEmailString);
        editor.putString("all", Global.all);
        editor.putInt("over", Global.over);
        editor.putBoolean("db", Global.dbCreated);
        editor.putBoolean("eastwest", Global.east_west);
        
        
        
        
        
       
        
        // Don't forget to commit your edits!!!
        //to see your stuff go into DDMS->File Explorer->data->data->org.wikispeedia.backseatdriver->shared_prefs
        editor.commit();      
        //editor.apply();
    }
    
    

}




/**
 * An ASCII key listener. Supports control characters and escape. Keeps track of
 * the current state of the alt, shift, and control keys.
 */
class TermKeyListener {
    /**
     * The state engine for a modifier key. Can be pressed, released, locked,
     * and so on.
     *
     */
    private class ModifierKey {

        private int mState;

        private static final int UNPRESSED = 0;

        private static final int PRESSED = 1;

        private static final int RELEASED = 2;

        private static final int USED = 3;

        private static final int LOCKED = 4;

        /**
         * Construct a modifier key. UNPRESSED by default.
         *
         */
        public ModifierKey() {
            mState = UNPRESSED;
        }

        public void onPress() {
            switch (mState) {
            case PRESSED:
                // This is a repeat before use
                break;
            case RELEASED:
                mState = LOCKED;
                break;
            case USED:
                // This is a repeat after use
                break;
            case LOCKED:
                mState = UNPRESSED;
                break;
            default:
                mState = PRESSED;
                break;
            }
        }

        public void onRelease() {
            switch (mState) {
            case USED:
                mState = UNPRESSED;
                break;
            case PRESSED:
                mState = RELEASED;
                break;
            default:
                // Leave state alone
                break;
            }
        }

        public void adjustAfterKeypress() {
            switch (mState) {
            case PRESSED:
                mState = USED;
                break;
            case RELEASED:
                mState = UNPRESSED;
                break;
            default:
                // Leave state alone
                break;
            }
        }

        public boolean isActive() {
            return mState != UNPRESSED;
        }
    }

    private ModifierKey mAltKey = new ModifierKey();

    private ModifierKey mCapKey = new ModifierKey();

    private ModifierKey mControlKey = new ModifierKey();

    /**
     * Construct a term key listener.
     *
     */
    public TermKeyListener() {
    }

    public void handleControlKey(boolean down) {
        if (down) {
            mControlKey.onPress();
        } else {
            mControlKey.onRelease();
        }
    }

    public int mapControlChar(int ch) {
        int result = ch;
        if (mControlKey.isActive()) {
            // Search is the control key.
            if (result >= 'a' && result <= 'z') {
                result = (char) (result - 'a' + '\001');
            } else if (result == ' ') {
                result = 0;
            } else if ((result == '[') || (result == '1')) {
                result = 27;
            } else if ((result == '\\') || (result == '.')) {
                result = 28;
            } else if ((result == ']') || (result == '0')) {
                result = 29;
            } else if ((result == '^') || (result == '6')) {
                result = 30; // control-^
            } else if ((result == '_') || (result == '5')) {
                result = 31;
            }
        }

        if (result > -1) {
            mAltKey.adjustAfterKeypress();
            mCapKey.adjustAfterKeypress();
            mControlKey.adjustAfterKeypress();
        }
        return result;
    }

    /**
     * Handle a keyDown event.
     *
     * @param keyCode the keycode of the keyDown event
     * @return the ASCII byte to transmit to the pseudo-teletype, or -1 if this
     *         event does not produce an ASCII byte.
     */
    public int keyDown(int keyCode, KeyEvent event) {
        int result = -1;
        switch (keyCode) {
        case KeyEvent.KEYCODE_ALT_RIGHT:
        case KeyEvent.KEYCODE_ALT_LEFT:
            mAltKey.onPress();
            break;

        case KeyEvent.KEYCODE_SHIFT_LEFT:
        case KeyEvent.KEYCODE_SHIFT_RIGHT:
            mCapKey.onPress();
            break;

        case KeyEvent.KEYCODE_ENTER:
            // Convert newlines into returns. The vt100 sends a
            // '\r' when the 'Return' key is pressed, but our
            // KeyEvent translates this as a '\n'.
            result = '\r';
            break;

        case KeyEvent.KEYCODE_DEL:
            // Convert DEL into 127 (instead of 8)
            result = 127;
            break;

        default: {
            result = event.getUnicodeChar(
                   (mCapKey.isActive() ? KeyEvent.META_SHIFT_ON : 0) |
                   (mAltKey.isActive() ? KeyEvent.META_ALT_ON : 0));
            break;
            }
        }

        
        result = mapControlChar(result);

        return result;
    }

    /**
     * Handle a keyUp event.
     *
     * @param keyCode the keyCode of the keyUp event
     */
    public void keyUp(int keyCode) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_ALT_LEFT:
        case KeyEvent.KEYCODE_ALT_RIGHT:
            mAltKey.onRelease();
            break;
        case KeyEvent.KEYCODE_SHIFT_LEFT:
        case KeyEvent.KEYCODE_SHIFT_RIGHT:
            mCapKey.onRelease();
            break;
        default:
            // Ignore other keyUps
            break;
            
            
        }

        
    	
        
    }
    

	
    
}

