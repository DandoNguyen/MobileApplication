using System;
using System.Collections.Generic;
using System.Text;

namespace HyridApp.Models.CloudModels
{
    public class TripAndExpenseList
    {
        public string ID { get; set; }
        public string tripName { get; set; }
        public string tripDestination { get; set; }
        public long tripStartDate { get; set; }
        public long tripEndDate { get; set; }
        public bool isRiskAssessmentRequired { get; set; }
        public string tripDescription { get; set; }
        public long createdDate { get; set; }
        public long updatedDate { get; set; }
        public List<ExpenseItem> ListExpenses { get; set; }
    }
}
