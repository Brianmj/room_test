package com.brianmj.room_test.view.main

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brianmj.room_test.R
import com.brianmj.room_test.model.TodoItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.todo_item.*

class RecyclerListAdapter(private val items: MutableList<TodoItem>,
                          private val onItemCheckboxClicked: (TodoItem) -> Unit) : RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.todo_item,
                p0, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(items[p1])
    }

    fun setItems(items: List<TodoItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(item: TodoItem){
            cbTodoDone.isChecked = false
            tvTodoTitle.text = item.title

            cbTodoDone.setOnCheckedChangeListener{_, _ ->
                onItemCheckboxClicked(item)
            }
        }
    }
}