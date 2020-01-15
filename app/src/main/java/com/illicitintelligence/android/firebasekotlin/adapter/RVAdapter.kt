package com.illicitintelligence.android.firebasekotlin.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.illicitintelligence.android.firebasekotlin.R
import com.illicitintelligence.android.firebasekotlin.model.Message
import kotlinx.android.synthetic.main.rv_layout.view.*

class RVAdapter(var list: ArrayList<Message>, var sender: String) : RecyclerView.Adapter<RVAdapter.ViewHolder>(){


    override fun getItemViewType(position: Int): Int {
        return if (list[position].id.equals(sender)) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==0){
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_layout,parent,false))
        }else{
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_layout2,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.content.text = list[position].content
        holder.timeStamp.text = list[position].dateToString()
    }


    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var content: TextView = item.message_tv
        var timeStamp: TextView = item.timeStamp_tv
    }
}