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
    [Register ("SettingViewController")]
    partial class SettingViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton setting_sign_out_btn { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (setting_sign_out_btn != null) {
                setting_sign_out_btn.Dispose ();
                setting_sign_out_btn = null;
            }
        }
    }
}