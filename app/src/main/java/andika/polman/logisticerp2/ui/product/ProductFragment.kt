package andika.polman.logisticerp2.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andika.polman.logisticerp2.R
import android.widget.Toast

import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.fragment.findNavController


class ProductFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_addproduct)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_formProductFragment)
        }


        return  view

    }

}