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
 * fb_functions.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file deals with the connecting with Facebook and Other tasks related to it. 
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/	
 
 
	
	$app_id = $global['app_id'];
	$app_secret = $global['secret'];
	$my_url =  $global['my_url'];
	$scope = array('email', 'user_likes', 'user_interests');	
	$err = $_REQUEST['error'];

	
 
 /****************************************************************************
 * Function Name: getFacebookData 
 * Params: 1.) req: Consists of a session id and member id concatinated to a string.  
 * Returns: Nothing.
 *****************************************************************************/		
 
	function getFacebookData($req)
	{		
		global $app_id, $my_url, $scope, $code, $app_secret;	
		if(empty($code)) 
		{
			getAuthorizationUrl($req);			
		}
		else
		{		
			$state = explode(',' , $req);
			$sessionid = $state[0];
			$memberid = $state[1];

			if(!empty($err))
			{
				header("SESSION: ".$sessionid);
				header("MEMBERID: ".$memberid);				
				$query = "SELECT pageid FROM tb_pages WHERE pgtypeid=".SIGNUPFAILURE;
				sendSignUpResultPage($query, $sessionid, $memberid);				
			}
			else
			{
				$access_token = getAccessToken($app_id, $my_url, $app_secret, $code);		
				$query = "SELECT uid, first_name, last_name, pic_big, sex, interests, email FROM user WHERE uid=me()";
				$fql_query_obj = fbquery($query, $access_token);

				header("SESSION: ".$sessionid);
				header("MEMBERID: ".$memberid);
				$query = "SELECT pageid FROM tb_pages WHERE pgtypeid=".SIGNUPSUCCESS;
				sendSignUpResultPage($query, $sessionid, $memberid);				
				updateRecords($fql_query_obj, $memberid);
				
			}
		}
	}
	

 
 /****************************************************************************
 * Function Name: getAuthorizationUrl 
 * Params: 1.) req: Consists of a session id and member id concatinated to a string.  
 * Returns: Nothing.
 *****************************************************************************/	
	function getAuthorizationUrl($req)
	{
		global $app_id, $my_url, $scope;
		$dialog_url = "https://www.facebook.com/dialog/oauth?client_id=" . $app_id . "&redirect_uri=" . urlencode($my_url) . "&scope=" . implode(',',$scope)."&state=".$req;	
		header('Location: '.$dialog_url);
	}
	
	
 
 /****************************************************************************
 * Function Name: getAccessToken 
 * Params: 1.) app_id: App Id of the Facebook Application
 *		   2.) my_url: Redirect URL of the Page to which Facebook will return back. 
 *		   3.) app_secret: App Secret of the Facebook Application
 *		   4.) code: Device Code
 * Returns: Access Token returned by the Facebook
 *****************************************************************************/		
	function getAccessToken($app_id, $my_url, $app_secret, $code)
	{
		$token_url = "https://graph.facebook.com/oauth/access_token?" . "client_id=" . $app_id . "&redirect_uri=" . urlencode($my_url) . "&client_secret=" . $app_secret . "&code=" . $code;
		$response = getUrl($token_url);
		$params = null;
		parse_str($response, $params);
		$token = $params['access_token'];		
		return $token;
	}
	
	
	
 
 /****************************************************************************
 * Function Name: getUrl 
 * Params: 1.) url: Facebook URL to hit.
 * Returns: Response got on hitting the URL.
 *****************************************************************************/	
 
	function getUrl($url, $fields='', $method='GET')
	{
		$ch = curl_init();
		$fields_string = '';
		if(($fields != '')){
			foreach ( $fields as $key => $value ) {
				$fields_string .= $key . '=' . urlencode($value) . '&'; 
			} 
			rtrim ( $fields_string, "&" );	
		
			if ($method == 'GET'){
				$url .= '?' . $fields_string;
			} 
			else {
				curl_setopt ($ch, CURLOPT_POST, 0);
				curl_setopt ($ch, CURLOPT_POSTFIELDS,$fields_string);
			}	 
		}	  	
		curl_setopt ($ch, CURLOPT_URL, $url);
		curl_setopt ($ch, CURLOPT_VERBOSE, 0);
		curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt	($ch, CURLOPT_SSL_VERIFYPEER, false);
		$result = curl_exec ($ch);
		if ($result==false) {
			$result = "curl_exec_ERR";
		}
		curl_close ($ch);	
		return $result;		
	}
	
	
	
 
 /****************************************************************************
 * Function Name: fbquery 
 * Params: 1.) query: Query to execute.
 * 		   2.) token: Access Token
 * Returns: Response got on hitting the URL.
 *****************************************************************************/		
	function fbquery($query, $token, $json=false)
	{
		$url = "https://graph.facebook.com/fql?q=".urlencode($query)."&access_token=".$token;
		//echo $url;
		if($res = getUrl($url)){
			if($json){
				return $res;
			}
			else {
				return json_decode($res, TRUE);
			}
		}
		return false;
	}
	
	
	
	
 
 /****************************************************************************
 * Function Name: updateRecords 
 * Params: 1.) resultarr: Array of details returned from facebook
 * 		   2.) memberid: Member Id of the current member
 * Returns: Nothing.
 *****************************************************************************/			
	function updateRecords($resultarr, $memberid)
	{
		$db = new DB();
		$db->makeConnection();
		$query = "UPDATE tb_member SET firstname = '".$resultarr['data'][0]['first_name']."', lastname = '".$resultarr['data'][0]['last_name']."', profilepicture = '".$resultarr['data'][0]['pic_big']."', gender = '".$resultarr['data'][0]['sex']."', emailid = '".$resultarr['data'][0]['email']. "' WHERE memberid = '".$memberid."'";
		$db->query($query);
	
	}
	
	
 
 /****************************************************************************
 * Function Name: sendSignUpResultPage 
 * Params: 1.) query: SQL query to get next new page 
 * 		   2.) memberid: Member Id of the current member
 *		   3.) sessionid: Session Id of the current member
 * Returns: Nothing.
 *****************************************************************************/		
 
	function sendSignUpResultPage($query, $sessionid, $memberid)
	{
		$response = new ServerResponse();
		$db = new DB();
		$db->makeConnection();
		$result = $db->query($query);		
		$page = mysql_result($result,0);
		$details = $response->getPageDetails($page);
		$arr = array();
		while($row = (mysql_fetch_array($details,MYSQL_ASSOC))) 
		{
			$arr[] = $row;
		}
		$actbargrid = $response->getActBarGrid();
		$response->generateXML($arr, $actbargrid, $sessionid, $memberid);	
	}
?>