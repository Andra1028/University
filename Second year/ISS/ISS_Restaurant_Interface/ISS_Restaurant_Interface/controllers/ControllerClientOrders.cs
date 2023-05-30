using System.Windows.Forms;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerClientOrders
    {
        private Service srv;

        public ControllerClientOrders(Service srv)
        {
            this.srv = srv;
        }


        public void seeMyOrders(DataGridView dataGridView1)
        {
            dataGridView1.DataSource = srv.getClientOrders();
            dataGridView1.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
            foreach (DataGridViewColumn column in dataGridView1.Columns)
            {
                column.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            }
        }
        public void back()
        {
            Form4 form4 = new Form4(new ControllerMenu(srv));
            form4.Show();
        }
    }
}