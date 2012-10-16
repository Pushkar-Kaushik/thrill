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
 * PageLayoutDesc.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains class that layout of Thrill page to be displayed.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;

/**
 * This class represents layout of Thrill page to be displayed.
 */

public class PageLayoutDesc {
	
	
	static int pageBackColor1 = Color.WHITE;
	static int pageBackColor2 = Color.rgb(255, 190, 193);
	static int actionBarBackColor = Color.rgb(230, 81, 89);//Color.rgb(162, 29, 29);
	
	static final float defaultActionBarHeight = 48;
	static final float defaultRowHeight = 25/2;
	static final float defaultScreenHeightMargin = 12;
	
	private final float minWidthTolerance = 10;
    private float gutterWidth;
    private float gutterHeight;
    private float screenWidthMargin;
    private float screenHeightMargin;
    private int totalColumn;
    private int totalRows;
    private int screenWidth;
    private int screenHeight;
    private float rowWidth;
    private float rowHeight;
    private int actionBarWidth;
    private int actionBarHeight;
    
    private float c1,c2,c3,c4;
	
	
	public PageLayoutDesc
	(
			float gutterWidth,
			float screenWidthMargin,
			int totalColumns,
			int totalRows,
			int screenWidth,
			int screenHeight
	)
	{
		
		this.rowWidth = (((screenWidth)-2*screenWidthMargin)-(totalColumns-1)*gutterWidth)/totalColumns;
		
		
		this.gutterWidth = gutterWidth;
		this.screenWidthMargin = screenWidthMargin;
		this.screenHeightMargin = defaultScreenHeightMargin;
		this.totalColumn = totalColumns;
		this.totalRows = totalRows;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.actionBarWidth = screenWidth;
		this.actionBarHeight = (int)defaultActionBarHeight;
		this.rowHeight = (screenHeight - actionBarHeight - 2*screenHeightMargin)/(2*totalRows);//rh;
		this.gutterHeight = rowHeight;
		find_constants();
		
	}
	public void find_constants()
	{
		/**
		 * column(n)  = (n-1)*c1 + c2
		 */
		this.c1 = rowWidth + gutterWidth;
		this.c2 = screenWidthMargin;
		this.c3 = rowHeight+gutterHeight;
		this.c4 = actionBarHeight + screenHeightMargin;
		
	}
	
	/**
     * Get x-coordinate of grid element
     * @param column Column number 
     * @return x-coordinate    
     */
	public int getX(int column)
	{
		int x_coor = -1;
		x_coor = (int)((column -1)*c1 + c2);//(n-1)*c1 + c2		
		return x_coor;
	}
	/**
     * Get y-coordinate of grid element
     * @param coloumn Row number 
     * @return y-coordinate    
     */
	public int getY(int row)
	{
		int y_coor = -1;
		y_coor = (int)((row -1)*c3 + c4);//(m-1)*c3 + c4		
		return y_coor;
	}
	
	public int getWidth()
	{
		return screenWidth; 
	}
	public int getHeight()
	{
		return screenHeight; 
	}
	public int getTotalColumn()
	{
		return totalColumn;
	}
	public int getTotalRows()
	{
		return totalRows;
	}
	public float getScreenWidthMargin()
	{
		return screenWidthMargin;
	}
	public float getScreenHeightMargin()
	{
		return screenHeightMargin;
	}
	public float getRowWidth()
	{
		return rowWidth;
	}
	public float getRowHeight()
	{
		return rowHeight;
	}
	public float getGutter()
	{
		return gutterWidth;
	}
	/**
     * Get width of action bar.
     * @return width of action bar   
     */
	public int getActionbarWidth()
	{
		return actionBarWidth;
	}
	/**
     * Get height of action bar.
     * @return height of action bar   
     */
	public int getActionbarHeight()
	{
		return actionBarHeight;
	}
	/**
     * Get width of number of columns.
     * @param numberOfColumns Total number of columns 
     * @return Total width of Columns   
     */
	public int getWidthofColumns(int numberOfColumns)
	{
		int val = (int)(numberOfColumns*rowWidth + (numberOfColumns -1)*gutterWidth);
		return val;
	}
	/**
     * Get height of number of rows.
     * @param numberOfRows Total number of rows 
     * @return Total width of rows   
     */
	public int getHeightofRows(int numberOfRows)
	{
		int val = (int)(numberOfRows*rowHeight + (numberOfRows -1)*gutterHeight);
		return val;
	}
	
	
	
	
}
