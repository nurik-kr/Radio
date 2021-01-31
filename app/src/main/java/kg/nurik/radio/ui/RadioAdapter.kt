package kg.nurik.radio.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurik.radio.R
import kg.nurik.radio.data.RadioStations
import kotlinx.android.synthetic.main.item_radio.view.*

class RadioAdapter(
    private val listener: (item: RadioStations) -> Unit
) : RecyclerView.Adapter<RadioViewHolder>() {

    private val list = arrayListOf<RadioStations>()
    private var lastActivatedPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_radio, parent, false)
        return RadioViewHolder(view)
    }

    override fun getItemCount() = list.size

    fun update(data: ArrayList<RadioStations>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            if(lastActivatedPos >=0)
                list[lastActivatedPos].isActivated = false

            lastActivatedPos = position
            list[position].isActivated = true
            listener.invoke(list[position])
            notifyDataSetChanged()
        }
    }
}

class RadioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(radioStations: RadioStations) {
        itemView.tvStation.text = radioStations.name
        itemView.tvDesc.text = radioStations.desc
        itemView.parentLayout.isActivated = radioStations.isActivated
    }
}