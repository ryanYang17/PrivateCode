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


