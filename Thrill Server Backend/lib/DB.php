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
 * DB.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * This file maintains and performs all the Database Transations.
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/	
 
 
class DB
{
	var $link;
	
		
 
 /****************************************************************************
 * Function Name: makeConnection 
 * Params: None.
 * Returns: Connection handle
 *****************************************************************************/		
	function makeConnection()
	{
		$this->link = mysql_connect(SERVER,DB_USER,DB_PASSWORD);
		if ( ! $this->link )
			die( "Couldn't connect to MySQL. ".mysql_error());
		mysql_select_db( DATABASE, $this->link ) or die( "Couldn't open {DATABASE} database. ".mysql_error() );
      
        return($this->link);
	}


	 
 /****************************************************************************
 * Function Name: closeConnection 
 * Params: None.
 * Returns: Nothing.
 *****************************************************************************/		
	function closeConnection()
	{
		mysql_close($this->link);
	}
	


	 
 /****************************************************************************
 * Function Name: query 
 * Params: Query to execute.
 * Returns: Result.
 *****************************************************************************/		
	function query($query)
	{
		$res = mysql_query($query,$this->link) or die("$query <-error");
	    if(!$res){
		    trigger_error('FAILED: '.$query, E_USER_NOTICE);
	    }else{
		    return $res ;
	    }
	}

	 
 /****************************************************************************
 * Function Name: fetchRow 
 * Params: 1.) res: Result from executing query.
 * Returns: Result.
 *****************************************************************************/		
	function fetchRow($res)
	{
	    return mysql_fetch_assoc($res);
   	}


	
 /****************************************************************************
 * Function Name: numRows 
 * Params: 1.) res: Result from executing query.
 * Returns: Result.
 *****************************************************************************/		
	function numRows($res)
	{
	     return mysql_num_rows($res);
	}


	
 /****************************************************************************
 * Function Name: isEmpty 
 * Params: 1.) res: Result from executing query.
 * Returns: bool
 *****************************************************************************/			
	function isEmpty($res)
	{
	    if($this->numRows($res) > 0){
		    return false;
	    }else{
		    return true;
	    }
    	}
 
	
	
 /****************************************************************************
 * Function Name: insertIntoLog 
 * Params: 1.) pgid: PageId of the Page for which the User has responded. 
 *   	   2.) cid: Consists of ContentId and/or ParentId the User selected 
 *		   3.) sessionid: Session Id of the Current User.
 *		   4.) memberid: Member Id of the Current User. 
 * Returns: Nothing.
 *****************************************************************************/		
 
	function insertIntoLog($session_id, $memberid, $pgid, $cid)
	{
		$date = new DateTime();
		$time = $date->getTimestamp();
		$query = "INSERT INTO tb_log(sessionid, actiontime, memberid, addtime, pageid, contentid) VALUES('$session_id', '$time', '$memberid', '','$pgid','$cid')";
		$this->query($query);
	}


	
		
 /****************************************************************************
 * Function Name: updatePoints 
 * Params: 1.) cid: Consists of ContentId and/or ParentId the User selected 
 *		   2.) memberid: Member Id of the Current User. 
 * Returns: Nothing.
 *****************************************************************************/	
 
	function updatePoints($memberid, $cid)
	{
		
		$query = "SELECT points FROM tb_points WHERE cid =".$cid;
		$result = $this->query($query);
		if(mysql_num_rows($result)>0)
		{
			$points = mysql_result($result,0);
			$query = "SELECT points FROM tb_member WHERE memberid =".$memberid;
			$result_1 = $this->query($query);
			$mempoints = intval(mysql_result($result_1,0));
			$points = $points + $mempoints ;				
			$query = "UPDATE tb_member SET points = ".$points." WHERE memberid =".$memberid;
			$this->query($query);
		}
				
	}

	
		
		
 /****************************************************************************
 * Function Name: updatePoints 
 * Params: 1.) memberid: Member Id of the Current User. 
 * Returns: Points.
 *****************************************************************************/		
	function getMemberPoints($memberid)
	{

		$query = "SELECT points FROM tb_member WHERE memberid =".$memberid;
		$result = $this->query($query);

			$points = mysql_result($result,0);
			return $points;
		
	}

	
		
 /****************************************************************************
 * Function Name: getPageDetails 
 * Params: 1.) page: Page Id 
 * Returns: Array of Page Details
 *****************************************************************************/			
	public function getPageDetails($page)
	{
	

		$query = "SELECT tb_content.pageid, tb_content.cid, tb_content.url, tb_content.parentid, tb_content.gtypeid, tb_content.imagesize, tb_content.imageheight, tb_content.imagewidth, tb_grids.row, tb_grids.column, tb_grids.width, tb_grids.height, tb_content.gridgroup, tb_content.gtypeid, tb_pages.pgtypeid, tb_content.background, tb_content.textcolor, tb_content.text, tb_content.textsize
				FROM tb_content
				INNER JOIN tb_grids
				INNER JOIN tb_pages                                                                                                                       			ON tb_content.pageid = tb_pages.pageid AND tb_content.gridid = tb_grids.gridid
				WHERE (tb_pages.pageid ='".$page."') ORDER BY tb_content.gridgroup ASC";
		$result = $this->query($query);	

		$arr = array();
		while($row = (mysql_fetch_array($result ,MYSQL_ASSOC))) 
		{
			$arr[] = $row;
		}
		return $arr;
	}

	
	
 /****************************************************************************
 * Function Name: updateEmailId 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 * Returns: Page ID
 *****************************************************************************/			
	public function updateEmailId(UserProfile $userprof)
	{
		$query = "UPDATE tb_member SET emailid='".$userprof->email."' WHERE memberid='".$userprof->memberid."'";
		$this->query($query);
		$query = "SELECT pageid FROM tb_pages WHERE pgtypeid='".SIGNUPSUCCESS."'";
		$result = $this->query($query);		
		$page = mysql_result($result,0);
		return $page;
	}


		
 /****************************************************************************
 * Function Name: getActBarGrid 
 * Params: None
 * Returns: Action Bar Grid ID
 *****************************************************************************/		
 
	public function getActBarGrid()
	{
		$query = "SELECT tb_content.cid FROM tb_content WHERE tb_content.gridid ='0'";			
		$result_0 = $this->query($query);		
		$actbargrid = mysql_result($result_0,0); 
		return $actbargrid;
	}

	
	
 /****************************************************************************
 * Function Name: fillSignUpDetails 
 * Params: 1.) userprof: UserProfile Object consisting of all the current User details.
 * Returns: Nothing
 *****************************************************************************/		
 
	public function fillSignUpDetails(UserProfile $userprof)
	{		
		$query = "UPDATE tb_member SET emailid = '".$userprof->email."', password = '".$userprof->password."' WHERE memberid = '".$userprof->memberid."'";
		$this->query($query);				
	}
}


?>
