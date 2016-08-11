using System;

using UIKit;

namespace ValetSafeIOS
{
	public partial class OrderNowAndAdvancedViewController : UIViewController
	{
		public OrderNowAndAdvancedViewController() : base("OrderNowAndAdvancedViewController", null)
		{
			Title = "Valet Safe";

		}

		//protected OrderNowAndAdvancedViewController(IntPtr handle) : base(handle)
		//{
		//	// Note: this .ctor should not contain any initialization logic.
		//}


		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			// Perform any additional setup after loading the view, typically from a nib.
			order_now_adv_now_btn.TouchUpInside += buttonNow;
			order_now_adv_adv_btn.TouchUpInside += buttonAdvanced;




		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}


		private void buttonNow(object sender, EventArgs e)
		{
			MainMapViewController mmController = new MainMapViewController();
			NavigationController.PushViewController(mmController, true);

		}

		private void buttonAdvanced(object sender, EventArgs e)
		{
			ReserveOrderSettingViewController rosController = new ReserveOrderSettingViewController();
			NavigationController.PushViewController(rosController, true);

		}
	}
}


