using System;
using System.Collections.Generic;
using System.Linq;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant.repository;
using ISS_Restaurant.repositoryORM;

namespace ISS_Restaurant_Interface.repositoryORM
{
    public class OrderProductRepoORM:Repository<int, OrderProduct>
    {
        private DBContext _dbContext;

        public OrderProductRepoORM(DBContext dbContext)
        {
            _dbContext = dbContext;
        }

        public List<OrderProduct> findAll()
        {
            return _dbContext.orderproducts.ToList();
        }

        public OrderProduct save(OrderProduct op)
        {
            
            _dbContext.orderproducts.Add(op);
            _dbContext.SaveChanges();
            return op;
        }

        public OrderProduct update(OrderProduct op)
        {
            throw new NotImplementedException();
        }

        public OrderProduct delete(OrderProduct op)
        {
            throw new NotImplementedException();
        }
    }
}