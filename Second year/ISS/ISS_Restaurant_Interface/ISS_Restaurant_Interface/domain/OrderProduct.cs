using System;

namespace ISS_Restaurant_Interface.domain
{
    [Serializable]
    public class OrderProduct: Entity<int>
    {
        public int order_id { get; set; }
        public int product_id { get; set; }

        public OrderProduct(int newId, int order, int product) : base(newId)
        {
            this.order_id = order;
            this.product_id = product;
        }
        public OrderProduct() : base(-1)
        {
        }

        public override string to_string()
        {
            //throw new System.NotImplementedException();
            return order_id.ToString() + " " + product_id.ToString();
        }
    }
}