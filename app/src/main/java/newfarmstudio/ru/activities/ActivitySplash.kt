package newfarmstudio.ru.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import newfarmstudio.ru.R

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Loading(this).execute()
    }

    class Loading(context: Context) : AsyncTask<Unit, Unit, Unit>() {

        @SuppressLint("StaticFieldLeak")
        var mContext: Context = context.applicationContext

        override fun doInBackground(vararg params: Unit?) {
            try {
                Thread.sleep(2000)
            }
            catch(ie: InterruptedException) {
                ie.printStackTrace()
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            val intent = Intent(mContext, ActivityHome::class.java)
            mContext.startActivity(intent)
            (mContext as AppCompatActivity).overridePendingTransition(R.anim.open_next, R.anim.close_main)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    .or(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                    .or(View.SYSTEM_UI_FLAG_FULLSCREEN)
                    .or(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }
    }
}
