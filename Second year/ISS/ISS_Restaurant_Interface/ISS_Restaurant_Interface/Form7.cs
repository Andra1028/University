using System;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;

namespace ISS_Restaurant_Interface
{
    public partial class Form7 : Form
    {
        private ControllerManageMenu manage;
        public Form7(ControllerManageMenu manage)
        {
            InitializeComponent();
            this.manage = manage;
            manage.showMenu(dataGridView1);
            manage.setCategory(comboBox1);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            manage.deleteFromMenu(dataGridView1);
        }


        private void button2_Click(object sender, EventArgs e)
        {
            
            manage.addToMenu(dataGridView1,textBox1.Text, comboBox1.Text, float.Parse(numericUpDown1.Text));
        }

        private void button3_Click(object sender, EventArgs e)
        {
            manage.back();
            this.Hide();
        }
    }
}