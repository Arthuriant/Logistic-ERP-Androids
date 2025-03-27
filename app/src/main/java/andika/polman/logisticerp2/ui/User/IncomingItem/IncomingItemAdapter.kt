package andika.polman.logisticerp2.ui.User.IncomingItem

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.ProdukMasuk
import andika.polman.logisticerp2.model.ProdukMasukxProdukxSupplier
import andika.polman.logisticerp2.ui.productcategory.ProductCategoryAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncomingItemAdapter (
    private val list: List<ProdukMasukxProdukxSupplier>
) : RecyclerView.Adapter<IncomingItemAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtIdIncomeProduct: TextView = view.findViewById(R.id.tv_id_produk_incomingitem)
        val txtDateIncomeProduct: TextView = view.findViewById(R.id.tv_tanggalMasuk_incomingItem)
        val txtSupplierIncomeProduct: TextView = view.findViewById(R.id.tv_id_supplier_incomingItem)
        val txtPriceIncomeProduct : TextView = view.findViewById(R.id.tv_id_HargaBeli_incomingitem)
        val txtValueIncomeProduct : TextView = view.findViewById(R.id.tv_id_jumlahMasuk_incomingitem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_incomingitem, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incomeProduct = list[position]
        holder.txtIdIncomeProduct.text = "${incomeProduct.nama_produk}"
        holder.txtDateIncomeProduct.text = "Tanggal: ${incomeProduct.tanggal}"
        holder.txtSupplierIncomeProduct.text = "Supplier: ${incomeProduct.nama_supplier}"
        holder. txtPriceIncomeProduct.text = "Harga: ${incomeProduct.harga_beli}"
        holder.txtValueIncomeProduct.text = "Jumlah Masuk: ${incomeProduct.jumlah}"


    }

    override fun getItemCount(): Int = list.size
}
