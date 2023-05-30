using System;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;

namespace ISS_Restaurant_Interface
{
    public partial class Form6 : Form
    {
        private ControllerEmployeeOrders order;
        public Form6(ControllerEmployeeOrders order)
        {
            //this.Width = 1061;
            //this.Height = 1004;
            InitializeComponent();
            this.order = order;
            order.showPendingOrders(dataGridView1);
            order.showReadyOrders(dataGridView2);
        }


        private void button1_Click(object sender, EventArgs e)
        {
            order.modifyStatus(dataGridView1,dataGridView2);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            order.back();
            this.Hide();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            order.modifyMenu();
            this.Hide();
        }
    }
}