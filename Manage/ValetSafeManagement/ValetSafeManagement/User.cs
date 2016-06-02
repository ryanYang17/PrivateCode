using System;
using ValetSafeManagement;

namespace ValetSafeManagement
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
        enum Status  //当前司机是否开启接单
        {
            Busy = 0,
            StandBy =1,
            Inavailable = 2
        }
        public Driver() { }
        ~Driver() { }
        private int driverStatus;
        private string carType, carNo, score, account;
        public int DriverStatus
        {
            get { return driverStatus; }
            set { driverStatus = value; }
        }
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