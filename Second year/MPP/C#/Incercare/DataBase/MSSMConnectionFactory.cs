
using System;
using System.Collections.Generic;
using System.Data;
using Microsoft.Data.Sqlite;

namespace Incercare.DataBase
{
    public class MSSMConnectionFactory:ConnectionFactory
    {
        public override IDbConnection createConnection(IDictionary<string, string> props)
        {
            String connectionString = props["ConnectionString"];
            Console.WriteLine("SQLite ---Se deschide o conexiune la  ... {0}", connectionString);
            return new SqliteConnection(connectionString);
        }
    }
}