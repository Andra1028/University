using System.Data.SQLite;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerStart
    {
        private Service srv;

        public ControllerStart(Service srv)
        {
            this.srv = srv;
        }

        public void signupButton(Form1 form1)
        {
            Form2 form2 = new Form2(new ControllerSignup(srv));
            form2.Show();
            form1.Hide();
        }
        
        public void loginButton(Form1 form1)
        {
            Form3 form3 = new Form3(new ControllerLogin(srv));
            form3.Show();
            form1.Hide();
        }
    }
}