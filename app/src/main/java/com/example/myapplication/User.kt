package com.example.myapplication

import android.provider.ContactsContract.CommonDataKinds.Email
import org.intellij.lang.annotations.Language

class User {
    var name: String? = null
    var email: String? = null
    var language: String? = null
    var uid: String? = null

    constructor(){}

    constructor(name: String?, email: String?, uid: String?,language: String?){
        this.name = name
        this.email = email
        this.uid = uid
        this.language=language
    }
}