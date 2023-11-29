package com.example.firebase_pertemuan13

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class FetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<Model>
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<Model>()

        getEmployeesData()
    }

    private fun getEmployeesData() {
        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        db = FirebaseFirestore.getInstance()
        val employeesCollection = db.collection("employees")

        employeesCollection.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val empData = document.toObject(Model::class.java)
                    empList.add(empData)
                }

                val mAdapter = Adapter(empList)
                empRecyclerView.adapter = mAdapter

                mAdapter.setOnItemClickListener(object : Adapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@FetchingActivity, DetailsActivity::class.java)

                        //put extras
                        intent.putExtra("empId", empList[position].empId)
                        intent.putExtra("empName", empList[position].empName)
                        intent.putExtra("empAge", empList[position].empAge)
                        intent.putExtra("empSalary", empList[position].empSalary)
                        startActivity(intent)
                    }
                })

                empRecyclerView.visibility = View.VISIBLE
                tvLoadingData.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                // Handle failures
                TODO("Error fetching documents: $exception")
            }
    }
}
