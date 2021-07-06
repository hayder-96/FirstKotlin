package app.myapp.myappfirstkotln

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.PartMain.MainActivityPart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class myAdapter(var list:ArrayList<Family>, var context: Context) : RecyclerView.Adapter<myAdapter.MyHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAdapter.MyHolder {


        var item=LayoutInflater.from(parent.context).inflate(R.layout.split_main,parent,false)

        return MyHolder(item)
    }

    override fun getItemCount()=list.size





    override fun onBindViewHolder(holder: myAdapter.MyHolder, position: Int) {


        var item:Family=list[position]

        holder.fname.text=item.name

        var id=item.id

        var uri: Uri =Uri.parse(item.uri)
        Picasso.get().load(uri).resize(300,300).into(holder.image)




        var firebase= FirebaseAuth.getInstance()
        holder.image.setOnClickListener {

            var intent= Intent(context, MainActivityPart::class.java)

            intent.putExtra("id",item.id)
            context?.startActivity(intent)


        }
        holder.del.setOnClickListener{

            var data= FirebaseDatabase.getInstance().getReference("food")

            var data2= FirebaseDatabase.getInstance().getReference(item.id)
            data.child(id).removeValue()
            data2.removeValue()
        }


        holder.up.setOnClickListener{

            var alert=AlertDialog.Builder(context)

            alert.setTitle("تحديث البيانات")

            var view=LayoutInflater.from(context).inflate(R.layout.split_alert,null)

            val n=view.findViewById<TextView>(R.id.up_name)

            val a=view.findViewById<TextView>(R.id.up_age)






            alert.setView(view)

            alert.setPositiveButton("تحديث",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    val fm=Family(item.id,n.text.toString(),a.text.toString())

                    var database=FirebaseDatabase.getInstance().getReference("food")





                    database.child(fm.id).setValue(fm).addOnCompleteListener{

                        Toast.makeText(context,"تم التحديث",Toast.LENGTH_SHORT).show()
                    }


                }


            })

            alert.setNegativeButton("الغاء",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    dialog!!.dismiss()
                }


            })

            alert.show()

        }



    }


    class MyHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var fname:TextView=itemView.findViewById(R.id.t_name)
       var image=itemView.findViewById<ImageView>(R.id.imageView_split)

        var up:Button=itemView.findViewById(R.id.but_update)
        var del:Button=itemView.findViewById(R.id.but_delete)

    }



}