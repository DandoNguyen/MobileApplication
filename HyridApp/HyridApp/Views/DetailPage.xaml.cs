using HyridApp.DTO;
using HyridApp.Models;
using HyridApp.Models.CloudModels;
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
    public partial class DetailPage : ContentPage
    {
        public string selectedTripID { get; set; }
        public Trip selectedTrip = new Trip();
        public bool isEditing = false;

        public List<expenseDto> listExpenseDto = new List<expenseDto>();
        public DetailPage(string selectedTripID)
        {
            InitializeComponent();
            this.selectedTripID = selectedTripID;
        }

        protected override async void OnAppearing()
        {
            base.OnAppearing();
            await getTripDetail();
        }

        public async Task getTripDetail()
        {
            DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
            selectedTrip = await db.GetTripByID(selectedTripID);
            if (selectedTrip != null)
            {
                entry_name.Text = selectedTrip.tripName;
                entry_dest.Text = selectedTrip.tripDestination;
                entry_start.Date = new DateTime(selectedTrip.tripStartDate);
                entry_end.Date = new DateTime(selectedTrip.tripEndDate);
                entry_risk.Text = (selectedTrip.isRiskAssessmentRequired) ? "Yes" : "No";
                entry_desc.Text = selectedTrip.tripDescription;
            }

            listExpenseDto.Clear();
            var listExpense = await db.GetAllExpense(selectedTripID);
            if (listExpense.Any())
            {
                foreach (var expense in listExpense)
                {
                    var newExpenseDto = new expenseDto()
                    {
                        ID = expense.ID,
                        expenseType = expense.expenseType,
                        expenseAmount = expense.expenseAmount,
                        expenseDateTime = new DateTime(expense.expenseDateTime),
                        expenseComment = expense.expenseComment
                    };
                    listExpenseDto.Add(newExpenseDto);
                }
                lv_listExpenses.ItemsSource = null;
                lv_listExpenses.ItemsSource = listExpenseDto;
                lv_listExpenses.IsVisible = true;
            }
            else
            {
                lv_listExpenses.ItemsSource = null;
                lv_listExpenses.IsVisible = false;
            }
        }

        public void enableEditing(bool isEditting)
        {
            if (isEditting)
            {
                entry_name.IsReadOnly = false;
                entry_dest.IsReadOnly = false;
                entry_start.IsEnabled = true;
                entry_end.IsEnabled = true;
                entry_risk.IsReadOnly = false;
                entry_desc.IsReadOnly = false;

                lv_listExpenses.IsEnabled = false;
                btn_Save.IsVisible = true;
                toobbalBtn_expenseList.IsEnabled = false;
                this.DisplayToastAsync("Editing is Enabled", 3000);
            }
            else
            {
                entry_name.IsReadOnly = true;
                entry_dest.IsReadOnly = true;
                entry_start.IsEnabled = false;
                entry_end.IsEnabled = false;
                entry_risk.IsReadOnly = true;
                entry_desc.IsReadOnly = true;

                lv_listExpenses.IsEnabled = true;
                btn_Save.IsVisible = false;
                toobbalBtn_expenseList.IsEnabled = true;
                this.DisplayToastAsync("Editing is Disabled", 1500);
            }
            
        }

        private async void editButtonClicked(object sender, EventArgs e)
        {
            if (isEditing)
            {
                isEditing = false;
                enableEditing(isEditing);
                await getTripDetail();
            }
            else
            {
                isEditing = true;
                enableEditing(isEditing);
            }
        }

        private async void btn_Save_Clicked(object sender, EventArgs e)
        {
            //save the detail before reset the content
            var existTrip = new Trip();

            if (!isEditValid())
            {
                await this.DisplayToastAsync("Requirements are not met!", 1500);
                return;
            }

            existTrip.ID = Guid.Parse(selectedTripID);
            existTrip.tripName = entry_name.Text;
            existTrip.tripDestination = entry_dest.Text;
            existTrip.tripStartDate = entry_start.Date.Ticks;
            existTrip.tripEndDate = entry_end.Date.Ticks;
            existTrip.isRiskAssessmentRequired = entry_risk.Text.Equals("Yes") ? true : false;
            existTrip.tripDescription = entry_desc.Text;

            DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
            var rows = await db.UpdateTrip(existTrip);
            await this.DisplayToastAsync($"{rows} trip editted", 3000);

            await getTripDetail();
            isEditing = false;
            enableEditing(isEditing);
        }

        private bool isEditValid()
        {
            if (string.IsNullOrEmpty(entry_name.Text) || string.IsNullOrEmpty(entry_dest.Text))
            {
                entry_name.PlaceholderColor = Color.Red;
                entry_dest.PlaceholderColor = Color.Red;
                return false;
            }

            return true;
        }

        private void onEntryTextChanged(object sender, TextChangedEventArgs e)
        {
            ((Entry)sender).PlaceholderColor = Color.Default;
        }

        private async void toobbalBtn_expenseList_Clicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new AddExpensePage(
                new DateTime(selectedTrip.tripStartDate), new DateTime(selectedTrip.tripEndDate), selectedTripID));
        }

        private async void SwipeItem_Clicked(object sender, EventArgs e)
        {
            SwipeItem swipeItem = sender as SwipeItem;
            var selectedExpense = swipeItem.BindingContext as expenseDto;

            bool isConfirm = await DisplayAlert(
                "Delete this Expense",
                $"Are you sure you want to delete this Expense: \n\"{selectedExpense.expenseType}\"?",
                "Confirm", "Cancel");
            if (isConfirm)
            {
                DatabaseHelper db = new DatabaseHelper(App.databaseLocation);
                await db.RemoveExpense(selectedExpense.ID.ToString());
                await getTripDetail();
            }
        }
    }
}