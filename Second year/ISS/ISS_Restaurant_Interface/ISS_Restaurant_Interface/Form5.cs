using System;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;

namespace ISS_Restaurant_Interface
{
    public partial class Form5 : Form
    {
        private ControllerClientOrders ord;
        public Form5(ControllerClientOrders ord)
        {
            InitializeComponent();
            this.ord = ord;
            ord.seeMyOrders(dataGridView1);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ord.back();
            this.Hide();
        }
    }
}