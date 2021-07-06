package app.myapp.myappfirstkotln.newPackage

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewActivity : AppCompatActivity() {



    lateinit var rec:RecyclerView
    lateinit var floting:FloatingActionButton
    lateinit var arrayList:ArrayList<Notes>
    lateinit var con:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)


        rec=findViewById(R.id.rec_notes)
        floting=findViewById(R.id.floatingActionButton)

        arrayList=ArrayList()

        con=this



        var database=FirebaseDatabase.getInstance().getReference("Notes")

        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                arrayList.clear()
                if(snapshot.exists()){

                    for (n in snapshot.children){

                        arrayList.add(0,n.getValue(Notes::class.java)!!)
                        

                    }
                    
                    var dapter=AdapterRec(arrayList,con)
                    rec.adapter=dapter
                    rec.layoutManager=LinearLayoutManager(baseContext)
                    rec.setHasFixedSize(true)

                    
                }
            }


        })









        floting.setOnClickListener{


            var database=FirebaseDatabase.getInstance().getReference("Notes")
            var alert=AlertDialog.Builder(this)

            alert.setTitle("ادخل الملاحضة")

            var view=LayoutInflater.from(this).inflate(R.layout.alert_notes,null)

            var title=view.findViewById<EditText>(R.id.edit_title)
            var note=view.findViewById<EditText>(R.id.edit_note)




            alert.setView(view)

            alert.setPositiveButton("اضافة",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    var t=title.text.toString()
                    var n=note.text.toString()

                    var id=database.push().key.toString()
                    var N=Notes(id,t,n,getTime())

                    database.child(id).setValue(N)

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



    fun getTime():String{
        var calnder=Calendar.getInstance()
        var simble=SimpleDateFormat("EEEE hh:mm a")
        var date=simble.format(calnder.time)

        return  date
    }
}

