package app.myapp.myappfirstkotln.InfoUser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.R

class AdapterInfo(var list:ArrayList<info>, var context: Context) : RecyclerView.Adapter<AdapterInfo.MyHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInfo.MyHolder {


        var item=LayoutInflater.from(parent.context).inflate(R.layout.split_request,parent,false)

        return MyHolder(item)
    }

    override fun getItemCount()=list.size





    override fun onBindViewHolder(holder: AdapterInfo.MyHolder, position: Int) {


        var item:info=list[position]

        holder.fname.text=item.name

        holder.address.text=item.adress

        var userid=item.user_id


        var id=item.id

        holder.itemView.setOnClickListener {

            var intent=Intent(context,DetailsRequest::class.java)
            intent.putExtra("id",id)
            intent.putExtra("userid",userid)
            intent.putExtra("name",item.name)
            intent.putExtra("address",item.adress)
            context.startActivity(intent)
        }


    }


    class MyHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var fname:TextView=itemView.findViewById(R.id.text_name_user)
       var address=itemView.findViewById<TextView>(R.id.text_address_user)



    }



}