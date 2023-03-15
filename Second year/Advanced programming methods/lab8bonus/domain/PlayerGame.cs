using System;
using System.Data.SqlClient;
using System.Runtime.InteropServices.ComTypes;
using lab8bonus.domain.generic;

namespace lab8bonus.domain
{
    public class PlayerGame
    {
        public int id_player { get; set; }
        public int id_team { get; set; }
        public int id_game { get; set; }
        public int points { get; set; }

        public PlayerGame(SqlDataReader dataReader)
        {
            id_player = (int) dataReader["id_player"];
            id_team = (int) dataReader["id_team"];
            id_game = (int) dataReader["id_game"];
            points = (int) dataReader["points"];
        }
        
        
        
    }
}