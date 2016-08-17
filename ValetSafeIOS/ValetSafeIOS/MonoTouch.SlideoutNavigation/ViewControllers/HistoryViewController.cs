using System;

using UIKit;
using System.Collections.Generic;
using Foundation;

namespace ValetSafeIOS
{
	public partial class HistoryViewController : UIViewController
	{
		public HistoryViewController() : base("HistoryViewController", null)
		{
		}

		public override void ViewDidLoad()
		{
			base.ViewDidLoad();
			List<string> time_data = new List<string>() { "8:00 pm 20/05/16","10:00 pm 20/05/16"};
			List<string> name_data = new List<string>() { "Name Nissan Teana","Name Nissan Teana" };
			List<string> price_data = new List<string>() { "SGD 30", "SGD 30" };
			listTableView.Source = new OrderTableViewSource(time_data,name_data,price_data,this);
			// Perform any additional setup after loading the view, typically from a nib.
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
			// Release any cached data, images, etc that aren't in use.
		}

		public void change() { 
		
			OrderDetailViewController odvController = new OrderDetailViewController();
			NavigationController.PushViewController(odvController, true);
		}

		private class OrderTableViewSource : UITableViewSource {

			HistoryViewController hvController;
			private readonly List<string> time_datas;
			private readonly List<string> name_datas;
			private readonly List<string> price_datas;


			public OrderTableViewSource(List<string> t,List<string> n,List<string> p,HistoryViewController hvPController)
			{
				time_datas = t;
				name_datas = n;
				price_datas = p;
				hvController = hvPController;
			}



			public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
			{
				var t = time_datas[indexPath.Row];
				var n = name_datas[indexPath.Row];
				var p = price_datas[indexPath.Row];
				var cell = (OrderTableViewCell)tableView.DequeueReusableCell(OrderTableViewCell.Key);
				if (cell == null)
				{
					cell = OrderTableViewCell.Create();

				}
				cell.time_content = t;
				cell.name_content = n;
				cell.price_content = p;

				return cell;
			}

			public override nfloat GetHeightForRow(UITableView tableView, NSIndexPath indexPath)
			{
				return 180.0f;
			}



			public override nint RowsInSection(UITableView tableview, nint section)
			{
				return time_datas.Count;
			}

			public override void RowSelected(UITableView tableView, NSIndexPath indexPath)
			{
				//base.RowSelected(tableView, indexPath);
				hvController.change();
			}

		}

	}
}


