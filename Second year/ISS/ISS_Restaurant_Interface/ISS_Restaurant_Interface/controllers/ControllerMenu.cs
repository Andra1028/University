using System;
using System.Collections.Generic;
using System.Windows.Forms;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.service;
using Menu = ISS_Restaurant_Interface.domain.Menu;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerMenu
    {
        private Service srv;

        public ControllerMenu(Service srv)
        {
            this.srv = srv;
        }

        public void getAllMenu(DataGridView dataGridView1)
        {
            List<Menu> menuList = srv.displayMenu();
            dataGridView1.DataSource = menuList;
        }

        public void back()
        {
            Form1 form1 = new Form1(new ControllerStart(srv));
            form1.Show();
        }

        public void setComboBox(ComboBox combo)
        {
            List<String> pay = new List<string>();
            pay.Add(Payment.CARD.ToString());
            pay.Add((Payment.CASH.ToString()));
            combo.DataSource = pay;
        }

        public void saveOrder(DataGridView dataGridView1, ComboBox combo)
        {
            DataGridViewSelectedRowCollection selectedRows = dataGridView1.SelectedRows;
            if (selectedRows.Count < 1)
            {
                MessageBox.Show("Must select a product");
            }
            else
            {
                List<int> products = new List<int>();
                foreach (DataGridViewRow row in selectedRows)
                {
                    products.Add(Convert.ToInt16(row.Cells[3].Value));
                }

                string pay = combo.Text;
                srv.makeOrder(products, pay);
                MessageBox.Show("Your order was sent");
            }
        }

        public void seeClientOrders()
        {
            Form5 form5 = new Form5(new ControllerClientOrders(srv));
            form5.Show();
        }

        public void deteleAccount()
        {
            srv.deleteAccount();
            Form1 form1 = new Form1(new ControllerStart(srv));
            form1.Show();
        }
    }
}