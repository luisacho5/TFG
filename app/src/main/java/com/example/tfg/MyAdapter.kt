package com.example.tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList:ArrayList<User> ):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener:OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)

        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val user:User = userList[position]
        holder.name.text=user.name
        holder.mail.text=user.email
        holder.rol.text=user.rol

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val mail: TextView = itemView.findViewById(R.id.tv_email)
        val rol: TextView = itemView.findViewById(R.id.tv_rol)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}