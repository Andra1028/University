using System;
using System.Collections.Generic;
using System.Data;
using System.Diagnostics;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface
{
    public partial class Form4 : Form
    {
       // private Service srv;
       private ControllerMenu menu;
        public Form4(ControllerMenu menu)
        {
            InitializeComponent();
            this.menu = menu;
            initialize();
        }

        public void initialize()
        { 
            menu.getAllMenu(dataGridView1);
            menu.setComboBox(comboBox1);
            dataGridView1.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dataGridView1.MultiSelect = true;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            menu.back();
            this.Hide();
        }

        private void label2_Click(object sender, EventArgs e)
        {
            throw new System.NotImplementedException();
        }


        private void button2_Click(object sender, EventArgs e)
        {
           menu.saveOrder(dataGridView1,comboBox1);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            menu.seeClientOrders();
            this.Hide();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            menu.deteleAccount();
            this.Hide();
        }
    }
}