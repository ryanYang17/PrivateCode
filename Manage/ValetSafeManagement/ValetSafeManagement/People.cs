using System;

namespace ValetSafeManagement
{
    public class People
    {
        public People() { }
        private int id;
        private string name, password, cellphone, email;
        public int ID
        {
            get { return id; }
            set { id = value; }
        }
        public string Name
        {
            get { return name; }
            set { name = value; }
        }
        public string Password
        {
            get { return password; }
            set { password = value; }
        }
        public string Cellphone
        {
            get { return cellphone; }
            set { cellphone = value; }
        }
        public string Email
        {
            get { return email; }
            set { email = value; }
        }
    }

}
