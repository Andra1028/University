using System.Data.SqlClient;

namespace lab8bonus.domain.generic
{
    public class Student : Entity<int>
    {
        protected string name { get; set; }
        protected int school { get; set; }

        public Student(SqlDataReader dataReader) : base((int) dataReader["id"])
        {
            name = (string) dataReader["student_name"];
            school = (int) dataReader["id_school"];
        }
        
        public override string to_string()
        {
            string ret = "";
            ret += name;
            return ret;
        }
    }
}