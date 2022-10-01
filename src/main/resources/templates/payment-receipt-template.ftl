<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Moonlight Hotel and Spa</title>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="600" id="template_container" style="background-color:#ffffff;border:1px solid #dedede;box-shadow:0 1px 4px rgba(0,0,0,0.1);border-radius:3px;">
        <tbody>
         <tr>
          <td align="center" valign="top">
           <table border="0" cellpadding="0" cellspacing="0" width="100%" id="template_header" style="background-color:#3a7bce;color:#ffffff;border-bottom:0;font-weight:bold;line-height:100.0%;vertical-align:middle;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;border-radius:3px 3px 0 0;">
            <tbody>
             <tr>
              <td id="header_wrapper" style="padding:36px 48px;display:block;"> <h1 style="font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;font-size:30px;font-weight:300;line-height:150.0%;margin:0;text-align:left;text-shadow:0 1px 0 #6195d8;color:#ffffff;background-color:inherit;">Moonlight Hotel and Spa</h1> </td>
             </tr>
            </tbody>
           </table>  </td>
         </tr>
         <tr>
          <td align="center" valign="top">
           <table border="0" cellpadding="0" cellspacing="0" width="600" id="template_body">
            <tbody>
             <tr>
              <td valign="top" id="body_content" style="background-color:#ffffff;">
               <table border="0" cellpadding="20" cellspacing="0" width="100%">
                <tbody>
                 <tr>
                  <td valign="top" style="padding:48px 48px 32px;">
                   <div id="body_content_inner" style="color:#636363;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;font-size:14px;line-height:150.0%;text-align:left;">
                    <p style="margin:0 0 16px;"> Your order has been successfully processed (room reservation at Moonlight Hotel and Spa). If you have any questions or concerns, please contact our Customer Service.</p>
                    <h2 style="color:#3a7bce;display:block;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;font-size:18px;font-weight:bold;line-height:130.0%;margin:0 0 18px;text-align:left;"> [Order ORDER NUMBER] (DATE)</h2>
                    <div style="margin-bottom:40px;">
                     <table cellspacing="0" cellpadding="6" border="1" style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;width:100.0%;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;">
                      <thead>
                       <tr>
                        <th style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Product</th>
                        <th style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Days</th>
                        <th style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Price</th>
                       </tr>
                      </thead>
                       <tbody>
                        <tr>
                         <td style="color:#636363;border:1px solid #e5e5e5;padding:12px;text-align:left;vertical-align:middle;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;word-wrap:break-word;">${RoomType}</td>
                         <td style="color:#636363;border:1px solid #e5e5e5;padding:12px;text-align:left;vertical-align:middle;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;">${Days}</td>
                         <td style="color:#636363;border:1px solid #e5e5e5;padding:12px;text-align:left;vertical-align:middle;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;"> <span><span>€</span>${TotalPrice}</span></td>
                        </tr>
                       </tbody>
                        <tfoot>
                         <tr>
                          <th colspan="2" style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;border-top-width:4px;">Subtotal:</th>
                           <td style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;border-top-width:4px;"><span><span>€</span>${TotalPrice}</span></td>
                          </tr>
                         <tr>
                          <th colspan="2" style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Transaction fee:</th>
                           <td style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;"><span>0</span></td>
                          </tr>
                         <tr>
                          <th colspan="2" style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Payment method:</th>
                           <td style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Paypal card payment</td>
                          </tr>
                         <tr>
                          <th colspan="2" style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;">Total:</th>
                           <td style="color:#636363;border:1px solid #e5e5e5;vertical-align:middle;padding:12px;text-align:left;"><span><span>€</span>${TotalPrice}</span></td>
                         </tr>
                        </tfoot>
                      </table>
                    </div>
                    <h2 style="color:#3a7bce;display:block;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;font-size:18px;font-weight:bold;line-height:130.0%;margin:0 0 18px;text-align:left;">Room Information</h2>
                    <ul>
                     <li> <strong>Room type:</strong> ${RoomType}</li>
                     <li> <strong>View:</strong> ${View}</li>
                     <li> <strong>Bed type:</strong> ${BedType}</li>
                     <li> <strong>Adults:</strong> ${Adults}</li>
                     <li> <strong>Kids:</strong> ${Kids}</li>
                     <li> <strong>Start date:</strong> ${StartDate}</li>
                     <li> <strong>End date:</strong> ${EndDate}</li>
                    </ul>
                    <table id="addresses" cellspacing="0" cellpadding="0" border="0" style="width:100.0%;vertical-align:top;margin-bottom:40px;padding:0;">
                     <tbody>
                      <tr>
                       <td valign="top" width="50%" style="text-align:left;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;border:0;padding:0;"> <h2 style="color:#3a7bce;display:block;font-family:'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif;font-size:18px;font-weight:bold;line-height:130.0%;margin:0 0 18px;text-align:left;">Billing address</h2> <address style="padding:12px;color:#636363;border:1px solid #e5e5e5;"> ${Name}<br>${Address} <br>${Phone}</a> <br>${email} </address> </td>
                      </tr>
                     </tbody>
                    </table>
                    <p style="margin:0 0 16px;"><strong>Thank you for using our service.</p>
                    <p style="margin:0 0 16px;">E-mail: someemail@gmail.com<br> Mobile: some mobile number</p>
                   </div> </td>
                 </tr>
                </tbody>
               </table>  </td>
             </tr>
            </tbody>
           </table>  </td>
         </tr>
        </tbody>
       </table>
</body>
</html>