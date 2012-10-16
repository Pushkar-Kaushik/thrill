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
 * createtemplate.js
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * Javascript to display and control the display logic of 'Create Template' page
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/
 











						var max_rows = document.getElementById('max_rows').value;
						var max_cols = document.getElementById('max_cols').value;
						
						
						//Function which create the table as Number of grids are selected
						function createtable()
						{
						var tempname= document.getElementById('tmpltname').value;
						var sel = document.getElementById("gridcount").value;
						
						mytable= "<table border='1' bordercolor='#FFFFFF'><form action='index.php?action=savetemplate' method='post' name='myform'><tr><td class='smallwhitetxt'>Grid ID</td><td class='smallwhitetxt'>Row</td><td class='smallwhitetxt'>Column</td><td class='smallwhitetxt'>Width (in Columns)</td><td class='smallwhitetxt'>Height (in Rows)</td></tr>";

							for(var i=1;i<=sel;i++)
							{
							mytable= mytable + "<tr><td>"+i+"</td>";
							mytable= mytable + "<td><input type='text' class='txtbox' name= 'row"+i+"' id='row"+i+"' onblur='validateR(\"row"+i+"\")' /></td>";
							mytable= mytable + "<td><input type='text' class='txtbox' name= 'col"+i+"' id='col"+i+"' onblur='validateC(\"col"+i+"\")' /></td>";
							mytable= mytable + "<td><input type='text' class='txtbox' name= 'width"+i+"' id='width"+i+"' onblur='validateW(\"width"+i+"\",\"col"+i+"\")' /></td>";
							mytable= mytable + "<td><input type='text' class='txtbox' name= 'height"+i+"' id='height"+i+"' onblur='validateH(\"height"+i+"\",\"row"+i+"\")'/></td></tr>";
							}
							mytable = mytable + "<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><input type='submit' value='Save !'/></td></tr>";
							
							mytable = mytable + "<input type='hidden' value ="+ tempname +" name= 'tempname'/>";
							mytable = mytable + "<input type='hidden' value ="+ sel +" name= 'gridcount'/>";
							//Making the Table
							document.getElementById("tbl").innerHTML = mytable+"</form></table>";
	
						}
						
						
						
						//Function to validate the row fields
						function validateR(rField)
						{
							var row = document.getElementById(rField);
							
							if(row.value == "" || row.value == null)
							{
								alert("This field cannot be left empty");
								row.focus();
							 	return false;
							}
							else if(parseInt(row.value)>max_rows)
							{
							 alert("Sorry ! Rows can not not be more than " + max_rows);
							 row.value="";
							 row.focus();
							 return false;
							} 
						}
						
						
						
						//Function to validate column fields
						function validateC(cField)
						{
						
						 	var col = document.getElementById(cField);
							
							if(col.value == "" || col.value == null)
							{
								alert("This field cannot be left empty");
								col.focus();
							 	return false;
							}
							else if(parseInt(col.value)>max_cols)
							{
							 alert("Sorry ! Columns can not not be more than " + max_cols);
							 col.value="";
							 col.focus();
							 return false;
							}
						}
						
						
						
						//Function to validate Width fields
						function validateW(wField,cField)
						{
							var col = document.getElementById(cField);
							var wdth = document.getElementById(wField);
							if(wdth.value == "" || wdth.value == null)
							{
								alert("This field cannot be left empty");
								wdth.focus();
							 	return false;
							}
							else if((parseInt(col.value) + parseInt(wdth.value))-1 > max_cols)
							{
							 alert("Sorry ! Column & Width combined could not be more than " + max_cols);
							 wdth.value="";
							 wdth.focus();
							 return false;
							}
						}
						
						
						
						//Function to validate height fields
						function validateH(hField,rField)
						{
						
							 ht = document.getElementById(hField);
							 row = document.getElementById(rField);
							 if(ht.value == "" || ht.value == null)
							 {
								alert("This field cannot be left empty");
								ht.focus();
							 	return false;
							 }
							 if((parseInt(ht.value) + parseInt(row.value))-1 > max_rows)
							 {
							 	alert("Sorry ! Row & Height combined could not be more than " + max_rows);
							 	ht.value="";
							 	ht.focus();
							 	return false;
							 }
						}