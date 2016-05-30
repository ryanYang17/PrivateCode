using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Management
{
    enum Mode
    {
        History = 0,
        Processing = 1,
        Future = 2
    }
    enum Status
    {
        Ontheway = 0,
        Done = 1
    }
    enum Pay
    {
        Cash = 0,
        Credit = 1
    }
    public class Order
    {
        public Order() { }
        private int id, orderMode, orderStatus, totalPrice, payment;
        private DateTime startTime, endTime;
        private string startAddress, endAddress, midAddress1st, midAddress2nd;
        public int ID
        {
            get { return id; }
            set { id = value; }
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
    }
    
}
