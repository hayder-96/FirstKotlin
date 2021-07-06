package app.myapp.myappfirstkotln.InfoUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.R
import app.myapp.myappfirstkotln.notyUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailsRequest : AppCompatActivity() {

    lateinit var r:RecyclerView
    lateinit var list:ArrayList<ItemFoodRequest>
    lateinit var id:String
    lateinit var us:String
    lateinit var namee:String
    lateinit var address:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_request)


        r=findViewById(R.id.rec_request)

        list= ArrayList()

       id=intent.getStringExtra("id")
         us=intent.getStringExtra("userid")
        namee=intent.getStringExtra("name")
        address=intent.getStringExtra("address")

        Toast.makeText(this,us+"user_id",Toast.LENGTH_LONG).show()


        var database = FirebaseDatabase.getInstance().getReference(id)

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {



                if (snapshot!!.exists()){

                    list.clear()
                    for (f in snapshot.children){

                        var fm=f.getValue(ItemFoodRequest::class.java)



                        list.add(fm!!)


                    }


                    var a=AdapterFoodreq(list,baseContext)
                    r.adapter=a
                    r.layoutManager= LinearLayoutManager(baseContext)
                    r.setHasFixedSize(true)
                }
            }

        })


    }

    fun doneReq(view: View) {


        var database = FirebaseDatabase.getInstance().getReference("doneReq")


        var i=info(id,namee,address,us)
        database.child(id).setValue(i).addOnCompleteListener{

            var databas = FirebaseDatabase.getInstance().getReference("request")

            databas.child(id).removeValue()


            var code="$us notyNo"

            var databa = FirebaseDatabase.getInstance().getReference(code)


            var nono=notyUser(databa.push().key.toString(),"حيدر","تم التوصيل","no")

            databa.child(nono.id).setValue(nono).addOnCompleteListener{



            }




        }










    }
}