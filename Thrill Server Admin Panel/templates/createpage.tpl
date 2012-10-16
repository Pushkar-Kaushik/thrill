<!-- /*****************************************************************************
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
 * createpage.tpl
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * The smarty template file which displays the 'Create Page' page
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/ -->
 







{include file="header.tpl"}

<!-- Container Start -->
  <div class="container">
    
    <!-- Container Left -->
    {include file="left-menu.tpl"}
	<!-- Container Left End -->
    
    
    
    
    
    
    
    
    
    
    
    
    {literal}
    <script type="text/javascript">
	
	
	//Java-script to run when the create page loads
	window.onload = function () 
	{
	var temp= document.getElementById("pagetypes").value;
	var temp2=new Array();
	// Splitting page type string on basis of # de-limiter
	temp2=temp.split("#");
	
	
	//creating the pg type drop-down list
	var pgtypelist="<select name='pgtypelist' id='pgtypelist' onchange='getgridgroups()'>";
 	//Adding a default option
	pgtypelist=pgtypelist+"<option>Select</option>";
	
	for(var i=0;i<temp2.length-1;i++)
		{
		var temp3=new Array();
		//Now splitting those parts on the basis of "," 
		temp3=temp2[i].split(",");
		
		//******* Page Type id ******//
		var pgtypeid= temp3[0];
		//******* Page Type ********//
		var pgtype = temp3[1];
		
		//Making the page type drop-down
		pgtypelist=pgtypelist+"<option value="+pgtypeid+">"+pgtype+"</option>";
		}
		pgtypelist=pgtypelist+"</select>";
		document.getElementById("pgtypecontainer").innerHTML = pgtypelist;
	}
	
	
	
	
	
	//Function to create the Ajax Request object
	function ajaxRequest()
	{
		//Create a boolean variable to check for a valid Internet Explorer instance.
		var xmlhttp = false;
		//Check if we are using IE.
		try 
		{
		//If the Javascript version is greater than 5.
			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
		} 
		catch (e) 
		{
		//If not, then use the older active x object.
			try 
			{
			//If we are using Internet Explorer.
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (E) 
			{
			//Else we must be using a non-IE browser.
				xmlhttp = false;
			}
		}
		//If we are using a non-IE browser, create a javascript instance of the object.
		if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
			xmlhttp = new XMLHttpRequest();
		}
	 return xmlhttp;
	}
	
	
	
	

	//Function to Retrieve Grid group according to selection
	function getgridgroups()
	{
	pgtypechosen=document.getElementById("pgtypelist").value;
	
	
		//Now creating a common ajax object for all browsers
		var mygetrequest=new ajaxRequest()
		mygetrequest.open("GET","index.php?action=getgridgroups&pgtypechosen="+pgtypechosen, true);
		
		
		mygetrequest.onreadystatechange=function()
		{
		  if (mygetrequest.readyState==4)
		  {
		   var response= mygetrequest.responseText;
			if(response=="" || response== null)
			{
			alert("Sorry ! No Matching grid groups found, try with a different page type");
			}
			else
			{
			 create_gridgroups(response);
			}
		  }
		}
		mygetrequest.send(null)		
	}
	
	
	
	
	
	//Function to create grid group type menu
	function create_gridgroups(ggtypes)
	{
	var temp=new Array();
	temp=ggtypes.split("#");
	
	
	//creating the pg type drop-down list
	var ggtypelist="<select name='ggtypelist' id='ggtypelist' onchange='getgridtypes()'>";
 	//Adding a default option
	ggtypelist=ggtypelist+"<option>Select</option>";
	
	for(var i=0;i<temp.length-1;i++)
		{
		var temp2=new Array();
		temp2=temp[i].split(",");
		
		var ggtypeid= temp2[0];
		var ggtype = temp2[1];
		
		//Making the page type drop-down list
		ggtypelist=ggtypelist+"<option value="+ggtypeid+">"+ggtype+"</option>";
		}
		ggtypelist=ggtypelist+"</select>";
		
		document.getElementById("ggtypelabel").innerHTML="Select Group type of Grids:&nbsp;";
		document.getElementById("ggtypecontainer").innerHTML = ggtypelist;
	}
	
	
	
	
	//Function to Retrieve Grid-Types according to Grid-Group selected
	function getgridtypes()
	{
	ggtypechosen=document.getElementById("ggtypelist").value;
	
		//Now creating a common ajax object for all browsers
		var mygetrequest=new ajaxRequest()
		mygetrequest.open("GET","index.php?action=getgridtypes&ggtypechosen="+ggtypechosen, true);
		
		
		mygetrequest.onreadystatechange=function()
		{
		  if (mygetrequest.readyState==4)
		  {
		   var response= mygetrequest.responseText;
			if(response=="" || response== null)
			{
			alert("Sorry ! No Matching Grid-types found,try with a different Group.");
			}
			else
			{
			 create_gridtypes(response);
			}
		  }
		}
		mygetrequest.send(null);
	}
	
	
	
	
	//Function to create Grid-Type Menu
	function create_gridtypes(gridtypes)
	{
	var temp=new Array();
	temp=gridtypes.split("#");
	
 	//Adding a default option,so that onchange event could be processed
	var gridtypelist= "<option>Select</option>";
	
	
	//Since the last field is empty and the loop will run from 0 however length start from 1
	//We will run the loop till 1 less the count and 1 less all the values
	
	for(var i=0;i<temp.length-1;i++)
		{
		var temp2=new Array();
		temp2=temp[i].split(",");
		
		var gridtypeid= temp2[0];
		var gridtype = temp2[1];
		
		//Making the page type drop-down list
		gridtypelist=gridtypelist+"<option value="+gridtypeid+">"+gridtype+"</option>";
		}
	
	//Now We will start creating the table which will have the template info
	datatable(gridtypelist);
	}
	
	
	
	
	//Two different operation are performed here
	//-> Get the grid types corresponding to grid group chosen
	//-> Create a table with template data and corresponding upload button,grid type selection fields etc
	function datatable(gridtypelist)
	{
	
	 var templateinfo = document.getElementById("templateinfo").value;
	 var grids=new Array();
	 grids=templateinfo.split("#");
	 
	 
	 var templateprops=new Array();
	 templateprops=grids[0].split(",");
	 
	 var templateid=templateprops[0];
 	 var templatename=templateprops[1];
	 var templategrdcnt=templateprops[2];
	 
	 var table="<p class='bluetxt'>Template <b>'"+templatename+"'</b>&nbsp;Grid-Details</p><table border='1' bordercolor='#FFFFFF'><tr><td class='smallwhitetxt'>Grid #</td><td class='smallwhitetxt'>Row</td><td class='smallwhitetxt'>Column</td><td class='smallwhitetxt'>Width (in Columns)</td><td class='smallwhitetxt'>Height (in Rows)</td><td class='smallwhitetxt'>Select Grid-Types</td><td class='smallwhitetxt'>Upload Image</td><td class='smallwhitetxt'>Grid Group ID</td></tr>";
	 
	 //Function to create a grid-group id drop down where the Maximum Number of Groups possible will be equal to Max Number of Grids in a template.
	 var gridgroupids = create_gridgroupids(templategrdcnt);

		//Now Creating the Table
	 	for(var i=1;i<=templategrdcnt;i++)
		{
		 var gridprops=new Array();
		 gridprops = grids[i].split(",");
		 
		 var gridid= gridprops[0];			
		 var row= gridprops[1];
		 var column= gridprops[2];
		 var width= gridprops[3];
		 var height= gridprops[4];
		 table = table+"<tr><td class='txtbox'>"+i+"</td>";
		 table = table+"<td class='txtbox'>"+row+"</td>";
		 table = table+"<td class='txtbox'>"+column+"</td>";
		 table = table+"<td class='txtbox'>"+width+"</td>";
		 table = table+"<td class='txtbox'>"+height+"</td>";
		 
		 //*********************** The name of the grid type drop-down is unique *****************//
		 //*********************** the gridtype list was passed from the function above "create gridtypes" which called this table creation function **********//
		 table = table+"<td class='txtbox'><select name='gt"+gridid+"' id='gt"+gridid+"'>"+ gridtypelist +"</select></td>";
		 
		 //****** Image files will be uploaded using these controls ******//
		 table = table+"<td class='txtbox'><input type='file' name='fl"+gridid+"' id='fl"+gridid+"' size='20'></td>";
		 
		 //****** Grid-Group Id Will be selected using this drop-down ******//
		 table = table+"<td class='txtbox'><select name='gid"+gridid+"' id='gid"+gridid+"'>"+ gridgroupids +"</select></td></tr>";
		}
		table = table + "</table><br/><input type='submit' value='Save'></input>";
		
		document.getElementById('tbl').innerHTML = table;
	}
	
	
	
	//Function to create a grid-group id drop down where the Maximum Number of Groups possible will be equal to Max Number of Grids in a template.
	function create_gridgroupids(templategrdcnt)
	{
	var gridgroupids= "<option>Select</option>";
	
	 for(var i=1;i<=templategrdcnt;i++)
		{
		 gridgroupids=gridgroupids+"<option value="+ i +">"+ i +"</option>";
		}
		
	return(gridgroupids);	
	}
	
	</script>
    {/literal}
    
    
    
    
    
    
    
    
    
   
 	<!-- Container Right -->
	<div class="cont-right">
		<div class="cont-right-midd" style="background:#FFFFFF;">
		<!-- Heading Start -->
			
			<div class="heading-middle-box"><img src="images/icon-user.jpg"/>&nbsp;&nbsp;Create Your Page</div>
			<b class='b4bh'></b>
			<b class='b3bh'></b>
			<div>
            
            <!-- @@@@@@@@!!!!!!!$$$$$$ Create Page contents $$$$$$$!!!!!!!!@@@@@@@@@@@ -->
            		<table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                    <td width="100%" class="pgheading" align="center">Create Page</td>
                    </tr>
                    
                    <tr><td>&nbsp;</td></tr>
                    
                    
                    
                    
                    <!-- Page Name Text Box -->
                    <tr>
                    <td width="100%">
                    	<table width="100%" border="0">
                        <tr><td width="30%" align="right"><font size="2">Page Name:&nbsp;</font></td>
                        	<td width="70%" align="left"><input type="text" name="pagename" id="pagename" class="txtbox" /></td>
                        </tr>      
                        </table>
                    </td>
                    </tr>
                    
                    <tr><td>&nbsp;</td></tr>
                    
                    
                    
                    <!-- Page-Type drop down -->
           			<input type="hidden" value="{$pagetypes}" id="pagetypes" />
                    
                    <tr>
                    <td width="100%">
                    	<table width="100%" border="0">
                        <tr><td width="30%" align="right"><font size="2">Select a Page-type:&nbsp;</font></td>
                        	<td width="70%" align="left">
           					<!-- Container for page-type drop drown list -->
                            <div id="pgtypecontainer"></div>
                            
                            </td>
                        </tr>      
                        </table>
                    </td>
                    </tr>
                    
                    
                    <tr><td>&nbsp;</td></tr>
                    
                    
                    
                    
                    <!-- Grid Groups Type Drop Down -->
                    <tr>
                    <td width="100%">
                    	<table width="100%" border="0">
                        <tr><td width="30%" align="right"><font size="2"><div id="ggtypelabel">&nbsp;</div></font></td>
                        	<td width="70%" align="left">
           					<!-- Container for page-type drop drown list -->
                            <div id="ggtypecontainer"></div>
                            
                            </td>
                        </tr>      
                        </table>
                    </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    
                    
                    
                    
                    
                    
                    <input type="hidden" value="{$templateinfo}" id="templateinfo" />
                    <!--  Data table will be created here -->    
                    <tr>
                    <td>
                        <div id="tbl" style="overflow:auto;">
                        </div>
                    </td>
                    </tr>
                    
                    
                    
                    
                    
                    
                    </table>
            <!-- Create page contents end -->        
            </div>
        </div>
	</div>
	<!-- Container Right End -->
    
    
   <br class="cl" />
  </div>
  
  
{include file="footer.tpl"}