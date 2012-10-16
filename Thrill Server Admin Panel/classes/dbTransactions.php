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
 * dbTransactions.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * Carries out any type of transaction required with the DB to retrieve various data
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/
 









include("configs/dbconfig.php");
include("configs/dbFunctions.php");

$db= new DB();
$con =$db->makeConnection();

class dbTransactions
{


	//Function to Save the Template Data received from createtemplate.tpl
	function savetemplate($tempname,$gridcount,$rowdata,$coldata,$widthdata,$heightdata)
	{
	 $smarty = new Smarty;
	 $createDateTime = date("Y-m-d H:i:s");
	 
	 //Getting the value of Auto-Incremented field
	 $sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'tb_template' AND TABLE_SCHEMA = '".DATABASE."'";
	 $result = mysql_query($sql);
	 $row=mysql_fetch_assoc($result);
	 $tempid = $row['AUTO_INCREMENT']; 
	 
	 //Creating Template Data
	 $sql1="insert into tb_template values('','".$tempname."','".$gridcount."','".$createDateTime."','1')";
	 mysql_query($sql1);
	 
		//Creating the Grid data
		 for($x=1;$x<=$gridcount;$x++)
		 {
		 $sql3="insert into tb_grids values('','".$tempid."','".$rowdata[$x]."','".$coldata[$x]."','".$heightdata[$x]."','".$widthdata[$x]."','".$createDateTime."','1')";
		 mysql_query($sql3);
		 }
		 
	//If the process was successfull	 
	header('Location: /thrill.adminpanel/index.php?action=showsuccess');
	}
	
	
	
	
	
	//Function to get the template data for creating the page
	function getTemplateData()
	{
		 $sql="select templateid,templatename,gridcount from tb_template where status=1";
		 $res = mysql_query($sql);
		 /** TEMPLATE COUNT **/
		 $tempcount = mysql_num_rows($res);
		 
		 for($i=1;$i<=$tempcount;$i++)
		 {
		 $rows= mysql_fetch_assoc($res);
		 /** TEMPLATE IDS **/
		 $tempids[$i]=$rows['templateid'];
		 /* TEMPLATE NAMES **/
		 $tempnames[$i]=$rows['templatename'];
		 /** RELATED GRID COUNT **/
		 $gridcnt[$i]=$rows['gridcount']; 
		 
		 }
		 
		 
		 $returnstr="";
		 for($j=1;$j<= $tempcount;$j++)
		 { 
		 $temp1[$j]= $tempids[$j].",".$tempnames[$j].",".$gridcnt[$j]."#";
		 	
		 $sql2="select * from tb_grids where status=1 and templateid=".$tempids[$j];// and gridId=".$grid;
			
		 $res2 = mysql_query($sql2);
			
			$temp2="";

		 	for($k=1;$k<=$gridcnt[$j];$k++)
			{

		    	$rows2 = mysql_fetch_assoc($res2);
					
				$temp2 = $temp2.$rows2['row'].",".$rows2['column'].",".$rows2['width'].",".$rows2['height']."#";
					
			}
		 $returnstr = $returnstr.$temp1[$j].$temp2.":";
		 }

		 return $returnstr;
	}
	
	
	
	
	//Get template data for only one template
	function getoneTemplateData($tmpltselected)
	{
		 $returnstr="";
		 
	 	 $sql="select templatename,gridcount from tb_template where status=1 and templateid=".$tmpltselected;
		 $res = mysql_query($sql);
		 $rows= mysql_fetch_assoc($res);
		 
		 //* TEMPLATE NAMES **/
		 $tempname=$rows['templatename'];
		 /** RELATED GRID COUNT **/
		 $gridcnt=$rows['gridcount']; 
		 
		 $temp1= $tmpltselected.",".$tempname.",".$gridcnt."#";
		 
		 
		 // Fetching the Grid data	
		 $sql2="select * from tb_grids where status=1 and templateid=".$tmpltselected;// and gridId=".$grid;	
		 $res2 = mysql_query($sql2);
		 
			$temp2="";
		 	for($k=1;$k<=$gridcnt;$k++)
			{
		    $rows2 = mysql_fetch_assoc($res2);
			
			$temp2 = $temp2.$rows2['gridId'].",".$rows2['row'].",".$rows2['column'].",".$rows2['width'].",".$rows2['height']."#";	
			}
			
		$returnstr = $returnstr.$temp1.$temp2.":";
		
		return $returnstr;	
	}
	
	
	
	//Function to retrieve the page type data from the database
	function getpgtypeData()
	{
	$pagetypes="";
	$sql= "select * from tb_pagetype where status=1";
	$res=mysql_query($sql);
	$count=mysql_num_rows($res);

	$temp="";
		for($i=0;$i<=$count-1;$i++)
		{
		$data=mysql_fetch_assoc($res);
		$temp=$temp.$data['pgtypeid'].",".$data['pagetpe']."#";
		}
		
	$pagetypes=$pagetypes.$temp;
	return $pagetypes;
	}
	
	
	
	
	
	
	//Function to retrieve grid group types
	function getgridgroups($pgtypechosen)
	{
	$ggtypes="";
	$sql= "select * from tb_grdgroups where status=1 and pgtypeid LIKE '%".$pgtypechosen."%'";
	$res=mysql_query($sql);
	$count=mysql_num_rows($res);
	
	$temp="";
		for($i=0;$i<=$count-1;$i++)
		{
		$data=mysql_fetch_assoc($res);
		$temp=$temp.$data['grdgroupid'].",".$data['grdgroup']."#";
		}
		
	$ggtypes=$ggtypes.$temp;	
	return $ggtypes;
	}
	
	
	
	
	
	//Function to retrieve Grid-types in the format "GridType-Id,GridType#"
	function getgridtypes($ggtypechosen)
	{
	 
	 $gridtypes="";
	 $sql= "select gtypeid,gtype from tb_gridtypes where status=1 and grdgroupid LIKE '%".$ggtypechosen."%'";
	 $res=mysql_query($sql);
	 $count=mysql_num_rows($res);
	 
	 $temp="";
		for($i=0;$i<=$count-1;$i++)
		{
		$data=mysql_fetch_assoc($res);
		$temp=$temp.$data['gtypeid'].",".$data['gtype']."#";
		}
		
	 $gridtypes=$gridtypes.$temp;	
	 return $gridtypes;
	}
}
?>