using System;
using System.Net.NetworkInformation;
using System.Windows.Forms;
using ISS_Restaurant_Interface.controllers;
using ISS_Restaurant_Interface.service;
using ISS_Restaurant_Interface.domain;


namespace ISS_Restaurant_Interface
{
    public partial class Form2 : Form
    {

        /// private Service srv = new Service();
        private ControllerSignup signup;
        public Form2(ControllerSignup signup)
        {
            InitializeComponent();
            this.signup = signup;
        }
        
        private void button1_Click(object sender, EventArgs e)
        {
            bool c1, c2;
            if (checkBox1.Checked)
                c1 = true;
            else c1 = false;
            if (checkBox2.Checked)
                c2 = true;
            else c2 = false;
            signup.signUp(this,textBox1.Text, textBox2.Text, textBox3.Text, textBox4.Text, checkBox1.Text, checkBox2.Text, c1,c2);
        }

        private void Form2_Click(object sender, EventArgs e)
        {
            throw new System.NotImplementedException();
        }

        private void textBox2_Click(object sender, EventArgs e)
        {
            textBox2.Text = "";
            textBox2.PasswordChar = '*';
        }

        private void textBox1_Click(object sender, EventArgs e)
        {
            textBox1.Text = "";
        }

        private void textBox4_Click(object sender, EventArgs e)
        {
            textBox4.Text = "";
        }

        private void textBox3_Click(object sender, EventArgs e)
        {
            textBox3.Text = "";
        }
    }
}