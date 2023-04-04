/*using System;
using System.Collections.Generic;
using System.Configuration;
using System.Drawing.Printing;
using System.Runtime;
using Proiect1_CSharp.domain;
using Proiect1_CSharp.repository;

namespace Incercare
{
    internal class Class1
    {
        
        static string GetConnectionStringByName(string name)
        {
            string returnValue = null;

            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
        public static void Main(string[] args)
        {
            log4net.Config.XmlConfigurator.Configure();
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("SqliteKey"));

            //string connectionString = "Server=localhost; Database=ZboruirMpp; Integrated Security=True;";
            UserRepository userRepo = new UserRepository(props);
            ///ZborRepository zborRepo = new ZborRepository(props);
            ///BiletRepository biletRepo = new BiletRepository(props);
            ///BiletTuristRepository btRepo = new BiletTuristRepository(props);
            ///User user = new User(1,"jscs", "jsch");
            //userRepo.save(user);
            List < User >  users= userRepo.findAll();
            foreach(User user in users)
            {
                Console.WriteLine(user.id + " "+ user.username+ " "+ user.password);
            }
        }
    }
}*/

using System;
using System.Collections.Generic;
using System.Configuration;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Incercare;
using Incercare.domain;
using Incercare.repository;
using Incercare.service;
using Microsoft.IdentityModel.Protocols;

namespace Incercare
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        static string GetConnectionStringByName(string name)
        {
            string returnValue = null;

            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
        [STAThread]
        static void Main()
        {
            log4net.Config.XmlConfigurator.Configure();
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("SqliteKey"));
            Service srv = new Service(props);
            Application.EnableVisualStyles(); 
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(srv));
        }
    }
}