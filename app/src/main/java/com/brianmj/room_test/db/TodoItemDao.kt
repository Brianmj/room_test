package com.brianmj.room_test.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Query
import com.brianmj.room_test.model.TodoItem

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM todos")
    fun loadAllTodos(): LiveData<List<TodoItem>>

    @Insert(onConflict = IGNORE)
    fun insertTodo(todo: TodoItem)

    @Delete
    fun deleteTodo(todo: TodoItem)
}