package com.example.ourdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DiaryAdapter(private val diarylist: MutableList<Diary>):
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    init{
        setHasStableIds(true)
    }

    //セルをタップできるようにする処理
    /*
    private var listener: ((Long?) -> Unit)? = null
    fun setOnItemClickListener(listener: (Long?) -> Unit){
        this.listener = listener
    }*/

    //viewの初期化
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val date: TextView
        val feeling: TextView
        val title: TextView

        init {
            date = view.findViewById(R.id.text_item_date)
            feeling = view.findViewById(R.id.text_item_feeling)
            title = view.findViewById(R.id.text_item_title)

        }
    }

    //レイアウトの設定
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_diary_item, viewGroup, false)
        return ViewHolder(view)
    }

    //viewの設定
    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {
        val diary = diarylist[position]

        viewholder.date.text = diary.date
        viewholder.feeling.text = diary.feeling
        viewholder.title.text = diary.title
        /*
        viewholder.itemView.setOnClickListener {
            listener?.invoke(dairy?.id)
        }*/
    }

    override fun getItemCount() = diarylist.size

    /*
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)?.id ?:0
    }*/

}



