using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Valet_Safe_Android.ValetSafe.UI
{
    [Activity(Label = "RegisterActivity")]
    public class RegisterActivity : Activity
    {

        private int userD = Resource.Drawable.register_user;
        private int passwordD = Resource.Drawable.register_password;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.register);
            EditText userEdit = FindViewById<EditText>(Resource.Id.UsernameEdit);
            EditText passwordEdit = FindViewById<EditText>(Resource.Id.PasswordEdit);
        }

        private void initDrawable()
        {


        }
    }
}