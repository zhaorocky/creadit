package com.like.upper.pleasure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.upper.pleasure.databinding.ItemMoreInfoBinding
import com.like.upper.pleasure.view.onclick.OnItemDataClick
import com.like.upper.pleasure.entity.DictionaryInfo
import com.like.upper.pleasure.entity.MoreInfo

class MoreInfoAdapter(var context: Context, var list: List<MoreInfo>?) : RecyclerView.Adapter<MoreInfoAdapter.MoreAdapter>() {

    var onItemDataClick : OnItemDataClick? =  null

    class MoreAdapter(bindingView : ItemMoreInfoBinding) : RecyclerView.ViewHolder(bindingView.root) {
        var tvTitle = bindingView.tvMoreTitle
        var tvAmount = bindingView.tvMoreAccount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreAdapter {
        return MoreAdapter(ItemMoreInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MoreAdapter, position: Int) {
        holder.tvTitle.text = list?.get(position)?.maleMostTheme
        holder.tvAmount.text = list?.get(position)?.russianAgriculturalBornNiece

    }

    override fun getItemCount() : Int {
        list?.let {
            return it.size
        }
        return 0
    }
}