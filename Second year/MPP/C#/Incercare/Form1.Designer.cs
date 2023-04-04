namespace Incercare
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

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
            this.username = new System.Windows.Forms.TextBox();
            this.password = new System.Windows.Forms.TextBox();
            this.login = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // username
            // 
            this.username.Location = new System.Drawing.Point(265, 185);
            this.username.Name = "username";
            this.username.Size = new System.Drawing.Size(268, 26);
            this.username.TabIndex = 0;
            // 
            // password
            // 
            this.password.Location = new System.Drawing.Point(265, 251);
            this.password.Name = "password";
            this.password.Size = new System.Drawing.Size(268, 26);
            this.password.TabIndex = 1;
            // 
            // login
            // 
            this.login.Location = new System.Drawing.Point(353, 324);
            this.login.Name = "login";
            this.login.Size = new System.Drawing.Size(106, 47);
            this.login.TabIndex = 2;
            this.login.Text = "login";
            this.login.UseVisualStyleBackColor = true;
            this.login.Click += new System.EventHandler(this.button1_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.login);
            this.Controls.Add(this.password);
            this.Controls.Add(this.username);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.TextBox username;
        private System.Windows.Forms.TextBox password;
        private System.Windows.Forms.Button login;

        #endregion
    }
}