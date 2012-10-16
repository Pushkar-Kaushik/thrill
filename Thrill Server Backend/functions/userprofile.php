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
 * userprofile.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file maintains the details of the current User Member
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/	
 
 


 /****************************************************************************
 * Class Name: UserProfile 
 * Function: It contains all the member functions related to the generating and
 * storing of Current Member Details
 *****************************************************************************/
 
class UserProfile
{
	public $session_id;
	public $memberid;
	public $useragent;
	public $screen_w;
	public $screen_h;
	public $imsi;
	public $imei;
	public $countrycode;
	public $provinceid;
	public $addtime;
	public $mobileid;
	public $pgid;
	public $action;
	public $email;
	public $password;
	
	
		
 
 /****************************************************************************
 * Function Name: make 
 * Params: 1.) headers: Consists of an array of User parameters sent by the Client 
 * Returns: Nothing.
 *****************************************************************************/	
	public function make($headers)
	{

		foreach ($headers as $name => $value) 
		{
			if($name == "ssid") { $this->session_id = $value; }
			else if($name == "mid") { $this->memberid = $value; }
			else if($name == "imei") { $this->imei = $value; } 
			else if($name == "imsi") { $this->imsi = $value; }
			else if($name == "gid") { $this->gid = $value; }
			else if($name == "User-Agent") { $this->useragent = $value; }
			else if($name == "screenwidth") { $this->screen_w = $value; }
			else if($name == "screenheight") { $this->screen_h = $value; }
			else if($name == "countrycode") { $this->countrycode = $value; }
			else if($name == "provinceid") { $this->provinceid = $value; }
			else if($name == "time") { $this->addtime = $value; }
			else if($name == "email") { $this->email = $value; }
			else if($name == "password") { $this->password = $value; }
			
		}
	}
	
	
	
 /****************************************************************************
 * Function Name: initialise 
 * Params: None
 * Returns: Boolean. Whether User is a valid user.
 *****************************************************************************/		
	public function initialise()
	{
		
		$date = new DateTime();	
		$db= new DB();
		$con =$db->makeConnection();
		$response = new ServerResponse();
			
		if($this->memberid == "0")
		{
			if($this->imei != "")
			{
				$query = "SELECT memberid FROM tb_member WHERE imei='".$this->imei."'";
				$result = $db->query($query);	
				if($db->isEmpty($result))
				{
					$query = "INSERT INTO tb_member(sessionid, imei, emailid, mobilenum, profilepicture, firstname, lastname, status, useragent, screenwidth, screenheight, imsi, countrycode, provinceid, mobileid) VALUES('', '".$this->imei."', '', '', '', '', '', '0', '".$this->useragent."', '".$this->screen_w."','".$this->screen_h."', '".$this->imsi."', '".$this->countrycode."', '".$this->provinceid."', '')";
					$db->query($query);						
				}
				
				$query = "SELECT memberid FROM tb_member WHERE imei='".$this->imei."'";
				$result = $db->query($query);	
				$this->memberid = mysql_result($result,0);
			}
			else
			{
				return "false";
			}
		}
		
		
		if($this->session_id == "0")
		{		
			if($this->imei != "")
			{
				session_start();
				$this->session_id = session_id();
				$query = "SELECT memberid FROM tb_session WHERE memberid='".$this->memberid."'";
				$result = $db->query($query);
				if(!$db->isEmpty($result))
				{					
					$query = "UPDATE tb_session SET sessionid='".$this->session_id ."', addtime='".$this->addtime."'  WHERE memberid='".$this->memberid."'";
					$db->query($query);		
				}
				else
				{
					$query = "INSERT INTO tb_session(sessionid, actiontime, memberid, addtime) VALUES('".$this->session_id ."', '".$date->getTimestamp()."', '".$this->memberid."', '".$this->addtime."')";
					$db->query($query);	
					
					$query = "INSERT INTO tb_log(sessionid, actiontime, memberid, addtime, pageid, contentid) VALUES('".$this->session_id."', '".$date->getTimestamp()."', '".$this->memberid."', '".$this->addtime."','','Get Session')";
					
				}
				
				$query = "UPDATE tb_member SET sessionid = '".$this->session_id."' WHERE memberid='".$this->memberid."'";
				$db->query($query);				
				return "true";
			}
			else
			{
				return "false";
			}			
		}
		else if($this->session_id != "0")
		{
			$query = "SELECT memberid FROM tb_session WHERE sessionid='".$this->session_id."' and memberid='".$this->memberid."'";			
			$result_2 = $db->query($query);
			if(!$db->isEmpty($result_2))
			{
				$query = "INSERT INTO tb_log(sessionid, actiontime, memberid, addtime, pageid, contentid) VALUES('".$this->session_id."', '".$date->getTimestamp()."', '".$this->memberid."', '".$this->addtime."','".$this->pageid."','".$this->action."')";							
				return "true";
			}
			else
			{
				return "false";
			}		
		}
		else
		{
			return "false";
		}
	}

}

?>