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
    [Register ("DummyViewController")]
    partial class DummyViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton dummy_about_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton dummy_help_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton dummy_history_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton dummy_payment_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton dummy_processing_btn { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton dummy_settings_btn { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (dummy_about_btn != null) {
                dummy_about_btn.Dispose ();
                dummy_about_btn = null;
            }

            if (dummy_help_btn != null) {
                dummy_help_btn.Dispose ();
                dummy_help_btn = null;
            }

            if (dummy_history_btn != null) {
                dummy_history_btn.Dispose ();
                dummy_history_btn = null;
            }

            if (dummy_payment_btn != null) {
                dummy_payment_btn.Dispose ();
                dummy_payment_btn = null;
            }

            if (dummy_processing_btn != null) {
                dummy_processing_btn.Dispose ();
                dummy_processing_btn = null;
            }

            if (dummy_settings_btn != null) {
                dummy_settings_btn.Dispose ();
                dummy_settings_btn = null;
            }
        }
    }
}