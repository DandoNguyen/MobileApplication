
using AutoMapper;
using HyridApp.Models;
using HyridApp.Models.CloudModels;
using HyridApp.Views;
using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace HyridApp
{
    public partial class App : Application
    {
        public static string databaseLocation = string.Empty;

        public App()
        {
            InitializeComponent();

            MainPage = new MainTabbedPage();
        }

        public App(string databaseLocation)
        {
            InitializeComponent();

            MainPage = new MainTabbedPage();

            App.databaseLocation = databaseLocation;
        }

        public static IMapper CreateMapper()
        {
            var mapperConfig = new MapperConfiguration(cfg =>
            {
                cfg.CreateMap<Trip, TripAndExpenseList>()
                    .ForMember(dest => dest.ID, opt => opt.MapFrom(src => src.ID.ToString()));
                cfg.CreateMap<Expense, ExpenseItem>()
                    .ForMember(dest => dest.ID, opt => opt.MapFrom(src => src.ID.ToString()));
            });

            return mapperConfig.CreateMapper();
        }

        protected override void OnStart()
        {
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}
