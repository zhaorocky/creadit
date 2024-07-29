package com.like.upper.pleasure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.upper.pleasure.R
import com.like.upper.pleasure.view.onclick.OnItemDataClick
import com.like.upper.pleasure.databinding.ItemTermBinding

class TermAdapter(var context: Context,var term : Int, var duration: Int?) : RecyclerView.Adapter<TermAdapter.TermHolder>() {

    var onItemDataClick : OnItemDataClick? =  null

    class TermHolder(bindingView : ItemTermBinding) : RecyclerView.ViewHolder(bindingView.root) {
        var tvTerm = bindingView.tvItemTermTerm
        var tvDays = bindingView.tvItemTermDays
        var ly = bindingView.lyItemTerm
        var iv = bindingView.ivTerm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermHolder {
        return TermHolder(ItemTermBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TermHolder, position: Int) {

        if(position == 0){
            holder.ly.setBackgroundResource(R.drawable.shape_theme_radios8)
            holder.tvTerm.setTextColor(context.getColor(R.color.white))
            holder.tvDays.setTextColor(context.getColor(R.color.white))
            holder.iv.visibility = View.GONE
        }else{
            holder.ly.setBackgroundResource(R.drawable.shape_theme_not_chose_radios8)
            holder.tvTerm.setTextColor(context.getColor(R.color.text_999))
            holder.tvDays.setTextColor(context.getColor(R.color.text_999))
            holder.iv.visibility = View.VISIBLE
        }
        if(duration== null || duration == -1){
            duration = 7
        }
        holder.tvTerm.text = ((position+3)).toString()+" "+context.getString(R.string.home_cuotas)
        holder.tvDays.text = "("+((position+3)*duration!!).toString()+ context.getString(R.string.home_days)+")"


    }

    override fun getItemCount() : Int {

        return 3

    }
}