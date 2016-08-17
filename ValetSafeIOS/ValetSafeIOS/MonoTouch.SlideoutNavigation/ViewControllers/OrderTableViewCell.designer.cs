// WARNING
//
// This file has been generated automatically by Xamarin Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace ValetSafeIOS
{
    [Register ("OrderTableViewCell")]
    partial class OrderTableViewCell
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel name { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel price { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel time { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (name != null) {
                name.Dispose ();
                name = null;
            }

            if (price != null) {
                price.Dispose ();
                price = null;
            }

            if (time != null) {
                time.Dispose ();
                time = null;
            }
        }
    }
}