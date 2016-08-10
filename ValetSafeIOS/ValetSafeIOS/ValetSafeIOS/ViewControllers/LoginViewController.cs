﻿using System;
using MonoTouch.SlideoutNavigation;
using UIKit;

namespace ValetSafeIOS
{
	public partial class LoginViewController : UIViewController
	{
		UIWindow window;

		public SlideoutNavigationController Menu { get; private set; }


		public LoginViewController() : base("LoginViewController", null)
		{
		}

		protected LoginViewController(IntPtr handle) : base(handle)
		{
			// Note: this .ctor should not contain any initialization logic.
		}


		public override void ViewDidLoad()
		{
			base.ViewDidLoad();


			Menu = new SlideoutNavigationController();
			Menu.MainViewController = new MainNavigationController(new LoginViewController(), Menu);
			Menu.MenuViewController = new MenuNavigationController(new DummyControllerLeft(), Menu) { NavigationBarHidden = true };


			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}

	}
}


