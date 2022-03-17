package com.example.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.inventory.databinding.FragmentQuanitityBinding


class QuanitityFragment : Fragment() {
    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.

    private var _binding: FragmentQuanitityBinding? = null
    private val binding get() = _binding!!


    private val viewModel: dataViewModel by activityViewModels {
        dataviewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuanitityBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

binding.apply {


    orderOneCupcake.setOnClickListener {
        viewModel.setQuantity(1)
        moveFragment()
    }

    orderSixCupcakes.setOnClickListener {
        viewModel.setQuantity(6)
        moveFragment()
    }

    orderTwelveCupcakes.setOnClickListener {
        viewModel.setQuantity(12)
        moveFragment()
    }
}
    }


    fun isENTRYVALID(): Boolean{
        if (viewModel.isEntryValid(
                viewModel.flavor.value.toString(),
                viewModel.price.value.toString(),
                viewModel.quantity.value.toString()

        )){
            return true
        }
        return false
    }
    fun moveFragment(){
        if (isENTRYVALID()){
        viewModel.addNewItem(

        viewModel.flavor.value.toString(),
            viewModel.price.value.toString(),
            viewModel.quantity.value.toString(),

            )
            val action = QuanitityFragmentDirections.actionQuanitityFragment2ToItemListFragment()
            findNavController().navigate(action)
        }

    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */


    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        _binding = null
    }
}