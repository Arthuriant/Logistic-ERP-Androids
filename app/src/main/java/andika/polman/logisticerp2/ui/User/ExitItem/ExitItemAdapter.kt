package andika.polman.logisticerp2.ui.User.ExitItem

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.ProdukKeluarxProduk
import andika.polman.logisticerp2.ui.User.ExitItem.ExitItemAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExitItemAdapter  (
    private val list: List<ProdukKeluarxProduk>
) : RecyclerView.Adapter<ExitItemAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtIdExitProduct: TextView = view.findViewById(R.id.tv_id_produk_exititem)
        val txtDateExitProduct: TextView = view.findViewById(R.id.tv_tanggalkeluar_exititem)
        val txtPriceExitProduct : TextView = view.findViewById(R.id.tv_id_HargaJual_exititem)
        val txtValueExitProduct : TextView = view.findViewById(R.id.tv_id_jumlahkeluar_exititem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_exititem, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ExitProduct = list[position]
        holder.txtIdExitProduct.text = "${ExitProduct.nama_produk}"
        holder.txtDateExitProduct.text = "Tanggal: ${ExitProduct.tanggal}"
        holder. txtPriceExitProduct.text = "Harga Jual: ${ExitProduct.harga_jual}"
        holder.txtValueExitProduct.text = "Jumlah Keluar: ${ExitProduct.jumlah}"


    }

    override fun getItemCount(): Int = list.size
}