package com.example.ourdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DiaryAdapter(private val diarylist: MutableList<Diary>):
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    //リスナーを格納する変数
    private lateinit var listener: OnDiaryCellClickListener

    //インターフェース作成
    interface  OnDiaryCellClickListener {
        fun onItemClick(diary: Diary)
    }

    // リスナーをセット
    fun setOnDiaryCellClickListener(listener: OnDiaryCellClickListener) {
        // 定義した変数listenerに実行したい処理を引数で渡す（BookListFragmentで渡している）
        this.listener = listener
    }

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

        // 4. セルのクリックイベントにリスナをセット
        viewholder.itemView.setOnClickListener {
            // セルがクリックされた時にインターフェースの処理が実行される
            listener.onItemClick(diary)
        }
    }

    override fun getItemCount() = diarylist.size

    /*
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)?.id ?:0
    }*/

}



