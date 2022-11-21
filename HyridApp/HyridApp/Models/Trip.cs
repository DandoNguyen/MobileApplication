using SQLite;
using System;
using System.Collections.Generic;
using System.Text;

namespace HyridApp.Models
{
    public class Trip
    {
        [PrimaryKey]
        public Guid ID { get; set; }
        public string tripName { get; set; }
        public string tripDestination { get; set; }
        public long tripStartDate { get; set; }
        public long tripEndDate { get; set; }
        public bool isRiskAssessmentRequired { get; set; }
        public string tripDescription { get; set; }
        public long createdDate { get; set; }
        public long updatedDate { get; set; }

        public Trip()
        {
            ID = Guid.NewGuid();
            createdDate = DateTime.Today.Ticks;
        }
    }
}
