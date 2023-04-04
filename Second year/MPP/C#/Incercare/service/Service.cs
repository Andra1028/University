using System;
using System.Collections.Generic;
using System.Configuration;
using System.Xml.Schema;
using Incercare.domain;
using Incercare.repository;

namespace Incercare.service
{
    public class Service
    {
        private UserRepository repou;
        private ZborRepository repoz;
        private BiletRepository repob;
        private BiletTuristRepository repobt;
        private int user_id;

        
        static string GetConnectionStringByName(string name)
        {
            string returnValue = null;

            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
        
        public Service(IDictionary<String,String> props)
        {
            log4net.Config.XmlConfigurator.Configure();

            this.repou = new UserRepository(props);
            this.repoz = new ZborRepository(props);
            this.repob = new BiletRepository(props);
            this.repobt = new BiletTuristRepository(props);
        }

        public List<Zbor> findAllFlights()
        {
            return repoz.findAll();
        }

        public User login(string username, string password)
        {
            List<User> users = repou.findAll();
            foreach(User user in users)
            {
                if (user.username == username)
                {
                    user_id = user.id;
                    return user;
                }
            }

            return null;
        }

        public List<Zbor> findFlight(string destination, DateTime data)
        {
            List<Zbor> zboruri = repoz.findAll();
            List<Zbor> newzboruri = new List<Zbor>();
            foreach (Zbor zbor in zboruri)
            {
                if (zbor.destinatie==destination && zbor.data_ora.Date==data.Date)
                    newzboruri.Add(zbor);
            }

            return newzboruri;
        }

        public int generate_id()
        { 
            List<Bilet> bilete = repob.findAll();
            int maxi = 0;
            foreach (Bilet bilet in bilete)
            {
                if (bilet.id > maxi)
                    maxi = bilet.id;
            }

            maxi = maxi + 1;
            return maxi;
        }


        public Zbor findOne(int id)
        {
            foreach (Zbor zbor in repoz.findAll())
            {
                if (zbor.id == id)
                    return zbor;
            }

            return null;
        }
        public void buyTicket(int zbor_id, string client_nume, string client_adresa, int nr_locuri, List<String> turisti)
        {
            int id = generate_id();
            Bilet bilet = new Bilet(id, zbor_id, user_id, client_nume, client_adresa, nr_locuri);
            repob.save(bilet);
            Zbor zbor = findOne(zbor_id);
            int locuri = zbor.locuri;
            zbor.locuri = locuri - nr_locuri;
            repoz.update(zbor);
          ///  foreach(string turist in turisti)
           /// {
           ///     BiletTurist bt = new BiletTurist(id, turist);
             ///   repobt.save(bt);
            ///}
        }

        public void logout()
        {
            user_id = -1;
        }

        public List<Zbor> getAvailableFlights()
        {
            List<Zbor> zboruri = new List<Zbor>();
            foreach (Zbor zbor in repoz.findAll())
            {
                if (zbor.locuri>0)
                    zboruri.Add(zbor);
            }

            return zboruri;
        }
    }
}