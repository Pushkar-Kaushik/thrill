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
 * FullImage.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains activity that is used for Crop image functionality.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mmt.thrill.GridElement.GRIDTYPE;
import org.mmt.thrill.GridElement.VIEWTYPE;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper; 
/**
 * Currently used for debugging
 * This activity is used for Crop image functionality
 */
public class FullImage extends Activity {
    
	static GridElement gElement;
	String tag = "TH: FullImage";
	final int CROP_RESULT =11;
	RelativeLayout rl_main;
	List<GridElement> tileSets;
	Drawable mDrawable;
    @Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        tileSets = temp_data_init(); 
        
        mDrawable =  new BitmapDrawable(CommonAppData.res,gElement.getGridImageData());  
        
        draw_background_layout();
        display_tilesets();

        
    }
    int REQ_CODE_PICK_IMAGE = 1;
    public void copy_image_to_temp_file(GridElement gElement)
    {
    	try 
    	{
    		File file = new File(Environment.getExternalStorageDirectory()+"/temp_file.jpg");
    	FileOutputStream fOut = new FileOutputStream(file);

    	
    	gElement.getGridImageData().compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		fOut.flush();
		fOut.close();
    	}
    	catch (Exception e)
    	{
    		Log.e(tag,tag+"Exception e"+e);
    	}
    }
    public void crop_image()
    {
    	  
    	Intent intent = new Intent("com.android.camera.action.CROP");
    	Uri mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/temp_file.jpg"));       
        intent.setDataAndType(mImageCaptureUri, "image/*");        
        intent.putExtra("crop", true);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent,CROP_RESULT);

    }
    public void display_tilesets()
    {
    	
    	int total_tileset = tileSets.size();
    	
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
    				image_src = "http://maaretta.files.wordpress.com/2011/01/powerpuff_girls_movie.jpg";//CommonAppData.thrillBaseURL+"?event="+0+"&gid="+temp.getGridId();
    			} 
    			LoaderImageView grid = new LoaderImageView(this,image_src,temp); 
    			int width = ThrillMmtActivity.pageLayout.getWidthofColumns(temp.getWidthNumber());
    			int height = ThrillMmtActivity.pageLayout.getHeightofRows(temp.getHeightNumber());
    			params = new RelativeLayout.LayoutParams(width, height);
    			params.leftMargin = ThrillMmtActivity.pageLayout.getX(temp.getColumnIndex());
    			params.topMargin = ThrillMmtActivity.pageLayout.getY(temp.getRowIndex());
    			grid.setLayoutParams(params);
    			grid.setBackgroundColor(PageLayoutDesc.actionBarBackColor);
    			//grid_elements[i].setScaleType(ScaleType.FIT_XY);
    			rl_main.addView(grid);
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
    			int width = ThrillMmtActivity.pageLayout.getWidthofColumns(temp.getWidthNumber());
    			int height = ThrillMmtActivity.pageLayout.getHeightofRows(temp.getHeightNumber());
    			params = new RelativeLayout.LayoutParams(width, height);
    			params.leftMargin = ThrillMmtActivity.pageLayout.getX(temp.getColumnIndex());
    			params.topMargin = ThrillMmtActivity.pageLayout.getY(temp.getRowIndex());
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
    			grid.setGravity(Gravity.CENTER);
    			grid.setTextColor(Color.WHITE);
    			rl_main.addView(grid);
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
    		case TEXTVIEW:
    		{
    			TextView grid = new TextView(this); 
    			int width = ThrillMmtActivity.pageLayout.getWidthofColumns(temp.getWidthNumber());
    			int height = ThrillMmtActivity.pageLayout.getHeightofRows(temp.getHeightNumber());
    			params = new RelativeLayout.LayoutParams(width, height);
    			params.leftMargin = ThrillMmtActivity.pageLayout.getX(temp.getColumnIndex());
    			params.topMargin = ThrillMmtActivity.pageLayout.getY(temp.getRowIndex());
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
    			rl_main.addView(grid);
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
    public void action_on_image_click(View item)
    {
    	
    	GridElement selected_element =  tileSets.get(item.getId());
    	int elemet_idx = selected_element.getGridId()/selected_element.getGridGroupId();
    	switch(elemet_idx)
    	{
    	case 1:
    		Intent resultData = new Intent();
			resultData.putExtra("action_on_full_image", "select");
			setResult(Activity.RESULT_OK, resultData);
			finish();
    		break;
    	case 2:
    		 copy_image_to_temp_file(tileSets.get(0));
    	     crop_image();
    		break;
    	}
    	
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode != RESULT_OK)
    		return;

    	switch (requestCode) {

    	case CROP_RESULT:         
    		Bundle extras = data.getExtras();
    		if (extras != null) {               
    			Bitmap photo = extras.getParcelable("data");    

    			Intent resultData = new Intent();
    			resultData.putExtras(extras);
    			resultData.putExtra("action_on_full_image", "crop");
    			setResult(Activity.RESULT_OK, resultData);
    			finish();
    			break;
    		}
    	}
    }
    public void draw_background_layout()
    {
    	RelativeLayout rl_action_bar;
    	RelativeLayout.LayoutParams params;
    	int items_id = 998;	
    	
          try
          {
        	  rl_main = new RelativeLayout(this);
        	  int width = ThrillMmtActivity.pageLayout.getWidth();
        	  int height = ThrillMmtActivity.pageLayout.getHeight();
        	  params = new RelativeLayout.LayoutParams(width, height);
        	  rl_main.setBackgroundDrawable(CommonAppData.draw_background(ThrillMmtActivity.pageLayout));
        	  
        	  this.setContentView(rl_main,params);
        	  
        	  
        	  width =ThrillMmtActivity.pageLayout.getActionbarWidth();
        	  height = ThrillMmtActivity.pageLayout.getActionbarHeight();
        	  
        	  rl_action_bar = new RelativeLayout(this);
        	  params = new RelativeLayout.LayoutParams(width,height);
        	  rl_action_bar.setLayoutParams(params); 
        	  rl_action_bar.setBackgroundColor(PageLayoutDesc.actionBarBackColor);

        	  TextView titletext = new TextView(this);
        	  titletext.setId(items_id);
        	  titletext.setText("Thrill");
        	  titletext.setTextColor(Color.WHITE);
        	  titletext.setTextSize(20);
        	  params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);		
        	  params.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        	  params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        	 
        	  rl_action_bar.addView(titletext,params);
        	 


        	  rl_main.addView(rl_action_bar);
        	 

          }
          catch (Exception e)
          {
        	  Log.e("tag",tag+"Exception occured e ="+e);
          }
          
         
         
    }
  public List<GridElement> temp_data_init()
  {
    List<GridElement> grid_item_list;
  	
  	grid_item_list = new ArrayList<GridElement>();
      
  	GridElement element1 = new GridElement(4,-1,3,1,1,10,12,GRIDTYPE.USER_CONTENT,null,null,0,0,0,null,"");
  	
  	GridElement element2 = new GridElement(5,4,3,12,1,2,6,GRIDTYPE.TEXTVIEW_CLICK,null,null,0,0,0,"Select","#E65159");
  	
  	
  	GridElement element3 = new GridElement(6,4,3,12,7,2,6,GRIDTYPE.TEXTVIEW_CLICK,null,null,0,0,0,"Crop","#E65159");    	
  	
  	grid_item_list.add(element1);
  	grid_item_list.add(element2);
  	grid_item_list.add(element3);
  	
  	
  	return grid_item_list;
  	
  }

  
    
}