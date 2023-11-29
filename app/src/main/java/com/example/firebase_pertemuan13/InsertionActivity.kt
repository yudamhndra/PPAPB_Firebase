package com.example.firebase_pertemuan13

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }


    private fun saveEmployeeData() {

        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etEmpAge.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etEmpSalary.error = "Please enter salary"
        }

        val empId = dbRef.push().key!!
        Log.d("InsertionActivity", "Generated empId: $empId")

        val employee = Model(empId, empName, empAge, empSalary)
        Log.d("InsertionActivity", "Created employee: $employee")

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("InsertionActivity", "Data inserted successfully")
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    etEmpName.text.clear()
                    etEmpAge.text.clear()
                    etEmpSalary.text.clear()
                } else {
                    Log.e("InsertionActivity", "Data insertion failed")
                    Toast.makeText(this, "Data insertion failed", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { err ->
                Log.e("InsertionActivity", "Error: ${err.message}", err)
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

        }
    }