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
                conversations!!.add(
                    //The createForRemoteUser() method is to be used when the message was not sent by the user
                    //The createForLocalUser() method is to be used when the message was sent by your user; note that this doesn't require the user's UID
                    FirebaseTextMessage.createForRemoteUser(
                        intext, System.currentTimeMillis(), userId
                    )
                )
                smartReplies()
            }
        }
    }

    private fun smartReplies() {
        FirebaseNaturalLanguage.getInstance().smartReply.suggestReplies(conversations!!.takeLast(1))
            .addOnSuccessListener(object : OnSuccessListener<SmartReplySuggestionResult> {
                override fun onSuccess(result: SmartReplySuggestionResult) {
                    if (result.getStatus() === SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        // The conversation's language isn't supported, so the
                        // the result doesn't contain any suggestions.
                    } else if (result.getStatus() === SmartReplySuggestionResult.STATUS_SUCCESS) {
                        var replyText = ""
                        for (suggestion in result.getSuggestions()) {
                            replyText += suggestion.getText() + ". "
                        }
                        replies!!.text = replyText
                    }
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    // Task failed with an exception
                    // ...
                }
            })

    }

    companion object {
        private val userId = "user0001"
    }

}
