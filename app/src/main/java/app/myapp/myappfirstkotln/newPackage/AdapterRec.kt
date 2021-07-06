package app.myapp.myappfirstkotln.newPackage

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.Family
import app.myapp.myappfirstkotln.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class AdapterRec(var arrayList:ArrayList<Notes>,var context:Context) : RecyclerView.Adapter<AdapterRec.MyHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRec.MyHolder {

        var view=LayoutInflater.from(parent.context).inflate(R.layout.split_newmain,null)

        return MyHolder(view)


    }

    override fun getItemCount()=arrayList.size



    override fun onBindViewHolder(holder: AdapterRec.MyHolder, position: Int) {


        var note=arrayList[position]

        holder.title.text=note.title
        holder.notes.text=note.note
        holder.time.text=note.timestamp

        note.note

        var database= FirebaseDatabase.getInstance().getReference("Notes")
        holder.bup.setOnClickListener {


            var alert= AlertDialog.Builder(context)

            alert.setTitle("تحديث البيانات")

            var view=LayoutInflater.from(context).inflate(R.layout.split_alert,null)

            val n=view.findViewById<TextView>(R.id.up_name)

            val a=view.findViewById<TextView>(R.id.up_age)

            n.text=note.title
            a.text=note.note




            alert.setView(view)

            alert.setPositiveButton("تحديث",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    val fm= Notes(note.id,n.text.toString(),a.text.toString(),note.timestamp)



                    database.child(fm.id).setValue(fm).addOnCompleteListener{

                        Toast.makeText(context,"تم التحديث", Toast.LENGTH_SHORT).show()
                    }


                }


            })

            alert.setNegativeButton("الغاء",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    dialog!!.dismiss()
                }


            })

            alert.show()

        }










        holder.bdel.setOnClickListener {

            var database= FirebaseDatabase.getInstance().getReference("Notes")


            database.child(note.id).removeValue()



        }



    }




    class MyHolder(item: View) :RecyclerView.ViewHolder(item) {

        var  title=item.findViewById<TextView>(R.id.split_title)


        var notes=item.findViewById<TextView>(R.id.text_note)



        var time=item.findViewById<TextView>(R.id.split_time)

        var bup=item.findViewById<Button>(R.id.but_up)

        var bdel=item.findViewById<Button>(R.id.but_del)




    }
}