using AutoMapper;
using HyridApp.Models;
using HyridApp.Models.CloudModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;

namespace HyridApp.Services
{
    public class CloudDatabaseHelper
    {
        private readonly HttpClient httpClient;
        private readonly IMapper mapper;

        public CloudDatabaseHelper(IMapper mapper)
        {
            this.mapper = mapper;
        }

        public async void UploadTrips(List<Trip> listTrips)
        {
            DatabaseHelper db = new DatabaseHelper(App.databaseLocation);

            var listTripsDto = new List<TripAndExpenseList>();
            foreach (var trip in listTrips)
            {
                var newTripDto = new TripAndExpenseList();
                newTripDto = mapper.Map<TripAndExpenseList>(trip);
                listTripsDto.Add(newTripDto);
                
                foreach (var tripDto in listTripsDto)
                {
                    var listExpense = await db.GetAllExpense(tripDto.ID);

                    var listExpenseDto = new List<ExpenseItem>();
                    foreach (var expense in listExpense)
                    {
                        var expenseDto = mapper.Map<ExpenseItem>(expense);
                        listExpenseDto.Add(expenseDto);
                    }

                    if (listExpenseDto.Any())
                    {
                        tripDto.ListExpenses = listExpenseDto;
                    }
                }
            }

            if (listTripsDto.Any())
            {
                // Do something
            }
        }
    }
}
