package com.example.todoappskpc.view

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoappskpc.R
import com.example.todoappskpc.viewmodel.DetailRodoViewModel


class EditFragment : Fragment() {
    private lateinit var viewModel: DetailRodoViewModel

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



        val txtJudulTodo = view.findViewById<TextView>(R.id.txtJudulTodo)
        txtJudulTodo.text = "Edit Todo"
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.text ="Save"

        val uuid = EditFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        btnAdd.setOnClickListener {
            val txtTitle = view?.findViewById<EditText>(R.id.textInputTitle)
            val txtNotes = view?.findViewById<EditText>(R.id.txtInputNote)
            val radioGroup =  view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            viewModel.update(txtTitle?.text.toString(), txtNotes?.text.toString(), radioButton.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }


        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
//            it.notes  bla2 yerserah
            val txtTitle = view?.findViewById<EditText>(R.id.textInputTitle)
            val txtNotes = view?.findViewById<EditText>(R.id.txtInputNote)
            txtTitle?.setText(it.title)
            txtNotes?.setText(it.notes)

            val high = view?.findViewById<RadioButton>(R.id.radioHigh)
            val med = view?.findViewById<RadioButton>(R.id.radioMedium)
            val low = view?.findViewById<RadioButton>(R.id.radioLow)

            when(it.priority){
                1 -> low?.isChecked = true
                2 -> med?.isChecked = true
                3 -> high?.isChecked = true
            }
        })
    }


}