package com.example.myapplication

class Message {
    var message: String? = null
    var senderId: String? = null
    var language:String="ENGLISH"
    constructor(){}
    constructor(message: String?, senderId: String?){
        this.message = message
        this.senderId = senderId
    }
}