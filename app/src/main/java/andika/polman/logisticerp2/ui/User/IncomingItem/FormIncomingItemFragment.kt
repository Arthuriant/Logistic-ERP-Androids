package andika.polman.logisticerp2.ui.User.IncomingItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.ResponseMessage
import andika.polman.logisticerp2.RetrofitClient
import andika.polman.logisticerp2.model.ProdukMasuk
import andika.polman.logisticerp2.model.Produk
import andika.polman.logisticerp2.model.ProdukKategori
import andika.polman.logisticerp2.model.ProdukxKategori
import andika.polman.logisticerp2.model.Supplier
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


class FormIncomingItemFragment : Fragment() {
    private lateinit var tvTitleFormIncoming : TextView
    private lateinit var etincomingItemDate: EditText
    private lateinit var etincomingItemValue: EditText
    private lateinit var btnSimpanincomingItem: Button
    private lateinit var spinnerProduk: Spinner
    private lateinit var spinnerSupplier: Spinner

    private var listProduk = mutableListOf<Produk>()
    private var listSupplier = mutableListOf<Supplier>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_form_incoming_item, container, false)
        tvTitleFormIncoming = view.findViewById(R.id.tv_titleformincomingitem)
        spinnerProduk = view.findViewById(R.id.spinnerProdukMasuk)
        spinnerSupplier = view.findViewById(R.id.spinnerSupplierMasuk)
        etincomingItemDate= view.findViewById(R.id.et_tanggal_incomingitem)
        etincomingItemValue= view.findViewById(R.id.et_Jumlah_incomingitem)
        btnSimpanincomingItem= view.findViewById(R.id.btn_simpanIncomingItem)

        loadProduk()
        loadSupplier()

        tvTitleFormIncoming.text = "Tambah Produk yang Masuk"

        etincomingItemDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // Format ke YYYY-MM-DD
                val selectedDate = "$selectedYear-${String.format("%02d", selectedMonth + 1)}-${String.format("%02d", selectedDay)}"
                etincomingItemDate.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }



        btnSimpanincomingItem.setOnClickListener {
            val selectedProductIndex = spinnerProduk.selectedItemPosition
            val selectedSupplierIndex = spinnerSupplier.selectedItemPosition

            if (selectedProductIndex == -1 || selectedSupplierIndex == -1) {
                Toast.makeText(requireContext(), "Pilih produk dan supplier terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idProductincomingItem = listProduk[selectedProductIndex].id_produk
            val idSupplierincomingItem = listSupplier[selectedSupplierIndex].id_supplier
            val dateincomingItem = etincomingItemDate.text.toString().trim()
            val valueincomingItem = etincomingItemValue.text.toString().trim().toIntOrNull() ?: 0

            tambahProdukMasuk(idProductincomingItem, dateincomingItem, idSupplierincomingItem, valueincomingItem)
        }

        return  view
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

    private fun loadSupplier() {
        RetrofitClient.instance.getSupplier().enqueue(object : Callback<List<Supplier>> {
            override fun onResponse(call: Call<List<Supplier>>, response: Response<List<Supplier>>) {
                if (response.isSuccessful) {
                    listSupplier = response.body()?.toMutableList() ?: mutableListOf()

                    // Konversi ke list nama untuk Spinner
                    val supplierNames = listSupplier.map { it.nama_supplier }

                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, supplierNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerSupplier.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Supplier>>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal memuat supplier!", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun tambahProdukMasuk(idProductincomingItem: Int, dateincomingItem: String, idSupplierincomingItem: Int,valueincomingItem: Int) {
        val incomingItemBaru = ProdukMasuk(
            id_transaksi = 0,
            id_produk = idProductincomingItem,
            tanggal = dateincomingItem,
            id_supplier = idSupplierincomingItem,
            harga_beli = 0f,
            jumlah = valueincomingItem,
        )

        RetrofitClient.instance.tambahProdukMasuk(incomingItemBaru).enqueue(object :
            Callback<ResponseMessage> {

            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                if (response.isSuccessful) {
                    changeToincomingItemFragment()
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

    private fun changeToincomingItemFragment()
    {
        findNavController().navigate(R.id.action_FormIncomingFragment_to_IncomingFragment)
    }


}