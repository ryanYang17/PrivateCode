using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ValetSafeManagement;

namespace ValetSafeManagement
{
    enum Mode //订单属于历史，当前还是预约订单
    {
        History = 0,
        Processing = 1,
        Future = 2
    }
    enum Status  //订单状态，正在进行还是完成
    {
        Ontheway = 0,
        Done = 1
    }
    enum Pay //支付方式
    {
        Cash = 0,
        Credit = 1
    }
    public class Order
    {
        private int passengerID, driveID, orderMode, orderStatus, totalPrice, payment;//id和people的ID一样，没想好怎么用
        private DateTime startTime, endTime;
        private string startAddress, endAddress, midAddress1st, midAddress2nd;
        public int PassengerID
        {
            get { return passengerID; }
            set { passengerID = value; }
        }
        public int DriveID
        {
            get { return driveID; }
            set { driveID = value; }
        }
        public int OrderMode
        {
            get { return orderMode; }
            set { orderMode = value; }
        }
        public int OrderStatus
        {
            get { return orderStatus; }
            set { orderStatus = value; }
        }
        public int TotalPrice
        {
            get { return totalPrice; }
            set { totalPrice = value; }
        }
        public int Payment
        {
            get { return payment; }
            set { payment = value; }
        }
        public DateTime StartTime
        {
            get { return startTime; }
            set { startTime = value; }
        }
        public DateTime EndTime
        {
            get { return endTime; }
            set { endTime = value; }
        }
        public string StartAddress
        {
            get { return startAddress; }
            set { startAddress = value; }
        }
        public string EndAddress
        {
            get { return endAddress; }
            set { endAddress = value; }
        }
        public string MidAddress1st
        {
            get { return midAddress1st; }
            set { midAddress1st = value; }
        }
        public string MidAddress2nd
        {
            get { return midAddress2nd; }
            set { midAddress2nd = value; }
        }
        public Order()
        {
            this.totalPrice = 40;
        }
    }
    
}
