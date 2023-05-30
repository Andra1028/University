using System;

namespace ISS_Restaurant_Interface.domain
{
    [Serializable]
    public abstract class Entity<ID>
    {
        public ID id { get; set;}

        public Entity(ID newId)
        {
            id = newId;
        }

        public abstract string to_string();
    }
}