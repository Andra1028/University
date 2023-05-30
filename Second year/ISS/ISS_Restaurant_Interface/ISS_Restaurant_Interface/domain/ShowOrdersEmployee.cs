using System;

namespace ISS_Restaurant_Interface.domain
{
    public class ShowOrdersEmployee
    {
        public int order_id { get; set; }
        public string user { get; set; }
        public DateTime time { get; set; }
        public Payment pay { get; set; }
        public Status status { get; set; }
        public string products { get; set; }
        public float price { get; set; }

        public ShowOrdersEmployee(int orderId, string user, DateTime time, Payment pay, Status status, string products, float price)
        {
            order_id = orderId;
            this.user = user;
            this.time = time;
            this.pay = pay;
            this.status = status;
            this.products = products;
            this.price = price;
        }
    }
}