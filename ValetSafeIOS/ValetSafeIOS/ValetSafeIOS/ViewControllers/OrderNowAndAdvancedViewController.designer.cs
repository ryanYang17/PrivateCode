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
    [Register ("OrderNowAndAdvancedViewController")]
    partial class OrderNowAndAdvancedViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton order_now_adv_adv_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIImageView order_now_adv_adv_img { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton order_now_adv_now_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIImageView order_now_adv_now_img { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (order_now_adv_adv_btn != null) {
                order_now_adv_adv_btn.Dispose ();
                order_now_adv_adv_btn = null;
            }

            if (order_now_adv_adv_img != null) {
                order_now_adv_adv_img.Dispose ();
                order_now_adv_adv_img = null;
            }

            if (order_now_adv_now_btn != null) {
                order_now_adv_now_btn.Dispose ();
                order_now_adv_now_btn = null;
            }

            if (order_now_adv_now_img != null) {
                order_now_adv_now_img.Dispose ();
                order_now_adv_now_img = null;
            }
        }
    }
}