using System;

using UIKit;

namespace ValetSafeIOS
{
	public partial class DummyViewController : UIViewController
	{
		public DummyViewController() : base("DummyViewController", null)
		{
		}
		protected DummyViewController(IntPtr handle) : base(handle)
		{
			// Note: this .ctor should not contain any initialization logic.
		}

		public override void ViewDidLoad()
		{
			// Perform any additional setup after loading the view, typically from a nib.
			base.ViewDidLoad();
			dummy_payment_btn.TouchUpInside += buttonPayment;
			dummy_processing_btn.TouchUpInside += buttonProcessing;
			dummy_history_btn.TouchUpInside += buttonHistory;
			dummy_settings_btn.TouchUpInside += buttonSettings;
			dummy_help_btn.TouchUpInside += buttonHelp;
			dummy_about_btn.TouchUpInside += buttonAbout;

		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}


		private void buttonPayment(object sender, EventArgs e) 
		{
			OrderNowAndAdvancedViewController onaController = new OrderNowAndAdvancedViewController();
			NavigationController.PushViewController(onaController, true);
		
		}

		private void buttonProcessing(object sender, EventArgs e)
		{
			OrderNowAndAdvancedViewController onaController = new OrderNowAndAdvancedViewController();
			NavigationController.PushViewController(onaController, true);

		}

		private void buttonHistory(object sender, EventArgs e)
		{
			HistoryViewController hvController = new HistoryViewController();
			NavigationController.PushViewController(hvController, true);

		}

		private void buttonSettings(object sender, EventArgs e)
		{
			OrderNowAndAdvancedViewController onaController = new OrderNowAndAdvancedViewController();
			NavigationController.PushViewController(onaController, true);

		}

		private void buttonHelp(object sender, EventArgs e)
		{
			OrderNowAndAdvancedViewController onaController = new OrderNowAndAdvancedViewController();
			NavigationController.PushViewController(onaController, true);

		}

		private void buttonAbout(object sender, EventArgs e)
		{
			OrderNowAndAdvancedViewController onaController = new OrderNowAndAdvancedViewController();
			NavigationController.PushViewController(onaController, true);

		}





	}
}


