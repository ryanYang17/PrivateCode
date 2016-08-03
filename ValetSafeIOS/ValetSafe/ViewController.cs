using System;
using System.Drawing;
using UIKit;

namespace ValetSafe
{
	public partial class ViewController : UIViewController
	{
		protected ViewController(IntPtr handle) : base(handle)
		{
			// Note: this .ctor should not contain any initialization logic.
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			UIView vv = new UIView();
			vv.Frame = new RectangleF(0,10,320,250);
			this.View.AddSubview(vv);
			vv.BackgroundColor = UIColor.LightGray;
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}
	}
}

