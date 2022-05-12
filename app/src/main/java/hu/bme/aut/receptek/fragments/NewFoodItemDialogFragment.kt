package hu.bme.aut.receptek.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.receptek.R
import hu.bme.aut.receptek.data.Item
import hu.bme.aut.receptek.databinding.DialogNewFoodItemBinding

class NewFoodItemDialogFragment : DialogFragment() {
    interface NewFoodItemDialogListener {
        fun onItemCreated(newItem: Item)
    }

    private lateinit var listener: NewFoodItemDialogListener

    private lateinit var binding: DialogNewFoodItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewFoodItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewFoodItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewFoodItemBinding.inflate(LayoutInflater.from(context))
        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_food_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onItemCreated(getItem())
                }
            }

            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getItem() = Item(
        name = binding.etName.text.toString(),
        description = binding.etDescription.text.toString(),
        estimatedTime = binding.etEstimatedPrice.text.toString().toIntOrNull() ?: 0,
        category = Item.Category.getByOrdinal(binding.spCategory.selectedItemPosition)
            ?: Item.Category.Főétel,
        isLiked = binding.cbAlreadyPurchased.isChecked
    )


    companion object {
        const val TAG = "NewFoodItemDialogFragment"
    }
}
