package com.example.ourdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.CollectionReference

class DiaryAdapter internal constructor(options: CollectionReference?):
    FirestoreRecyclerAdapter<Diary, DiaryViewHolder>(data, true){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_diary_item,parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int, model: Diary) {
        holder.setDiaryItem(model.title!!, model.date!!)
    }
}

class DiaryViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view){
    internal fun setDiaryItem(title: String, date: String) {

        val textviewFirst = view.findViewById<TextView>(R.id.text_item_title)
        textviewFirst.text = title

        val textviewLast = view.findViewById<TextView>(R.id.text_item_date)
        textviewLast.text = date
    }
}



