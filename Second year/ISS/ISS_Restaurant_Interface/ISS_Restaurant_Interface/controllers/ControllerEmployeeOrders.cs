using System.Collections.Generic;
using System.Windows.Forms;
using ISS_Restaurant_Interface.domain;
using ISS_Restaurant_Interface.service;

namespace ISS_Restaurant_Interface.controllers
{
    public class ControllerEmployeeOrders
    {
        private Service srv;

        public ControllerEmployeeOrders(Service srv)
        {
            this.srv = srv;
        }

        public void showPendingOrders(DataGridView dataGridView)
        {
            List<ShowOrdersEmployee> show = srv.getOrdersEmployeePending();
            if (show.Count > 0)
                dataGridView.DataSource = show;
            else dataGridView.DataSource = null;
        }

        public void showReadyOrders(DataGridView dataGridView)
        {
            List<ShowOrdersEmployee> show = srv.getOrdersEmployeeReady();
            if (show.Count > 0)
            {
                dataGridView.DataSource = show;
            }
            else dataGridView.DataSource = null;
        }

        public void modifyStatus(DataGridView dataGridView1, DataGridView dataGridView2)
        {
            if (dataGridView1.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = dataGridView1.SelectedRows[0];
                ShowOrdersEmployee show = (ShowOrdersEmployee)selectedRow.DataBoundItem;
                srv.modifyStatus(show);
                showPendingOrders(dataGridView1);
                showReadyOrders(dataGridView2);
            }
            else
            {
                if (dataGridView2.SelectedRows.Count > 0)
                {
                    DataGridViewRow selectedRow = dataGridView2.SelectedRows[0];
                    ShowOrdersEmployee show = (ShowOrdersEmployee)selectedRow.DataBoundItem;
                    srv.modifyStatus(show);
                    showReadyOrders(dataGridView2);
                }
                else
                {
                    MessageBox.Show("Must select an order");
                }
            }
        }

        public void back()
        {
            Form1 form1 = new Form1(new ControllerStart(srv));
            form1.Show();
        }

        public void modifyMenu()
        {
            Form7 form7 = new Form7(new ControllerManageMenu(srv));
            form7.Show();
        }
    }
}