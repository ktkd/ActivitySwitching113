package org.wikispeedia.speedlimit;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;





import org.wikispeedia.backseatdriver.Global;

import com.thebuzzmedia.sjxp.XMLParser;
import com.thebuzzmedia.sjxp.XMLParserException;
import com.thebuzzmedia.sjxp.rule.DefaultRule;
import com.thebuzzmedia.sjxp.rule.IRule;
import com.thebuzzmedia.sjxp.rule.IRule.Type;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;



public class SpeedlimitManager3 {


	  static String theString="";
	  static Integer pass=0;
	  static Integer fail=0;
	  static Signs mySign;
	  static String label="";
	  static Boolean a0=false;
	  static Boolean a1=false;
	  static Boolean a2=false;
	  static Boolean a3=false;
	  static Boolean a4=false;
	  static Boolean a5=false;
	  static Boolean a6=false;
	  static Boolean a7=false;
	  static Boolean a8=false;
	  static String lat="";
	  static String lng="";
	  static String mph="";
	  static String kph="";
	  static String cog="";
	  static String sutc;
	  static String submittedOn;
	  static String deletedOn;
	  static String dutc;
	  static ArrayList<Signs> signs = new ArrayList<Signs>();
	  static int pegMph, pegKph, pegMph_truck;
	  static double pegLatitude; 	
	  static double pegLongitude;
	  static Float pegCog;
	  static double pegAltitude_meters;
	  static String pegName="";
	  static String pegTag="";
	  static String pegEmail="";
	  static String pegBitcoin="";
	  static String pegsutc="";
	  static String pegdutc="";
	 
	 
	  
	  static Boolean markedforspeedydeletion;
	  
	  static String pegnelat="";
	  static String pegswlat="";
	  static String pegnelng="";
	  static String pegswlng="";
	  




	  public static InputStream fromStringBuffer(StringBuffer buf) {
		  return new ByteArrayInputStream(buf.toString().getBytes());
		}



