package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassSettingsBinding
import com.capstoneprojectg8.schoolscheduleapp.models.SClass

class ClassesRvAdapter : RecyclerView.Adapter<ClassesRvAdapter.ViewHolder>() {

    private val classesList: ArrayList<SClass> = arrayListOf()
    var onItemClick: ((SClass) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemClassSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val classes = classesList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(classes)
        }
        holder.bind(classes)
    }

    override fun getItemCount(): Int = classesList.size

    fun appendClass(newMovie: List<SClass>) {
        classesList.clear()
        classesList.addAll(newMovie)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemClassSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classes: SClass) {
            binding.apply {
                classCodeTv.text = classes.code
                classNameTv.text = classes.name

                constraintLayout.background =
                    ContextCompat.getDrawable(root.context, classes.colour)
            }
        }
    }
}