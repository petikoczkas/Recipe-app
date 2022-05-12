package hu.bme.aut.receptek.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.receptek.databinding.ItemFoodListBinding
import hu.bme.aut.receptek.DescriptionActivity
import hu.bme.aut.receptek.data.Item


class FoodAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    var etelType: Int? = null

    private val items = mutableListOf<Item>()
    private var eloitems = ArrayList<Int>()
    private var foitems = ArrayList<Int>()
    private var desszitems = ArrayList<Int>()

    private fun makeArrays(){

        var temp = ArrayList<Int>()
        for(item: Item in items){
            if(etelType==1 && item.category.name.equals("Előétel")){
                temp.add(items.indexOf(item))
            }
            else if(etelType==2 && item.category.name.equals("Főétel")){
                temp.add(items.indexOf(item))
            }
            else if(etelType==3 && item.category.name.equals("Desszert")){
                temp.add(items.indexOf(item))
            }
        }
        eloitems.clear()
        foitems.clear()
        desszitems.clear()
        if(etelType==1) eloitems=temp
        else if(etelType==2) foitems=temp
        else if(etelType==3) desszitems=temp

    }

    private fun makebinding(holder: FoodViewHolder, Item: Item){
        holder.binding.cbIsLiked.isChecked = Item.isLiked
        holder.binding.tvName.text = Item.name
        holder.binding.tvCategory.text = Item.category.name
        holder.binding.tvTime.text = "${Item.estimatedTime} perc"

        holder.binding.cbIsLiked.setOnCheckedChangeListener { buttonView, isChecked ->
            Item.isLiked = isChecked
            listener.onItemChanged(Item)
        }
        holder.binding.ibRemove.setOnClickListener{
            listener.onItemDeleted(Item)
        }
        holder.binding.ibDescription.setOnClickListener{
            val intent = Intent(it.context, DescriptionActivity::class.java)
            intent.putExtra("DESC", Item.description)
            it.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FoodViewHolder(
        ItemFoodListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        makeArrays()


        if(etelType==1){
            val Item = items[eloitems.get(position)]

            makebinding(holder, Item)
        }
        else if(etelType==2){
            val Item = items[foitems.get(position)]

            makebinding(holder, Item)
        }
        else{
            val Item = items[desszitems.get(position)]

            makebinding(holder, Item)
        }



    }


    override fun getItemCount(): Int{
        var cnt = 0
        for(item: Item in items){
            if(etelType==1 && item.category.name.equals("Előétel")) cnt++
            else if(etelType==2 && item.category.name.equals("Főétel")) cnt++
            else if(etelType==3 && item.category.name.equals("Desszert")) cnt++

        }
        return cnt
    }

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(Items: List<Item>) {
        items.clear()
        items.addAll(Items)
        notifyDataSetChanged()
    }

    fun delete(Item: Item){
        items.remove(Item)
        notifyDataSetChanged()
    }



    interface ItemClickListener {
        fun onItemChanged(item: Item)
        fun onItemDeleted(item: Item)
    }

    inner class FoodViewHolder(val binding: ItemFoodListBinding) : RecyclerView.ViewHolder(binding.root){

    }
}
