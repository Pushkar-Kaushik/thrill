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
 * LoaderImageView.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains class that represents a  view to load image from url.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

  
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
  
/**
 * 
 * A view to load image from url. 
 *
 */
public class LoaderImageView extends LinearLayout{
  
        private static final int COMPLETE = 0;
        private static final int FAILED = 1;
  
        private Context mContext;
        private Drawable mDrawable;
        private ProgressBar mSpinner;
        private ImageView mImage;
        private GridElement gElement;
        
        /**
         * This is used when creating the view in XML
         * To have an image load in XML use the tag 'image="http://developer.android.com/images/dialog_buttons.png"'
         * Replacing the url with your desired image
         * Once you have instantiated the XML view you can call
         * setImageDrawable(url) to change the image
         * @param context
         * @param attrSet
         */
        public LoaderImageView(final Context context, final AttributeSet attrSet) {
                super(context, attrSet);
                final String url = attrSet.getAttributeValue(null, "image");
               
                if(url != null){
                        instantiate(context, url,null);
                } else {
                        instantiate(context, null,null);
                }
        }
        
        /**
         * This is used when creating the view programatically
         * Once you have instantiated the view you can call
         * setImageDrawable(url) to change the image
         * @param context the Activity context
         * @param imageUrl the Image URL you wish to load
         */
        public LoaderImageView(final Context context, final String imageUrl, final GridElement gridElement) {
                super(context);                
                instantiate(context, imageUrl,gridElement);       
        }
  
        /**
         *  First time loading of the LoaderImageView
         *  Sets up the LayoutParams of the view, you can change these to
         *  get the required effects you want
         */
        private void instantiate(final Context context, final String imageUrl,final GridElement gridElement) {
                mContext = context;
                
                gElement = gridElement;
                
                mImage = new ImageView(mContext);
                mImage.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                
                mSpinner = new ProgressBar(mContext);
                mSpinner.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        
                mSpinner.setIndeterminate(true);
                
                addView(mSpinner);
                addView(mImage);
                
                if(imageUrl != null){
                        setImageDrawable(imageUrl);
                }
        }
  
        /**
         * Set's the view's drawable, this uses the internet to retrieve the image
         * don't forget to add the correct permissions to your manifest
         * @param imageUrl the url of the image you wish to load
         */
        public void setImageDrawable(final String imageUrl) {
                mDrawable = null;
                mSpinner.setVisibility(View.VISIBLE);
                mImage.setVisibility(View.GONE);
                new Thread(){
                        public void run() {
                                try {
                                        
                                        Bitmap imageData = getBitmapFromUrl(imageUrl);
                                        gElement.setGridImageData(imageData);
                                        mDrawable = new BitmapDrawable(CommonAppData.res,imageData);
                                        imageLoadedHandler.sendEmptyMessage(COMPLETE);
                                } catch (MalformedURLException e) {
                                        imageLoadedHandler.sendEmptyMessage(FAILED);
                                } catch (IOException e) {
                                        imageLoadedHandler.sendEmptyMessage(FAILED);
                                }
                        };
                }.start();
        }
        
        /**
         * Callback that is received once the image has been downloaded
         */
        private final Handler imageLoadedHandler = new Handler(new Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                        case COMPLETE:
                                mImage.setImageDrawable(mDrawable);
                                mImage.setScaleType(ScaleType.FIT_XY);
                                mImage.setVisibility(View.VISIBLE);
                                mSpinner.setVisibility(View.GONE);
                                break;
                        case FAILED:
                        default:
                                // Could change image here to a 'failed' image
                                // otherwise will just keep on spinning
                                break;
                        }
                        return true;
                }             
        });
  
        /**
         * Pass in an image url to get a drawable object
         * @return a drawable object
         * @throws IOException
         * @throws MalformedURLException
         */
        private static Drawable getDrawableFromUrl(final String url) throws IOException, MalformedURLException {
                return Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), "name");
        }
        private static Bitmap getBitmapFromUrl(final String url) throws IOException, MalformedURLException {
        	URL url1 = new URL(url);   	    
    	    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();   	    
    	    conn.setRequestMethod("POST");
    	    conn.setDoInput(true);
    	    conn.setUseCaches(false);
    	    XmlHandling.setRequestProperties(conn);
    	    conn.connect();
    	    
    	    String response = conn.getResponseMessage();
    	    int responceCode = conn.getResponseCode();
    	    if (responceCode== HttpURLConnection.HTTP_OK) {
    		    Log.v("info","HTTP OK");
    		    String ssid=  conn.getHeaderField("SESSION");
    	        String mid=  conn.getHeaderField("MEMBERID");
    	        InputStream is = conn.getInputStream();
        	 Bitmap temp = BitmapFactory.decodeStream((InputStream)conn.getContent());//((InputStream)new URL(url).getContent());        	
        	 return temp;
    	    }
    	    else
    	    	return null;
    	    
        	 
        }
        /**
         * Draw Image from external drawable
         * @param mDrawable			drawable of external source
         */
        public void drawImageFromExternalDrawable(Drawable mDrawable)
        {
        	mImage.setImageDrawable(mDrawable);
        	mImage.setScaleType(ScaleType.FIT_XY);
            mImage.setVisibility(View.VISIBLE);
        }
}