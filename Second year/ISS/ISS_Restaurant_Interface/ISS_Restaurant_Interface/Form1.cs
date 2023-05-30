using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;

namespace ISS_Restaurant_Interface
{
    public partial class Form1 : Form
    {
        private ControllerStart start;
        public Form1(ControllerStart start)
        {
            InitializeComponent();
            this.start = start;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            start.signupButton(this);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            start.loginButton(this);
        }
       
    }
}