package com.example.student_app.data

import com.example.student_app.model.Student

object StudentRepository {
    private val students = mutableListOf(
        Student("1", "Nguyễn Văn An", "B21DCCN001", "Công nghệ thông tin", 3.6),
        Student("2", "Trần Thị Bình", "B21DCCN002", "Điện tử viễn thông", 3.4),
        Student("3", "Lê Hoàng Cường", "B21DCCN003", "Khoa học máy tính", 3.8)
    )

    fun getStudents(): List<Student> = students

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun deleteStudent(id: String) {
        students.removeAll { it.id == id }
    }
}
