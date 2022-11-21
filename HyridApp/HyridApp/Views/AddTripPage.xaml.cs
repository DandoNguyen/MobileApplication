using HyridApp.Models;
using HyridApp.Services;
using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.CommunityToolkit.Extensions;
using Xamarin.CommunityToolkit.UI.Views.Options;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HyridApp.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class AddTripPage : ContentPage
    {
        Trip newTrip = new Trip();
        public AddTripPage()
        {
            InitializeComponent();
            
            dp_startDate = new DatePicker()
            {
                MinimumDate = DateTime.MinValue,
                MaximumDate = DateTime.MaxValue,
                Date = DateTime.Today
            };
        }

        private void CheckBox_CheckedChanged(object sender, CheckedChangedEventArgs e)
        {
            newTrip.isRiskAssessmentRequired = e.Value;
        }

        private void onEntryTextChanged(object sender, TextChangedEventArgs e)
        {
            ((Entry)sender).PlaceholderColor = Color.Default;
        }

        private async void btn_Save_Clicked(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(entry_tripName.Text))
            {
                entry_tripName.PlaceholderColor = Color.Red;
                await this.DisplayToastAsync("Trip's Name must not be empty", 3000);
                return;
            }
            
            if (string.IsNullOrEmpty(entry_tripDestination.Text))
            {
                entry_tripDestination.PlaceholderColor = Color.Red;
                await this.DisplayToastAsync("Destination must not be empty", 3000);
                return;
            }

            if (dp_startDate.Date.Equals(DateTime.MinValue))
            {
                dp_startDate.TextColor = Color.Red;
                 await this.DisplayToastAsync("Start Date must not be empty", 3000);
                return;
            }

            newTrip.tripName = entry_tripName.Text;
            newTrip.tripDestination = entry_tripDestination.Text;
            newTrip.tripDescription = entry_tripDescription.Text;

            newTrip.tripStartDate = dp_startDate.Date.Ticks;
            newTrip.tripEndDate = dp_endDate.Date.Ticks;

            DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
            try
            {
                var rows = await db.AddTripAsync(newTrip);
                await this.DisplayToastAsync($"{rows} trip(s) added", 5000);
            }
            catch (Exception ex)
            {
                await this.DisplayToastAsync(ex.Message, 5000);
            }
        }

    }
}