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
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormUsersFragment : Fragment() {
    private lateinit var tvTitleFormUser : TextView
    private lateinit var etUserName: EditText
    private lateinit var etUserEmail: EditText
    private lateinit var etUserPassword: EditText
    private lateinit var cbRoleAdmin: CheckBox
    private lateinit var cbRoleUser: CheckBox
    private lateinit var btnSimpanSupllier: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_form_users, container, false)

        tvTitleFormUser = view.findViewById(R.id.tv_TitleUser)
        etUserName = view.findViewById(R.id.et_usersname)
        etUserEmail = view.findViewById(R.id.et_email)
        etUserPassword = view.findViewById(R.id.et_password)
        cbRoleAdmin = view.findViewById(R.id.checkBoxAdmin)
        cbRoleUser = view.findViewById(R.id.checkBoxUser)
        btnSimpanSupllier = view.findViewById(R.id.btn_simpanuser)



        val idUser = arguments?.getInt("id_User") ?: -1

        if (idUser != -1) {
            tvTitleFormUser.text = "Ubah Produk User"
            fetchDetailUser(idUser)
        }
        else
        {
            tvTitleFormUser.text = "Tambah produk User"
        }

        cbRoleAdmin.setOnClickListener{
            cbRoleUser.isChecked = false
        }

        cbRoleUser.setOnClickListener{
            cbRoleAdmin.isChecked = false
        }

        btnSimpanSupllier.setOnClickListener {

            val UserNama = etUserName.text.toString().trim()
            val UserEmail =etUserEmail.text.toString().trim()
            val UserPassword =etUserPassword.text.toString().trim()
            var UserRole = -1

            if (cbRoleUser.isChecked) {
                UserRole = 2
            } else if (cbRoleAdmin.isChecked) {
                UserRole = 1
            } else {
                Toast.makeText(requireContext(), "Pilih Role !", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (idUser == -1) {
                tambahUser(UserNama, UserEmail, UserPassword,UserRole)
            } else {
                updateUser(idUser,UserNama,UserEmail, UserPassword,UserRole)
            }
        }

        
        
        return view
    }

    private fun  fetchDetailUser(idUser: Int) {
        RetrofitClient.instance.getDetailUser(idUser).enqueue(object :
            Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let { User ->

                    etUserName.setText(User.nama)
                    etUserEmail.setText(User.email)
                    etUserPassword.setText(" ")
                    if(User.role == 1)
                    {
                        cbRoleAdmin.isChecked = true
                    }else if(User.role == 2)
                    {
                        cbRoleUser.isChecked = true
                    }


                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal !", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser(idUser: Int, UserNama: String, UserEmail: String , UserPassword: String,UserRole: Int) {
        val UserUpdate = User(
            id = idUser,
            nama = UserNama,
            email = UserEmail,
            password_hash = UserPassword,
            role = UserRole,
            created_at = "0"
        )

        RetrofitClient.instance.updateUser(idUser, UserUpdate).enqueue(object :
            Callback<ResponseMessage> {
            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToUserFragment()
                    Toast.makeText(requireContext(), "User berhasil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal memperbarui User: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun tambahUser(UserNama: String, UserEmail: String , UserPassword: String,UserRole: Int) {
        val UserBaru = User(
            id = 0,
            nama = UserNama,
            email = UserEmail,
            password_hash = UserPassword,
            role = UserRole,
            created_at = "0"

        )

        RetrofitClient.instance.tambahUser(UserBaru).enqueue(object :
            Callback<ResponseMessage> {

            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToUserFragment()
                    Toast.makeText(requireContext(), "Produk User berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika respons dari server gagal, tampilkan pesan error
                    Toast.makeText(requireContext(), "Produk User menambahkan buku: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Produk menambahkan buku", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changeToUserFragment()
    {
        findNavController().navigate(R.id.action_FormUserFragment_to_UserFragment)
    }


}