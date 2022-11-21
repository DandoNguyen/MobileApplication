using HyridApp.Models.CloudModels;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace HyridApp.Services
{
    public interface IRestAPIService
    {
        Task<bool> insertMany(List<TripAndExpenseList> listTripDto);
    }
}
