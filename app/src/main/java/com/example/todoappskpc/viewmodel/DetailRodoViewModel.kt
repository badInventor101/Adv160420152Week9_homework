package com.example.todoappskpc.viewmodel

import android.app.Application
import android.icu.text.CaseMap.Title
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoappskpc.model.Todo
import com.example.todoappskpc.model.TodoDatabase
import com.example.todoappskpc.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailRodoViewModel(application: Application):AndroidViewModel(application), CoroutineScope {

    val todoLD = MutableLiveData<Todo>()

    private val job = Job()

    fun fetch(uuid:Int){
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }


    fun update(title:String, notes:String, priority:Int, uuid:Int) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().update(title, notes, priority, uuid)

        }
    }

    fun addTodo(todo: Todo){
        launch {
//            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "tododb").build()
            val db = buildDB(getApplication())
            db.todoDao().insertAll(todo)
        }

    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


}