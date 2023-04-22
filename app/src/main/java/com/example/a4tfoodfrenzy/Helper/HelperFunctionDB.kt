package com.example.a4tfoodfrenzy.Helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class HelperFunctionDB(private var context: Context) {
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()

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
}