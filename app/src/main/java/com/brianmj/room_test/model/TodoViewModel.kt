package com.brianmj.room_test.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.brianmj.room_test.db.AppDatabase
import com.brianmj.room_test.db.DB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val dao by lazy {AppDatabase.getDatabase(getApplication()).todoItemDao()}

    //suspend fun loadAllTodos(): MutableList<TodoItem>
     //       = withContext(DB) {dao.loadAllTodos().toMutableList()}

    suspend fun getTodos(): LiveData<List<TodoItem>> =
            withContext(DB){
                dao.loadAllTodos()
            }

    fun add(todo: TodoItem) = GlobalScope.launch(DB) {dao.insertTodo(todo)}
    fun delete(todo: TodoItem) = GlobalScope.launch(DB) {dao.deleteTodo(todo)}
}