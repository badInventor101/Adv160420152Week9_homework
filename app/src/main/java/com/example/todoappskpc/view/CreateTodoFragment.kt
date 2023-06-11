package com.example.todoappskpc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoappskpc.R
import com.example.todoappskpc.model.Todo
import com.example.todoappskpc.viewmodel.DetailRodoViewModel


class CreateTodoFragment : Fragment() {
    private lateinit var viewModel:DetailRodoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailRodoViewModel::class.java)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val txtTitle = view.findViewById<EditText>(R.id.textInputTitle)
            val txtNotes = view.findViewById<EditText>(R.id.txtInputNote)
            val radioGroup =  view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val checkBx = view.findViewById<CheckBox>(R.id.checkTask)
            val todo = Todo(
                txtTitle.text.toString(),
                txtNotes.text.toString(),
                radioButton.tag.toString().toInt(),
//                checkBx.isChecked.toString().toInt()



            )
            viewModel.addTodo(todo)

            Toast.makeText(view.context, "Todo created", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

}