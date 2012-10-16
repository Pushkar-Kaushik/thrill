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
 * index.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This is the Base File to which every request is made by the Client Side. 
 * It subsequently passes on all the requests to other methods depending on the 
 * request type.
 * 
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/	
 
 
 
 /****************************************************************************
 * Include Files
 *****************************************************************************/
 
	include("lib/db_config.php");
	include("lib/DB.php");
	include("lib/config.php");
	include("functions/userprofile.php");
	include("functions/fb_functions.php");
	include("functions/xmlClass.php");
	include("functions/ServerResponse.php");

	
 /****************************************************************************
 * Define Objects of Classes UserProfile, ServerResponse, DB
 *****************************************************************************/
 
	$date = new DateTime();	
	$db= new DB();
	$user_prof = new UserProfile();
	$response = new ServerResponse();
	$con =$db->makeConnection();
	
		
	
 /****************************************************************************
 * Get all the Requested Parameters from the Client
 *****************************************************************************/	
 
    $headers = array();
	$headers = getallheaders();
	$user_prof->make($headers);
	

	if(isset($_REQUEST['event']) && $_REQUEST['event']!='') { $event=$_REQUEST['event']; } else { $event=''; }
		
	if(isset($_REQUEST['gid']) && $_REQUEST['gid']!='') { $gid=$_REQUEST['gid']; } else { $gid=''; }
	
	if(isset($_REQUEST['pgid']) && $_REQUEST['pgid']!='') { $pgid=$_REQUEST['pgid']; } else { $pgid=''; }	
		
	if(isset($_REQUEST['pgtype']) && $_REQUEST['pgtype']!='') { $pgtype=$_REQUEST['pgtype']; } else { $pgtype=''; }		
		
	if(isset($_REQUEST['pid']) && $_REQUEST['pid']!='') { $pid=$_REQUEST['pid']; } else { $pid=''; }
	
	if(isset($_REQUEST['cid']) && $_REQUEST['cid']!='') { $cid=$_REQUEST['cid']; } else { $cid=''; }
		
	if(isset($_REQUEST['response']) && $_REQUEST['response']!='') { $respns=$_REQUEST['response']; $res = explode(',' ,$respns); } else { $respns=''; }
	
	
	
	
 /****************************************************************************
 * Initialize as well as verify Member and Session Details, and perform action
 * based on client request.
 *****************************************************************************/		
 
	$flag = $user_prof->initialise();
	if($flag == "true")
	{		
		header("Content-type: application/text");
		header("SESSION: ".$user_prof->session_id);
		header("MEMBERID: ".$user_prof->memberid);

		if($event != "")
		{
			switch($event)
			{
				case DOWNLOADIMAGE: $response->downloadimage($user_prof,$gid); break;
				case GETNEXTPAGE: $response->send_new_page($user_prof, $pgid, $pgtype, $res); break;
			}
		}
		else
		{
			$response->send_new_page($user_prof, $pgid, $pgtype, $res);
		}

	}
	else
	{
		echo "Request Not Complete";
	}
	




?>
