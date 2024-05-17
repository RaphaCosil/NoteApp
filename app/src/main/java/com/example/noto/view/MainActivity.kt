package com.example.noto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noto.NoteViewModel
import com.example.noto.data.dto.NoteData
import com.example.noto.data.remoteRepository.NoteRepositoryImpl
import com.example.noto.databinding.ActivityMainBinding
import com.example.noto.domain.useCase.GetAllNotesUseCase
import com.example.noto.domain.useCase.GetNoteUseCase
import com.example.noto.domain.useCase.SendNoteUseCase
import com.example.noto.domain.useCase.UpdateNoteUseCase

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteRepositoryImpl = NoteRepositoryImpl()

        val getAllNotesUseCase = GetAllNotesUseCase(noteRepositoryImpl)
        val getNoteUseCase = GetNoteUseCase(noteRepositoryImpl)
        val sendNoteUseCase = SendNoteUseCase(noteRepositoryImpl)
        val updateNoteUseCase = UpdateNoteUseCase(noteRepositoryImpl)

        noteViewModel = NoteViewModel(getAllNotesUseCase, getNoteUseCase, sendNoteUseCase, updateNoteUseCase)

        binding.buttonGetAllNotes.setOnClickListener {

            noteViewModel.getAllNotes()

        }
        binding.buttonOneNote.setOnClickListener {
            if(binding.editTextId.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                val noteResult=noteViewModel.getNote(binding.editTextId.text.toString())
                binding.textViewResult.text = noteResult.toString()
            }
        }
        binding.buttonSendNote.setOnClickListener {
            if(binding.editTextTitle.text.isEmpty() || binding.editTextContent.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                noteViewModel.sendNote(
                    NoteData(
                        binding.editTextId.text.toString().toInt(),
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString()
                    )
                )
            }
        }
        binding.buttonUpdateNote.setOnClickListener {
            if(binding.editTextId.text.isEmpty() || binding.editTextTitle.text.isEmpty() || binding.editTextContent.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                noteViewModel.updateNote(
                    binding.editTextId.text.toString(),
                    NoteData(
                        binding.editTextId.text.toString().toInt(),
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString()
                    )
                )
            }
        }

    }
}