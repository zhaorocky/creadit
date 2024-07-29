package com.like.upper.pleasure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.like.upper.pleasure.R
import com.like.upper.pleasure.databinding.ItemPlanBinding
import com.like.upper.pleasure.view.onclick.OnItemDataClick
import com.like.upper.pleasure.databinding.ItemTermBinding
import com.like.upper.pleasure.entity.DictionaryInfo
import com.like.upper.pleasure.entity.PlanItemInfo

class PlanAdapter(var context: Context,var list: List<PlanItemInfo>?) : RecyclerView.Adapter<PlanAdapter.PlanHolder>() {

    var onItemDataClick : OnItemDataClick? =  null

    class PlanHolder(bindingView : ItemPlanBinding) : RecyclerView.ViewHolder(bindingView.root) {
        val tvCount = bindingView.tvItemTermTerm
        val tvAmount = bindingView.tvItemPlanAmount
        val tvAmountTitle = bindingView.tvItemPlanAmountTitle
        val tvDate = bindingView.tvItemPlanDate
        val tvDateTitle = bindingView.tvItemPlanDateTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanHolder {
        return PlanHolder(ItemPlanBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlanHolder, position: Int) {
        list?.get(position).let {plan->

            holder.tvCount.text = plan?.crowdedInsuranceInternationalFeatherSweetSock+"//"+itemCount
            holder.tvAmount.text = plan?.suitableSkilledRestTrueBuddhist
            holder.tvAmountTitle.text = plan?.ableUndividedEnemy
            holder.tvDate.text = plan?.rawCapChallengingNoisyBridge 
            holder.tvDateTitle.text = plan?.giftedSuspectYourselvesForeigner

        }

    }

    override fun getItemCount() : Int {
        list?.let {
            return it.size
        }
        return 0
    }
}