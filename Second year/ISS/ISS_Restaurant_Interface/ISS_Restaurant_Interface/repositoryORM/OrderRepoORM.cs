using System;
using System.Collections.Generic;
using System.Linq;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant.repository;
using ISS_Restaurant.repositoryORM;

namespace ISS_Restaurant_Interface.repositoryORM
{
    public class OrderRepoORM: Repository<int, Order>
    {
        private DBContext dbcontext;

        public OrderRepoORM(DBContext dbcontext)
        {
            this.dbcontext = dbcontext;
        }


        public List<Order> findAll()
        {
            return dbcontext.orders.ToList();
        }

        public Order save(Order order)
        {
            dbcontext.orders.Add(order);
            dbcontext.SaveChanges();
            return order;
        }

        public Order update(Order order)
        {
            dbcontext.orders.Update(order);
            dbcontext.SaveChanges();
            return order;
        }

        public Order delete(Order order)
        {
            throw new NotImplementedException();
        }
    }
}