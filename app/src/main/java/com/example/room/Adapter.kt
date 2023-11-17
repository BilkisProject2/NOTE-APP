package com.example.room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class Adapter(private val context: Context, var notclick: NoteClickListener) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    var notelist = ArrayList<Note>()
    var fulllist = ArrayList<Note>()

    class ViewHolder(var item: View) : RecyclerView.ViewHolder(item) {
        var cardview = item.findViewById<CardView>(R.id.cardview)
        var Titletxt = item.findViewById<TextView>(R.id.titletxt)
        var notetxt = item.findViewById<TextView>(R.id.notes)
        var datetxt = item.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var views = LayoutInflater.from(parent.context).inflate(R.layout.sample, parent, false)
        return ViewHolder(views)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var currenitem = notelist[position]
        holder.Titletxt.text = currenitem.title
        holder.Titletxt.isSelected = true
        holder.notetxt.text = currenitem.note
        holder.datetxt.text = currenitem.date
        holder.Titletxt.isSelected = true

        holder.cardview.setCardBackgroundColor(holder.itemView.resources.getColor(randomcolour(),
            null))

        holder.cardview.setOnClickListener {
            notclick.onItemClick(notelist[holder.adapterPosition])
        }

        holder.cardview.setOnLongClickListener {
            notclick.onLongItemClicked(notelist[holder.adapterPosition], holder.cardview)
            true
        }

    }

    // Notes different colour
    fun randomcolour(): Int {

        var list = ArrayList<Int>()
        list.add(R.color.Sink)
        list.add(R.color.Partole)
        list.add(R.color.Purpule)
        list.add(R.color.Teal)
        list.add(R.color.Yellow)
        list.add(R.color.secondpink)
        list.add(R.color.red)
        list.add(R.color.bule)
        list.add(R.color.orgen)


        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    fun update(newlist: List<Note>) {
        fulllist.clear()
        fulllist.addAll(newlist)

        notelist.clear()
        notelist.addAll(fulllist)
        notifyDataSetChanged()
    }

    fun filterlist(search: String) {
        notelist.clear()
        for (item in fulllist) {

            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                notelist.add(item)
            }
        }
        notifyDataSetChanged()
    }

    interface NoteClickListener {
        fun onItemClick(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)


    }


}