using System.Collections.Generic;
using System.Collections;
using System.IO;


namespace ValetSafeManage
{
    class PublicFunction
    {
        public const string HistoryFilePath = "History.txt";
        public void WriteHistoryFile(string newInput)
        {
            if (File.Exists(HistoryFilePath))
            {
            }
        }
        public List<string> ReadHistoryFile()
        {
            return null;
        }
    }
}
