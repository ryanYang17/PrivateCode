using System.Collections.Generic;
using System.IO;
using System.Text;

namespace ValetSafeManagement
{
    class PublicFunction
    {
        public const string HistoryFilePath = "History.txt";
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
                string str = "";
                FileStream fs1 = new FileStream(HistoryFilePath, FileMode.Open, FileAccess.Write);
                StreamWriter sw = new StreamWriter(fs1);
                if (fileArray.Length == 5)
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
