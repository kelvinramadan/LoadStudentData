package com.example.loadstudentdata.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.loadstudentdata.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class StudentRepository(private val context: Context) {

    suspend fun loadStudentsFromAssets(): List<Student> {
        return withContext(Dispatchers.IO) {
            delay(2000)

            val jsonString = context.assets.open("students.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<Student>>() {}.type
            Gson().fromJson(jsonString, type)
        }
    }
}