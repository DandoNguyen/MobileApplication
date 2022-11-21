using SQLite;
using System;
using System.Collections.Generic;
using System.Text;

namespace HyridApp.Models
{
    public class Expense
    {
        [PrimaryKey]
        public Guid ID { get; set; }
        public string expenseType { get; set; }
        public string expenseAmount { get; set; }
        public long expenseDateTime { get; set; }
        public string expenseComment { get; set; }
        public string tripID { get; set; }
        public Expense()
        {
            ID = Guid.NewGuid();
        }
    }
}
