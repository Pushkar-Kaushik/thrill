/***************************************************************************** 
*  Copyright Statement:
*  --------------------
*  This software is protected by Copyright and the information contained
*  herein is confidential. The software may not be copied and the information
*  contained herein may not be used or disclosed except with the written
*  permission of MoMagic Technologies Pvt Ltd.
*
*  BY OPENING THIS FILE, BUYER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
*  THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MOMAGIC TECHNOLOGIES SOFTWARE")
*  RECEIVED FROM MOMAGIC TECHNOLOGIES AND/OR ITS REPRESENTATIVES ARE PROVIDED TO BUYER ON
*  AN "AS-IS" BASIS ONLY. MOMAGIC TECHNOLOGIES EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
*  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
*  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
*  NEITHER DOES MOMAGIC TECHNOLOGIES PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
*  SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
*  SUPPLIED WITH THE MOMAGIC TECHNOLOGIES SOFTWARE, AND BUYER AGREES TO LOOK ONLY TO SUCH
*  THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. MOMAGIC TECHNOLOGIES SHALL ALSO
*  NOT BE RESPONSIBLE FOR ANY MOMAGIC TECHNOLOGIES SOFTWARE RELEASES MADE TO BUYER'S
*  SPECIFICATION OR TO CONFORM TO A PARTICULAR STANDARD OR OPEN FORUM.
*
*  BUYER'S SOLE AND EXCLUSIVE REMEDY AND MOMAGIC'S ENTIRE AND CUMULATIVE
*  LIABILITY WITH RESPECT TO THE MOMAGIC TECHNOLOGIES SOFTWARE RELEASED HEREUNDER WILL BE,
*  AT MOMAGIC'S OPTION, TO REVISE OR REPLACE THE MOMAGIC TECHNOLOGIES SOFTWARE AT ISSUE,
*  OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY BUYER TO
*  MOMAGIC TECHNOLOGIES FOR SUCH MOMAGIC TECHNOLOGIES SOFTWARE AT ISSUE. 
*
*  THE TRANSACTION CONTEMPLATED HEREUNDER SHALL BE CONSTRUED IN ACCORDANCE
*  WITH THE LAWS OF THE STATE OF CALIFORNIA, USA, EXCLUDING ITS CONFLICT OF
*  LAWS PRINCIPLES.  ANY DISPUTES, CONTROVERSIES OR CLAIMS ARISING THEREOF AND
*  RELATED THERETO SHALL BE SETTLED BY ARBITRATION IN SAN FRANCISCO, CA, UNDER
*  THE RULES OF THE INTERNATIONAL CHAMBER OF COMMERCE (ICC).
*
*****************************************************************************/

/*****************************************************************************
 *
 * Filename:
 * ---------
 * XmlHandling.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains class that do all the handling of Server-Client communication through xml.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.xmlpull.v1.XmlPullParserException;



import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * This class do all the handling of Server-Client communication through xml.
 * Given an url, it returns a page content,
 * which represents current thrill page.
 */
public class XmlHandling {
	
	enum EVENTTYPE {
	   
	    DOWNLOADICON(0),		
	    REQUESTNEWPAGE (1),
	    UPLOADIMAGE (2),
	    SHARECONTENT (3);
		
		
		private int code;
		 
		 private EVENTTYPE(int c) {
		   code = c;
		 }
		 
		 public int getCode() {
		   return code;
		 }
	} 
	static String  tag = "TH: XmlHandling";
	static String  string_to_append=null;
	static String  header_fields = null;
	
