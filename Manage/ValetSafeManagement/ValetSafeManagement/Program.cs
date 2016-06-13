using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ValetSafeManagement
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                //smtp.163.com
                string senderServerIp = "123.125.50.133";
                string toMailAddress = "keepryan0610@163.com";
                string fromMailAddress = "lihaifighting@163.com";
                string subjectInfo = "Test sending e_mail";
                string bodyInfo = "Hello Eric, This is my first testing e_mail";
                string mailUsername = "lihaifighting";
                string mailPassword = "SANmeimuZI17*"; //发送邮箱的密码（）
                string mailPort = "25";

                MyEmail email = new MyEmail(senderServerIp, toMailAddress, fromMailAddress, subjectInfo, bodyInfo, mailUsername, mailPassword, mailPort, false, false);
                email.Send();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}
