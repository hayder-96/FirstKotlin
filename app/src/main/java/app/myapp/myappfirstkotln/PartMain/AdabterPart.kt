package app.myapp.myappfirstkotln.PartMain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.R
import com.squareup.picasso.Picasso


class AdabterPart(var list:ArrayList<Item>,var con: Context) : RecyclerView.Adapter<AdabterPart.MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdabterPart.MyHolder {

        var view=LayoutInflater.from(parent.context).inflate(R.layout.split_part,parent,false)

      return  MyHolder(view)

    }

    override fun getItemCount() =list.size



    override fun onBindViewHolder(holder: AdabterPart.MyHolder, position: Int) {

        var item=list[position]

        holder.name.text=item.name

        Picasso.get().load(item.uri).resize(300,300).into(holder.image)


    }

    class MyHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var name: TextView =itemView.findViewById(R.id.textView_split_part)
        var image=itemView.findViewById<ImageView>(R.id.image_split_part)


    }
}

