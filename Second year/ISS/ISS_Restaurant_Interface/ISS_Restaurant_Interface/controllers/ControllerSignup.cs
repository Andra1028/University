using System.Windows.Forms;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerSignup
    {
        public Service srv;

        public ControllerSignup(Service srv)
        {
            this.srv = srv;
        }

        public void signUp(Form2 form,string textBox1, string textBox2, string textBox3, string textBox4 , string checkBox1, string checkBox2, bool check1, bool check2)
        {
            Form4 form4 = new Form4(new ControllerMenu(srv));
            if (textBox1 != "" && textBox2 != "" && textBox2 != "" && textBox4 != "")
            {
                if (check1 && !check2)
                {
                    Type type = srv.signup(textBox1, textBox2, textBox4, textBox3, checkBox1.ToUpper());
                    if (type == Type.CLIENT)
                    {
                        form4.Show();
                        form.Hide();
                    }

                }
                else if (check2 && !check1)
                {
                    Type type = srv.signup(textBox1, textBox2, textBox4, textBox3, checkBox2.ToUpper());
                    if (type == Type.WORKER)
                    {
                        Form6 form6 = new Form6(new ControllerEmployeeOrders(srv));
                        form6.Show();
                        form.Hide();
                    }
                }
            }
            else
            {
                MessageBox.Show("Incomplete data!");
            }
            
        }
    }
}