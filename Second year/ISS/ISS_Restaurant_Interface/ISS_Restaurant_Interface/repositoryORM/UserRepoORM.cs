/*using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using ISS_Restaurant.domain;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant.repository;
//using Microsoft.EntityFrameworkCore;

namespace ISS_Restaurant_Interface.repository
{
    public class UserRepoORM : Repository<int, User>
    {
        private readonly DbContext dbContext;

        public UserRepoORM(DbContext dbContext)
        {
            this.dbContext = dbContext;
        }

        public List<User> findAll()
        {
            return dbContext.Set<User>().ToList();
        }

        public User save(User entity)
        {
            dbContext.Set<User>().Add(entity);
            dbContext.SaveChanges();
            return entity;
        }

        public User update(User entity)
        {
            dbContext.Entry(entity).State = EntityState.Modified;
            dbContext.SaveChanges();
            return entity;
        }
    }
}*/


using System.Collections.Generic;
using System.Linq;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.repositoryORM;
using ISS_Restaurant.repository;
using ISS_Restaurant.repositoryORM;

namespace ISS_Restaurant_Interface.repositoryORM
{
    public class UserRepoORM : Repository<int, User>
    {
        private DBContext dataBaseContext;

        public UserRepoORM(DBContext dataBaseContext)
        {
            this.dataBaseContext = dataBaseContext;
        }

        public List<User> findAll()
        {
            return dataBaseContext.users.ToList();
        }

        public User save(User user)
        {
            dataBaseContext.users.Add(user);
            dataBaseContext.SaveChanges();
            return user;
        }

        
        public User update(User user)
        {
            dataBaseContext.users.Update(user);
            dataBaseContext.SaveChanges();
            return user;
        }

        public User delete(User user)
        {
            dataBaseContext.users.Remove(user);
            dataBaseContext.SaveChanges();
            return user;
        }
    }
}
