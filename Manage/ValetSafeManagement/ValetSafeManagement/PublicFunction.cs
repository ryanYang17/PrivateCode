using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Mail;
using System.Net.Mime;
using System.IO;
using System.Timers;
using System.Xml;

namespace ValetSafeManagement
{
    class PublicFunction
    {
        public const string HistoryFilePath = "History.txt";
        /*************************************************
        Description:    写入地址列表的历史文件，从上到下，从旧到新，不断冲掉最旧的记录，结束输入才要记录
        Input:          新输入的地址      
        *************************************************/
        public void WriteHistoryFile(string newInput)
        {
            if (!File.Exists(HistoryFilePath))
            {
                FileStream fs1 = new FileStream(HistoryFilePath, FileMode.Create, FileAccess.Write);
                StreamWriter sw = new StreamWriter(fs1);
                sw.WriteLine(newInput);
                sw.Close();
                fs1.Close();
            }
            else
            {
                string[] fileArray = File.ReadAllLines(HistoryFilePath, Encoding.Default);
                FileStream fs1 = new FileStream(HistoryFilePath, FileMode.Open, FileAccess.Write);
                StreamWriter sw = new StreamWriter(fs1);
                if (fileArray.Length == 5)   //不会超过五条记录
                {
                    for (int i = 1; i < fileArray.Length; i++)
                        sw.WriteLine(fileArray[i]);
                }
                else
                {
                    for (int i = 0; i < fileArray.Length; i++)
                        sw.WriteLine(fileArray[i]);
                }
                sw.WriteLine(newInput);
                sw.Close();
                fs1.Close();
            }
        }
        /*************************************************     
        Description:    读取历史文件，返回到下拉框，当开始输入字符，则不需要显示       
        Return:         返回历史记录的string list
        *************************************************/
        public List<string> ReadHistoryFile()
        {
            List<string> ResultStr = new List<string> { "" };
            if (!File.Exists(HistoryFilePath))
                return null;
            else
            {
                ResultStr.RemoveAt(0);
                string[] fileArray = File.ReadAllLines(HistoryFilePath, Encoding.Default);
                for (int i = fileArray.Length - 1; i >= 0; --i)
                {
                    ResultStr.Add(fileArray[i]);
                }
                return ResultStr;
            }
        }
        
    }

    public class MyEmail
    {
        private MailMessage mMailMessage;   //主要处理发送邮件的内容（如：收发人地址、标题、主体、图片等等）
        private SmtpClient mSmtpClient; //主要处理用smtp方式发送此邮件的配置信息（如：邮件服务器、发送端口号、验证方式等等）
        private int mSenderPort;   //发送邮件所用的端口号（htmp协议默认为25）
        private string mSenderServerHost;    //发件箱的邮件服务器地址（IP形式或字符串形式均可）
        private string mSenderPassword;    //发件箱的密码
        private string mSenderUsername;   //发件箱的用户名（即@符号前面的字符串，例如：hello@163.com，用户名为：hello）
        private bool mEnableSsl;    //是否对邮件内容进行socket层加密传输
        private bool mEnablePwdAuthentication;  //是否对发件人邮箱进行密码验证


        public MyEmail(string server, string toMail, string fromMail, string subject, string emailBody, string username, string password, string port, bool sslEnable, bool pwdCheckEnable)
        {
            try
            {
                mMailMessage = new MailMessage();
                mMailMessage.To.Add(toMail);
                mMailMessage.From = new MailAddress(fromMail);
                mMailMessage.Subject = subject;
                mMailMessage.Body = emailBody;
                mMailMessage.IsBodyHtml = true;
                mMailMessage.BodyEncoding = System.Text.Encoding.UTF8;
                mMailMessage.Priority = MailPriority.Normal;
                this.mSenderServerHost = server;
                this.mSenderUsername = username;
                this.mSenderPassword = password;
                this.mSenderPort = Convert.ToInt32(port);
                this.mEnableSsl = sslEnable;
                this.mEnablePwdAuthentication = pwdCheckEnable;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
        ///<summary>
        /// 邮件的发送
        ///</summary>
        public void Send()
        {
            try
            {
                if (mMailMessage != null)
                {
                    mSmtpClient = new SmtpClient();
                    //mSmtpClient.Host = "smtp." + mMailMessage.From.Host;
                    mSmtpClient.Host = this.mSenderServerHost;
                    mSmtpClient.Port = this.mSenderPort;
                    mSmtpClient.UseDefaultCredentials = false;
                    mSmtpClient.EnableSsl = this.mEnableSsl;
                    if (this.mEnablePwdAuthentication)
                    {
                        System.Net.NetworkCredential nc = new System.Net.NetworkCredential(this.mSenderUsername, this.mSenderPassword);
                        //mSmtpClient.Credentials = new System.Net.NetworkCredential(this.mSenderUsername, this.mSenderPassword);
                        //NTLM: Secure Password Authentication in Microsoft Outlook Express
                        mSmtpClient.Credentials = nc.GetCredential(mSmtpClient.Host, mSmtpClient.Port, "NTLM");
                    }
                    else
                    {
                        mSmtpClient.Credentials = new System.Net.NetworkCredential(this.mSenderUsername, this.mSenderPassword);
                    }
                    mSmtpClient.DeliveryMethod = System.Net.Mail.SmtpDeliveryMethod.Network;
                    mSmtpClient.Send(mMailMessage);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}
