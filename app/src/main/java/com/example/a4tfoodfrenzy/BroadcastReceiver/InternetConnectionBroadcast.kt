package com.example.a4tfoodfrenzy.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB

open class InternetConnectionBroadcast : BroadcastReceiver() {
    private var disconnDialog : SweetAlertDialog? = null

    override fun onReceive(context : Context?, intent : Intent?) {
        // disconnected from wifi / internet case
        if(!HelperFunctionDB.isConnectedToInternet(context!!)){
            // disconnected alert dialog for user
            disconnDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            disconnDialog!!.titleText = "Vui lòng kiểm tra lại đường truyền mạng của bạn"
            disconnDialog!!.setCancelable(false) // show only confirm button

            disconnDialog!!.show()

            disconnDialog!!.setConfirmClickListener {
                disconnDialog!!.dismiss()
            }
        }
    }

    // register broadcast
    fun registerInternetConnBroadcast(context : Context){
        context.registerReceiver(this@InternetConnectionBroadcast, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    // unregister broadcast
    fun unregisterInternetConnBroadcast(context: Context){
        // dismiss sweet dialog if showing
        if(disconnDialog != null && disconnDialog!!.isShowing)
            disconnDialog!!.dismiss()

        context.unregisterReceiver(this@InternetConnectionBroadcast)
    }
}