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
 * GridElement.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains class that represents a single Grid Element.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;




/**
 * This class represents a single Grid Element.
 */
public class GridElement {
	
	/**
	 * This enum represents types of Grid Elements.
	 */
	enum GRIDTYPE {
	    PROFILE_QUESTION,
	    PROFILE_ANSWER,
	    EDITBOX_EMAIL,
	    EDITBOX_PASSWORD,
	    SUBMIT_BOX,
	    NEXT_PAGE,
	    PREVIOUS_PAGE,
	    SKIP_PAGE,
	    OPEN_BROWSER,
	    PROFILE_PIC,
	    USER_CONTENT,
	    EDITBOX_TEXT,
	    OPEN_FILEMANAGER,
	    TEXTVIEW,
	    TEXTVIEW_CLICK
	}
	enum VIEWTYPE {
		LOADERIMAGEVIEW,
		EDITTEXT,
		TEXTVIEW
	}
	
	private int       	gridId;
	private int      	parentGridId;
	private int      	gridGroupId;
	private int      	startRowIndex;
	private int     	startColIndex;
	private int     	numRow;
	private int     	numCol;
	private GRIDTYPE 	gridType;
	private String   	gridURL;
	private String   	gridGroupURL;
	private Bitmap   	gridImageData;
	private int      	orgImageWidth;
	private int      	orgImageHeight;
	private int      	orgImageSize;
	private String      gridText;
	private String      gridColor;
	private boolean     isClickable;
	private boolean     isPassword;
   
    public GridElement(
    	int gridId,
    	int parentGridId,
        int gridGroupId,        
        int startRowIndex,
        int startColIndex,
        int numRow,
        int numCol,
        GRIDTYPE gridType,
        String gridURL,
        String gridGroupURL,
        int orgImageWidth,
        int orgImageHeight,
        int orgImageSize,
        String gridText,
        String gridColor)
	{
        this.gridId         = gridId;
        this.parentGridId   = parentGridId;
		this.gridGroupId    = gridGroupId;
        this.startRowIndex  = startRowIndex;
        this.startColIndex  = startColIndex;
        this.numRow         = numRow;
        this.numCol         = numCol;
        this.gridType       = gridType;
        this.gridURL        = gridURL;
        this.gridGroupURL   = gridGroupURL;
        this.orgImageWidth  = orgImageWidth;
        this.orgImageHeight = orgImageHeight;
        this.orgImageSize   = orgImageSize; 
        this.gridText	    = gridText;
        this.gridColor      = gridColor;
        this.isPassword     = false;
        this.isClickable    = false;
	}
    /**
     * Get id for Grid Element 
     * @return Grid id.  
     */
	public int getGridId()
	{
		return gridId;
	}
	/**
     * Get parent id for Grid Element 
     * @return Parent Grid id.  
     */
	public int getparentGridId()
	{
		return parentGridId;		
	}
	/**
     * Get Group id for Grid Element 
     * @return Group id.  
     */
	public int getGridGroupId()
	{
		return gridGroupId;
	}
	/**
     * Get row index for start of Grid Element 
     * @return Row index. 
     * @see
     * {@link org.mmt.thrill.PageLayoutDesc#getY(int row)} 
     */
	public int getRowIndex()
	{
		return startRowIndex;
	}
	/**
     * Get column index for start of Grid Element 
     * @return Column index. 
     * @see
     * {@link org.mmt.thrill.PageLayoutDesc#getX(int column)  
     */
	public int getColumnIndex()
	{
		return startColIndex;
	}
	/**
     * Get width of Grid Element in number of columns 
     * @return Number of columns. 
     * @see
     * {@link org.mmt.thrill.PageLayoutDesc#getWidthofColumns(int numberOfColumns)}
     */
	public int getWidthNumber()
	{
		return numCol;
	}
	/**
     * Get height of Grid Element in number of rows 
     * @return Number of rows. 
     * @see
     * {@link org.mmt.thrill.PageLayoutDesc#getHeightofRows(int numberOfRows)}
     */
	public int getHeightNumber()
	{
		return numRow;
	}
	/**
     * Get Grid Type for Grid Element 
     * @return Grid Type. 
     * @see
     * {@link org.mmt.thrill.GridElement.GRIDTYPE} 
     */
	public GRIDTYPE getGridType()
	{
		return gridType;
	}
	/**
     * Get url associated Grid Element 
     * @return url string. 
     * 
     */
	public String getGridUrl()
	{
		return gridURL;
	}
	public String getGridGroupURL()
	{
		return gridGroupURL;
	}
	/**
     * Get Bitmap image data for Grid Element 
     * @return Bitmap of image. 
     * 
     */
	public Bitmap getGridImageData()
	{
		return gridImageData;
	}
	/**
     * Get width of image associated with Grid Element 
     * @return original image width. 
     * 
     */
	public int getOrgImageWidth()
	{
		return orgImageWidth;
	}
	/**
     * Get height of image associated with Grid Element 
     * @return original image height. 
     * 
     */
	public int getOrgImageHeight()
	{
		return orgImageHeight;
	}
	/**
     * Get size of image associated with Grid Element 
     * @return image size in bytes. 
     * 
     */
	public int getOrgImageSize()
	{
		return orgImageSize;
	}
	/**
     * Get text associated with Grid Element 
     * @return text string or empty string. 
     * 
     */
	public String getGridText()
	{
		return gridText;
	}
	/**
     * Get background color for Grid Element 
     * @return Color value in int.  
     */
	public int getGridColor()
	{
		int color = Color.parseColor(gridColor);
		return color;
	}
	/**
     * Get Grid Element is clickable 
     * @return true if clickable otherwise false   
     */
	public boolean isClickable()
	{
		return isClickable;
	}
	/**
     * Get Grid Element is Edittext of password type 
     * @return true if edittext is of password type otherwise false   
     */
	public boolean isPassword()
	{
		return isPassword;
	}
	/**
     * Set bitmap image data for grid element
     * @param imageData			Bitmap image data    
     */
	public void setGridImageData(Bitmap imageData)
	{
		this.gridImageData = imageData;
	}
	/**
     * Set parent id for grid element
     * @param parentGridId			Grid id for parent   
     */
	public void setParentGridId(int parentGridId)
	{
		this.parentGridId = parentGridId;
	}
	/**
     * Set if Grid Element is clickable
     * @param value			true if clickable or false if not clickable   
     */
	public void setClickable(boolean value)
	{
		this.isClickable = value;
	}
	/**
     * Set if Grid Element is view for taking password
     * @param value			true if it is password or false if its not.   
     */
	public void setIsPassword(boolean value)
	{
		this.isPassword = value;
	}
	
