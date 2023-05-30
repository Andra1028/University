using System;
using System.Data.SQLite;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface
{
    public partial class Form3 : Form
    {
        //private Service srv;
        private ControllerLogin login;
        public Form3(ControllerLogin login)
        {
            InitializeComponent();
            this.login = login;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            login.login(this,textBox1.Text, textBox2.Text);
            //this.Hide();
        }

        private void textBox1_Click(object sender, EventArgs e)
        {
            textBox1.Text = "";
        }

        private void textBox2_Click(object sender, EventArgs e)
        {
            textBox2.Text = "";
            textBox2.PasswordChar = '*';
        }
    }
}