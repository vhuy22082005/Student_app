package com.example.student_app.ui

import androidx.lifecycle.ViewModel
import com.example.student_app.data.StudentRepository
import com.example.student_app.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentViewModel : ViewModel() {
    private val _studentList = MutableStateFlow<List<Student>>(StudentRepository.getStudents())
    val studentList: StateFlow<List<Student>> = _studentList.asStateFlow()

    fun addStudent(name: String, code: String, major: String, gpa: Double) {
        val newStudent = Student(
            id = (_studentList.value.size + 1).toString(),
            name = name,
            studentCode = code,
            major = major,
            gpa = gpa
        )
        StudentRepository.addStudent(newStudent)
        _studentList.value = StudentRepository.getStudents()
    }
}
