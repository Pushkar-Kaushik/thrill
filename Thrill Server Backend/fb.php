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
 * fb.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * Facebook Redirect Page
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/	
 

 
 /****************************************************************************
 * Include Files
 *****************************************************************************/
 
	include("lib/config.php");
	include("lib/DB.php");
	include("lib/db_config.php");
	include("functions/xmlClass.php");
	include("functions/ServerResponse.php");
	include("functions/fb_functions.php");
	
	
	$app_id = $global['app_id'];
	$app_secret = $global['secret'];
	$my_url =  $global['my_url'];
	$scope = array('email', 'user_likes', 'user_interests');

	
 /****************************************************************************
 * Get all the Requested Parameters from the Client
 *****************************************************************************/		
	
	$code = $_REQUEST['code'];
	$req = $_REQUEST['state'];

	getFacebookData($req);
	
	

	
	
?>