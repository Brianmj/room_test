package com.brianmj.room_test.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.brianmj.room_test.model.TodoItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

val DB = newSingleThreadContext("DB")

@Database(entities = [TodoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(ctx: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    ctx, AppDatabase::class.java, "AppDatabase"
                )
                    .addCallback(prepopulateCallback(ctx))
                    .build()
            }

            return INSTANCE!!
        }

        private fun prepopulateCallback(ctx: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    populateWithSampleData(ctx)
                }
            }
        }

        private fun populateWithSampleData(ctx: Context) {
            GlobalScope.launch(DB) {
                with(getDatabase(ctx).todoItemDao()) {
                    insertTodo(TodoItem("Create entity"))
                    insertTodo(TodoItem("Add a DAO for data access"))
                    insertTodo(TodoItem("Inherit from RoomDatabase"))
                }
            }
        }
    }

    abstract fun todoItemDao(): TodoItemDao
}