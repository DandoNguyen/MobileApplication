using HyridApp.Models;
using SQLite;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;

namespace HyridApp.Services
{
    public class DatabaseHelper
    {
        private string dbPath;
        private SQLiteAsyncConnection db;

        public DatabaseHelper(string databasePath)
        {
            this.dbPath = databasePath;
        }

        private async Task Init()
        {
            if (db != null)
            {
                return;
            }

            var databasePath = Path.Combine(FileSystem.AppDataDirectory, "hybridApp.db");

            db = new SQLiteAsyncConnection(dbPath);

            await db.CreateTableAsync<Trip>();
            await db.CreateTableAsync<Expense>();
        }

        public async Task SearchTrip(string tripName)
        {
            await Init();

            try
            {

            }
            catch (Exception ex)
            {

                Console.WriteLine(ex);
            }
            await db.CloseAsync();
        }

        public async Task<int> AddTripAsync(Trip newTrip)
        {
            await Init();

            try
            {
                return await db.InsertAsync(newTrip);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            await db.CloseAsync();
            return 0;
        }

        public async Task<List<Trip>> GetAllTrips()
        {
            await Init();
            var listTrips = await db.Table<Trip>().ToListAsync();  
            await db.CloseAsync();
            return listTrips;
        }

        public async Task<int> AddExpense(Expense newExpense)
        {
            var rows = 0;
            await Init();

            rows = await db.InsertAsync(newExpense);

            await db.CloseAsync();
            return rows;
        }

        public async Task<List<Expense>> GetAllExpense(string tripID)
        {
            await Init();
            var listExpense = new List<Expense>();
            listExpense = await db.Table<Expense>().Where(x => x.tripID.Equals(tripID)).ToListAsync();
            await db.CloseAsync();
            return listExpense;
        }

        public async Task<Trip> GetTripByID(string tripID)
        {
            var guidID = Guid.Parse(tripID);
            await Init();
            var existTrip = await db.Table<Trip>()
                .Where(x => x.ID.Equals(guidID))
                .FirstOrDefaultAsync();
            await db.CloseAsync();
            return existTrip;
        }

        public async Task RemoveExpense(string expenseID)
        {
            await Init();
            try
            {
                await db.DeleteAsync<Expense>(Guid.Parse(expenseID));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            await db.CloseAsync();
        }

        public async Task RemoveTripAsync(string existTripID)
        {
            await Init();

            try
            {
                await db.DeleteAsync<Trip>(Guid.Parse(existTripID));
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            await db.CloseAsync();
        }

        public async Task<int> UpdateTrip(Trip existTrip)
        {
            var rows = 0;
            await Init();
            try
            {
                rows = await db.UpdateAsync(existTrip);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            await db.CloseAsync();
            return rows;
        }

        public async Task DropDatabase()
        {
            await Init();
            try
            {
                await db.DeleteAllAsync<Expense>();
                await db.DeleteAllAsync<Trip>();
                
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            await db.CloseAsync();
        }
    }
}
