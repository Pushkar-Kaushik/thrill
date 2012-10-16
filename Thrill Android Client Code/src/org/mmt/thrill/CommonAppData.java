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
 * CommonAppData.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file represents class that contains data common for different activities of Thrill Application
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/

package org.mmt.thrill;

import java.io.IOException;
import java.net.MalformedURLException;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;


/**
 * This class represents data common for different activities of Thrill App.
 */
public class CommonAppData {
       public static Resources res;
       public static SharedPreferences pref;
       public static TelephonyManager telephonyManager;
       public static String thrillBaseURL = "http://182.71.50.155/thrill/index.php";
       public static String defaultParentId = "-1";
       public static String defaultGridColor = "#E65159";
       public static String defaultImageSpecs = "0";
       
       /**
        * Pass page layout to get a drawable object
        * @return a drawable object
        */  
	   public static Drawable draw_background(PageLayoutDesc page_layout) {
	    	int v_width, v_height;
	    	Bitmap bmp;
	    	Canvas c;
	    	Path fillPath;
	    	v_width = page_layout.getWidth();
	    	v_height = page_layout.getHeight();
	    	bmp = Bitmap.createBitmap(v_width, v_height, Config.ARGB_8888);
	    	c = new Canvas(bmp);	    	
	    	Paint p = new Paint();
	    	p.setColor(PageLayoutDesc.pageBackColor1);
	    	
	    	p.setStyle(Paint.Style.FILL);
	    	p.setStrokeWidth(0);
	    	
	    	fillPath = new Path();
	    	fillPath.moveTo(0, 0);
	    	fillPath.lineTo(0, page_layout.getHeight());
	    	fillPath.lineTo(page_layout.getWidth(),page_layout.getHeight());
	    	fillPath.lineTo(page_layout.getWidth(), 0);
	    	c.drawPath(fillPath, p);
	    	float x;
	    	x = page_layout.getScreenWidthMargin();
	    	p.setColor(PageLayoutDesc.pageBackColor2);
	    	for(int i =0;i< page_layout.getTotalColumn();i++)
	    	{
	    		fillPath.reset();
	    		fillPath.moveTo(x, 0);
	    		fillPath.lineTo(x, page_layout.getHeight());
	        	x = x + page_layout.getRowWidth();
	        	fillPath.lineTo(x,page_layout.getHeight());
	        	fillPath.lineTo(x, 0);
	        	x = x + page_layout.getGutter();
	        	c.drawPath(fillPath, p);
	    	}

	    	Drawable d = new BitmapDrawable(res,bmp);
	    	return d;

	    }
	   /**
	     * Get member id for Thrill app 
	     * @return Member id > 0 or 0 if new user.  
	     */
	   public static String getMId()
	   {
		   String key = res.getString(R.string.mid);
		   String mid = pref.getString(key, "0");
		   return mid;
	   }
	   /**
	     * Set member id for Thrill app 
	     * 
	     */
	   public static void setMId(String mId)
	   {
		   String key = res.getString(R.string.mid);
		   Editor edit = pref.edit(); 
		   edit.putString(key, mId);
		   edit.commit();
	   }
	   /**
	     * Get member id for Thrill app 
	     * @return Member id > 0 or 0 if new user.  
	     */
	   public static String getSSId()
	   {
		   String ssid = pref.getString(res.getString(R.string.ssid), "0");
		   return ssid;
	   }
	   /**
	     * Get session id for Thrill app 
	     * @return session id > 0 or 0 if new session with Thrill server.  
	     */
	   public static void setSSId(String ssId)
	   {
		   Editor edit = pref.edit();
		   edit.putString(res.getString(R.string.ssid), ssId);
		   edit.commit();
	   }
	   /**
	     * Get IMEI for device 
	     * @return IMEI for device.  
	     */
	   public static String getIMEI()
	   {
		   String imei = telephonyManager.getDeviceId();
		   return imei;
	   }
	   /**
	     * Get IMSI for SIM in device 
	     * @return IMSI string or 0 if SIM is absent.  
	     */
	   public static String getIMSI()
	   {
		   
		   String imsi = telephonyManager.getSubscriberId();
		   if(imsi == null)
		   {
			   imsi = "0";
		   }
		   return imsi;
	   }
	   /**
	     * Get ISO country code (SIM provider's country code)
	     * @return ISO country code string .  
	     */
	   public static String getCountryISO()
	   {
		   String countryCode = telephonyManager.getSimCountryIso();
		   return countryCode;
	   }
}