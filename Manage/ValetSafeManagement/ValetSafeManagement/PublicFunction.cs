using System.Collections.Generic;
using System.IO;
using System.Text;

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
}
