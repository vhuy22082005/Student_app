package com.example.student_app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.student_app.databinding.ActivityMainBinding
import com.example.student_app.databinding.DialogAddStudentBinding
import com.example.student_app.ui.StudentAdapter
import com.example.student_app.ui.StudentViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: StudentViewModel by viewModels()
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.btnAddStudent.setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(emptyList())
        binding.recyclerViewStudents.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = studentAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.studentList.collect { students ->
                    studentAdapter.updateData(students)
                }
            }
        }
    }

    private fun showAddStudentDialog() {
        val dialogBinding = DialogAddStudentBinding.inflate(layoutInflater)

        AlertDialog.Builder(this)
            .setTitle("Thêm sinh viên mới")
            .setView(dialogBinding.root)
            .setPositiveButton("Thêm") { _, _ ->
                val name = dialogBinding.etName.text.toString()
                val code = dialogBinding.etCode.text.toString()
                val major = dialogBinding.etMajor.text.toString()
                val gpa = dialogBinding.etGpa.text.toString().toDoubleOrNull() ?: 3.5
                if (name.isNotBlank() && code.isNotBlank()) {
                    viewModel.addStudent(name, code, major, gpa)
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
