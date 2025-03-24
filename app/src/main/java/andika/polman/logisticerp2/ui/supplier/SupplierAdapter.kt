package andika.polman.logisticerp2.ui.supplier

import andika.polman.logisticerp2.R
import andika.polman.logisticerp2.model.Supplier
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SupplierAdapter(
    private val list: List<Supplier>,
    private val onItemClick: (Supplier, ActionType) -> Unit
) : RecyclerView.Adapter<SupplierAdapter.ViewHolder>() {

    enum class ActionType {
        EDIT, DELETE
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNamaSupplier: TextView = view.findViewById(R.id.tv_suppliername)
        val txtKontakSupplier: TextView = view.findViewById(R.id.tv_supplierkontak)
        val txtAlamatSupplier: TextView = view.findViewById(R.id.tv_supplieralamat)
        val txtProdukSupplier: TextView = view.findViewById(R.id.tv_supplierkontak)
        val imgBtnEditSupplier : ImageButton = view.findViewById(R.id.btn_editsupply)
        val imgBtnDeleteSupplier : ImageButton = view.findViewById(R.id.btn_deletesupply)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_supplier, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supplier = list[position]
        holder.txtNamaSupplier.text = supplier.nama_supplier
        holder.txtKontakSupplier.text = "Kontak: ${supplier.kontak}"
        holder. txtAlamatSupplier.text = "Alamat: ${supplier.alamat}"
        holder. txtProdukSupplier.text = "Produk: ${supplier.produksupllie}"

        holder.imgBtnEditSupplier.setOnClickListener { onItemClick(supplier,ActionType.EDIT) }
        holder.imgBtnDeleteSupplier.setOnClickListener { onItemClick(supplier,ActionType.DELETE) }
    }

    override fun getItemCount(): Int = list.size
}
