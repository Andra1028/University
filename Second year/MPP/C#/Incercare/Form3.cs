using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using Incercare.service;
using log4net;

namespace Incercare
{
    public partial class Form3 : Form
    {
        private Service srv;
        private static readonly ILog log = LogManager.GetLogger("Form3");
        private String destinatie;
        private DateTime data;
        
        
        public Form3(Service srv, String destination, DateTime date)
        {
            this.srv = srv;
            this.destinatie = destination;
            this.data = date;
            log.Info("CreatingBuyFlightsPage");
            InitializeComponent();
            try
            {
                dataGridView1.DataSource = srv.findFlight(destination, date);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private void button1_Click(object sender, EventArgs e)
        {
            String client_nume = textBox1.Text;
            String turisti_nume = textBox2.Text;
            String client_adresa = textBox3.Text;
            int locuri=Convert.ToInt32(textBox4.Text);
            List<String> turisti = new List<string>();
            string[] turistilist = turisti_nume.Split(',');
            foreach (string turist in turistilist)
            {
                turisti.Add(turist);
            }
            try
            {
                int zbor_id = (int)dataGridView1.Rows[dataGridView1.SelectedCells[4].RowIndex].Cells[4].Value;
                srv.buyTicket(zbor_id,client_nume,client_adresa,locuri,turisti);
                dataGridView1.DataSource = srv.findFlight(destinatie, data);
            }
            catch (Exception ex)
            {
                textBox1.Clear();
                textBox2.Clear();
                textBox3.Clear();
                textBox4.Clear();
                MessageBox.Show(ex.Message);
            }
        }
    }
}