package com.example.todoappskpc.viewmodel

import android.app.Application
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

class ListTodoViewModel (application: Application)
    : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var job = Job()



    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())

//            val db = Room.databaseBuilder((getApplication()), TodoDatabase::class.java, "tododb").build() // di panggil dari file sblmnya
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }

    fun clearTask(todo:Todo){
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "tododb").build() // di panggil dari file sblmnya
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
    fun uncheckTask(is_done:Int ,uuid:Int){
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "tododb").build() // di panggil dari file sblmnya
            db.todoDao().updateCheckList(is_done,uuid)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }


}