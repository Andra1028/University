
using System;

namespace ISS_Restaurant_Interface.domain
{
    [Serializable]
    public class Menu : Entity<int>
    {
        public string name { get; set; }
        public Category category { get; set; }
        public float price { get; set; }

        public Menu(int newId, string name, Category category, float price):base(newId) 
        {
            this.name = name;
            //this.category = (Category)Enum.Parse(typeof(Category), category);
            this.category = category;
            this.price = price;
        }

        public Menu() : base(-1)
        {
        }


        public override string to_string()
        {
            return name + category+ price;
        }

    }
}