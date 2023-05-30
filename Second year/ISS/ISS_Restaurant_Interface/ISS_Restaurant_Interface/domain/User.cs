

using System;

namespace ISS_Restaurant_Interface.domain
{

    [Serializable]
    public class User : Entity<int>
    {
        public string user_name { get; set; }
        public string password { get; set; }
        public Type user_type { get; set; }
        public string address { get; set; }
        public string phone { get; set; }

        public User(int newId, string name, string password, Type type, string address, string phone) : base(newId)
        {
            this.user_name = name;
            this.password = password;
            this.user_type = type;
            this.address = address;
            this.phone = phone;
        }

        public User() : base(-1)
        {
        }

        public override string to_string()
        {
            return user_name + password;
        }

    }
}