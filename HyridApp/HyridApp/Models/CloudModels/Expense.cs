using System;
using System.Collections.Generic;
using System.Text;

namespace HyridApp.Models.CloudModels
{
    public class ExpenseItem
    {
        public string ID { get; set; }
        public string expenseType { get; set; }
        public string expenseAmount { get; set; }
        public long expenseDateTime { get; set; }
        public string expenseComment { get; set; }
        public string tripID { get; set; }
    }
}
