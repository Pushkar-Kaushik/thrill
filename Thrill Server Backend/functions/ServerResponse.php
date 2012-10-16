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
 * ServerResponse.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This is the main functions file which performs all the logical calculations 
 * regarding Responses to be sent back to the Client. 
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/	
 
 
 
 
 /****************************************************************************
 * Class Name: ServerResponse 
 * Function: It contains all the member functions related to the responses sent
 * to the client.
 *****************************************************************************/
 
 class ServerResponse 
{
	
	
 /****************************************************************************
 * Function Name: downloadimage 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 *   	   2.) contentid: Consists of the ImageId of the Image Client requested to download. 
 * Returns: Sends an Image in the form of the attachment to the Client.
 *****************************************************************************/	
 
	public function downloadimage(UserProfile $userprof, $contentid)
	{		
		$db= new DB();
		$date = new DateTime();
		$con =$db->makeConnection();
				
		$query = "SELECT filename, savepath FROM tb_content WHERE cid='".$contentid."' AND status='1'";  //Check whether ContentId is a valid one and is not banned by the Admin
		$result = $db->query($query);
				
		$contentdetails = array();
		$contentdetails = mysql_fetch_row($result);
		$filename = $contentdetails[0];
		$filepath = BASE_URL.$contentdetails[1];

		if ($fd = fopen ($filepath, "r")) 
		{			
			$fsize = filesize($filepath);
			$path_parts = pathinfo($filepath);
			$ext = strtolower($path_parts["extension"]);
			$db->insertIntoLog($userprof->session_id, $userprof->memberid, '', 'fetch imageid '.$contentid);			
																							
			switch($ext)
			{		
			case 'png' : header("Content-type: application/png"); 
					header("Content-Disposition: attachment; filename= \"".$path_parts["basename"]."\""); // use 'attachment' to force a download
					break;
			case 'jpg' : header("Content-type: application/jpg"); 
					header("Content-Disposition: attachment; filename= \"".$path_parts["basename"]."\""); // use 'attachment' to force a download	
					break;																									
			}	
						
			while(!feof($fd))
			{
				$buffer = fread($fd, 2048);
				echo $buffer;
			}
		}
		fclose($fd);		
		exit;	
	}
	
	
 
 
 /****************************************************************************
 * Function Name: send_new_page 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 *   	   2.) pgid: PageId of the Page for which the User has responded. 
 *		   3.) pgtype: Type of the Page
 *		   4.) res: Consists of ContentId and/or ParentId the User selected 
 * Returns: Nothing. Generates an XML which is sent directly to the Client.
 *****************************************************************************/		
 
	public function send_new_page(UserProfile $userprof, $pgid, $pgtype, $res)
	{		
		$db= new DB();
		$db->makeConnection();

		$actbargrid = $db->getActBarGrid();		
		
		if(empty($pgid))
		{
			$page = $this->getDefaultPage($userprof);			
		}
		else if(!empty($pgid) && !empty($pgtype))
		{			
			switch($pgtype)
			{
			case PROFILEQA:
				{
					$page = $this->getNextPage($userprof, $res, $pgid);							
					break;	
				}
			case USERSIGNUP:
				{	
					$page =$this->getSignUpRequest($userprof, $res, $pgid);					
					break;
				}
			}			
		}
		else
		{
			return "false";
		}
		
		if(!empty($page))
		{
			$arr = $db->getPageDetails($page);							
			$this->generateXML($arr,$actbargrid,$userprof->session_id, $userprof->memberid);	
		}
	}
	
	
 /****************************************************************************
 * Function Name: generateXML 
 * Params: 1.) arr: Consists of the Array of Pages and their details. 
 *   	   2.) actbarid: Consists of the Id of the ActionBar 
 *		   3.) sessionid: Session Id of the Current User.
 *		   4.) memberid: Member Id of the Current User. 
 * Returns: Generates an XML which is sent directly to the Client.
 *****************************************************************************/		
 
	public function generateXML($arr, $actbarid, $sessionid, $memberid)
	{
		$db= new DB();
		$con =$db->makeConnection();

		if($arr[0]['pgtypeid'] == USERSIGNUP)
		{
			$points = $db->getMemberPoints($memberid);
			$textview = "You have earned $points so far! Don't lose them! Sign up below with Facebook or your e-mail address to continue: ";			
		}


		$xmlstr = "<PAGE></PAGE>";
		$xml = new xmlClass($xmlstr);
			$xml->addChildAttribute("id",$arr[0]['pageid']);
			$xml->addChildAttribute("type",$arr[0]['pgtypeid']);
		$actbargrid = $xml->appendChildKey("ACTBARGRID");
			$actbargrid->addChildAttribute("id",$actbarid);

		$i=0;
		while($i < count($arr))
		{
			$ggroupnum = $arr[$i]['gridgroup'];
			$gridgroup = $xml->appendChildKey("GGROUP");	
			while($ggroupnum == $arr[$i]['gridgroup'])
			{		
							
				if($arr[$i]['url'] != "")
				{	
					if($arr[$i]['gtypeid'] == OPENBROWSER && $arr[$i]['pgtypeid'] == USERSIGNUP) // FOR Facebook URL
					{
						$url = $arr[$i]['url']."?state=".$sessionid.",".$memberid;
					}
					else
					{
						$url = $arr[$i]['url'];
					}
				}
				else
					$url = "";

				if($arr[$i]['gtypeid'] == TEXTVIEW && $arr[$i]['pgtypeid'] == USERSIGNUP)
				{
					$arr[$i]['text'] = $textview;
				}
					
				$grid = $gridgroup->appendChildKey("GRID");
					$grid->addChildAttribute("id", $arr[$i]['cid']);
					$grid->addChildAttribute("url", $url);
					$grid->addChildAttribute("pid", $arr[$i]['parentid']);
					$grid->addChildAttribute("r", $arr[$i]['row']);
					$grid->addChildAttribute("c", $arr[$i]['column']);
					$grid->addChildAttribute("w", $arr[$i]['width']);
					$grid->addChildAttribute("h", $arr[$i]['height']);
					$grid->addChildAttribute("background", $arr[$i]['background']);	
					$grid->addChildAttribute("textcolor", $arr[$i]['textcolor']);	
					$grid->addChildAttribute("text", $arr[$i]['text']);	
					$grid->addChildAttribute("textsize", $arr[$i]['textsize']);	
					$grid->addChildAttribute("imgw", $arr[$i]['imagewidth']);
					$grid->addChildAttribute("imgh", $arr[$i]['imageheight']);
					$grid->addChildAttribute("imgs", $arr[$i]['imagesize']);
					$grid->addChildAttribute("type", $arr[$i]['gtypeid']);
					$grid->addChildAttribute("groupid", $arr[$i]['gridgroup']);
					
					$db->insertIntoLog($sessionid, $memberid, $arr[0]['pageid'], $arr[$i]['cid']);
				$i++;
			}
		}
		$xml->printXml();
	}
	


	
 /****************************************************************************
 * Function Name: getDefaultPage 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 * Returns: A Default Page Id
 *****************************************************************************/		
 
