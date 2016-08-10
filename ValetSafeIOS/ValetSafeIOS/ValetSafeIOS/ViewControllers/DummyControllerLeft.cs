using System;
using MonoTouch.Dialog;
using UIKit;

namespace ValetSafeIOS
{
	public partial class DummyControllerLeft : DialogViewController
	{
		public DummyControllerLeft()
				: base(UITableViewStyle.Plain, new RootElement(""))
		{
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();

			Root.Add(new Section() {
				new StyledStringElement("Home", () => NavigationController.PushViewController(new LoginViewController(), true)) { TextColor = UIColor.White, BackgroundColor = UIColor.Clear },
				new StyledStringElement("About", () => NavigationController.PushViewController(new LoginViewController(), true)) { TextColor = UIColor.White, BackgroundColor = UIColor.Clear },
				new StyledStringElement("Stuff", () => NavigationController.PushViewController(new LoginViewController(), true)) { TextColor = UIColor.White, BackgroundColor = UIColor.Clear },
				new StyledStringElement("Table", () => NavigationController.PushViewController(new LoginViewController(), true)) { TextColor = UIColor.White, BackgroundColor = UIColor.Clear },
			});

			TableView.SeparatorStyle = UITableViewCellSeparatorStyle.None;

			//var img = new UIImageView(UIImage.FromFile("galaxy.png"));
			//TableView.BackgroundView = img;

		}
	}
}


