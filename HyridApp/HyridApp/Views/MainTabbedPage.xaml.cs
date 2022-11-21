using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.PlatformConfiguration;
using Xamarin.Forms.PlatformConfiguration.AndroidSpecific;
using Xamarin.Forms.Xaml;
using TabbedPage = Xamarin.Forms.TabbedPage;

namespace HyridApp.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MainTabbedPage : TabbedPage
    {
        public MainTabbedPage()
        {
            InitializeComponent();
            On<Android>().SetToolbarPlacement(ToolbarPlacement.Bottom);

            this.BarBackgroundColor = Color.LightBlue;

            NavigationPage listTripNavigationPage = new NavigationPage(new ListTrip());
            listTripNavigationPage.Title = "My Trips";
            listTripNavigationPage.IconImageSource = new FontImageSource()
            {
                FontFamily = "FontAwesome",
                Glyph = "\uf15c"
            };
            Children.Add(listTripNavigationPage);

            Children.Add(new AddTripPage()
            {
                Title = "Add New Trip",
                IconImageSource = new FontImageSource()
                {
                    FontFamily = "FontAwesome",
                    Glyph = "\uf0fe"
                }

            });

            Children.Add(new AboutPage()
            {
                Title = "About",
                IconImageSource = new FontImageSource()
                {
                    FontFamily = "FontAwesome",
                    Glyph = "\uf2bd"
                }
            });
        }
    }
}