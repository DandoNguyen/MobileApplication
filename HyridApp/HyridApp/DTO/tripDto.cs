using System;
using System.Collections.Generic;
using System.Text;

namespace HyridApp.DTO
{
    public class tripDto
    {
        public Guid ID { get; set; }
        public string tripName { get; set; }
        public string tripDestination { get; set; }
        public DateTime tripStartDate { get; set; }
        public DateTime tripEndDate { get; set; }
        public bool isRiskAssessmentRequired { get; set; }
        public string tripDescription { get; set; }
        public long createdDate { get; set; }
        public long updatedDate { get; set; }
    }
}
