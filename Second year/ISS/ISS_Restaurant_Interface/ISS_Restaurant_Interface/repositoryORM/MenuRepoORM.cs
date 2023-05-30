using System.Collections.Generic;
using System.Linq;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.repositoryORM;

using ISS_Restaurant.repository;
using ISS_Restaurant.repositoryORM;

namespace ISS_Restaurant_Interface.repositoryORM
{
    public class MenuRepoORM : Repository<int, Menu>
    {
        private DBContext dataBaseContext;

        public MenuRepoORM(DBContext dataBaseContext)
        {
            this.dataBaseContext = dataBaseContext;
        }

        public List<Menu> findAll()
        {
            return dataBaseContext.menus.OrderBy(menu => menu.category).ToList();
        }

        public Menu save(Menu menu)
        {
            dataBaseContext.menus.Add(menu);
            dataBaseContext.SaveChanges();
            return menu;
        }

        
        public Menu update(Menu menu)
        {
            dataBaseContext.menus.Update(menu);
            dataBaseContext.SaveChanges();
            return menu;
        }

        public Menu delete(Menu menu)
        {
            dataBaseContext.menus.Remove(menu);
            dataBaseContext.SaveChanges();
            return menu;
        }
    }
}