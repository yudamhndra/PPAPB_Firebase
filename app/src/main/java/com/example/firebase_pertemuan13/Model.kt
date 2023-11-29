package com.example.firebase_pertemuan13

import com.google.firebase.firestore.PropertyName

data class Model(
    @get:PropertyName("empId") @set:PropertyName("empId")
    var empId: String? = null,

    @get:PropertyName("empName") @set:PropertyName("empName")
    var empName: String? = null,

    @get:PropertyName("empAge") @set:PropertyName("empAge")
    var empAge: String? = null,

    @get:PropertyName("empSalary") @set:PropertyName("empSalary")
    var empSalary: String? = null
)

