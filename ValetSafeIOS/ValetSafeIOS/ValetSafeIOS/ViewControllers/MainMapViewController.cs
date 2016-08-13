using System;

using UIKit;

namespace ValetSafeIOS
{
	public partial class MainMapViewController : UIViewController
	{
		public MainMapViewController() : base("MainMapViewController", null)
		{
			Title = "Valet Safe";
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			main_map_economy_btn.Layer.BorderColor = UIColor.Gray.CGColor;
			main_map_economy_btn.Layer.BorderWidth = 1;
			main_map_economy_btn.Layer.MasksToBounds = true;

			main_map_limo_btn.Layer.BorderColor = UIColor.Gray.CGColor;
			main_map_limo_btn.Layer.BorderWidth = 1;
			main_map_limo_btn.Layer.MasksToBounds = true;

			main_map_sport_btn.Layer.BorderColor = UIColor.Gray.CGColor;
			main_map_sport_btn.Layer.BorderWidth = 1;
			main_map_sport_btn.Layer.MasksToBounds = true;

			main_map_next_btn.Layer.CornerRadius = main_map_next_btn.Frame.Width /2 ;

			main_map_next_btn.TouchUpInside += buttonNext;
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}

		private void buttonNext(object sender, EventArgs e)
		{
			NowOrderViewController noController = new NowOrderViewController();
			NavigationController.PushViewController(noController, true);

		}

	}
}


