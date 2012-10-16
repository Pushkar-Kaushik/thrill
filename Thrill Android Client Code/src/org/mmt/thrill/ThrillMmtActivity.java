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
 * ThrillMmtActivity.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains activity that is launcher and will display the Thrill page from server to user.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import org.mmt.thrill.GridElement.GRIDTYPE;
import org.mmt.thrill.GridElement.VIEWTYPE;
import org.mmt.thrill.PageContent.PAGETYPE;
import org.mmt.thrill.XmlHandling.EVENTTYPE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity is Launcher.
 * It will display the thrill page to user.
 * 
 */

public class ThrillMmtActivity extends Activity {
    /** Called when the activity is first created. */
	static PageLayoutDesc pageLayout;
	RelativeLayout rlMain;
	String tag = "TH: ThrillMmtActivity ";
	 List<GridElement> tileSets;
	 DownloadFile xmldownload;
	 final int FULL_IMAGE = 1;
	 LoaderImageView tappedView = null;
	 PageContent pageContent;
	 PageContent nextPage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CommonAppData.res = getResources();
        CommonAppData.pref = PreferenceManager.getDefaultSharedPreferences(this);
        CommonAppData.telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        pageLayout = new PageLayoutDesc(10, 15, 12,14,540,960);                
        drawBackgroundLayout();
        getPageContentFromServer();
        
        
        
    }
    /**
     * Get Page Content from Thrill server 
     */
    public void getPageContentFromServer()
    {
    	showDialog(DIALOG_DOWNLOAD_PROGRESS); 
        xmldownload = new DownloadFile(); 
        xmldownload.execute(null);
        
    }
    /**
     * Draws background layout 
     */
    public void drawBackgroundLayout()
    {
    	RelativeLayout rl_action_bar;
    	RelativeLayout.LayoutParams params;
    	int items_id = 998;	
    	
          try
          {
        	  rlMain = new RelativeLayout(this);
        	  int width = pageLayout.getWidth();
        	  int height = pageLayout.getHeight();
        	  params = new RelativeLayout.LayoutParams(width, height);
        	  //rl_main.setLayoutParams(params); 
        	  rlMain.setBackgroundDrawable(CommonAppData.draw_background(pageLayout));
        	  
        	  this.setContentView(rlMain,params);
        	  
        	  
        	  width =pageLayout.getActionbarWidth();
        	  height = pageLayout.getActionbarHeight();
        	  
        	  rl_action_bar = new RelativeLayout(this);
        	  params = new RelativeLayout.LayoutParams(width,height);
        	  rl_action_bar.setLayoutParams(params); 
        	  rl_action_bar.setBackgroundColor(PageLayoutDesc.actionBarBackColor);
        	  
        	  ImageView option_menu = new ImageView(this);


        	  option_menu = new ImageView(this);
        	 
        	  option_menu.setImageResource(R.drawable.top_left_icon);
        	  option_menu.setId(items_id);
        	  params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);		
        	  params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        	  params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        	 
        	  rl_action_bar.addView(option_menu,params);
        	  option_menu.setOnClickListener(new OnClickListener() {

                  public void onClick(View view) {
                     Toast.makeText(getApplicationContext(),"Option menu selected.",Toast.LENGTH_SHORT).show();
                          
                      
                  }
              });
        	  items_id++;
        	  
        	  
        	  TextView title = new TextView(this);
        	  title.setId(items_id);
        	  params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);		
        	  params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        	  title.setText("Thrill");
        	  title.setTextColor(Color.WHITE);
        	  title.setTextSize(20);
        	  rl_action_bar.addView(title,params);
        	  items_id++;
        	  
        	  ImageView setting_menu = new ImageView(this);
        	  setting_menu = new ImageView(this);        	 
        	  setting_menu.setImageResource(R.drawable.top_right_icon);
        	  setting_menu.setId(items_id);
        	  params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);		
        	  params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        	  params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        	 
        	  rl_action_bar.addView(setting_menu,params);
        	  setting_menu.setOnClickListener(new OnClickListener() {

                  public void onClick(View view) {
                     Toast.makeText(getApplicationContext(),"Setting selected.",Toast.LENGTH_SHORT).show();
                          
                      
                  }
              });
        	  items_id++;
        	  rlMain.addView(rl_action_bar);
        	 

          }
          catch (Exception e)
          {
        	  Log.e("tag",tag+"Exception occured e ="+e);
          }
          
         
         
    }
    /**
     * Display tile set on screen 
     */
    public void display_tilesets1()
    {
    	
    	int total_tileset = tileSets.size();
    	rlMain.removeAllViews();
    	drawBackgroundLayout();
    	RelativeLayout.LayoutParams params;
    	for(int i=0;i<total_tileset;i++)
    	{
    		GridElement temp = tileSets.get(i);
    		
    		VIEWTYPE view = GridElement.getView(temp);
    		switch(view)
    		{
    		case LOADERIMAGEVIEW:
    		{
    			String image_src = null;//temp.getGridUrl();
    			if(image_src == null)
    			{
    				image_src = CommonAppData.thrillBaseURL+"?event="+EVENTTYPE.DOWNLOADICON.getCode()+"&gid="+temp.getGridId();
    			} 
    			LoaderImageView grid = new LoaderImageView(this,image_src,temp); 
    			int width = pageLayout.getWidthofColumns(temp.getWidthNumber());
    			int height = pageLayout.getHeightofRows(temp.getHeightNumber());
    			params = new RelativeLayout.LayoutParams(width, height);
    			params.leftMargin = pageLayout.getX(temp.getColumnIndex());
    			params.topMargin = pageLayout.getY(temp.getRowIndex());
    			grid.setLayoutParams(params);
    			grid.setBackgroundColor(PageLayoutDesc.actionBarBackColor);
    			//grid_elements[i].setScaleType(ScaleType.FIT_XY);
    			rlMain.addView(grid);
    			grid.setId(i);
    			if(temp.isClickable())
    			{
    				grid.setOnClickListener(new OnClickListener() {

                        public void onClick(View view) {
                        	action_on_image_click(view);
                                
                            
                        }
                    });    				
    			}
    		}    			
    			break;
    		case EDITTEXT:
    		{
    			EditText grid = new EditText(this); 
    			int width = pageLayout.getWidthofColumns(temp.getWidthNumber());
    			int height = pageLayout.getHeightofRows(temp.getHeightNumber());
    			params = new RelativeLayout.LayoutParams(width, height);
    			params.leftMargin = pageLayout.getX(temp.getColumnIndex());
    			params.topMargin = pageLayout.getY(temp.getRowIndex());
    			grid.setLayoutParams(params);
    			int back_color = temp.getGridColor();
    			if(back_color == -1)
    			{
    				back_color = PageLayoutDesc.actionBarBackColor;
    			}
    			grid.setBackgroundColor(back_color);
    			String hint = temp.getGridText();
    			if(hint!=null)
    			{
    				grid.setHint(hint);
    			}
    			//grid_elements[i].setScaleType(ScaleType.FIT_XY);
    			grid.setGravity(Gravity.CENTER);
    			grid.setTextColor(Color.WHITE);
    			rlMain.addView(grid);
    			grid.setId(i);
    			if(temp.isPassword())
    			{
    				grid.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    			}
    			if(temp.isClickable())
    			{
    				grid.setOnClickListener(new OnClickListener() {

                        public void onClick(View view) {
                        	action_on_image_click(view);
                                
                            
                        }
                    });    				
    			}
    		}    
    			break;
    		case TEXTVIEW:
    		{
    			TextView grid = new TextView(this); 
    			int width = pageLayout.getWidthofColumns(temp.getWidthNumber());
    			int height = pageLayout.getHeightofRows(temp.getHeightNumber());
    			params = new RelativeLayout.LayoutParams(width, height);
    			params.leftMargin = pageLayout.getX(temp.getColumnIndex());
    			params.topMargin = pageLayout.getY(temp.getRowIndex());
    			grid.setLayoutParams(params);
    			int back_color = temp.getGridColor();
    			if(back_color == -1)
    			{
    				back_color = PageLayoutDesc.actionBarBackColor;
    			}
    			grid.setBackgroundColor(back_color);
    			
    			String text = temp.getGridText();
    			if(text!=null)
    			{
    				grid.setText(text);
    			}
    			grid.setGravity(Gravity.CENTER);
    			grid.setTextColor(Color.WHITE);
    			rlMain.addView(grid);
    			grid.setId(i);
    			if(temp.isClickable())
    			{
    				grid.setOnClickListener(new OnClickListener() {

                        public void onClick(View view) {
                        	action_on_image_click(view);
                                
                            
                        }
                    });    				
    			}
    		}    
    		
    			break;
    		}
    		
    	}
    	
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode != RESULT_OK)
    		return;

    	switch (requestCode) {

    	case FULL_IMAGE:
    		String action_on_full_image = data.getStringExtra("action_on_full_image");
    		resultFromFullImage(action_on_full_image,data);
    		
    		break;
    	}
    }
    public void resultFromFullImage(String action, Intent data)
    {
    	if(action.equalsIgnoreCase("crop"))
    	{
    		Bundle extras = data.getExtras();
    		if (extras != null) {               
    			Bitmap photo = extras.getParcelable("data");
    			setCropedImageToSelectedGrid(photo);

    		}
    	}
    	else if(action.equalsIgnoreCase("select"))
    	{
    		getPageContentFromServer();
    	}
    }
    public void setCropedImageToSelectedGrid(Bitmap photo)
    {
    	Drawable mDrawable =  new BitmapDrawable(photo);
    	tappedView.drawImageFromExternalDrawable(mDrawable);
        
    }
    @Override 
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	   {
	        if(keyCode == KeyEvent.KEYCODE_BACK)
	        {
	        	Log.i("tag",tag+" onKeyDown KEYCODE_BACK ");
	        	if(Loadingdialog.isShowing())
	        	{
	        		Loadingdialog.dismiss();
	        	}
	        	
	        }
	        return super.onKeyDown(keyCode, event);
	   }
    /**
     * Get string to append with URL in case of sending responce to Thrill Server
     * @param selectedGelement			GridElement selected by user.
     * @return			                 String to append with base URL
     */
    public String getStringToAppendForURL(GridElement selectedGelement)
    {
    	String stringToAppend = null;
    	String response = "";
    	int pgid   =  pageContent.getPageId();
    	PAGETYPE pagetype = pageContent.getPageType();
    	int pgtype =  pagetype.getCode();
    	int pid    =  selectedGelement.getparentGridId();
    	int cid    =  selectedGelement.getGridId();
    	
    	stringToAppend = "&pgid="+pgid+
    			"&pgtype="+pgtype;
    	if(pid != -1)
    	{
    		response = pid+":";
    	}
    	response = response+cid;
    	
    	stringToAppend = stringToAppend + "&response="+response;
    			
    	return stringToAppend;
    }
    public void open_browser_application(String url)
    {
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); 
    	browserIntent.putExtra("mid",CommonAppData.getMId());
    	browserIntent.putExtra("ssid",CommonAppData.getSSId());
		startActivity(browserIntent); 
    }
    public void action_on_image_click(View item)
    {
    	GridElement selectedGelement =  tileSets.get(item.getId());
    	if(selectedGelement.getGridType() == GRIDTYPE.OPEN_BROWSER)
    	{
    		open_browser_application(selectedGelement.getGridUrl());
    		
    		return;
    	}
    	
    	PAGETYPE pageType = pageContent.getPageType(); 
    	String response = null;
    	XmlHandling.string_to_append = null;
    	XmlHandling.header_fields = null;
    	response = getStringToAppendForURL(selectedGelement);
    	switch(pageType)
    	{
    	case PROFILE_QUESTION:   	    
    		
    		XmlHandling.string_to_append = "event="+EVENTTYPE.REQUESTNEWPAGE.getCode()+response;   		
    		break;
    	case SIGN_UP:
    		XmlHandling.string_to_append = "event="+EVENTTYPE.REQUESTNEWPAGE.getCode()+response;
    		XmlHandling.header_fields = getHeaderFieldsSignUp();
    		if(XmlHandling.header_fields == null)
    			return;
    		break;
    	}
    	getPageContentFromServer();   	
    }
   public String getHeaderFieldsSignUp()
   {
	   String hFields = "";
       for(int i = 0; i < tileSets.size(); i++)
       {
    	   GridElement temp = tileSets.get(i);
    	   if(temp.getGridType().equals(GRIDTYPE.EDITBOX_EMAIL))
    	   {
    		   EditText email = (EditText)rlMain.getChildAt(i+1) ;
    		   if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
    		   {
    			   Toast.makeText(getApplicationContext(), "Enter valid email id", Toast.LENGTH_SHORT).show();
    			   return null;
    		   }
    		   hFields = hFields + "email,"+email.getText().toString()+",";
    	   }
    	   else if(temp.getGridType().equals(GRIDTYPE.EDITBOX_PASSWORD))
    	   {
    		   EditText email = (EditText)rlMain.getChildAt(i+1) ;
    		   if(email.getText().toString().equalsIgnoreCase(""))
    		   {
    			   Toast.makeText(getApplicationContext(), "Enter valid password", Toast.LENGTH_SHORT).show();
    			   return null;
    		   }
    		   hFields = hFields + "password,"+email.getText().toString()+",";
    	   }
       }
	   return hFields;
   }
    public void download_file()
    { 
    	
    	nextPage =XmlHandling.getPageContent(CommonAppData.thrillBaseURL); 
    	    	
    }
    public void message_to_main_activity()
    {
    	String text;
    	Loadingdialog.cancel();
    	
    	Message msg = new Message();
    	if(nextPage != null)
    	{
    		pageContent = nextPage;
    		tileSets = pageContent.getGridElements();	
    		text = "display_tilesets";	
    	}
    	else
    	{
    		text = "display_network_error";
    	}
    	
    	msg.obj = text;
    	mHandler.sendMessage(msg);
    	
    }
     Handler mHandler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
             String text = (String)msg.obj;
             if(text.equalsIgnoreCase("display_tilesets"))
             {
            	 display_tilesets1();
             }
             else
             {
            	 Toast.makeText(getApplicationContext(), "Network Error. Please try again.", Toast.LENGTH_SHORT).show();
             }
             //call setText here
         }
     };


     private class DownloadFile extends AsyncTask<String, Integer, Void> {
    	



    	 @Override
    	 protected Void doInBackground(String... u) {

    		 download_file();   

    		 return null;
    	 }

    	 @Override
    	 protected void onPostExecute(Void v) {
    		 
    		 Log.v("info",tag + "onPostExecute");
    		 message_to_main_activity();


    	 }


    	 @Override
    	 protected void onProgressUpdate(Integer... values) {
    		 super.onProgressUpdate(values);


    	 }


    	 @Override
    	 protected void onPreExecute() {    
    		 Log.v("info",tag + "onPreExecute");


    	 }        
     } 
     

    
     ProgressDialog Loadingdialog;
     final int DIALOG_DOWNLOAD_PROGRESS = 1;
     @Override
     protected Dialog onCreateDialog(int id) {
    	 switch (id) {

    	 case DIALOG_DOWNLOAD_PROGRESS:
    		 Loadingdialog = new ProgressDialog(this);
    		 Loadingdialog.setMessage("Please wait!");      	
    		 //MyDialog.setIndeterminate(false);
    		 Loadingdialog.setCancelable(false);
    		 Loadingdialog.show();
    		 Loadingdialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                 //@Override
    			 public boolean onKey (DialogInterface dialog, int keyCode, KeyEvent event) {
    				 if (keyCode == KeyEvent.KEYCODE_BACK && 
    						 event.getAction() == KeyEvent.ACTION_DOWN ) {
    					 
    					 boolean val = xmldownload.cancel(true);
    					 if(val)
    					 {
    						 dialog.cancel();
    					 }
    					 Log.i(tag,tag+"Cancel Tast val = " + val);
    					 return true;
    				 }
    				 return false;
    			 }
    		 });
    		 Loadingdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
    			 public void onDismiss(DialogInterface dialog) {


    			 }
    		 });

    	 default:
    		 return null;
    	 }
     }
     
}