	/**
     * Parse the string and return the Grid Type in GRIDTYPE FORMAT
     * @param string			string of page type.
     * @return			        grid type in GRIDTYPE FORMAT 
     */
	
	public static GRIDTYPE parseGridType(String string)
	{
		GRIDTYPE gtype;
		int value = Integer.parseInt(string);
		switch(value)
		{
		case 1:
			gtype = GRIDTYPE.PROFILE_QUESTION;
		break;
		case 2:
			gtype = GRIDTYPE.PROFILE_ANSWER;
			break;
		case 3:
			gtype = GRIDTYPE.EDITBOX_EMAIL;
			break;
		case 4:
			gtype = GRIDTYPE.EDITBOX_PASSWORD;
			break;
		case 5:
			gtype = GRIDTYPE.SUBMIT_BOX;
			break;
		case 6:
			gtype = GRIDTYPE.NEXT_PAGE;
			break;
		case 7:
			gtype = GRIDTYPE.PREVIOUS_PAGE;
			break;
		case 8:
			gtype = GRIDTYPE.SKIP_PAGE;
			break;
		case 9:
			gtype = GRIDTYPE.OPEN_BROWSER;
			break;
		case 10:
			gtype = GRIDTYPE.PROFILE_PIC;
			break;
		case 11:
			gtype = GRIDTYPE.USER_CONTENT;
			break;
		case 12:
			gtype = GRIDTYPE.EDITBOX_TEXT;
			break;
		case 13:
			gtype = GRIDTYPE.OPEN_FILEMANAGER;
			break;
		case 14:
			gtype = GRIDTYPE.TEXTVIEW;
			break;
		case 15:
			gtype = GRIDTYPE.TEXTVIEW_CLICK;
			break;
		default:
			gtype = GRIDTYPE.USER_CONTENT;
			
		}
		return gtype;
	}
	public static VIEWTYPE getView(GridElement element)
	{
		
		GRIDTYPE type = element.getGridType();
		switch(type)
		{
		case PROFILE_QUESTION :
			return VIEWTYPE.LOADERIMAGEVIEW;
		case PROFILE_ANSWER:
			element.setClickable(true);
			return VIEWTYPE.LOADERIMAGEVIEW;
		case EDITBOX_EMAIL:
			return VIEWTYPE.EDITTEXT;
		case EDITBOX_PASSWORD:
			element.setIsPassword(true);
			return VIEWTYPE.EDITTEXT;
		case SUBMIT_BOX:
			element.setClickable(true);
			return VIEWTYPE.LOADERIMAGEVIEW;
		case NEXT_PAGE:
			return VIEWTYPE.LOADERIMAGEVIEW;
		case PREVIOUS_PAGE:
			return VIEWTYPE.LOADERIMAGEVIEW;
		case SKIP_PAGE:
			return VIEWTYPE.LOADERIMAGEVIEW;
		case OPEN_BROWSER:
			element.setClickable(true);
			return VIEWTYPE.LOADERIMAGEVIEW;
		case PROFILE_PIC:
			return VIEWTYPE.LOADERIMAGEVIEW;
		case USER_CONTENT:
			return VIEWTYPE.LOADERIMAGEVIEW;
		case EDITBOX_TEXT:
			return VIEWTYPE.EDITTEXT;
		case OPEN_FILEMANAGER:
			return VIEWTYPE.LOADERIMAGEVIEW;
		case TEXTVIEW:
			return VIEWTYPE.TEXTVIEW;
		case TEXTVIEW_CLICK:
			element.setClickable(true);
			return VIEWTYPE.TEXTVIEW;
		default:
			return VIEWTYPE.LOADERIMAGEVIEW;
			
		}
		
	}
}