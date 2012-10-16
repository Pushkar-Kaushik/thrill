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
 * PageContent.java
 * 
 *
 * Project:
 * --------
 * Thrill
 * 
 *
 * Description:
 * ------------
 * This file contains class that represents page content for Thrill page.
 * 
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;

import java.util.List;

import android.graphics.Bitmap;


/**
 * This class represents a page content for Thrill page.
 */
public class PageContent {
	
	public enum PAGETYPE
	{
		PROFILE_QUESTION(1),		
		SIGN_UP (2),
		PROFILE_PAGE (3);
		
		private int code;
		 
		 private PAGETYPE(int c) {
		   code = c;
		 }
		 
		 public int getCode() {
		   return code;
		 }
	}
	
	private int                     pageId;
	private PAGETYPE                pageType;
	private List<GridElement>       gridElements;
   
    
	
	public int getPageId()
	{
		return pageId;
	}
	public void setPageId(int pageID)
	{
		this.pageId = pageID;
	}
	public List<GridElement> getGridElements()
	{
		return gridElements;		
	}
	public void setGridElements(List<GridElement> gridelements)
	{
		this.gridElements = gridelements;
	}
	public PAGETYPE getPageType()
	{
		return this.pageType;
	}
	/**
     * Set the page type for current page
     * @param pagetype			page type 
    
     */
	public void setPageType(PAGETYPE pagetype)
	{
		this.pageType = pagetype;
	}
	/**
     * Parse the string and return the Page Type in PAGETYPE FORMAT
     * @param string			string of page type.
     * @return			        page type in PAGETYPE FORMAT 
     */
	public static PAGETYPE parsePageType(String string)
	{
		PAGETYPE ptype;
		int value = Integer.parseInt(string);
		switch(value)
		{
		case 1:
			ptype = PAGETYPE.PROFILE_QUESTION;
		break;
		case 2:
			ptype = PAGETYPE.SIGN_UP;
			break;
		default:
			ptype = PAGETYPE.PROFILE_QUESTION;
			break;			
		}
		return ptype;
	}
}