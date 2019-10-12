package com.andreh.chatsuggestion

import com.andreh.chatsuggestion.R
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseTextMessage
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestion
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestionResult

import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    internal var send: Button? = null
    internal var inputText: EditText? = null
    internal var textin: TextView? = null
    internal var replies: TextView? = null
    internal var conversations: MutableList<FirebaseTextMessage>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        send = findViewById(R.id.send)
        inputText = findViewById(R.id.inputText)
        textin = findViewById(R.id.textin)
        replies = findViewById(R.id.replies)
        conversations = ArrayList<FirebaseTextMessage>()
        send!!.setOnClickListener {
            val intext = inputText!!.text.toString()
            if (!TextUtils.isEmpty(intext)) {
                textin!!.text = intext
                inputText!!.setText("")
                //add chat to conversation

                //get 3 suggested messages
                smartReplies()
            }
        }
    }

    private fun smartReplies() {


    }

    companion object {
        private val userId = "user0001"
    }

}
