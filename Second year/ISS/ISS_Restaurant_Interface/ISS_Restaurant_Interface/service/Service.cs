using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Diagnostics;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.repositoryORM;
using ISS_Restaurant_Interface.repositoryORM;
using ISS_Restaurant.repository;
using ISS_Restaurant.repositoryORM;
//using MenuRepo = ISS_Restaurant_Interface.repository.MenuRepo;
using Type = ISS_Restaurant_Interface.domain.Type;

namespace ISS_Restaurant_Interface.service
{

    public class Service
    {
        private DBContext _dbContext;
        private UserRepoORM userrepo;
        private MenuRepoORM menurepo;
        private OrderRepoORM orderrepo;
        private OrderProductRepoORM prodrepo;
        private List<User> loggedClients = new List<User>();
        private User logedIn;

        private SQLiteConnection connection;

        /*public Service()
        {
            string connectionString = "Data Source=C:/Users/40771/RiderProjects/ISS_Restaurant/restaurant.db;";
            this.userrepo =new UserRepo(connectionString);
            this.menurepo = new MenuRepo(connectionString);
            //connection = new SQLiteConnection(connectionString);
            //connection.Open();
        }*/

        public Service(DBContext dbContext)
        {
            this._dbContext = dbContext;
            userrepo = new UserRepoORM(this._dbContext);
            menurepo = new MenuRepoORM(this._dbContext);
            orderrepo = new OrderRepoORM(this._dbContext);
            prodrepo = new OrderProductRepoORM(this._dbContext);
        }

        
        public Type login(string name, string password)
        {
            if(_dbContext == null)
            {
                throw new ArgumentNullException();
            }
            foreach (var user in userrepo.findAll())
            {
                if (user.user_name == name && user.password == password)
                {
                    logedIn = user;
                    loggedClients.Add(user);
                    return user.user_type;
                }
            }
            throw new ArgumentNullException();
        }

        public int generateId()
        {
            int max = 0;
            foreach (var user in userrepo.findAll())
            {
                if (user.id > max)
                    max = user.id;
            }

            max = max + 1;
            return max;
        }

        public Type signup(string name, string password, string address, string phone, string type)
        {
            if(_dbContext == null)
            {
                throw new ArgumentNullException();
            }
            domain.Type type_ok =
                (domain.Type)Enum.Parse(typeof(domain.Type), type);
            int id = generateId();
            User user = new User(id, name, password, type_ok, address, phone);
            
                foreach (User u in userrepo.findAll())
                {
                    if (u.user_name == name && u.password == password)
                        throw new ArgumentNullException();
                }

                logedIn = user;
                userrepo.save(user);
                loggedClients.Add(user);
                return user.user_type;
        }

        public List<Menu> displayMenu()
        {
            if(_dbContext == null)
            {
                throw new ArgumentNullException();
            }
            return menurepo.findAll();
        }

        public int generateIdOrder()
        {
            int max = 0;
            foreach (var user in orderrepo.findAll())
            {
                if (user.id > max)
                    max = user.id;
            }

            max = max + 1;
            return max;
        }
        
        public int generateIdOrderProducts()
        {
            int max = 0;
            foreach (var user in prodrepo.findAll())
            {
                if (user.id > max)
                    max = user.id;
            }

            max = max + 1;
            return max;
        }
        
        public void makeOrder(List<int> products, string payment)
        {
            Payment payment_ok = (Payment)Enum.Parse(typeof(domain.Payment), payment);
            Status status_ok = (Status)Enum.Parse(typeof(Status), "PENDING");
            DateTime time = DateTime.Now;
            int id_order = generateIdOrder();
            Order order = new Order(id_order, logedIn.id,time, status_ok, payment_ok);
            orderrepo.save(order);
            foreach (var prod in products)
            {
                int id_order_product = generateIdOrderProducts();
                OrderProduct ordprod = new OrderProduct(id_order_product, id_order, prod);
                prodrepo.save(ordprod);
            }
            //orderrepo.save(order);
        }

        public User findOneUser(int id)
        {
            foreach (var user in userrepo.findAll())
            {
                if (user.id == id)
                    return user;
            }

            return null;
        }

