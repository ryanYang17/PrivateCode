using System;
using System.Drawing;
using UIKit;
using Foundation;
using System.Collections.Generic;

namespace ValetSafeIOS
{
	public partial class HistoryViewController : UIViewController
	{
		private List<string> p;
		public HistoryViewController() : base("HistoryViewController", null)
		{
		}
		#region View lifecycle
		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			p = new List<string>() {"gege","hehe" };

			tableView.Source = new listViewSource(p);
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}

		private class listViewSource : UITableViewSource
		{
			private List<string> pp;
			public listViewSource(List<string> dpp) {
				pp = dpp;
			
			}
			public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
			{
				UITableViewCell cell = new UITableViewCell();
				cell.TextLabel.Text = pp[indexPath.Row];
				return cell;
			}

			public override nint RowsInSection(UITableView tableview, nint section)
			{
				return pp.Count;
			}
		}

		#endregion
	}
}


