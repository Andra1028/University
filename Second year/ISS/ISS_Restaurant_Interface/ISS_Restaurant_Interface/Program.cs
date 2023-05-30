using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;
using ISS_Restaurant_Interface.service;
using ISS_Restaurant.repositoryORM;
using Microsoft.EntityFrameworkCore;

namespace ISS_Restaurant_Interface
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            var contextOptions  = new DbContextOptionsBuilder<DBContext>()
                .UseSqlite("Data Source=C:\\Users\\40771\\RiderProjects\\ISS_Restaurant\\restaurant.db").Options;
            
            var context = new DBContext(contextOptions);
            Service service = new Service(context);
            //ApplicationConfiguration.Initialize();
            ControllerStart start = new ControllerStart(service);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(start));
        }
    }
}