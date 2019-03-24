package com.brianmj.room_test

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.brianmj.room_test.db.AppDatabase
import com.brianmj.room_test.db.DB
import com.brianmj.room_test.model.TodoItem
import com.brianmj.room_test.model.TodoViewModel
import com.brianmj.room_test.view.add.AddTodoActivity
import com.brianmj.room_test.view.common.getViewModel
import com.brianmj.room_test.view.main.RecyclerListAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = getViewModel(TodoViewModel::class)

        setupRecyclerView()
        setupFloatingActionButton()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        with(recyclerViewTodos)
        {
            adapter = RecyclerListAdapter(mutableListOf(), onRecyclerItemClick())

            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            GlobalScope.launch(Dispatchers.Main) {
                val todosLiveData = viewModel.getTodos()
                todosLiveData.observe(this@MainActivity,
                    Observer { todos ->
                        todos?.let {
                            val adapter = (recyclerViewTodos.adapter as RecyclerListAdapter)
                            adapter.setItems(it)
                        }
                    })
            }
        }
    }

    private fun setupFloatingActionButton() {
        fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onRecyclerItemClick(): (TodoItem) -> Unit = { todo ->
        GlobalScope.launch(DB) {
            viewModel.delete(todo)
        }
    }
}
