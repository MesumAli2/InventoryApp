package com.example.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.databinding.FragmentChooseItemBinding
import com.google.android.material.snackbar.Snackbar


class ChooseItem : Fragment() {

    private var _binding: FragmentChooseItemBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    lateinit var item: Item

    private val viewModel: dataViewModel by activityViewModels {
        dataviewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChooseItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sendData()
    }

    fun sendData() {

        // Log.d("ID = ", "${navigationArgs.itemId}")

        binding.apply {

            val id = navigationArgs.itemId
            if (id > 0) {
                    viewModel.retrieveItem(id).observe(viewLifecycleOwner){
                        currentItem -> item = currentItem

                        //Checks the flavor
                        vanilla.isChecked = viewModel.flavor.value.equals(getString(R.string.vanilla))
                        chocolate.isChecked = viewModel.flavor.value.equals(getString(R.string.chocolate))
                        redVelvet.isChecked = viewModel.flavor.value.equals(getString(R.string.red_velvet))
                        saltedCaramel.isChecked = viewModel.flavor.value.equals(getString(R.string.salted_caramel))
                        coffee.isChecked = viewModel.flavor.value.equals(getString(R.string.coffee))
                        specialFlavor.isChecked = viewModel.flavor.value.equals(getString(R.string.special_flavor))

                        //saves it
                        vanilla.setOnClickListener { viewModel.setFlavor(getString(R.string.vanilla))
                        imageView.setImageResource(R.drawable.cupcakenoback)
                        }
                        chocolate.setOnClickListener { viewModel.setFlavor(getString(R.string.chocolate))
                        imageView.setImageResource(R.drawable.chocolatenoback)
                        }
                        redVelvet.setOnClickListener { viewModel.setFlavor(getString(R.string.red_velvet))
                        imageView.setImageResource(R.drawable.redvelvet)
                        }
                        saltedCaramel.setOnClickListener { viewModel.setFlavor(getString(R.string.salted_caramel))
                        imageView.setImageResource(R.drawable.saltedcaramel)
                        }
                        coffee.setOnClickListener { viewModel.setFlavor(getString(R.string.coffee))
                        imageView.setImageResource(R.drawable.coffeecupcake)
                        }
                        specialFlavor.setOnClickListener { viewModel.setFlavor(getString(R.string.special_flavor))
                        imageView.setImageResource(R.drawable.specialcupcake)
                        }



                        nextButton.setOnClickListener {
                            viewModel.updateoldItem(
                                item.id,
                                viewModel.flavor.value.toString(),
                                item.itemPrice.toString(),
                                item.quantityInStock.toString()
                            )
                            val action = ChooseItemDirections.actionChooseItem2ToItemDetailFragment(item.id)
                            findNavController().navigate(action)
                        }
                        cancelButton.setOnClickListener {
                            val action = ChooseItemDirections.actionChooseItem2ToItemListFragment()

                            findNavController().navigate(action)
                        }

                    }


            } else {
                vanilla.isChecked = viewModel.flavor.value.equals(getString(R.string.vanilla))
                vanilla.setOnClickListener {
                        imageView.setImageResource(R.drawable.cupcakenoback)
                    viewModel.setFlavor(getString(R.string.vanilla)) }

                chocolate.setOnClickListener { viewModel.setFlavor(getString(R.string.chocolate))
                    imageView.setImageResource(R.drawable.chocolatenoback)
                    viewModel.setFlavor(getString(R.string.chocolate))

                }
                redVelvet.setOnClickListener { viewModel.setFlavor(getString(R.string.red_velvet))
                    imageView.setImageResource(R.drawable.redvelvet)
                    viewModel.setFlavor(getString(R.string.red_velvet))
                }
                saltedCaramel.setOnClickListener { viewModel.setFlavor(getString(R.string.salted_caramel))
                imageView.setImageResource(R.drawable.saltedcaramel)
                    viewModel.setFlavor(getString(R.string.salted_caramel))
                }
                coffee.setOnClickListener { viewModel.setFlavor(getString(R.string.coffee))
                imageView.setImageResource(R.drawable.coffeecupcake)
                    viewModel.setFlavor(getString(R.string.coffee))
                }
                specialFlavor.setOnClickListener { viewModel.setFlavor(getString(R.string.special_flavor))
                imageView.setImageResource(R.drawable.specialcupcake)
                    viewModel.setFlavor(getString(R.string.special_flavor))
                }


                nextButton.setOnClickListener {
                    if (viewModel.isFlavorValid(viewModel.flavor.value.toString())) {
                        val action = ChooseItemDirections.actionChooseItem2ToQuanitityFragment2()
                        findNavController().navigate(action)
                    }
                    Snackbar.make(binding.root, "Select flavor", Snackbar.LENGTH_SHORT)
                        .show()
                }
                cancelButton.setOnClickListener {
                    val action = ChooseItemDirections.actionChooseItem2ToItemListFragment()
                    findNavController().navigate(action)
                }

            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}