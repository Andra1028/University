using System;
using System.Windows.Forms;
using Incercare.service;
using log4net;

namespace Incercare
{
    public partial class Form2 : Form
    {
        private Service srv;
        private static readonly ILog log = LogManager.GetLogger("Form2");
        public Form2(Service srv)
        {
            this.srv = srv;
            log.Info("CreatingFlightsPage");
            InitializeComponent();
            try
            {
                dataGridView1.DataSource = srv.findAllFlights();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String dest = destination.Text;
            DateTime data= date.Value;
            try
            {
                var form3 = new Form3(srv,dest,data);
                form3.Show();
                ///Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        
    }
}