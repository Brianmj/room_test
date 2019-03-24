package com.brianmj.room_test.view.add

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brianmj.room_test.R
import com.brianmj.room_test.db.DB
import com.brianmj.room_test.model.TodoItem
import com.brianmj.room_test.model.TodoViewModel
import com.brianmj.room_test.view.common.getViewModel
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTodoActivity : AppCompatActivity() {

    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        todoViewModel = getViewModel(TodoViewModel::class)
        setupListeners()
    }

    fun setupListeners(){
        btnAddTodo.setOnClickListener {
            val newTodo = etNewTodo.text.toString()
            GlobalScope.launch(DB){
                todoViewModel.add(TodoItem(newTodo))
                finish()
            }
        }
    }
}
