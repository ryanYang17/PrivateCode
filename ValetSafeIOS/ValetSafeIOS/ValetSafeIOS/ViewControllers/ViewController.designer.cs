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
    [Register ("ViewController")]
    partial class ViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton login_btn_signin { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton login_btn_signup { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (login_btn_signin != null) {
                login_btn_signin.Dispose ();
                login_btn_signin = null;
            }

            if (login_btn_signup != null) {
                login_btn_signup.Dispose ();
                login_btn_signup = null;
            }
        }
    }
}