package com.example.noto.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.noto.utils.RetrofitClient
import com.example.noto.data.dto.NoteData
import com.example.noto.data.remoteRepository.NoteRepositoryImpl
import com.example.noto.databinding.ActivityMainBinding
import com.example.noto.domain.useCase.GetAllNotesUseCase
import com.example.noto.domain.useCase.GetNoteUseCase
import com.example.noto.domain.useCase.SendNoteUseCase
import com.example.noto.domain.useCase.UpdateNoteUseCase
import com.example.noto.view.viewModel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create repository
        val noteService = RetrofitClient.getService()
        val noteRepositoryImpl = NoteRepositoryImpl(noteService)

        // Create use cases
        val getAllNotesUseCase = GetAllNotesUseCase(noteRepositoryImpl)
        val getNoteUseCase = GetNoteUseCase(noteRepositoryImpl)
        val sendNoteUseCase = SendNoteUseCase(noteRepositoryImpl)
        val updateNoteUseCase = UpdateNoteUseCase(noteRepositoryImpl)

        // Create ViewModel
        noteViewModel = NoteViewModel(getAllNotesUseCase, getNoteUseCase, sendNoteUseCase, updateNoteUseCase)
        // Observing LiveData from ViewModel
        noteViewModel.noteListLiveData.observe(this, Observer { notes ->
            // Update UI with list of notes
            binding.textViewResult.text = notes.joinToString("\n")
        })
        // Observing LiveData from ViewModel
        noteViewModel.noteLiveData.observe(this, Observer { note ->
            // Update UI with single note
            binding.textViewResult.text = note.toString()
        })
        // Button click listeners
        binding.buttonGetAllNotes.setOnClickListener {
            // Call use case to get all notes
            noteViewModel.getAllNotes()
        }
        // Button click listeners for get note
        binding.buttonOneNote.setOnClickListener {
            if(binding.editTextId.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }else {
                // Call use case to get single note
                val noteResult=noteViewModel.getNote(binding.editTextId.text.toString())
                binding.textViewResult.text = noteResult.toString()
                // Clear edit text
                binding.editTextId.text.clear()
                binding.editTextContent.text.clear()
                binding.editTextTitle.text.clear()
            }
        }
        // Button click listeners for send note
        binding.buttonSendNote.setOnClickListener {
            if(binding.editTextTitle.text.isEmpty() || binding.editTextContent.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else {
                // Call use case to send note
                noteViewModel.sendNote(
                    NoteData(
                        binding.editTextId.text.toString().toInt(),
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString()
                    )
                )
                // Clear edit text
                binding.editTextId.text.clear()
                binding.editTextContent.text.clear()
                binding.editTextTitle.text.clear()
            }
        }
        // Button click listeners for update note
        binding.buttonUpdateNote.setOnClickListener {
            if(binding.editTextId.text.isEmpty() || binding.editTextTitle.text.isEmpty() || binding.editTextContent.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else {
                // Call use case to update note
                noteViewModel.updateNote(
                    binding.editTextId.text.toString(),
                    NoteData(
                        binding.editTextId.text.toString().toInt(),
                        binding.editTextTitle.text.toString(),
                        binding.editTextContent.text.toString()
                    )
                )
                // Clear edit text
                binding.editTextId.text.clear()
                binding.editTextContent.text.clear()
                binding.editTextTitle.text.clear()
            }
        }

    }
}