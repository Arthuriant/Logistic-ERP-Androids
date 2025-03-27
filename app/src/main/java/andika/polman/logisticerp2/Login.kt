package andika.polman.logisticerp2

import andika.polman.logisticerp2.model.LoginRequest
import andika.polman.logisticerp2.model.LoginResponse
import andika.polman.logisticerp2.model.Produk
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)



        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)


        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val loginaccount = LoginRequest(
                email = username,
                password = password
            )

            RetrofitClient.instance.loginUser(loginaccount).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.status == "success") {
                            Toast.makeText(this@Login, "Login berhasil", Toast.LENGTH_SHORT).show()

                            // Ambil role dari respons API
                            val role = loginResponse.user?.role

                            val intent = when (role) {
                                1 -> Intent(this@Login, MainActivity::class.java) // Admin
                                2 -> Intent(this@Login, SeconActivity::class.java) // User
                                else -> {
                                    Toast.makeText(this@Login, "Role tidak dikenali!", Toast.LENGTH_SHORT).show()
                                    return
                                }
                            }

                            startActivity(intent)
                            finish() // Tutup halaman login agar tidak bisa kembali dengan tombol "Back"
                        } else {
                            Toast.makeText(this@Login, "Login gagal: ${loginResponse?.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@Login, "Terjadi kesalahan saat login", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@Login, "Gagal terhubung ke server!", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}