	   public static List<Signs> doTranslate(Double myLatitude, Double myLongitude, String since) {   	   

		   
		   signs.clear();
		   
		
		StringBuffer response = new StringBuffer("");
		
		URL url = null;
		    
		
	
		
		if(since!="") {
			
			String encodedsince= null;
			
			try {
			    encodedsince = URLEncoder.encode(since,"UTF-8");
			    Log.d("TEST", encodedsince);
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			} 
			
			
			try {        		
		    	url = new URL("http://www.wikispeedia.org/a/marks_bb5.php" +
		    			"?name=JPWEB" +
		    			"&nelat=" + ( myLatitude + .002) +
		    			"&swlat=" + ( myLatitude - .002) +	
		    			"&nelng=" + ( myLongitude + .002) +	
		    			"&swlng=" + ( myLongitude - .002) +
		    			//"&since=2000-01-01");
		    			"&since="+encodedsince );
		    	
		    	
		    	System.out.println(url.toString());
		    } catch (MalformedURLException e) {
		    	Log.e(e.getMessage(), null);
		    }
		} else {
			try {        		
		    	url = new URL("http://www.wikispeedia.org/a/marks_bb4b.php" +
		    			"?name=JPWEB" +
		    			"&nelat=" + ( myLatitude + .002) +
		    			"&swlat=" + ( myLatitude - .002) +	
		    			"&nelng=" + ( myLongitude + .002) +	
		    			"&swlng=" + ( myLongitude - .002) 
		    			);
		    	System.out.println(url.toString());
		    } catch (MalformedURLException e) {
		    	//Log.e(e.getMessage(), null);
		    }
		}
		
   

   int amount=0;
   
   if (url != null) {
   	try {
   		HttpURLConnection urlConn = (HttpURLConnection) url
           	.openConnection();
   		
   		//TODO I GET ANR KEY TIMEOUTS FROM THIS NEXT LINE. NOT SURE HOW TO FIX!!!!
           BufferedReader in = new BufferedReader(new InputStreamReader(
           		urlConn.getInputStream()));
           String inputLine;
           //was 500
           while (   ((amount++)<1000) &&       ((inputLine = in.readLine()) != null)         ) {	
           	//System.out.println(inputLine);
           	response.append( inputLine );
           }       
           //Log.d("TAGG","amount= " + Integer.toString(amount));
           in.close();
           urlConn.disconnect();

   	} catch (IOException e) {
   		System.out.println(e.toString());
   	}
   }
   
   if(amount>15) {
	   	theString= "network down";
		return null;
   }
   
   
   if(true) {

	       InputStream a= fromStringBuffer(response);
	   	
	       try {
	    	   parser.parse(a);
	    	   pass=pass+1;
	       } catch (IllegalArgumentException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (XMLParserException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (ArrayIndexOutOfBoundsException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (Exception e) {
	    	   fail= fail + 1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } 	 
   }
	return signs;
		
}


		 
	   public static List<Signs> doTranslate3(Double swlat, Double swlon, Double nelat, Double nelon, String since) throws UnsupportedEncodingException {   	   

		   
		   signs.clear();
		   
		
		StringBuffer response = new StringBuffer("");
		
		URL url = null;
		
		pegName= Global.all;
		if(pegName == null || pegName == "") {
			pegName="all";
		}
		
		
		if(since!="") {
			
			String encodedsince= null;
			
			try {
			    encodedsince = URLEncoder.encode(since,"UTF-8");
			    Log.d("TEST", encodedsince);
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			} 
			
			
			try {        		
		    	url = new URL("http://www.wikispeedia.org/a/marks_bb5.php" +
		    			"?name="  + URLEncoder.encode(pegName,"UTF-8") + 
		    			"&nelat=" + ( nelat) +
		    			"&swlat=" + ( swlat) +	
		    			"&nelng=" + ( nelon) +
		    			"&swlng=" + ( swlon) +
		    			//"&since=2000-01-01");
		    			"&since="+encodedsince );
		    	
		    	
		    	System.out.println(url.toString());
		    } catch (MalformedURLException e) {
		    	Log.e(e.getMessage(), null);
		    }
		} else {
			Log.d("TAGG","ERR: Illegal args for this call. Since can't be null");
		}
		
   

   int amount=0;
   
   if (url != null) {
   	try {
   		HttpURLConnection urlConn = (HttpURLConnection) url
           	.openConnection();
   		
   		//TODO I GET ANR KEY TIMEOUTS FROM THIS NEXT LINE. NOT SURE HOW TO FIX!!!!
           BufferedReader in = new BufferedReader(new InputStreamReader(
           		urlConn.getInputStream()));
           String inputLine;
           //was 500
           while (   ((amount++)<1000) &&       ((inputLine = in.readLine()) != null)         ) {	
           	//System.out.println(inputLine);
           	response.append( inputLine );
           }       
           //Log.d("TAGG","amount= " + Integer.toString(amount));
           in.close();
           urlConn.disconnect();

   	} catch (IOException e) {
   		System.out.println(e.toString());
   	}
   }
   
   if(amount>15) {
	   	theString= "network down";
		return null;
   }
   
   
   if(true) {

	       InputStream a= fromStringBuffer(response);
	   	
	       try {
	    	   parser.parse(a);
	    	   pass=pass+1;
	       } catch (IllegalArgumentException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (XMLParserException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (ArrayIndexOutOfBoundsException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (Exception e) {
	    	   fail= fail + 1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } 	 
   }
	return signs;
		
}
	  
	 
		 
	   public static List<Signs> doTranslate2(Double swlat, Double swlon, Double nelat, Double nelon, String since) throws UnsupportedEncodingException {   	   

		   
		   signs.clear();
		   
		
		StringBuffer response = new StringBuffer("");
		
		URL url = null;
		
		pegName= Global.all;
		if(pegName == null || pegName == "") {
			pegName="all";
		}
		
		
		if(since!="") {
			
			String encodedsince= null;
			
			try {
			    encodedsince = URLEncoder.encode(since,"UTF-8");
			    Log.d("TEST", encodedsince);
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			} 
			
			
			try {        		
		    	url = new URL("http://www.wikispeedia.org/a/marks_bb6.php" +
		    			"?name="  + URLEncoder.encode(pegName,"UTF-8") + 
		    			"&nelat=" + ( nelat) +
		    			"&swlat=" + ( swlat) +	
		    			"&nelng=" + ( nelon) +	
		    			"&swlng=" + ( swlon) +
		    			//"&since=2000-01-01");
		    			"&since="+encodedsince );
		    	
		    	
		    	System.out.println(url.toString());
		    } catch (MalformedURLException e) {
		    	Log.e(e.getMessage(), null);
		    }
		} else {
			Log.d("TAGG","ERR: Illegal args for this call. Since can't be null");
		}
		
   

   int amount=0;
   
   if (url != null) {
   	try {
   		HttpURLConnection urlConn = (HttpURLConnection) url
           	.openConnection();
   		
   		//TODO I GET ANR KEY TIMEOUTS FROM THIS NEXT LINE. NOT SURE HOW TO FIX!!!!
           BufferedReader in = new BufferedReader(new InputStreamReader(
           		urlConn.getInputStream()));
           String inputLine;
           //was 500
           while (   ((amount++)<1000) &&       ((inputLine = in.readLine()) != null)         ) {	
           	//System.out.println(inputLine);
           	response.append( inputLine );
           }       
           //Log.d("TAGG","amount= " + Integer.toString(amount));
           in.close();
           urlConn.disconnect();

   	} catch (IOException e) {
   		System.out.println(e.toString());
   	}
   }
   
   if(amount>15) {
	   	theString= "network down";
		return null;
   }
   
   
   if(true) {

	       InputStream a= fromStringBuffer(response);
	   	
	       try {
	    	   parser.parse(a);
	    	   pass=pass+1;
	       } catch (IllegalArgumentException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (XMLParserException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (ArrayIndexOutOfBoundsException nsme) {
	    	   fail=fail+1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } catch (Exception e) {
	    	   fail= fail + 1;
	    	   Log.d("TAGG","Err Parser.parse XML Exception pass=" + pass.toString() + " fail=" + fail.toString());		//TODO  Riyad says I need to cleanup a,response...
	    	   return null;
	       } 	 
   }
	return signs;
		
}
	  
	 
 

		static IRule markerRule = new DefaultRule(Type.ATTRIBUTE, "/markers/marker",
				"label", "lat", "lng", "mph", "kph", "cog", "sutc", "dutc", "submittedOn", "deletedon" ) {
			@SuppressLint("NewApi")
			@Override
			public void handleParsedAttribute(XMLParser parser, int index,
					String value, Object userObject) {
				switch (index) {
				case 0:
					mySign = new Signs();					//this must be first
					//System.out.println("Label: " + value);
					label= value;
				    mySign.tag= label;  

					a0=false;
					a1=false;
					a2=false;
					a3=false;
					a4=false;
					a5=false;
					a6=false;
					a7=false;
					a8=false;
					
					a0=true;
					break;
				case 1:
					//System.out.println("Latitude: " + value);					
					lat=value;
					double latDouble        = Double.valueOf(lat.trim()).doubleValue();
					mySign.lat= latDouble;
					a1=true;
					break;
				case 2:
					//System.out.println("Longitude: " + value);
					lng=value;
					double lonDouble        = Double.valueOf(lng.trim()).doubleValue();
					mySign.lng= lonDouble;
					a2=true;
					break;
				case 3:
					//System.out.println("MPH: " + value);
					mph=value;
					int    mphInt           = Integer.parseInt(mph);
					mySign.mph= mphInt;
					a3=true;
					break;
				case 4:
					//System.out.println("KPH: " + value);
					kph=value;
				    int    kphInt           = Integer.parseInt(kph);
				    mySign.kph= kphInt;
				    a4=true;
					break;
				case 5:
					//System.out.println("COG: " + value);
					cog=value;
					int    cogInt           = Integer.parseInt(cog);
					mySign.cog= Integer.parseInt(cog.trim());
					mySign.cog= cogInt;
					a5=true;
					break;					
				case 6:
					sutc= null;
					if(value!=null && value.contains("20")) {  	//looking for 2012,2013.... other ways didnt work for some reason...
						sutc = value;
					}
					mySign.sutc= sutc;
					a6=true;
					break;						
				case 7:
					dutc= null;
					if(value!=null && value.contains("20")) {
						dutc = value; 
					}
					mySign.dutc= dutc;
					a7=true;
					break;
				case 8:
				    submittedOn=null;
					if(value!=null && value.contains("20")) {
						submittedOn= value;
					}
					mySign.submittedOn= submittedOn;
				    a8=true;
				    break;				    
				case 9:	
					deletedOn=null;
					if(value!=null && value.contains("20")) {
					   deletedOn=value;
					}
					mySign.deletedOn= deletedOn;
					
					if(a0==true && a1==true && a2==true && a3==true && a4==true && a5==true && a6==true && a7==true && a8==true) {
				       //if it gets here, it parsed good, so save the sign...
						try {  	  
							signs.add(mySign);
							Integer n=signs.size();
							//Log.d("TAGG","In signs.add  signs.size=" + n.toString() );
						} catch (IllegalArgumentException nsme) {
						  	/* failure */
						  	Log.d("TAGG","ERR: cant add to signs List, clearing it. That may help. Not sure if this is best...");
						  	//clearSigns();		
						  	theString=  "5";
						}
						Log.d("TAGG","signs.size= " + Integer.toString(signs.size()));
					} else {
						Log.d("TAGG","ERROR: not all tags showed up in xml");						
					}

			    	
			    	
					break;
				}
			}
		};






		@SuppressWarnings("unchecked")
		private static XMLParser parser = new XMLParser(markerRule);

		

	
	public static  void doSomethingExpensive_submit2() throws UnsupportedEncodingException {  
      {			
	   	   final int  myDummy= 69;   //goofy necessary flag that wikispeedia uses
	       String speed, speed_truck;
	        
	       if(Global.mph_kph) {
	    	   speed= Integer.toString(pegMph);
			   speed_truck= Integer.toString(pegMph_truck);
	       } else {
	    	   speed= Integer.toString(pegKph);
			   speed_truck= "0";	//TODO
	       }     	 
       	 
           String latString = Double.toString(pegLatitude);
           String lonString = Double.toString(pegLongitude);
           String alt_metersString = Double.toString(pegAltitude_meters);
           String dummy=      Integer.toString(myDummy);
       	   String direction=  Float.toString(pegCog);
       	   String hours=      "";
  	       String mmmphval,mmkphval, mmmph_truckval;
  	    	   	     
           if(Global.mph_kph == true) {
           		mmmphval= speed;
			   	mmmph_truckval= speed_truck;
           		mmkphval=dummy;
           } else {
           		mmmphval= dummy;
           		mmkphval=speed;
			   	mmmph_truckval= ""; //TODO
           }
      
	           
	       URL url = null;
	       StringBuffer response = new StringBuffer("");
  		   
  		   try {
  			 url= new URL("http://www.wikispeedia.org/a/process_submit_bb6.php" +
						"?name=" + pegName +
						"&mlat=" + latString +			
						"&mlon=" + lonString +			
						"&malt_meters=" + alt_metersString +			
						"&mmph=" + mmmphval +			
						"&mkph=" + mmkphval +
						"&mtag=" + URLEncoder.encode(pegTag, "UTF-8") + 
						"&mcog=" + direction +
						"&mhours=" + "" +
						"&memail=" + pegEmail +
						"&msutc="   + URLEncoder.encode(pegsutc, "UTF-8") +
			            "&mmph_truck=" + mmmph_truckval);
  			} catch (MalformedURLException e) {
  				e.printStackTrace();
  			} catch (UnsupportedEncodingException e) {
  				e.printStackTrace();
  			}
	  	       
	        int TIMEOUT_VALUE = 10000;
	  	    try {
	  	        URLConnection testConnection = url.openConnection();
	  	        testConnection.setConnectTimeout(TIMEOUT_VALUE);
	  	        testConnection.setReadTimeout(TIMEOUT_VALUE);
	  	        BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
	  	        String inputLine;
	
	  	        while ((inputLine = in.readLine()) != null) {
	   	            //System.out.println(inputLine);
	  	            response.append(inputLine);
	  	        }
	  	        in.close();
	  	     	       
	  	    } catch (SocketTimeoutException e) {
	  	    	System.out.println(e.toString());
	    		Global.no_wifi_found=true;
	    		
	  	    } catch (IOException e) {
	    		/*if you get here, make sure phone browser works to get to internet. It will fix it!!!! */
	    		System.out.println(e.toString());
	    		Global.no_wifi_found=true;
	    	}   
  	    
  	    
  	    
  	        
  	      
    	}
    	
	        			
	        	
	        		
	        		 
	        		 
	        	
	        
           
	        
          
      Log.d("TAGG","need end jim");
      
   
  }

	
		
		
	
	

	   public static void deletePointAt(String dutcc, double pointLat, double pointLon ) {
 	 

		   //ck for garbage, cases to ignore...
		   if(pointLat>=0.0 && pointLat<0.000001) {
			   theString= "pointLat=0  delete-IGNORED";
			   return;
		   }
	   
	   
		   StringBuffer response = new StringBuffer("");

			URL url = null;
			
			try {
				url = new URL("http://www.wikispeedia.org/a/delete_bb4a.php" +
						"?mdutc=" + URLEncoder.encode(dutcc,"UTF-8") +
						"&delmail=" + pegEmail +	
						"&nelat=" + ( pointLat + .00001) +			
						"&swlat=" + ( pointLat - .00001) +			
						"&nelng=" + ( pointLon + .00001) +			
						"&swlng=" + ( pointLon - .00001));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}


	        int TIMEOUT_VALUE = 9000;
	  	    try {
	  	        URLConnection testConnection = url.openConnection();
	  	        testConnection.setConnectTimeout(TIMEOUT_VALUE);
	  	        testConnection.setReadTimeout(TIMEOUT_VALUE);
	  	        BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
	  	        String inputLine;
	
	  	        while ((inputLine = in.readLine()) != null) {
	   	            //System.out.println(inputLine);
	  	            response.append(inputLine);
	  	        }
	  	        in.close();
	  	     
	  	    } catch (SocketTimeoutException e) {
	  	    	System.out.println(e.toString());
	    		Global.no_wifi_found=true;
	    		
	  	    } catch (IOException e) {
	    		/*if you get here, make sure phone browser works to get to internet. It will fix it!!!! */
	    		System.out.println(e.toString());
	    		Global.no_wifi_found=true;
	    	}

		   Log.d("TAGG","did i get here");
	   }
   	 
		
	    
	    
		public static void doSomethingExpensive_submitAndDelete(Signs sign,
				String ppegName,
				String ppegEmail,
				String ppegBitcoin,
				String ppegTag
				) {
		
		  
			pegName= ppegName;
			pegEmail= ppegEmail;
			pegBitcoin=ppegBitcoin;
			pegTag= ppegTag;
			
				
				pegMph= sign.mph;
				pegKph= sign.kph;
				pegLatitude= sign.lat; 	
				pegLongitude= sign.lng;
				pegCog= (float) sign.cog;
				pegAltitude_meters= sign.altitude_meters;
				markedforspeedydeletion= sign.markedforspeedydeletion;
				pegMph_truck= sign.mph_truck;

				if(sign.sutc!=null) {
					pegsutc= sign.sutc;
				} else {
					pegsutc="";
				}

				pegdutc= sign.dutc;
				if(pegdutc == null ) {				
					pegdutc= "";
				}
				
				
				
		    	
				if(markedforspeedydeletion) {
					   deletePointAt(pegdutc, sign.lat,sign.lng );
				} else {
					try {
						doSomethingExpensive_submit2();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				
			
			return;
			
		}

		
	    
		

}
      
   

	   
	   
	   
	   
	   
	   
	   
	   
	
	
	
			
			

