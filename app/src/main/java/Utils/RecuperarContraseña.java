package Utils;


import android.util.Log;

import com.plaza19.sharelife.RecupinActivity;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class RecuperarContraseña {


    public RecuperarContraseña() {

        String aux = String.valueOf(Math.random()*999999+1);
        aux = aux.replace(",", "");
        RecupinActivity.pin = Integer.valueOf(aux.substring(0,4));
        Log.d("Pin",
                RecupinActivity.pin+"");

    }




    public void enviaCorreo(String correo) {

        Log.d("Direccion de correo", correo);

        String plantilla = "<tbody>\n" +
                "    <tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "        <td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n" +
                "            <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#e2eace\"><![endif]-->\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <div\n" +
                "                                        style=\"color:#8E8080;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "                                        <div class=\"txtTinyMce-wrapper\"\n" +
                "                                            style=\"font-size: 12px; line-height: 1.2; color: #8E8080; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                "                                            <p\n" +
                "                                                style=\"font-size: 14px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 17px; margin: 0;\">\n" +
                "                                                Si no puedes ver bien este mensaje haz click <a\n" +
                "                                                    href=\"*|BROWSER_VIEW|*\" target=\"_blank\" rel=\"noopener\"\n" +
                "                                                    style=\"text-decoration: none; color: #0068A5;\">aquí</a><br></p>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <div class=\"img-container center fullwidth\" align=\"center\"\n" +
                "                                        style=\"padding-right: 0px;padding-left: 0px;\">\n" +
                "                                        <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n" +
                "                                        <div style=\"font-size:1px;line-height:25px\">&nbsp;</div><img\n" +
                "                                            class=\"center fullwidth\" align=\"center\" border=\"0\"\n" +
                "                                            src=\"https://www.dropbox.com/s/zvs47o0p4syeren/logo_transparent.png?dl=1\"\n" +
                "                                            alt=\"Image\" title=\"Image\"\n" +
                "                                            style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; width: 70%; max-width: 600px; display: block;\"\n" +
                "                                            width=\"600\">\n" +
                "                                        <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    </div>\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: #FFFFFF;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#FFFFFF;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <div\n" +
                "                                        style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.5;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "                                        <div class=\"txtTinyMce-wrapper\"\n" +
                "                                            style=\"font-size: 12px; line-height: 1.5; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 18px;\">\n" +
                "                                            <p\n" +
                "                                                style=\"font-size: 14px; line-height: 1.5; text-align: center; word-break: break-word; mso-line-height-alt: 21px; margin: 0;\">\n" +
                "                                                Se ha solicitado recuperar la contraseña de la cuenta: <strong>" + correo +"</strong></p>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: #FFFFFF;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#FFFFFF;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <div\n" +
                "                                        style=\"color:#0D0D0D;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "                                        <div class=\"txtTinyMce-wrapper\"\n" +
                "                                            style=\"font-size: 12px; line-height: 1.2; color: #0D0D0D; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                "                                            <p\n" +
                "                                                style=\"font-size: 28px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 34px; margin: 0;\">\n" +
                "                                                <span style=\"font-size: 28px;\"><strong>Recuperación de contraseña Sharelife</strong></span></p>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <div\n" +
                "                                        style=\"color:#555555;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.5;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "                                        <div class=\"txtTinyMce-wrapper\"\n" +
                "                                            style=\"font-size: 12px; line-height: 1.5; color: #555555; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 18px;\">\n" +
                "                                            <p\n" +
                "                                                style=\"font-size: 14px; line-height: 1.5; text-align: center; word-break: break-word; mso-line-height-alt: 21px; margin: 0;\">\n" +
                "                                                <span style=\"color: #81bebf; font-size: 14px;\"><strong>El código pin de recuperación es:</strong></span></p>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <div class=\"button-container\" align=\"center\"\n" +
                "                                        style=\"padding-top:25px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "                                        <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-top: 25px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:46.5pt; width:144pt; v-text-anchor:middle;\" arcsize=\"7%\" stroke=\"false\" fillcolor=\"#a8bf6f\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#ffffff; font-family:Tahoma, sans-serif; font-size:16px\"><![endif]-->\n" +
                "                                        <div\n" +
                "                                            style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#81bebf;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;width:auto; width:auto;;border-top:1px solid #81bebf;border-right:1px solid #81bebf;border-bottom:1px solid #81bebf;border-left:1px solid #81bebf;padding-top:15px;padding-bottom:15px;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\">\n" +
                "                                            <span\n" +
                "                                                style=\"padding-left:15px;padding-right:15px;font-size:16px;display:inline-block;letter-spacing:undefined;\"><span\n" +
                "                                                    style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><strong>" + RecupinActivity.pin + "</strong>\n" +
                "                                        <!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->\n" +
                "                                    </div>\n" +
                "                                    <table class=\"divider\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                        style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\"\n" +
                "                                        role=\"presentation\" valign=\"top\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;height: 40px;\">\n" +
                "                <div class=\"block-grid three-up\"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: #525252;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#525252;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#525252\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"200\" style=\"background-color:#525252;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\n" +
                "                        \n" +
                "                            <div class=\"col num4\"\n" +
                "                            \n" +
                "                                        style=\"color:#a8bf6f;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:20px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\n" +
                "                                        <div class=\"txtTinyMce-wrapper\"\n" +
                "                                            style=\"font-size: 12px; line-height: 1.2; color: #81bebf; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                "                                            <p\n" +
                "                                                style=\"font-size: 12px; line-height: 1.2; text-align: center; word-break: break-word; mso-line-height-alt: 14px; margin: 0;\">\n" +
                "                                                Email <span\n" +
                "                                                    style=\"color: #ffffff; font-size: 12px;\">sharelife1919@gmail.com</span>\n" +
                "                                            </p>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                         \n" +
                "                                </div>\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    \n" +
                "                                    <table class=\"social_icons\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                        role=\"presentation\"\n" +
                "                                        style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                        valign=\"top\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "                                                <td style=\"word-break: break-word; vertical-align: top; padding-top: 15px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px;\"\n" +
                "                                                    valign=\"top\">\n" +
                "                                                    \n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td><td align=\"center\" width=\"200\" style=\"background-color:#525252;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num4\"\n" +
                "                            style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 20px; padding-bottom: 0px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <div\n" +
                "                                        style=\"color:#a8bf6f;font-family:Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;line-height:1.2;padding-top:20px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\n" +
                "                                        <div class=\"txtTinyMce-wrapper\"\n" +
                "                                            style=\"font-size: 12px; line-height: 1.2; color: #a8bf6f; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14px;\">\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td><td align=\"center\" width=\"200\" style=\"background-color:#525252;width:200px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num4\"\n" +
                "                            style=\"display: table-cell; vertical-align: top; max-width: 320px; min-width: 200px; width: 200px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 20px; padding-bottom: 0px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <table class=\"divider\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                        style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\"\n" +
                "                                        role=\"presentation\" valign=\"top\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Tahoma, sans-serif\"><![endif]-->\n" +
                "                                    <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                    <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"background-color:transparent;\">\n" +
                "                <div class=\"block-grid \"\n" +
                "                    style=\"min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "                        <div class=\"col num12\"\n" +
                "                            style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\n" +
                "                            <div class=\"col_cont\" style=\"width:100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div\n" +
                "                                    style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "                                    <table class=\"divider\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n" +
                "                                        style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\"\n" +
                "                                        role=\"presentation\" valign=\"top\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                </div>\n" +
                "                                <!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</tbody>";




        try
        {

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "sharelife1919@gmail.com");
            props.setProperty("mail.smtp.auth", "true");


            Session session = Session.getDefaultInstance(props);




            BodyPart texto = new MimeBodyPart();




            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);


            MimeMessage message = new MimeMessage(session);
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correo));
            message.setSubject("Recuperación de contraseña Sharelife");


            //ponemos como contenido el cuerpo y el adjunto
            message.setContent(plantilla, "text/html");






            Transport t = session.getTransport("smtp");
            t.connect("sharelife1919@gmail.com", "8UatNCqxfmDATuk");
            t.sendMessage(message, message.getAllRecipients());
            Log.d("ISSUE", "El correo ha sido enviado correctamente");

            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
