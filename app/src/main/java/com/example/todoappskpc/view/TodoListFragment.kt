package com.example.todoappskpc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappskpc.R
import com.example.todoappskpc.model.Todo
import com.example.todoappskpc.viewmodel.ListTodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoListFragment : Fragment() {
    private lateinit var viewModel: ListTodoViewModel
    private val adapter = TodoListAdapter(arrayListOf(), {item -> viewModel.uncheckTask(item.is_done,item.uuid)})


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()

        val recViewTodo = view.findViewById<RecyclerView>(R.id.recViewTodoo)
        recViewTodo.layoutManager = LinearLayoutManager(context)
        recViewTodo.adapter = adapter

        val fabAddTodo = view.findViewById<FloatingActionButton>(R.id.fabAddTodo)
        fabAddTodo.setOnClickListener {
            // blm
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)

        }
        obseerveViewModel()

    }

    fun obseerveViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            adapter.updateTodoList(it as ArrayList<Todo>)
            val txtEmpty = view?.findViewById<TextView>(R.id.txtEmpty)

            if(it.isEmpty()){
                txtEmpty?.visibility = View.VISIBLE

            }
            else{
                txtEmpty?.visibility = View.GONE

            }

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }


}