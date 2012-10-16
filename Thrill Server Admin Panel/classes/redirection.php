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
 * redirection.php
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * Responsible for re-directing to various display templates after assigning appropriate values using smarty
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/
 









class redirection
{
	// Function to Load the basic dash-board at startup.Might require login verification later
	function dashboard()
	{
	$smarty = new Smarty;
	$smarty->display('dashboard.tpl');
	}
	
	
	//Function to Re-Direct to create template page
	function createtemplate()
	{
	$smarty = new Smarty;
	
	//Running a for loop to create a the grid-count drop down
	//Changing this number will change the Maximum Number of grids that can be created for a template
	$maxcount= MAXGRIDS;
	
	//Creating an array which would be used to populate the drop-down
	for($i=1;$i<=$maxcount;$i++)
	{
		$temp[$i]=$i;	
	}
	$smarty->assign('grid_count',$temp);
	$smarty->assign('max_rows',MAXROWS);
	$smarty->assign('max_cols',MAXCOLUMNS);
	
	$smarty->display('createtemplate.tpl');
	}
	
	
	//Function to Re-Direct to Create page tpl
	function slct_template()
	{
	$templatedata = dbTransactions::getTemplateData();
	$smarty = new Smarty;
	$smarty->assign('templatedata',$templatedata);
	$smarty->display('slct-template.tpl');
	}
	
	
	
	
	//Function to re-direct to the user after template has been selected
	function createpage($tmpltselected)
	{
	 // Getting the Template info for template selected
	 $templateinfo = dbTransactions::getoneTemplateData($tmpltselected);
	 //Getting the list of page types
	 $pagetypes = dbTransactions::getpgtypeData();
	 
	 $smarty = new Smarty;
	 $smarty->assign('templateinfo',$templateinfo);
	 $smarty->assign('pagetypes',$pagetypes);
	 $smarty->display('createpage.tpl');
	}
	
	
	
	//Function to show success page on completion of any action
	function showsuccess()
	{
	$smarty = new Smarty;
	$smarty->display('success.tpl');
	}
}
?>