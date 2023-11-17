package com.example.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Adapter.NoteClickListener,
    PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    private lateinit var viemodel: NoteViewModel
    lateinit var adapter: Adapter
    lateinit var selectnote: Note


    private val updateNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as Note
                if (note != null) {
                    viemodel.updatenote(note)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        viemodel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viemodel.allnotes.observe(this) { list ->
            list?.let {
                adapter.update(list)
            }
        }

        database = NoteDatabase.getdatabase(this)

        initUI()

        //user want to create the note
        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {
                    val note = result.data?.getSerializableExtra("note") as Note
                    if (note != null) {
                        viemodel.insertnote(note)
                    }
                }
            }
        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(this, ADDNOTES::class.java)
            getContent.launch(intent)
        }

        //search the note
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                if (p0 != null) {
                    adapter.filterlist(p0)
                }
                return true
            }

        })
    }


    private fun initUI() {
        binding.recyclerView2.setHasFixedSize(true)
        binding.recyclerView2.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = Adapter(this, this)
        binding.recyclerView2.adapter = adapter


    }


    override fun onItemClick(note: Note) {

        var intent = Intent(this, ADDNOTES::class.java)
        intent.putExtra("currentnote", note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectnote = note
        popupDisplay(cardView)
    }

    private fun popupDisplay(cardView: CardView) {
        var pop = PopupMenu(this, cardView)
        pop.inflate(R.menu.pop_up_menu)
        pop.setOnMenuItemClickListener(this)
        pop.show()
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        if (p0?.itemId == R.id.menu) {
            viemodel.deltenote(selectnote)
            return true
        }
        return false
    }
}