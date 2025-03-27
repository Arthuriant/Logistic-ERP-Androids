package andika.polman.logisticerp2.ui.User.ExitItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.Produk
import andika.polman.logisticerp2.model.ProdukKeluar
import android.app.DatePickerDialog
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class FormExitItemFragment : Fragment() {
    private lateinit var tvTitleFormExit : TextView
    private lateinit var etExitItemDate: EditText
    private lateinit var etExitItemValue: EditText
    private lateinit var btnSimpanExitItem: Button
    private lateinit var spinnerProduk: Spinner


    private var listProduk = mutableListOf<Produk>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form_exit_item, container, false)
        tvTitleFormExit = view.findViewById(R.id.tv_titleformexititem)
        etExitItemDate= view.findViewById(R.id.et_tanggal_exititem)
        etExitItemValue= view.findViewById(R.id.et_Jumlah_exititem)
        btnSimpanExitItem= view.findViewById(R.id.btn_simpanexititem)
        spinnerProduk = view.findViewById(R.id.spinnerProdukKeluar)

        loadProduk()
        tvTitleFormExit.text = "Tambah Produk yang Keluar"

        etExitItemDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // Format ke YYYY-MM-DD
                val selectedDate = "$selectedYear-${String.format("%02d", selectedMonth + 1)}-${String.format("%02d", selectedDay)}"
                etExitItemDate.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }


        btnSimpanExitItem.setOnClickListener {
            val selectedProductIndex = spinnerProduk.selectedItemPosition

            if (selectedProductIndex == -1) {
                Toast.makeText(requireContext(), "Pilih produk terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedProduct = listProduk[selectedProductIndex]
            val idProductExitItem = selectedProduct.id_produk

            val dateExitItem = etExitItemDate.text.toString().trim()
            val valueExitItem = etExitItemValue.text.toString().trim().toIntOrNull() ?: 0

            tambahProdukKeluar(idProductExitItem, dateExitItem, valueExitItem)
        }

        return view
    }

    private fun loadProduk() {
        RetrofitClient.instance.getDataProductAdd().enqueue(object : Callback<List<Produk>> {
            override fun onResponse(call: Call<List<Produk>>, response: Response<List<Produk>>) {
                if (response.isSuccessful) {
                    listProduk = response.body()?.toMutableList() ?: mutableListOf()

                    // Konversi ke list nama untuk ditampilkan di Spinner
                    val produkNames = listProduk.map { it.nama_produk }

                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, produkNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerProduk.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Produk>>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal memuat produk!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun tambahProdukKeluar(idProductExitItem: Int, dateExitItem: String,valueExitItem: Int) {
        val ExitItemBaru = ProdukKeluar(
            id_transaksi = 0,
            id_produk = idProductExitItem,
            tanggal = dateExitItem,
            harga_jual = 0f,
            jumlah = valueExitItem,
        )

        RetrofitClient.instance.tambahProdukKeluar(ExitItemBaru).enqueue(object :
            Callback<ResponseMessage> {

            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToExitItemFragment()
                    Toast.makeText(requireContext(), "Transaksi Baru", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika respons dari server gagal, tampilkan pesan error
                    Toast.makeText(requireContext(), "Gagal Menambahkan Transaksi: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Toast.makeText(requireContext(), "Transaksi Gagal!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changeToExitItemFragment()
    {
        findNavController().navigate(R.id.action_FormExitFragment_to_ExitFragment)
    }



}