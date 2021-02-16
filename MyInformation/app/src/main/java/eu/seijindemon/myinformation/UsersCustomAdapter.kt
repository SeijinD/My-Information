package eu.seijindemon.myinformation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import eu.seijindemon.myinformation.data.User
import kotlinx.android.synthetic.main.model_link.view.*

class UsersCustomAdapter: RecyclerView.Adapter<UsersCustomAdapter.UsersViewHolder>() {

    private var usersList = emptyList<User>()

    companion object {
        private var potition_id: Int = -1
    }

    //the class is hodling the list view
    class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { v: View ->
                potition_id = adapterPosition
                Toast.makeText(itemView.context,"You clicked on item # ${potition_id +1}",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getPotition(): Int
    {
        return potition_id
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder
    {
        return UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.model_link, parent, false))
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
        holder.itemView.firstName_user.text = currentItem.firstName
        holder.itemView.lastName_user.text = currentItem.lastName
    }

    fun setData(user: List<User>)
    {
        this.usersList = user
        notifyDataSetChanged()
    }
}