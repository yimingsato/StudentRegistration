package com.example.studentregistration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentViewModel(private val dao: StudentDatabase) : ViewModel(){
    var students =  MutableLiveData<List<Student>>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allStudents = dao.getAllStudents()
            if (allStudents != null) {
                students.postValue(allStudents)
            }
        }
    }

    fun insertStudent(student:Student)
    {
        var listStudents = students.value
        var listOfStudents = mutableListOf<Student>()
        if (listStudents != null) {
            listOfStudents.addAll(listStudents)
        }
        listOfStudents.add(student)
        students.postValue(listOfStudents)
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertStudent(student)
        }
    }

    fun updateStudent(student:Student) {
        var listStudents = students.value
        var listOfStudents = mutableListOf<Student>()
        if (listStudents != null) {
            listOfStudents.addAll(listStudents)
        }
        var idx = -1
        for (i in listOfStudents.indices) {
            if (listOfStudents[i].id == student.id) {
                idx = i
                break
            }
        }
        if (idx >= 0) {
            listOfStudents.removeAt(idx)
            listOfStudents.add(student)
        }
        students.postValue(listOfStudents)

        viewModelScope.launch (Dispatchers.IO) {
            dao.updateStudent(student)
        }
    }

    fun deleteStudent(student:Student) {
        var listStudents = students.value
        var listOfStudents = mutableListOf<Student>()
        if (listStudents != null) {
            listOfStudents.addAll(listStudents)
        }
        var idx = -1
        for (i in listOfStudents.indices) {
            if (listOfStudents[i].id == student.id) {
                idx = i
                break
            }
        }
        if (idx >= 0) {
            listOfStudents.removeAt(idx)
        }
        students.postValue(listOfStudents)

        viewModelScope.launch (Dispatchers.IO) {
            dao.deleteStudent(student)
        }
    }
}