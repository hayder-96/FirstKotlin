package app.myapp.myappfirstkotln.InfoUser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.R
import com.squareup.picasso.Picasso

class AdapterFoodreq(var list:ArrayList<ItemFoodRequest>, var context: Context) : RecyclerView.Adapter<AdapterFoodreq.MyHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFoodreq.MyHolder {


        var item=LayoutInflater.from(parent.context).inflate(R.layout.split_part,parent,false)

        return MyHolder(item)
    }

    override fun getItemCount()=list.size





    override fun onBindViewHolder(holder: AdapterFoodreq.MyHolder, position: Int) {


        var item:ItemFoodRequest=list[position]

        holder.fname.text=item.name



        Picasso.get().load(item.image).resize(300,300).into(holder.image)





    }


    class MyHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var fname:TextView=itemView.findViewById(R.id.textView_split_part)
       var image=itemView.findViewById<ImageView>(R.id.image_split_part)



    }



}