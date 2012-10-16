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
 * createtemplate.tpl
 *
 * Project:
 * --------
 * Thrill
 *
 * Description:
 * ------------
 * Smarty template to display the 'Create Template' page
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
    
    
 	<!-- Container Right -->
	<div class="cont-right">
		<div class="cont-right-midd" style="background:#FFFFFF;">
		<!-- Heading Start -->
			
			<div class="heading-middle-box"><img src="images/icon-user.jpg"/>&nbsp;&nbsp;Define & Create your Templates</div>
			<b class='b4bh'></b>
			<b class='b3bh'></b>
			<div style="min-height:428px; min-width:400px;">
            
            <!-- @@@@@@@@!!!!!!!$$$$$$ Create template page's contents $$$$$$$!!!!!!!!@@@@@@@@@@@ -->
            		<table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                    <td width="100%" class="pgheading" align="center">Create Template</td>
                    </tr>
                    
                    
                    
                    <tr><td>&nbsp;</td></tr>
                    <!-- Template Name Text Box -->
                    <tr>
                    <td width="100%">
                    	<table width="100%" border="0">
                        <tr><td width="30%" align="right"><font size="2">Template Name:&nbsp;</font></td>
                        	<td width="70%" align="center"><input type="text" name="tmpltname" id="tmpltname" class="txtbox" /></td>
                        </tr>      
                        </table>
                    </td>
                    </tr>
                    
                    <tr><td>&nbsp;</td></tr>
                    
                    
                    
                    
                    <!-- Grid Count drop down -->
                    <tr>
                    <td width="100%">
                    	<table width="100%" border="0">
                        <tr><td width="30%" align="right"><font size="2">Number of Grids:&nbsp;</font></td>
                        	<td width="70%" align="center">
                            <select name="gridcount" id="gridcount" onchange="createtable()">
   							{html_options values=$grid_count output=$grid_count}
							</select>
                            </td>
                        </tr>      
                        </table>
                    </td>
                    </tr>
                    
                    <tr><td>&nbsp;</td></tr>
                    
                    
                    
                    
                    <!-- The upper limit of rows and columns allowed in a template -->
                    <input type="hidden" value={$max_rows} id="max_rows" />
                    <input type="hidden" value={$max_cols} id="max_cols" />
                    
                    
                    
                    
                    <!--@@@@@ IMP IMP IMP Javascript function to create & Manipulatethe table IMP IMP IMP @@@@@-->
                    {literal}
                        <script src="javascripts/createtemplate.js">
						</script>    
					{/literal}
                    
                    
                    
                    
                    <!--  Data table will be created here -->    
                    <tr>
                    <td>
                        <div id="tbl" style="overflow:auto;">
                        &nbsp;
                        </div>
                    </td>
                    </tr>
                    
                    </table>
            <!-- Create template page contents end -->        
            </div>
        </div>
	</div>
	<!-- Container Right End -->
    
    
   <br class="cl" />
  </div>
  
  
{include file="footer.tpl"}