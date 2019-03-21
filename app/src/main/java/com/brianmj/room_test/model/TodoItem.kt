package com.brianmj.room_test.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoItem(val title: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}