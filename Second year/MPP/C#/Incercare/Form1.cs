using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Incercare.service;
using log4net;

namespace Incercare
{
    public partial class Form1 : Form
    {
        private Service srv;
        private static readonly ILog log = LogManager.GetLogger("Form1");
        public Form1(Service srv)
        {
            this.srv = srv;
            log.Info("CreatingLoginPage");
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            String usernameStr = username.Text;
            String passwordStr = password.Text;
            try
            {
                if (srv.login(usernameStr, passwordStr) != null)
                {
                    var form2 = new Form2(srv);
                    form2.Show();
                    ///Close();
                }
                else
                {
                    username.Clear();
                    password.Clear();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}