using HyridApp.Models;
using HyridApp.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.CommunityToolkit.Extensions;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HyridApp.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class AddExpensePage : ContentPage
    {
        private DateTime startDate;
        private DateTime endDate;
        private string tripID;

        public AddExpensePage(DateTime startDate, DateTime endDate, string tripID)
        {
            InitializeComponent();
            this.startDate = startDate;
            this.endDate = endDate;
            this.tripID = tripID;
            datePicker = new DatePicker()
            {
                MinimumDate = startDate,
                MaximumDate = endDate
            };
        }

        private async void btn_Save_Clicked(object sender, EventArgs e)
        {
            if (isValidModel())
            {
                DatabaseHelper db = new DatabaseHelper(App.databaseLocation);

                var dateOnly = new DateTime(datePicker.Date.Ticks);
                var timeOnly = new TimeSpan(timePicker.Time.Ticks);

                var newExpense = new Expense()
                {
                    expenseType = entry_type.Text,
                    expenseAmount = entry_amount.Text,
                    expenseDateTime = (dateOnly + timeOnly).Ticks,
                    expenseComment = entry_comment.Text,
                    tripID = tripID
                };
                
                var rows = await db.AddExpense(newExpense);
                this.DisplayToastAsync($"{rows} expense added", 3000);
                await Navigation.PopAsync();
            }
            else
            {
                return;
            }
        }

        public bool isValidModel()
        {
            if (string.IsNullOrEmpty(entry_type.Text))
            {
                entry_type.PlaceholderColor = Color.Red;
                this.DisplayToastAsync("Expense Type Missing", 3000);
                return false;
            }
            
            if (string.IsNullOrEmpty(entry_amount.Text))
            {
                entry_amount.PlaceholderColor = Color.Red;
                this.DisplayToastAsync("Expense Type Missing", 3000);
                return false;
            }

            return true;
        }

    }
}