	public function getDefaultPage(UserProfile $userprof)
	{
		$i = 0;
		$db= new DB();
		$date = new DateTime();
		$con =$db->makeConnection();
		
		$query = "SELECT pageid FROM tb_pages WHERE defaultpg='1'";
		$result_1 = $db->query($query);
		
		$pages = array();
		while($i < $db->numRows($result_1)) 
		{
			$pages[$i] = mysql_result($result_1,$i);
			$i++;
		}

		$pgnum = array_rand($pages,1);		
		$page = $pages[$pgnum];		
		return $page;		
	}
	
	

	
 /****************************************************************************
 * Function Name: getNextPage 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 *   	   2.) pgid: PageId of the Page for which the User has responded. 
 *		   3.) res: Consists of ContentId and/or ParentId the User selected 
 * Returns: Id of the Next Page to be pushed to the Client
 *****************************************************************************/			
	
	public function getNextPage(UserProfile $userprof, $res, $pgid)
	{	
		
		$i = 0;
		$pages = array();
		$db= new DB();
		$date = new DateTime();
		$con =$db->makeConnection();
		
		foreach($res as $value)
		{							
			$idlist = explode(':', $value);			
			if(count($idlist) > 1)
			{				
				$cid = $idlist[1];			
			}
			else
			{
				$cid = $idlist[0];
			}	

			$db->updatePoints($userprof->memberid, $cid);
			$query = "UPDATE tb_log SET selected = 'true', addtime = '".$date->getTimestamp()."'  WHERE memberid = '".$userprof->memberid."' AND pageid = '".$pgid."' AND contentid = '". $cid ."'";
			$db->query($query);		
		}
					
		$query = "SELECT COUNT(DISTINCT tb_log.pageid) FROM tb_log INNER JOIN tb_pages ON tb_log.pageid = tb_pages.pageid WHERE tb_pages.pgtypeid='".PROFILEQA."' AND tb_log.memberid = '".$userprof->memberid."'";
		$result_1 = $db->query($query);
		$pgcount = mysql_result($result_1,0);
		
		
		if(intval($pgcount) == 3)
		{
			$query = "SELECT pageid FROM tb_pages WHERE pgtypeid = '".USERSIGNUP."'";
			$result = $db->query($query);
			$page = mysql_result($result,0);
		}
		else
		{		
			$query = "SELECT pageid FROM tb_pages WHERE pageid NOT IN(SELECT DISTINCT(pageid) FROM tb_log WHERE memberid=".$userprof->memberid.") AND pgtypeid = '".PROFILEQA."'";
			$result = $db->query($query);
			while($i < $db->numRows($result)) 
			{
				$pages[$i] = mysql_result($result,$i);
				$i++;
			}
			$pgnum = array_rand($pages,1);			
			$page = $pages[$pgnum];						
		}
						
		return $page;
	}
	
	
	

	
 /****************************************************************************
 * Function Name: getSignUpRequest 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 *   	   2.) pgid: PageId of the Page for which the User has responded. 
 *		   3.) res: Consists of ContentId and/or ParentId the User selected 
 * Returns: Id of the Next Page to be pushed to the Client
 *****************************************************************************/		
 
	public function getSignUpRequest(UserProfile $userprof, $res, $pgid)
	{
		
		$db= new DB();
		$date = new DateTime();
		$con =$db->makeConnection();	

		$query = "UPDATE tb_log SET selected = 'true', addtime = '".$date->getTimestamp()."'  WHERE memberid = '".$userprof->memberid."' AND pageid = '".$pgid."' AND contentid = '". $res[0]."'";
		$db->query($query);		
			
		$query = "SELECT gtypeid FROM tb_content WHERE cid = '".$res[0]."'";
		$result = $db->query($query);

		$gridtype = mysql_result($result, 0);
	
		switch($gridtype)
		{		
		case SUBMITBOX:
			$page = $db->updateEmailId($userprof);
			return $page;	 
			
		}		
	}
	

}	

?>
