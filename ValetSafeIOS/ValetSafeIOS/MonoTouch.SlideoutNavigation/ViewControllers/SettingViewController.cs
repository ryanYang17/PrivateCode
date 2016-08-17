using System;

using UIKit;

namespace ValetSafeIOS
{
	public partial class SettingViewController : UIViewController
	{
		public SettingViewController() : base("SettingViewController", null)
		{
			
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			setting_sign_out_btn.Layer.CornerRadius = 4;
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}
	}
}


