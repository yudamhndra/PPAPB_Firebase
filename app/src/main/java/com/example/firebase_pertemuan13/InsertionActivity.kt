package com.example.firebase_pertemuan13

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSave)

        db = FirebaseFirestore.getInstance()

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()

        if (empName.isEmpty() || empAge.isEmpty() || empSalary.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show()
            return
        }

        val employee = hashMapOf(
            "name" to empName,
            "age" to empAge,
            "salary" to empSalary
        )

        db.collection("employees")
            .add(employee)
            .addOnSuccessListener { documentReference ->
                Log.d("InsertionActivity", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                clearEditTextFields()
            }
            .addOnFailureListener { e ->
                Log.e("InsertionActivity", "Error adding document", e)
                Toast.makeText(this, "Data insertion failed", Toast.LENGTH_LONG).show()
            }
    }

    private fun clearEditTextFields() {
        etEmpName.text.clear()
        etEmpAge.text.clear()
        etEmpSalary.text.clear()
    }

}
