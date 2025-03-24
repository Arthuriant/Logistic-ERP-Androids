package andika.polman.logisticerp2.ui.users

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.User
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter (
    private val list: List<User>,
    private val onItemClick: (User, ActionType) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    enum class ActionType {
        EDIT, DELETE
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNamaUser: TextView = view.findViewById(R.id.tv_usersname)
        val txtEmailUser: TextView = view.findViewById(R.id.tv_usersemail)
        val txtPasswordUser: TextView = view.findViewById(R.id.tv_userspassword)
        val txtRoleUser: TextView = view.findViewById(R.id.tv_usersrole)
        val imgBtnEditUser : ImageButton = view.findViewById(R.id.btn_editusers)
        val imgBtnDeleteUser : ImageButton = view.findViewById(R.id.btn_deleteusers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_users, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val User = list[position]
        holder.txtNamaUser.text = User.nama
        holder.txtEmailUser.text = "Email: ${User.email}"
        holder. txtPasswordUser.text = "Password: ******"
        holder.txtRoleUser.text = if (User.role == 1) "Role: Admin" else "Role: User"

        holder.imgBtnEditUser.setOnClickListener { onItemClick(User,ActionType.EDIT) }
        holder.imgBtnDeleteUser.setOnClickListener { onItemClick(User,ActionType.DELETE) }
    }

    override fun getItemCount(): Int = list.size
}
