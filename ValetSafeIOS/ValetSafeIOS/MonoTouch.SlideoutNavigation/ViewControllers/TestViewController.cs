using System;
using MonoTouch.SlideoutNavigation;
using MonoTouch.Dialog;
using UIKit;

namespace ValetSafeIOS
{
	public partial class TestViewController : UIViewController
	{

		UIWindow window;

		public SlideoutNavigationController Menu { get; private set; }
		public TestViewController() : base("TestViewController", null)
		{
		}
		protected TestViewController(IntPtr handle) : base(handle)
		{
			// Note: this .ctor should not contain any initialization logic.
		}
		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			window = new UIWindow(UIScreen.MainScreen.Bounds);

			Menu = new SlideoutNavigationController();
			Menu.MainViewController = new MainNavigationController(new LoginViewController(), Menu);
			Menu.MenuViewController = new MenuNavigationController(new DummyControllerLeft(), Menu) { NavigationBarHidden = true };

			window.RootViewController = Menu;
			window.MakeKeyAndVisible();

			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}
	}
}


