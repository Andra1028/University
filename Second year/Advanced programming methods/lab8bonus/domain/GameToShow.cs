using System;

namespace lab8bonus.domain
{
    public class GameToShow
    {
        
        private string team1;
        private string team2;
        private DateTime date;

        public GameToShow(string newTeam1, string newTeam2, DateTime newDate)
        {
            team1 = newTeam1;
            team2 = newTeam2;
            date = newDate;
        }

        public string to_string()
        {
            string ret = "";
            ret += team1;
            ret += " ";
            ret += team2;
            ret += " ";
            ret += date;
            return ret;
        }
        
    }
}