using HyridApp.DTO;
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
using System.Linq;
using AutoMapper;
using HyridApp.Models.CloudModels;

namespace HyridApp.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ListTrip : ContentPage
    {
        public List<tripDto> listTripsDto = new List<tripDto>();

        public ListTrip()
        {
            InitializeComponent();
        }

        protected override async void OnAppearing()
        {
            base.OnAppearing();

            await refrestList();

            searchBar_trip.Text = "";
        }

        private void searchBar_trip_SearchButtonPressed(object sender, TextChangedEventArgs e)
        {
            //DisplayAlert("Searh Result", searchBar_trip.Text, "ok");
            lv_listTrips.ItemsSource = listTripsDto.Where(x => x.tripName.StartsWith(e.NewTextValue));
        }

        private async void ResetDatabase(object sender, EventArgs e)
        {
            if (listTripsDto.Any())
            {
                DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
                await db.DropDatabase();
            }
        }

        private async void lv_listTrips_Refreshing(object sender, EventArgs e)
        {
            await refrestList();

            lv_listTrips.EndRefresh();
        }

        private async Task refrestList()
        {
            DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
            var listTrips = await db.GetAllTrips();
            if (listTrips.Any())
            {
                if (listTripsDto.Any())
                    listTripsDto.Clear();
                foreach (var trip in listTrips)
                {
                    var tripDto = new tripDto();

                    tripDto.ID = trip.ID;
                    tripDto.tripName = trip.tripName;
                    tripDto.tripDestination = trip.tripDestination;
                    tripDto.tripDescription = trip.tripDescription;
                    tripDto.tripStartDate = new DateTime(trip.tripStartDate);
                    tripDto.tripEndDate = new DateTime(trip.tripEndDate);
                    tripDto.isRiskAssessmentRequired = trip.isRiskAssessmentRequired;


                    listTripsDto.Add(tripDto);
                }
                if (lv_listTrips.ItemsSource != null)
                {
                    lv_listTrips.ItemsSource = null;
                }
                lv_listTrips.ItemsSource = listTripsDto;
            }
        }

        private async void openDetail(object sender, EventArgs e)
        {
            SwipeItem swipeItem = sender as SwipeItem;
            var selectedTrip = swipeItem.BindingContext as tripDto;

            //await this.DisplayToastAsync(selectedTrip.ID.ToString(), 3000);
            await Navigation.PushAsync(new DetailPage(selectedTrip.ID.ToString()));
        }
        
        private async void deleteTrip(object sender, EventArgs e)
        {
            SwipeItem swipeItem = sender as SwipeItem;
            var selectedTrip = swipeItem.BindingContext as tripDto;

            bool isConfirm = await DisplayAlert(
                "Delete this Trip", 
                $"Are you sure you want to delete this Trip: \n\"{selectedTrip.tripName}\"?", 
                "Confirm", "Cancel");
            if (isConfirm)
            {
                DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
                await db.RemoveTripAsync(selectedTrip.ID.ToString());
                await refrestList();
            }
        }

        private void ToolbarItem_Clicked(object sender, EventArgs e)
        {
            CloudDatabaseHelper cloud = new CloudDatabaseHelper(App.CreateMapper());
            
            // call upload cloud function
        }
    }
}