using System;
using System.Collections.Generic;

namespace ISS_Restaurant_Interface.domain
{
    [Serializable]
    public class Order: Entity<int>
    {
        public int user { get; set; }
        //private List<Menu> products;
        public DateTime time { get; set; }
        public Status status { get; set; }
        public Payment pay { get; set; }

        public Order(int newId, int user,  DateTime time, Status status, Payment pay) : base(newId)
        {
            this.user = user;
            this.time = time;
            this.status = status;
            this.pay = pay;
        }

        public Order() : base(-1)
        {
        }

        public override string to_string()
        {
            throw new NotImplementedException();
        }
    }
}