using System;
using System.Collections.Generic;
using System.Windows.Forms;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerManageMenu
    {
        private Service srv;

        public ControllerManageMenu(Service srv)
        {
            this.srv = srv;
        }

        public void showMenu(DataGridView dataGridView)
        {
            dataGridView.DataSource = srv.displayMenu();
        }

        public void setCategory(ComboBox comboBox)
        {
            List<Category> category = new List<Category>(Enum.GetValues(typeof(Category)) as Category[]);

            comboBox.DataSource = category;
        }

        public void addToMenu(DataGridView dataGridView,string name, string category, float price)
        {
            if (name != "" && price > 0)
            {
                srv.addProductToMenu(name, category,price);
                showMenu(dataGridView);
            }
            else
            {
                MessageBox.Show("Imcomplete data");
            }
        }

        public void deleteFromMenu(DataGridView dataGridView)
        {
            
            try
            {
                DataGridViewRow row = dataGridView.SelectedRows[0];
                domain.Menu product = (domain.Menu)row.DataBoundItem;
                srv.deleteProductFromMenu(product);
                showMenu(dataGridView);
            }
            catch (Exception e)
            {
                MessageBox.Show("Must select an item");
            }
        }

        public void back()
        {
            Form6 form6 = new Form6(new ControllerEmployeeOrders(srv));
            form6.Show();
        }
    }
}