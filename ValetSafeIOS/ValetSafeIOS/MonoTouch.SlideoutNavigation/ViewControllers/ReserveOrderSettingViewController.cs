using System;

using UIKit;

namespace ValetSafeIOS
{
	public partial class ReserveOrderSettingViewController : UIViewController
	{
		public ReserveOrderSettingViewController() : base("ReserveOrderSettingViewController", null)
		{
			Title = "Valet Safe";
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}
	}
}


