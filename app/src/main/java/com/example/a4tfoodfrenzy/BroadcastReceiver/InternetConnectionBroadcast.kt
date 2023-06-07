package com.example.a4tfoodfrenzy.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import kotlinx.coroutines.*

open class InternetConnectionBroadcast : BroadcastReceiver() {
    var disconnDialog: SweetAlertDialog? = null
    var clickedDialog = false

    override fun onReceive(context: Context?, intent: Intent?) {
        // disconnected from wifi / internet case
        if (!HelperFunctionDB.isConnectedToInternet(context!!)) {
            val mainScope = CoroutineScope(Job() + Dispatchers.Main)

            // disconnected alert dialog for user
            mainScope.launch {
                disconnDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                disconnDialog!!.titleText = "Vui lòng kiểm tra lại đường truyền mạng của bạn"
                disconnDialog!!.setCancelable(false) // show only confirm button
                disconnDialog!!.hideConfirmButton()

                disconnDialog!!.show()

                delay(1200)
                disconnDialog!!.dismiss()
            }
        }
    }

    // register broadcast
    fun registerInternetConnBroadcast(context: Context) {
        context.registerReceiver(
            this@InternetConnectionBroadcast,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    // unregister broadcast
    fun unregisterInternetConnBroadcast(context: Context) {
        // dismiss sweet dialog if showing
        if (disconnDialog != null)
            disconnDialog!!.dismiss()

        context.unregisterReceiver(this@InternetConnectionBroadcast)
    }
}