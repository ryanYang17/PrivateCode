﻿using System;

using UIKit;

namespace ValetSafeIOS
{
	public partial class OrderNowAndAdvancedViewController : UIViewController
	{
		public OrderNowAndAdvancedViewController() : base("OrderNowAndAdvancedViewController", null)
		{
			Title = "Home";

		}

		protected OrderNowAndAdvancedViewController(IntPtr handle) : base(handle)
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

