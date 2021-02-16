package eu.seijindemon.myinformation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.seijindemon.myinformation.data.User
import kotlinx.android.synthetic.main.model_link.view.*


class UsersCustomAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<UsersCustomAdapter.UsersViewHolder>() {

    private var usersList = emptyList<User>()

    companion object {

    }

    //the class is hodling the list view
    inner class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val firstName: TextView = itemView.firstName_user
        val lastName: TextView = itemView.lastName_user

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener
    {
        fun onItemClick(potition: Int)
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder
    {
        return UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.model_link,
                parent,
                false
            )
        )
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int
    {
        return usersList.size
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int)
    {
        val currentItem = usersList[position]
        holder.firstName.text = currentItem.firstName
        holder.lastName.text = currentItem.lastName
    }

    fun setData(users: List<User>)
    {
        this.usersList = users
        notifyDataSetChanged()
    }
}