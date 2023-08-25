package com.example.studying

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(
    private val db: FirebaseDatabase
) : ViewModel() {

    fun fetchMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchWithCallbackFlow().collect {
                Log.d("attadag", "$it")
            }
        }
    }

    private fun fetchWithCallbackFlow(): Flow<List<MessageCloud>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<MessageCloud>()
                for (message in snapshot.children) {
                    val mes = message.getValue(MessageCloud::class.java)
                    messages.add(mes!!)
                }

                trySend(messages)
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        db.reference.child("messages").addValueEventListener(listener)

        awaitClose {
            db.reference.removeEventListener(listener)
        }
    }

    fun createMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = db.reference.push().key ?: UUID.randomUUID().toString()
            db.reference.child("messages").child(id).setValue(MessageCloud(id, message))
        }
    }
}

data class MessageCloud(
    val id: String = "",
    val message: String = ""
)