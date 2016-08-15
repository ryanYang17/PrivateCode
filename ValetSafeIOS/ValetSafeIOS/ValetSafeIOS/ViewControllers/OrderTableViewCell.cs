using System;

using Foundation;
using UIKit;

namespace ValetSafeIOS
{
	public partial class OrderTableViewCell : UITableViewCell
	{
		public static readonly UINib Nib = UINib.FromName("OrderTableViewCell", NSBundle.MainBundle);
		public static readonly NSString Key = new NSString("OrderTableViewCell");
		public string time_content { get; set; }
		public string name_content { get; set; }
		public string price_content { get; set; }


		static OrderTableViewCell()
		{
			Nib = UINib.FromName("OrderTableViewCell", NSBundle.MainBundle);
		}

		protected OrderTableViewCell(IntPtr handle) : base(handle)
		{
			// Note: this .ctor should not contain any initialization logic.
		}

		public static OrderTableViewCell Create()
		{
			return (OrderTableViewCell)Nib.Instantiate(null, null)[0];
		}

		public override void LayoutSubviews()
		{
			base.LayoutSubviews();

			this.time.Text = time_content;
			this.name.Text = name_content;
			this.price.Text = price_content;
			//this.ConferenceStart.Text = Model.StartDate.ToShortDateString();
			//this.ConferenceDescription.Text = Model.Description;
		}

	}
}
