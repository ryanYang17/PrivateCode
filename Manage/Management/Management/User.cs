using System;
using Management;

namespace Management
{
    public class Passenger : People
    {
        public Passenger()
        {
        }
        ~Passenger() { }
    }

    public class Driver : People
    {
        public Driver() { }
        ~Driver() { }
        private string carType, carNo, score, account;
        public string CarType
        {
            get { return carType; }
            set { carType = value; }
        }
        public string CarNo
        {
            get { return carNo; }
            set { carNo = value; }
        }
        public string Score
        {
            get { return score; }
            set { score = value; }
        }
        public string Account
        {
            get { return account; }
            set { account = value; }
        }
    }
}