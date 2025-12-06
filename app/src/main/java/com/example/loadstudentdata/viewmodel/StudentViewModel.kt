package com.example.loadstudentdata.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loadstudentdata.model.Student
import com.example.loadstudentdata.repository.StudentRepository
import com.example.loadstudentdata.util.NotificationHelper
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudentRepository(application)
    private val notificationHelper = NotificationHelper(application)

    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadStudents() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val studentList = repository.loadStudentsFromAssets()
                _students.value = studentList

                notificationHelper.showNotification(
                    "Data Berhasil Dimuat",
                    "Berhasil memuat ${studentList.size} data mahasiswa"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}