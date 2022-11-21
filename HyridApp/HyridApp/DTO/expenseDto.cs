using System;
using System.Collections.Generic;
using System.Text;

namespace HyridApp.DTO
{
    public class expenseDto
    {
        public Guid ID { get; set; }
        public string expenseType { get; set; }
        public string expenseAmount { get; set; }
        public DateTime expenseDateTime { get; set; }
        public string expenseComment { get; set; }
        public string tripID { get; set; }
    }
}
