package com.example.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.room.databinding.ActivityAddnotesBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ADDNOTES : AppCompatActivity() {
    lateinit var binding:ActivityAddnotesBinding

    private lateinit var note: Note
    private lateinit var old_note:Note
    var isupdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddnotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {

            old_note = intent.getSerializableExtra("currentnote") as Note
            binding.editTextTextPersonName.setText(old_note.title)
            binding.editTextTextPersonName2.setText(old_note.note)
            isupdate = true

        }catch (e:Exception){
            e.printStackTrace()

        }

        binding.imageView2.setOnClickListener {
        var t=    binding.editTextTextPersonName.text.toString()
         var n=   binding.editTextTextPersonName2.text.toString()

            if (t.isNotEmpty() && n.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE , d MMM yyyy HH:mm a")

                if (isupdate){
                    note = Note(
                        old_note.id,t,n,formatter.format(Date())
                    )
                }else{
                    note = Note(
                       null,t,n,formatter.format(Date())
                    )
                }

                val intent =Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this,"PLEASE ENTER SOME DATA",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }

        binding.imageView.setOnClickListener {
            var intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}