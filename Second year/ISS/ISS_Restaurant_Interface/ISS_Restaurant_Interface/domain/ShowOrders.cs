using System;

namespace ISS_Restaurant_Interface.domain
{
    public class ShowOrders
    {
        public DateTime data { get; set; }
        public Payment pay { get; set; }
        public Status status { get; set; }
        public string products { get; set; }
        public float price { get; set; }

        public ShowOrders(DateTime data, Payment pay, Status status, string products, float price)
        {
            this.data = data;
            this.pay = pay;
            this.status = status;
            this.products = products;
            this.price = price;
        }
    }
}