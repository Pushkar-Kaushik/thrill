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
 * slct-template.js
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * Javascript to handle the Display logic and manipulation of Select template page
 *
 * Author:
 * -------
 * -------
 *
 ****************************************************************************/
 












			
			window.onload = function () 
			{
			
			//Getting templates data received from DB from hidden field
			var tmpltdata = document.getElementById('templatedata').value;
			//making a handle to the div where the tables will be printed 
			var contentbody=document.getElementById('contentbody');
			
			
			//Creating the templates array to store the templates data
			var templates = new Array();
			//Breaking the template data received on the basis of ":" de-limiters i.e Number of Templates
			templates = tmpltdata.split(":");
				
				
				
				
				
				
				
				//The variable which will have the table data
				var alltables="";
				//****@@@@ Starting the Table Creation Loop @@@@***//
				for (var i=0;i<templates.length-1;i++)
				{
				var values=new Array();
				values=templates[i].split("#");
				
				//In the 0th element template properties will be present,as the string was created after fetching data from templates table
				var templateprops= new Array();
				templateprops=values[0].split(",");
				
				var templateid=templateprops[0];
				var templatename=templateprops[1];
				var templategrdcnt=templateprops[2];
				
				
					//Creating the templates view in form of tables
					var onetable="<table border='0' cellpadding='0' cellspacing='0' width='50%' style='padding:5px;'>";
					for(var r=1;r<=28-1;r++)
					{
					onetable=onetable+"<tr>";
						for(var c=1;c<=24-1;c++)
						{
						// td id= templateid+rowid+columnid 
				 		onetable=onetable+"<td id="+templateid+","+r+","+c+" bgcolor='#CCFF99'>&nbsp;</td>";
						}
					onetable=onetable+"</tr>";	
					}
					//Ending the table,adding radio buttons at the bottom and adding the seperator bar
					onetable=onetable+"</table><input type='radio' value="+templateid+" name='tmpltsel'>&nbsp;<b>"+templatename+"</b><br/><hr/>";
					
					//Printng the tables to the div	
					alltables=alltables+onetable;
						
				}
				//***@@@@ Ending the table Creation loop @@@***//
				alltables=alltables+"<button type='submit' style='border: 0; background: transparent'><div class='img'></div></button>";
				contentbody.innerHTML = alltables;
				
				
				
				
				
				
				
				//Now we are filling the colors to the cells according to the data received
				//***@@@@ Starting the Color filling loop @@@@***//
				for (var i=0;i<templates.length-1;i++)
				{
				var values=new Array();
				values=templates[i].split("#");
				
				//In the 0th element template properties will be present,as the string was created after fetching data from templates table
				var templateprops= new Array();
				templateprops=values[0].split(",");
				
				var templateid=templateprops[0];
				var templatename=templateprops[1];
				var templategrdcnt=templateprops[2];
				
						//Getting the row, column etc properties of each grids of each templateid we received from database
						//Starting the Loop from 1 since we have taken the values from 0th element of values array
						for(var j=1;j<=templategrdcnt;j++)
						{
						//Getting the properties of each grid
						var gridprops=new Array();
						gridprops = values[j].split(",");
						
						var row=(2*(gridprops[0])-1);
						var column=(2*(gridprops[1])-1);
						var width=(2*(gridprops[2])-1);
						var height=(2*(gridprops[3])-1);
						
						var maxcolorRows=parseInt(row)+parseInt(height)-1;
						var maxcolorCols=parseInt(column)+parseInt(width)-1;
						
	
									//Coloring the cells of the table according to data
									for(var colorRow=row;colorRow<=maxcolorRows;colorRow++)
									{
									  for(var colorCols=column;colorCols<=maxcolorCols;colorCols++)
									  {
									   var cell = document.getElementById(templateid+","+colorRow+","+colorCols);
									   cell.style.background = "#E06666";
									  }
									}
						}
				}
				
			}	
