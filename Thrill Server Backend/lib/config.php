<?php 
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
 * config.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This File contains all the Global Definitions used in the entire Thrill Project. 
 * 
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/

 
 
	date_default_timezone_set('Asia/Calcutta'); 
	define('BASE_URL', 'http://182.71.50.155/thrill');
	
	
/*****************************************************************************
* SET Facebook Application Properties
******************************************************************************/		
	global $global;
	$global['app_id'] = '';
	$global['secret'] = '';
	$global['my_url'] = 'http://mreapps.in/thrill/fb.php';

	
	
	
/*****************************************************************************
* SET Event Type Properties
******************************************************************************/
	enum_page_type(array(
		'DOWNLOADIMAGE',
		'GETNEXTPAGE'),
	false);

	
	
/*****************************************************************************
* SET Page Type Properties
******************************************************************************/
	enum_page_type(array(
		'PROFILEQA',
		'USERSIGNUP',
		'SIGNUPSUCCESS',
		'SIGNUPFAILURE'),
	false);

	

	
/*****************************************************************************
* SET Grid Type Properties
******************************************************************************/	
	enum_grid_type(array(
		'PROFILEQUESTION',
		'PROFILEANSWER',
		'EMAILEDITBOX',
		'PASSWORDEDITBOX',
		'SUBMITBOX',
		'NEXTPAGE',
		'PREVIOUSPAGE',
		'SKIPPAGE',
		'OPENBROWSER',
		'PROFILEPICTURE',
		'USERCONTENT',
		'CHATEDITBOX',
		'OPENFILEMANAGER',
		'TEXTVIEW',
		'EDITTEXTVIEW',
		'FACEBOOKCONNECT',
		'VIEWABLEIMAGE'), 
	false);

	
	
/*****************************************************************************
* Method to Set Event Type Properties
******************************************************************************/	
	function enum_event_type($array, $asBitwise = false) 
	{
		if($array === null)             
			return FALSE;   
       
		if(!is_array($array))          
			return FALSE;  
	       
		$count = 0; 
		
		foreach($array as $i)
		{                   
			define($i, "$count");  	
			$count++;       
		}      
	}


	
/*****************************************************************************
* Method to Set Page Type Properties
******************************************************************************/	
	function enum_page_type($array, $asBitwise = false) 
	{
		if($array === null)             
			return FALSE;   
       
		if(!is_array($array))          
			return FALSE;  
	       
		$count = 1; 
		
		foreach($array as $i)
		{                   
			define($i, "$count");  	
			$count++;       
		}      
	}
	
	
	
/*****************************************************************************
* Method to Set Grid Type Properties
******************************************************************************/	
	
	function enum_grid_type($array, $asBitwise = false) 
	{          
		if($array === null)             
			return FALSE;   
       
		if(!is_array($array))          
			return FALSE;  
	       
		$count = 1; 
		
		foreach($array as $i)
		{             
 			define($i, "$count");  	
			$count++; 
		}      
	}  

	

	
?>
