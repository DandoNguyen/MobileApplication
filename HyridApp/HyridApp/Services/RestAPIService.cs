using HyridApp.Models.CloudModels;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace HyridApp.Services
{
    internal class RestAPIService : IRestAPIService
    {
        private const string CloudDatabaseUrl = "https://data.mongodb-api.com/app/data-iwkiu/endpoint/data/v1";
        private const string ApiKey = "7Ov4yVeMb7eRSjgVVCXKy0lZdSMfO6aod8kdo8uutMdQaSqq1cOIwMA9JX5l8M0T";

        private const string dataSource = "Cluster1";
        private const string database = "HybridApp";
        private const string collection = "TripAndExpense";

        private HttpClient client;

        private class defaultBody
        {
            public string dataSource { get; set; }
            public string database { get; set; }
            public string collection { get; set; }
        }

        private class insertOneBody : defaultBody
        {
            public TripAndExpenseList document { get; set; }
        }

        private class insertManyBody : defaultBody
        {
            public IEnumerable<TripAndExpenseList> documents { get; set; }
        }

        public async Task<bool> insertMany(List<TripAndExpenseList> listTripDto)
        {
            var enpoint = "/action/insertOne";

            var insertOneBody = new insertManyBody()
            {
                dataSource = dataSource,
                database = database,
                collection = collection,
                documents = listTripDto,
            };
            var stringContent = new StringContent(JsonConvert.SerializeObject(insertOneBody));

            client = new HttpClient();
            client.DefaultRequestHeaders.Add("Content-Type", "application/json");
            client.DefaultRequestHeaders.Add("api-key", ApiKey);
            var response = await client.PostAsync(CloudDatabaseUrl + enpoint, stringContent);

            if (response.IsSuccessStatusCode)
            {
                return true;
            }
            return false;
        }
    }
}
