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
 * ThrillXMLParser.java
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file contains class that parses XML data from THRILL server.
 *
 * Author:
 * -------
 * 
 *
 ****************************************************************************/
package org.mmt.thrill;

import android.util.Log;
import android.util.Xml;


import org.mmt.thrill.GridElement.GRIDTYPE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses XML data from THRILL server.
 * Given an InputStream representation of a page, it returns a page content,
 * where each list element represents a single GRID element in the XML data.
 */
public class ThrillXMLParser {
    // We don't use namespaces
	private static final String ns = null;
	String tag = "TH: ThrillXMLParser ";

    public PageContent parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readPage(parser);
        }
        catch(Exception e)
        {
        	Log.e(tag,tag+"Exception : "+e);   
        	return null;
        }
        finally {
            in.close();
        }
    }

    private PageContent readPage(XmlPullParser parser) throws XmlPullParserException, IOException {
    	PageContent pageContent = new PageContent();
        List<GridElement> entries = new ArrayList<GridElement>();
        int gridGroupDepth = 0;

        parser.require(XmlPullParser.START_TAG, ns, "PAGE");
        String pageId = parser.getAttributeValue(null, "id");
        String pageType = parser.getAttributeValue(null, "type");
        pageContent.setPageId(Integer.parseInt(pageId));
        pageContent.setPageType(PageContent.parsePageType(pageType));
        while (parser.next() != XmlPullParser.END_DOCUMENT) {

        	String name = parser.getName();
        	String gridGroupURL = null;

        	if (parser.getEventType() != XmlPullParser.START_TAG) {
                if (parser.getEventType() == XmlPullParser.END_TAG) {
                    if (name.equals("GGROUP")) {
                    	gridGroupDepth--;
                    }                	
                }
            	continue;
            }
            if (name.equals("GGROUP")) {
            	gridGroupURL = parser.getAttributeValue(null, "url");
            	gridGroupDepth++;
            } 
            else if (name.equals("GRID")) {
                if ((gridGroupDepth > 0) && ((gridGroupDepth % 2) != 0)){
            	    entries.add(readGridElement(parser, gridGroupDepth, gridGroupURL));                	
                }
                else {
                	throw new IllegalStateException();                	
                }
            } 
            else {
                skip(parser);
            }
        }
        
        pageContent.setGridElements(entries);
        return pageContent;
    }

    private GridElement readGridElement(XmlPullParser parser, int gridGroupId, String gridGroupURL) throws XmlPullParserException, IOException {
        String gridId         = parser.getAttributeValue(null, "id");
        String startRowIndex  = parser.getAttributeValue(null, "r");
        String startColIndex  = parser.getAttributeValue(null, "c");
        String numRow         = parser.getAttributeValue(null, "h");
        String numCol         = parser.getAttributeValue(null, "w");
        String imgWidth       = parser.getAttributeValue(null, "imgw");
        String imgHeight      = parser.getAttributeValue(null, "imgh");
        String imgSize        = parser.getAttributeValue(null, "imgs");
        String type           = parser.getAttributeValue(null, "type");
        String gridURL        = parser.getAttributeValue(null, "url");
        String parentId       = parser.getAttributeValue(null,"pid");
        String gridText       = parser.getAttributeValue(null,"text");
        String gridColor      = parser.getAttributeValue(null,"background"); 
        GRIDTYPE gridType;
        
        gridType = GridElement.parseGridType(type);
        if(parentId.equalsIgnoreCase("")|| parentId.equalsIgnoreCase(" "))
        {
        	parentId = CommonAppData.defaultParentId;
        }
        if(gridColor.equalsIgnoreCase("")||gridColor.equalsIgnoreCase(" ")) 
        {
        	gridColor = CommonAppData.defaultGridColor;
        }
        if(imgWidth.equalsIgnoreCase("")||imgHeight.equalsIgnoreCase("")||imgSize.equalsIgnoreCase(""))
        {
        	imgWidth = CommonAppData.defaultImageSpecs;
        	imgHeight = CommonAppData.defaultImageSpecs;
        	imgSize = CommonAppData.defaultImageSpecs;
        }
       
        
        return new GridElement(
        		Integer.parseInt(gridId),
        		Integer.parseInt(parentId),
        		gridGroupId,
        		Integer.parseInt(startRowIndex),
        		Integer.parseInt(startColIndex),
        		Integer.parseInt(numRow), 
        		Integer.parseInt(numCol), 
        		gridType,
        		gridURL,
        		gridGroupURL,
        		Integer.parseInt(imgWidth),
        		Integer.parseInt(imgHeight),
        		Integer.parseInt(imgSize),
        		gridText,
        		gridColor);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
