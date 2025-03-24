package andika.polman.logisticerp2.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.User
import android.app.AlertDialog
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private lateinit var recyclerViewUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private var UserList = mutableListOf<User>()
    private lateinit var rbUser : RadioButton
    private lateinit var rbAdmin : RadioButton
    private lateinit var rbAll : RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_users, container, false)
        rbUser = view.findViewById(R.id.rb_user)
        rbAdmin = view.findViewById(R.id.rb_admin)
        rbAll = view.findViewById(R.id.rb_semua)

        recyclerViewUser = view.findViewById(R.id.rv_users)
        recyclerViewUser.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter(UserList){ User,action ->
            when(action){
                UserAdapter.ActionType.EDIT->EditUser(User)
                UserAdapter.ActionType.DELETE->DeleteUser(User)
                else -> throw IllegalArgumentException("Unknown action: $action")
            }

        }


        recyclerViewUser.adapter = userAdapter

        fetchProduk()

        rbAdmin.setOnClickListener{
            fetchOnlyAdmin()
        }

        rbUser.setOnClickListener{
            fetchOnlyUser()
        }

        rbAll.setOnClickListener{
            fetchProduk()
        }

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_adduser)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_UserFragment_to_FormUserFragment)
        }
        return  view
    }

    private fun EditUser(User: User)
    {
        val bundle = Bundle().apply {
            putInt("id_User", User!!.id)
        }
        findNavController().navigate(R.id.action_UserFragment_to_FormUserFragment,bundle)
    }

    private fun DeleteUser(User: User)
    {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Produk User")
            .setMessage("Apakah Anda yakin ingin menghapus produk \"${User?.nama}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                User?.id?.let { idUser ->
                    RetrofitClient.instance.deleteUser(idUser).enqueue(object :
                        Callback<ResponseMessage> {
                        override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "User berhasil dihapus", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_FormUserFragment_to_UserFragment)
                            } else {
                                Toast.makeText(requireContext(), "Gagal menghapus User produk", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                            Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }






    private fun fetchProduk() {
        RetrofitClient.instance.getAllUser().enqueue(object :
            Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let {
                    UserList.clear()
                    UserList.addAll(it)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }

    private fun fetchOnlyAdmin() {
        RetrofitClient.instance.getAllUserAdmin().enqueue(object :
            Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let {
                    UserList.clear()
                    UserList.addAll(it)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })

    }

    private fun fetchOnlyUser() {
        RetrofitClient.instance.getAllUserUser().enqueue(object :
            Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let {
                    UserList.clear()
                    UserList.addAll(it)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("API_ERROR", "Gagal mengambil data produk", t)
            }
        })
    }



}