using System.ComponentModel;

namespace Incercare
{
    partial class Form2
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.destination = new System.Windows.Forms.TextBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.date = new System.Windows.Forms.DateTimePicker();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(50, 25);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 28;
            this.dataGridView1.Size = new System.Drawing.Size(704, 252);
            this.dataGridView1.TabIndex = 0;
            // 
            // destination
            // 
            this.destination.Location = new System.Drawing.Point(72, 320);
            this.destination.Name = "destination";
            this.destination.Size = new System.Drawing.Size(204, 26);
            this.destination.TabIndex = 1;
            // 
            // textBox2
            // 
            this.textBox2.Location = new System.Drawing.Point(721, 351);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(49, 26);
            this.textBox2.TabIndex = 2;
            ///this.textBox2.TextChanged += new System.EventHandler(this.textBox2_TextChanged);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(421, 341);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(112, 47);
            this.button1.TabIndex = 3;
            this.button1.Text = "Cauta zbor";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // date
            // 
            this.date.Location = new System.Drawing.Point(76, 384);
            this.date.Name = "date";
            this.date.Size = new System.Drawing.Size(200, 26);
            this.date.TabIndex = 4;
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.date);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.destination);
            this.Controls.Add(this.dataGridView1);
            this.Name = "Form2";
            this.Text = "Form2";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.DateTimePicker date;

        private System.Windows.Forms.TextBox destination;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.Button button1;

        private System.Windows.Forms.DataGridView dataGridView1;

        #endregion
    }
}