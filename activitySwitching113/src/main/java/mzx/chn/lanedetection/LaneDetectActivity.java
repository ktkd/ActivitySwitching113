package mzx.chn.lanedetection;

/*written by freelancer aug 2014. :   
 * Hired by jim pruett, wikispeedia.org
 * 
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.wikispeedia.roadrage2.R;

import niceandroid.net.androidsqlite.DatabaseHandler;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.wikispeedia.backseatdriver.Global;
import org.wikispeedia.speedlimit.SpeedlimitListener;
import org.wikispeedia.speedlimit.SpeedlimitManager4;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

public class LaneDetectActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "OCVSample::Activity";
    
    private DisplayMetrics			mMetrics;
    
    private double					mScale;
    private double 					mScaleFactor = 1.05;
    private int						mMinNeighbours = 2;

    private CameraBridgeViewBase 	mOpenCvCameraView;
    
    private File					mCascadeFile = null;
    private CascadeClassifier		mJavaDetector = null;
    
    private Mat						mRgba;
    private Mat						mGray;
    private Mat						mSmallImage;
    private Mat						mDstCanny;
    
    private MatOfRect 				mCars;
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    
                    InputStream is = getResources().openRawResource(R.raw.haarcascade_cars3);
					File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
					
					mCascadeFile = new File(cascadeDir, "haarcascade_cars3.xml");
					
					try {
						FileOutputStream os = new FileOutputStream(mCascadeFile);
						byte[] buffer = new byte[204800];
						int bytesRead;
						while((bytesRead = is.read(buffer)) != -1){
							os.write(buffer, 0, bytesRead);
						}
						
						is.close();
						os.close();
						
						mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
						if(mJavaDetector.empty()){
							Log.e(TAG, "Failed to load cascade classifier");
	                        mJavaDetector = null;
						} else {
							Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
						}
						
						cascadeDir.delete();
						
					} catch (IOException e) {
						e.printStackTrace();
	                    Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
					}
					
                    mOpenCvCameraView.enableView();                   
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public LaneDetectActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.lane_detection_activity);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.lane_detection_java_view);
        
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
        
        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        mScale = (double)(mMetrics.heightPixels) / 180.0;
        
        Log.i(TAG, "Scale =  " + mScale);
        
        
        
        
        
        


        Global.speedlimitListener = new SpeedlimitListener() {
           @Override
            public void onSpeedLimitChanged(Integer speedlimit, String copyright) {
               //done underneath already    Global.theSpeedLimitIsDouble= speedlimit;
               
        	   Log.d("TAGGG","speed limit changed to " + Integer.toString(speedlimit));
                
        	   
        	   final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        	     tg.startTone(ToneGenerator.TONE_PROP_BEEP);
        	     
        	     
        	     
               //doNaggingAudio();
            }
        };  
         
       Global.slm= new SpeedlimitManager4(getBaseContext());
  
       Global.slm.requestChanges( Global.speedlimitListener );
       
       Log.d("TAGG","SpeedLimitListener on");
       
       
      
       //Global.db = new DatabaseHandler(this);
       
       
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
        
        
        Global.slm.stopListener();
        
    }

    
    
    @Override
    protected void onStop(){
       super.onStop();
   


             
    }
    
    
    @Override
   
    public void onCameraViewStarted(int width, int height) {
    	mGray = new Mat();
    	mRgba = new Mat();
    	mSmallImage = new Mat(new Size(Math.round((double)mMetrics.widthPixels / mScale), 
    			Math.round((double)mMetrics.heightPixels / mScale)), CvType.CV_8UC1);
    	mCars = new MatOfRect();
    }

    public void onCameraViewStopped() {
    	mGray.release();
    	mRgba.release();
    	mSmallImage.release();
    }

    static int iframer=0;
    
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
    	
    	Log.d("TAGGG","onCameraFrame, getMyLocation= " + Integer.toString(iframer++));
    	org.wikispeedia.speedlimit.SpeedlimitManager4.getMyLocation();
    	
    	
    	
    	
    	mRgba = inputFrame.rgba();
    	mGray = inputFrame.gray();
    	
    	
    	Imgproc.resize(mGray, mSmallImage, mSmallImage.size(), 1.0/mScale, 1.0/mScale, 1);
    	Imgproc.equalizeHist(mSmallImage, mSmallImage);
    	
    	if(mJavaDetector != null){
    		mJavaDetector.detectMultiScale(mSmallImage, mCars, mScaleFactor, mMinNeighbours, 1, new Size(30, 30), new Size(200, 200));
    	}
    	
    	Rect[] carRects = mCars.toArray();
    	for(int i = 0; i < carRects.length ; i++){
    		Rect r = carRects[i];
    		Point center = new Point();
    		center.x = Math.round((r.x + r.width * 0.5) * mScale);
    		center.y = Math.round((r.y + r.height * 0.5) * mScale);
    		int radius = (int) Math.round((r.width + r.height) * 0.25 * mScale);
    		Global.radius=radius;
    		Core.circle(mRgba, center, radius, new Scalar(0,0,255), (int) Math.round(2 * mScale), 16, 0);
    	}
    	
    	//int halfWidth = mRgba.width()/2;
    	int halfHeight = mSmallImage.height()/2;
    	
    	Rect region_of_interest = new Rect(0, halfHeight - 0, mSmallImage.width() - 1, halfHeight - 1);
    	
    	//Imgproc.equalizeHist(mGray, mGray);
    	//Imgproc.GaussianBlur(mGray, mDstCanny, new Size((int)Math.round(5), (int)Math.round(5)), 3);
    	int blurValue = (int)Math.round(4*mScale/2);
    	if( blurValue % 2 == 0) blurValue+=1;
    	Imgproc.GaussianBlur(mSmallImage, mSmallImage, new Size(blurValue, blurValue), 5);
    	Imgproc.Canny(mSmallImage, mSmallImage, 50 /** mScale*/ / 2, 200 /** mScale*/ / 2);
    	
    	mDstCanny = new Mat(mSmallImage, region_of_interest);
    	
    	Mat lines = new Mat();
    	Imgproc.HoughLinesP(mDstCanny, lines, 1, Math.PI/180, 25, 25, 50);
    	
    	for(int i = 0; i < lines.cols(); i++){
    		double[] vec = lines.get(0, i);
    		double x1 = vec[0], y1 = vec[1], x2 = vec[2], y2 = vec[3];
    		double dx = x2-x1, dy = y2-y1;
    		double angle = Math.atan2(dy, dx) *180 / Math.PI;
    		if(Math.abs(angle) <= 10)
    			continue;
    		if(y1 > y2 + 50 || y1 < y2 - 50){
    			Core.line(mRgba, new Point(x1 * mScale, (y1 + halfHeight)*mScale), 
    					new Point(x2 * mScale, (y2 + halfHeight)*mScale), 
    					new Scalar(255, 0, 0), (int) Math.round(2 * mScale), 16, 0);
    		}
    	}
    	
        return mRgba;
    }
    
}
