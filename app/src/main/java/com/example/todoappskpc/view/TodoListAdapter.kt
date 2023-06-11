package com.example.todoappskpc.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappskpc.R
import com.example.todoappskpc.model.Todo

class TodoListAdapter(val todos:ArrayList<Todo>, val adapter: (Todo) -> Unit):RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(val view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val checkTask =  holder.view.findViewById<CheckBox>(R.id.checkTask) /// 1
        var chk_cond = 0
        checkTask.text = todos[position].title
        if (todos[position].is_done == 0){
            checkTask.isChecked = false
            chk_cond = 0

        }
        else{
            checkTask.isChecked = true
            chk_cond = 1

        }


        checkTask.setOnCheckedChangeListener { compoundButton, b ->
            if (chk_cond == 0){
                todos[position].is_done = 1
                chk_cond = 1
                checkTask.isChecked = true

            }
            else{
                todos[position].is_done = 0
                chk_cond = 0
                checkTask.isChecked = false

            }
            //todos[position].is_done = 0

            adapter(todos[position]) // mengirim object todo_ berdasarkan position ke TodoListFragment

        }
        val imgEdit = holder.view.findViewById<ImageView>(R.id.imageEdit)
        imgEdit.setOnClickListener {
            val uuid = todos[position].uuid
            val action = TodoListFragmentDirections.actionEditFragment(uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }


    fun updateTodoList(newTodos:ArrayList<Todo>){
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }
}