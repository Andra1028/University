using System.Data.SqlClient;
using lab8bonus.domain.generic;

namespace lab8bonus.domain
{
    public class Player : Student
    {
        public int id_team { get; set; }

        public Player(SqlDataReader dataReader) : base(dataReader)
        {
            id_team = (int) dataReader["id_team"];
        }
        
        public override string to_string()
        {
            string ret = "";
            ret += base.to_string();
            return ret;
        }
        
        
    }
}