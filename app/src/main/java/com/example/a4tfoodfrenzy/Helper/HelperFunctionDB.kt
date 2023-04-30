package com.example.a4tfoodfrenzy.Helper

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.View.LoginActivity
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class HelperFunctionDB(private var context: Context) {
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    private lateinit var pDialog:SweetAlertDialog
    companion object{
        var sweetAlertDialog:SweetAlertDialog?=null
    }

    fun uploadImageToCloudStorage(drawableName: String, pathStorage: String) {
        // Tạo đường dẫn đến tệp tin trên Cloud Storage
        val filename = "$drawableName.png"
        val storageRef = storage.reference.child(pathStorage).child(filename)

        // Tải hình ảnh từ thư mục drawable
        val drawableId = context.resources.getIdentifier(drawableName, "drawable", context.packageName)
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)

        // Nén hình ảnh và lưu trữ dữ liệu trên Cloud Storage
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)

        storageRef.putBytes(baos.toByteArray())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("Storage", "Upload successful")
                } else {
                    // Tải lên thất bại, do something
                }
            }
    }
    fun findSlotIdEmptyInCollection(collectionName: String, callback: (Long) -> Unit) {
        val collection = db.collection(collectionName)
        var idSlot: Long = 1
        collection.orderBy("id", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.getLong("id")

                    if (id != null) {
                        if (idSlot != id) {
                            break
                        }
                        else {
                            idSlot++
                        }
                    }
                }
                callback(idSlot)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
                callback(-1)
            }
    }
    fun showWarningAlert(
        title: String,
        message: String,
        onConfirmed: (Boolean) -> Unit

    ){
        var shouldDelete = false
        val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog.setTitleText(title)
        sweetAlertDialog.setContentText(message)
        sweetAlertDialog.setConfirmButton("Có") {
            it.dismissWithAnimation()
            onConfirmed(true)

        }
        sweetAlertDialog.setCancelButton("Không") {
            it.dismissWithAnimation()
            onConfirmed(false)
        }
        sweetAlertDialog.show()
    }
    fun showRemindAlert(title: String) {
        val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
        sweetAlertDialog.setTitleText(title)
        sweetAlertDialog.setCustomImage(R.drawable.input)
        sweetAlertDialog.show();
    }
    fun showSuccessAlert(
        title: String,
        message: String,
        callback: (Boolean) -> Unit
    ) {
        sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
        sweetAlertDialog!!.setTitleText(title)
        sweetAlertDialog!!.setContentText(message)
        sweetAlertDialog!!.setConfirmButton("OK") {
            callback(true)
            it.dismiss()
            it.cancel()
        }
        sweetAlertDialog!!.setCancelable(false)
        sweetAlertDialog!!.show()

    }

    fun showErrorAlert(
        title: String,
        message: String,
        callback: (Boolean) -> Unit
    ) {
        sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
        sweetAlertDialog!!.setTitleText(title)
        sweetAlertDialog!!.setContentText(message)
        sweetAlertDialog!!.setConfirmButton("OK") {
            it.dismiss()
            callback(true)
        }
        sweetAlertDialog!!.setCancelable(false)
        sweetAlertDialog!!.show()
    }
    fun showLoadingAlert()
    {
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#FFB200")
        pDialog.titleText = "Vui lòng đợi..."
        pDialog.setCancelable(false)
        pDialog.show()
    }
    fun stopLoadingAlert()
    {
        pDialog.dismiss()
    }

    fun uploadImage(uri:Uri,pathName:String,pathStorage: String)
    {

        val storageRef = storage.reference.child(pathStorage).child("$pathName.png")
        val uploadTask = storageRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Tải lên thành công
        }
        .addOnFailureListener { exception ->
            // Xử lý lỗi
        }

    }
}