        public String getProductName(int id)
        {
            foreach (var prod in menurepo.findAll())
            {
                if (prod.id == id)
                    return prod.name;
            }

            return null;
        }
        
        public float getProductPrice(int id)
        {
            foreach (var prod in menurepo.findAll())
            {
                if (prod.id == id)
                    return prod.price;
            }

            return 0;
        }

        public List<ShowOrders> getClientOrders()
        {
            List<ShowOrders> show = new List<ShowOrders>();
            foreach (var ord in orderrepo.findAll())
            {
                if (ord.user == logedIn.id)
                {
                    string products = "";
                    float price = 0;
                    foreach (var prod in prodrepo.findAll())
                    {
                        if (prod.order_id == ord.id)
                        { 
                            string name = getProductName(prod.product_id);
                            products = products + name + ", ";
                            price += getProductPrice(prod.product_id);
                        }
                    }

                    ShowOrders order = new ShowOrders(ord.time, ord.pay, ord.status, products, price);
                    show.Add(order);
                }
            }

            return show;
        }


        public void deleteAccount()
        {
            User user = userrepo.delete(logedIn);
            logedIn = null;
        }

        public List<ShowOrdersEmployee> getOrdersEmployeePending()
        {
            List<ShowOrdersEmployee> pendingList = new List<ShowOrdersEmployee>();
            foreach (Order ord in orderrepo.findAll())
            {
                if (ord.status == Status.PENDING)
                {
                    string products = "";
                    float price = 0;
                    foreach (OrderProduct prod in prodrepo.findAll())
                    {
                        if (ord.id == prod.order_id)
                        {
                            products += getProductName(prod.product_id);
                            price += getProductPrice(prod.product_id);
                        }
                    }

                    User user = findOneUser(ord.user);
                    if (user != null)
                    {
                        ShowOrdersEmployee pending = new ShowOrdersEmployee(ord.id, user.user_name, ord.time, ord.pay,
                            ord.status, products, price);
                        pendingList.Add(pending);
                    }
                }
            }

            return pendingList;
        }
        
        public List<ShowOrdersEmployee> getOrdersEmployeeReady()
        {
            List<ShowOrdersEmployee> readyList = new List<ShowOrdersEmployee>();
            foreach (Order ord in orderrepo.findAll())
            {
                if (ord.status == Status.READY)
                {
                    string products = "";
                    float price = 0;
                    foreach (OrderProduct prod in prodrepo.findAll())
                    {
                        if (ord.id == prod.order_id)
                        {
                            products += getProductName(prod.product_id);
                            price += getProductPrice(prod.product_id);
                        }
                    }

                    User user = findOneUser(ord.user);
                    if (user != null)
                    {
                        ShowOrdersEmployee ready = new ShowOrdersEmployee(ord.id, user.user_name, ord.time, ord.pay,
                            ord.status, products, price);
                        readyList.Add(ready);
                    }
                }
            }

            return readyList;
        }

        public Order getOrderById(int id)
        {
            foreach (var order in orderrepo.findAll())
            {
                if (order.id == id)
                    return order;
            }

            return null;
        }

        public void modifyStatus(ShowOrdersEmployee show)
        {
            Order order = getOrderById(show.order_id);
            if (order.status == Status.PENDING)
                order.status = Status.READY;
            else {
                if (order.status == Status.READY)
                    order.status = Status.TAKEN;
                
            }

            Order modify = orderrepo.update(order);
        }

        public int generateIdMenu()
        {
            int id = 0;
            foreach (var prod in menurepo.findAll())
            {
                if (prod.id > id)
                {
                    id = prod.id;
                }
            }

            id++;
            return id;
        }
        public void addProductToMenu(string name, string category, float price)
        {
            Category categopry_ok = (Category)Enum.Parse(typeof(Category), category);
            int id = generateIdMenu();
            Menu product = new Menu(id, name, categopry_ok, price);
            menurepo.save(product);
        }

        public void deleteProductFromMenu(Menu menu)
        {
            menurepo.delete(menu);
        }
    }
}