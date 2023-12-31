package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {




    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){
            // inflate receive
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(view)
        }else{
            // inflate sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java){
            // do the stuff for sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message

        }else{
            // do stuff for receive view holder
            val viewHolder = holder as ReceiveViewHolder
//            holder.receiveMessage.text = currentMessage.message
            var options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.HINDI)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
            if (currentMessage.language.equals("HINDI")){

            options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.HINDI)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
            }
            else{
                options = TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.ENGLISH)
                    .setTargetLanguage(TranslateLanguage.HINDI)
                    .build()
            }

            val englishHindiTranslator = Translation.getClient(options)
//            getLifecycle().addObserver(englishHindiTranslator)


//            btnTranslate.setOnClickListener({
//
//                //make sure that our EditText is not empty
//                if (!edtSourceLangText.text.isEmpty()) {
//
//                    progressBar.visibility = View.VISIBLE
//                    btnTranslate.visibility = View.GONE
//
//                    //Downloads the model files required for translation, if they are not already present,
//                    //when the given conditions are met.
                    englishHindiTranslator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            Log.e("Transl", "Download Successful")

                            //Translates the given input from the source language into the target language.
                            englishHindiTranslator.translate(currentMessage.message)
                                .addOnSuccessListener {
                                    //Translation successful
                                    holder.receiveMessage.text = it

                                }
                                .addOnFailureListener {
                                    //Error
                                    holder.receiveMessage.text = currentMessage.message

                                }
                        }
                        .addOnFailureListener {
                            holder.receiveMessage.text = currentMessage.message


                        }
//                }
//            })


        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size

    }

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}