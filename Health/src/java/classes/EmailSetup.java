/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author 100116544
 */
public class EmailSetup {

    private String toAddress;
    private final String fromAddress = "vigorhealthtracker@outlook.com";
    private final String password = "Vigor123";
    private String message;
    private String subject;

    public EmailSetup(String toAddress, String subject) {
        this.toAddress = toAddress;
        this.message = "";
        this.subject = subject;
    }

    public EmailSetup(String toAddress, String message, String subject) {
        this.toAddress = toAddress;
        this.message = message;
        this.subject = subject;
    }

    public boolean sendEmail() throws NoSuchProviderException {
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp-mail.outlook.com");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(props, null);
        session.setDebug(true);
        Transport transport = session.getTransport();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.fromAddress));
            message.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(toAddress));

            message.setSubject(this.subject);
            // message.setText(msg, "utf-8");
            message.setContent(this.message, "text/html");
            message.setSentDate(new Date());

            transport.connect("smtp-mail.outlook.com",
                    this.fromAddress, password);
            transport.sendMessage(message,
                    message.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();
            return true;
        } catch (MessagingException e) {
            System.out.println("Failed to send verification email");
            return false;
        }
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String setUpVerifyEmail(String link, String first) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\">\n"
                + " <head> \n"
                + "  <meta charset=\"UTF-8\"> \n"
                + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"> \n"
                + "  <meta content=\"telephone=no\" name=\"format-detection\"> \n"
                + "  <title>Email</title> \n"
                + "  <!--[if (mso 16)]>\n"
                + "    <style type=\"text/css\">\n"
                + "    a {text-decoration: none;}\n"
                + "    </style>\n"
                + "    <![endif]--> \n"
                + "  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n"
                + "  <style>\n"
                + "@media only screen and (max-width: 600px) {p, ul li, ol li, a { font-size: 16px !important } h1 { font-size: 30px !important; text-align: center } h2 { font-size: 26px !important; text-align: center } h3 { font-size: 20px !important; text-align: center } .es-menu td a { font-size: 16px !important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size: 16px !important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size: 16px !important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size: 12px !important } *[class=\"gmail-fix\"] { display: none !important } .es-m-txt-c { text-align: center !important } .es-m-txt-r { text-align: right !important } .es-m-txt-l { text-align: left !important } .es-m-txt-r a img, .es-m-txt-c a img, .es-m-txt-l a img { display: inline !important } .es-button-border { display: block !important } .es-button { font-size: 20px !important; display: block !important; border-width: 10px 0px 10px 0px !important } .es-btn-fw { border-width: 10px 0px !important; text-align: center !important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width: 100% !important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width: 100% !important; max-width: 600px !important } .es-adapt-td { display: block !important; width: 100% !important } .adapt-img { width: 100% !important; height: auto !important } .es-m-p0 { padding: 0px !important } .es-m-p0r { padding-right: 0px !important } .es-m-p0l { padding-left: 0px !important } .es-m-p0t { padding-top: 0px !important } .es-m-p0b { padding-bottom: 0 !important } .es-m-p20b { padding-bottom: 20px !important } .es-hidden { display: none !important } table.es-table-not-adapt, .esd-block-html table { width: auto !important } table.es-social td { display: inline-block !important } table.es-social { display: inline-block !important } }\n"
                + "\n"
                + "</style> \n"
                + "  <style>\n"
                + "\n"
                + "\n"
                + "#outlook a {\n"
                + "    padding: 0;\n"
                + "}\n"
                + "\n"
                + ".ExternalClass {\n"
                + "    width: 100%;\n"
                + "}\n"
                + "\n"
                + ".ExternalClass,\n"
                + ".ExternalClass p,\n"
                + ".ExternalClass span,\n"
                + ".ExternalClass font,\n"
                + ".ExternalClass td,\n"
                + ".ExternalClass div {\n"
                + "    line-height: 100%;\n"
                + "}\n"
                + "\n"
                + ".es-button {\n"
                + "    mso-style-priority: 100 !important;\n"
                + "    text-decoration: none !important;\n"
                + "}\n"
                + "\n"
                + "a[x-apple-data-detectors] {\n"
                + "    color: inherit !important;\n"
                + "    text-decoration: none !important;\n"
                + "    font-size: inherit !important;\n"
                + "    font-family: inherit !important;\n"
                + "    font-weight: inherit !important;\n"
                + "    line-height: inherit !important;\n"
                + "}\n"
                + "\n"
                + "@-ms-viewport {\n"
                + "    width: device-width;\n"
                + "}\n"
                + "\n"
                + "\n"
                + "\n"
                + "</style> \n"
                + " </head> \n"
                + " <body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;\"> \n"
                + "  <div class=\"es-wrapper-color\" style=\"background-color:#CCCCCC;\"> \n"
                + "   <!--[if gte mso 9]>\n"
                + "                    <v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "                        <v:fill type=\"tile\" src=\"\" color=\"#cccccc\"></v:fill>\n"
                + "                    </v:background>\n"
                + "                <![endif]--> \n"
                + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;\"> \n"
                + "    <tbody> \n"
                + "     <tr style=\"border-collapse:collapse;\"> \n"
                + "      <td valign=\"top\" style=\"padding:0;Margin:0;\"> \n"
                + "       <table class=\"es-content\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n"
                + "        <tbody> \n"
                + "         <tr style=\"border-collapse:collapse;\"> \n"
                + "          <td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0;\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#EFEFEF;\" align=\"center\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#efefef\"> \n"
                + "            <tbody> \n"
                + "             <tr style=\"border-collapse:collapse;\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:40px;padding-right:40px;\"> \n"
                + "               <!--[if mso]><table width=\"520\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"250\" valign=\"top\"><![endif]--> \n"
                + "               <table class=\"es-left\" align=\"left\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\"> \n"
                + "                <tbody> \n"
                + "                 <tr style=\"border-collapse:collapse;\"> \n"
                + "                  <td align=\"left\" width=\"250\" style=\"padding:0;Margin:0;\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                    <tbody> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td class=\"es-infoblock\" align=\"left\" style=\"padding:0;Margin:0;line-height:120%;font-size:12px;color:#CCCCCC;\">Vigor Health Tracker</td> \n"
                + "                     </tr> \n"
                + "                    </tbody> \n"
                + "                   </table> </td> \n"
                + "                 </tr> \n"
                + "                </tbody> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td><td width=\"20\"></td><td width=\"250\" valign=\"top\"><![endif]--> \n"
                + "               <table class=\"es-right\" align=\"right\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\"> \n"
                + "                <tbody> \n"
                + "                 <tr style=\"border-collapse:collapse;\"> \n"
                + "                  <td align=\"left\" width=\"250\" style=\"padding:0;Margin:0;\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                    <tbody> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td class=\"es-infoblock\" align=\"right\" style=\"padding:0;Margin:0;line-height:120%;font-size:12px;color:#CCCCCC;\"> <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:12px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:120%;color:#CCCCCC;\"><a href=\"https://stripo.email/\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:12px;text-decoration:underline;color:#CCCCCC;\">View in browser</a></p> </td> \n"
                + "                     </tr> \n"
                + "                    </tbody> \n"
                + "                   </table> </td> \n"
                + "                 </tr> \n"
                + "                </tbody> \n"
                + "               </table> \n"
                + "               <!--[if mso]></td></tr></table><![endif]--> </td> \n"
                + "             </tr> \n"
                + "            </tbody> \n"
                + "           </table> </td> \n"
                + "         </tr> \n"
                + "        </tbody> \n"
                + "       </table> \n"
                + "       <table class=\"es-header\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;\"> \n"
                + "        <tbody> \n"
                + "         <tr style=\"border-collapse:collapse;\"> \n"
                + "          <td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0;\"> \n"
                + "           <table class=\"es-header-body\" align=\"center\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\"> \n"
                + "            <tbody> \n"
                + "             <tr style=\"border-collapse:collapse;\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-top:5px;padding-bottom:10px;padding-left:40px;padding-right:40px;\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                <tbody> \n"
                + "                 <tr style=\"border-collapse:collapse;\"> \n"
                + "                  <td align=\"center\" width=\"520\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                    <tbody> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td class=\"es-m-p0l\" align=\"center\" style=\"padding:0;Margin:0;\"> <img src=\"https://gxtf.stripocdn.email/content/guids/d605c256-7c69-42ec-a316-e7a98718ca3f/images/92021526034317228.png\" alt=\"Smart home logo\" title=\"Smart home logo\" width=\"208\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;\"></td> \n"
                + "                     </tr> \n"
                + "                    </tbody> \n"
                + "                   </table> </td> \n"
                + "                 </tr> \n"
                + "                </tbody> \n"
                + "               </table> </td> \n"
                + "             </tr> \n"
                + "            </tbody> \n"
                + "           </table> </td> \n"
                + "         </tr> \n"
                + "        </tbody> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n"
                + "        <tbody> \n"
                + "         <tr style=\"border-collapse:collapse;\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;\" align=\"center\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"> \n"
                + "            <tbody> \n"
                + "             <tr style=\"border-collapse:collapse;\"> \n"
                + "              <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:20px;padding-left:40px;padding-right:40px;\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                <tbody> \n"
                + "                 <tr style=\"border-collapse:collapse;\"> \n"
                + "                  <td align=\"center\" width=\"520\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                    <tbody> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;\"> <h1 style=\"Margin:0;line-height:120%;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#4A7EB0;\">Registration successful</h1></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:20px;\"> \n"
                + "                       <table width=\"5%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                        <tbody> \n"
                + "                         <tr style=\"border-collapse:collapse;\"> \n"
                + "                          <td style=\"padding:0;Margin:0px;border-bottom:2px solid #999999;background:rgba(0, 0, 0, 0) none repeat scroll 0% 0%;height:1px;width:100%;margin:0px;\"></td> \n"
                + "                         </tr> \n"
                + "                        </tbody> \n"
                + "                       </table> </td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:10px;\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:16px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:150%;color:#666666;\">Hi&nbsp; " + first + " ,</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td align=\"left\" style=\"padding:0;Margin:0;\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:150%;color:#666666;\">Thank you for registering on Vigor Health.<br>If you did not register with us then please ignore this email, otherwise please verify your account by pressing the button below.</p></td> \n"
                + "                     </tr> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                     <td align=\"left\" style=\"padding:0;Margin:0;padding-top:20px;padding-bottom:20px;\"> " + link +"</td>"
                + "                    </tbody> \n"
                + "                   </table> </td> \n"
                + "                 </tr> \n"
                + "                </tbody> \n"
                + "               </table> </td> \n"
                + "             </tr> \n"
                + "            </tbody> \n"
                + "           </table> </td> \n"
                + "         </tr> \n"
                + "        </tbody> \n"
                + "       </table> \n"
                + "       <table class=\"es-content\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\"> \n"
                + "        <tbody> \n"
                + "         <tr style=\"border-collapse:collapse;\"> \n"
                + "          <td align=\"center\" style=\"padding:0;Margin:0;\"> \n"
                + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;\" align=\"center\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\"> \n"
                + "            <tbody> \n"
                + "             <tr style=\"border-collapse:collapse;\"> \n"
                + "              <td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;padding-bottom:30px;\"> \n"
                + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                <tbody> \n"
                + "                 <tr style=\"border-collapse:collapse;\"> \n"
                + "                  <td align=\"center\" width=\"560\" valign=\"top\" style=\"padding:0;Margin:0;\"> \n"
                + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\"> \n"
                + "                    <tbody> \n"
                + "                     <tr style=\"border-collapse:collapse;\"> \n"
                + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none;\"></td> \n"
                + "                     </tr> \n"
                + "                    </tbody> \n"
                + "                   </table> </td> \n"
                + "                 </tr> \n"
                + "                </tbody> \n"
                + "               </table> </td> \n"
                + "             </tr> \n"
                + "            </tbody> \n"
                + "           </table> </td> \n"
                + "         </tr> \n"
                + "        </tbody> \n"
                + "       </table> </td> \n"
                + "     </tr> \n"
                + "    </tbody> \n"
                + "   </table> \n"
                + "  </div>  \n"
                + " </body>\n"
                + "</html>";
    }

}
