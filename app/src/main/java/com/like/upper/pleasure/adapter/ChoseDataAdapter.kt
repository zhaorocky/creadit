package com.like.upper.pleasure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.upper.pleasure.R
import com.like.upper.pleasure.entity.DictionaryInfo
import com.like.upper.pleasure.view.onclick.OnItemDataClick
import com.like.upper.pleasure.databinding.ItemDataBinding

class ChoseDataAdapter(var context: Context, var list: MutableList<DictionaryInfo>?) : RecyclerView.Adapter<ChoseDataAdapter.DataHolder>() {

    var onItemDataClick : OnItemDataClick? =  null

    var choseIndex = 0
    class DataHolder(bindingView : ItemDataBinding) : RecyclerView.ViewHolder(bindingView.root) {
        val tv = bindingView.tvItemData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(ItemDataBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.tv.text = list?.get(position)?.cordlessPaperworkForeignMan
        if(choseIndex == position){
            holder.tv.setTextColor(context.getColor(R.color.purple))
        }else{
            holder.tv.setTextColor(context.getColor(R.color.text_999))
        }
        holder.tv.setOnClickListener{
            choseIndex = position
            onItemDataClick?.onLickListener(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() : Int {
        list?.let {
            return it.size
        }
        return 0
    }

    fun setItemCLickListener(myListener : OnItemDataClick){
        onItemDataClick = myListener
    }
}