	/**
     * Download data from the given url
     * @param urlString			url string that hold the data.
     * @throws                  IOException
     * @return			        page type in PAGETYPE FORMAT 
     */
	public static String download(String urlString) throws IOException {
		
		if(string_to_append != null) 
	    {
	    	urlString = urlString+"?"+string_to_append;
	    } 
	    URL url = new URL(urlString);
	   /* //For debugging: 
		URL url = new URL("http://182.71.50.155/thrill/index.php?gid=1"); */
	    
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	    
	    conn.setRequestMethod("POST"); 
	    conn.setDoInput(true);
	    conn.setUseCaches(false);
	    setRequestProperties(conn); 
	    conn.connect();
	    
	    String response = conn.getResponseMessage(); 
	    int responceCode = conn.getResponseCode(); 
	    if (responceCode== HttpURLConnection.HTTP_OK) {
		    Log.v("info","HTTP OK");
		    String ssid=  conn.getHeaderField("SESSION");
	        String mid=  conn.getHeaderField("MEMBERID"); 
	        CommonAppData.setMId(mid); 
	        CommonAppData.setSSId(ssid);
	        InputStream is = conn.getInputStream(); 
	        BufferedInputStream bis = new BufferedInputStream(is); 
	        int lenghtOfFile = conn.getContentLength();
	        ByteArrayBuffer baf = new ByteArrayBuffer(lenghtOfFile);
	        int current = 0;
	        long total = 0;

	        while ((current = bis.read()) != -1) {
	        	baf.append((byte) current); 
	        	total ++;

	        }

	        int len = baf.length();
	        String respoce_file = new String(baf.toByteArray());    
	        Log.i("Debug", tag+"respoce_file"+ respoce_file);        
	        return respoce_file; 
		    }
	    else
	    {
	    	  InputStream is = conn.getErrorStream();
	    	   BufferedInputStream bis = new BufferedInputStream(is);  

		       
		        int lenghtOfFile = conn.getContentLength();
		        ByteArrayBuffer baf = new ByteArrayBuffer(lenghtOfFile);
		        int current = 0;
		        long total = 0;

		        while ((current = bis.read()) != -1) {
		        	baf.append((byte) current); 
		        	total ++;

		        } 

		        int len = baf.length(); 
		        String respoce_file = new String(baf.toByteArray()); 
		        Log.i("Debug", tag+"respoce_file"+ respoce_file);   
		        return "error";
	    	
	    }
        
	    
	}
	/**
     * To get Page Content from url 
     * @param url    URL that hold the page content in XML format.
     * @return       PageContent if successfully paarsed from url or null in any error occurs
     * 
     */
	public static PageContent getPageContent(String url)
	{
		
		PageContent pagecontent = null;
		try{
			
			String xml_string =  download(url);
			Log.i(tag,tag+" xml_string "+xml_string);  
			/*//For debugging
			xml_string = "<PAGE id='123'><GGROUP><GRID id='1111' r='1' c='1' w='12' h='2' imgw='220' imgh='400' imgs='44' type='0'  /><GRID id='2222' r='3' c='1' w='6'  h='6' imgw='221' imgh='401' imgs='55' type='1' /><GRID id='3333' r='3' c='7' w='6'  h='6' imgw='331' imgh='402' imgs='66' type='1' /><GRID id='4444' r='9' c='1' w='6'  h='6' imgw='441' imgh='403' imgs='77' type='1' url ='http://momagic.mobi/pushkar/SGO_ICONS/MainMenu/icon1.png'/><GRID id='5555' r='9' c='7' w='6'  h='6' imgw='551' imgh='404' imgs='88' type='1' url='http://maaretta.files.wordpress.com/2011/01/powerpuff_girls_movie.jpg' /></GGROUP></PAGE>";
			*/
			InputStream input_stream = new ByteArrayInputStream(xml_string.getBytes()); 
			ThrillXMLParser xmlParser = new ThrillXMLParser();
			pagecontent = xmlParser.parse(input_stream);
		}
		catch(Exception e)
		{
			Log.e(tag,tag+ "getGridElements Exception is "+e); 
		}
		
		return pagecontent;
	}
	
	/**
     * Used only for debugging purpose. It creates the xml file in sdcard 
     * @param in    InputStream of connection that holds the xml data.
     * 
     * 
     */
	public static void getStringFromLink(InputStream in)
	{
		
        File tempFile = new File("/sdcard/qwertyuiop.xml"); //target dir is a file
        
        try
        {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile));

 
        byte[] buffer = new byte[8192];
        int len;
        len = in.read(buffer);

        
        StringBuilder total = new StringBuilder();
       
        while (len >= 0) {       
        	out.write(buffer, 0, len);
        	total.append(new String(buffer, 0, len));
        	len = in.read(buffer);
        	
        }
        in.close();
        out.close();

      
        }
        catch (Exception e)
        {
        	Log.e(tag,tag+ "write_to_file Exception is "+e);
        	
        }
        
	}
	/**
     * Sets the request properties of HttpURLConnection 
     * @param conn    HttpURLConnection.     
     */
	public static void setRequestProperties(HttpURLConnection conn)
	{
		if(CommonAppData.getMId().equalsIgnoreCase("0"))
		{
		    conn.setRequestProperty("ssid", "0"); 
		    conn.setRequestProperty("mid", "0");
		    conn.setRequestProperty("imsi", CommonAppData.getIMSI());
		    conn.setRequestProperty("imei", CommonAppData.getIMEI());

		    
		}
		else
		{
			conn.setRequestProperty("ssid",CommonAppData.getSSId());
		    conn.setRequestProperty("mid",CommonAppData.getMId());
		}
		if(header_fields != null)
		{
			datapart dp = new datapart();
			String tokens[] = dp.split(header_fields, ",");
			int i=0;
			while(tokens[i] != null && !(tokens[i].equalsIgnoreCase("")))
			{
				conn.setRequestProperty(tokens[i],tokens[i+1]);  
				Log.i(tag,tag+" request property "+tokens[i]+","+tokens[i+1]);
				i=i+2;
			}
		}
	}
	
}

