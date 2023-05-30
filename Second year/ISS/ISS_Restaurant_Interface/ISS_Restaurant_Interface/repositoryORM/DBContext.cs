
using ISS_Restaurant_Interface.domain;
using Microsoft.EntityFrameworkCore;

namespace ISS_Restaurant.repositoryORM
{
    public class DBContext: DbContext
    {
        public DBContext(DbContextOptions<DBContext> options)
            : base(options)
        {
        }

        public DbSet<User> users { get; set; }
        public DbSet<Menu> menus { get; set; }
        public DbSet<Order> orders { get; set; }
        public DbSet<OrderProduct> orderproducts { get; set; }
    }
}