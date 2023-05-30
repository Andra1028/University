using System;
using System.Windows.Forms;
using ISS_Restaurant_Interface.service;
using Type = ISS_Restaurant_Interface.domain.Type;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerLogin
    {
        private Service srv;

        public ControllerLogin(Service srv)
        {
            this.srv = srv;
        }

        public void login(Form3 form,string textBox1, string textBox2)
        {
            if (textBox1 != "" && textBox2 != "")
            {
                try
                {
                    Type type = srv.login(textBox1, textBox2);
                    if (type == Type.CLIENT)
                    {
                        Form4 form4 = new Form4(new ControllerMenu(srv));
                        form4.Show();
                        form.Hide();
                    }
                    else
                    {
                        Form6 form6 = new Form6(new ControllerEmployeeOrders(srv));
                        form6.Show();
                        form.Hide();
                    }
                }
                catch (Exception e)
                {
                    MessageBox.Show("username or password incorrect");
                }

            }
            else
            {
                MessageBox.Show("Incomplete data!");
            }
        }
    }
}