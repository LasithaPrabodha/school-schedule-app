package com.capstoneprojectg8.schoolscheduleapp.ui.settings.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstoneprojectg8.schoolscheduleapp.databinding.ItemClassesBinding
import com.capstoneprojectg8.schoolscheduleapp.models.Class

class ClassesRvAdapter : RecyclerView.Adapter<ClassesRvAdapter.ViewHolder>() {

    private val classesList: ArrayList<Class> = arrayListOf()
    var onItemClick: ((Class) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemClassesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun appendClass(newMovie: List<Class>) {
        classesList.clear()
        classesList.addAll(newMovie)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemClassesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classes: Class) {
            binding.apply {
                classCodeTv.text = classes.classCode
                classNameTv.text = classes.className

                constraintLayout.background =
                    ContextCompat.getDrawable(root.context, classes.colour)
            }
        }
    }
}