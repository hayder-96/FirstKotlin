package app.myapp.myappfirstkotln.PartMain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.Family
import app.myapp.myappfirstkotln.R
import app.myapp.myappfirstkotln.myAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class MainActivityPart : AppCompatActivity() {

    lateinit var rec: RecyclerView
    lateinit var edit: EditText
    lateinit var id: String
    lateinit var list:ArrayList<Item>
    lateinit var context:Context
    var firebase= FirebaseAuth.getInstance()
    lateinit var storge: StorageReference
    lateinit var uri: Uri
    lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_part)


        edit = findViewById(R.id.edit_firstfood)
        rec = findViewById(R.id.rec_food)
        image=findViewById(R.id.imageView_part)

        context=this
        list= ArrayList()

        id = intent.getStringExtra("id")

        var database = FirebaseDatabase.getInstance().getReference(id)

        Picasso.get().load(R.drawable.ic_baseline_add_24).resize(300,300).into(image)

        
        image.setOnClickListener{


            val img= Intent(Intent.ACTION_PICK)
            img.type="image/*"
            startActivityForResult(img,1)
            
        }
        
        
        
        
        
        
        
        
        
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot!!.exists()){

                    list.clear()
                    for (f in snapshot.children){

                 
                        var fm=f.getValue(Item::class.java)



                        list.add(fm!!)

                        }


                    var a= AdabterPart(list,context)
                    rec.adapter=a
                    rec.layoutManager= LinearLayoutManager(baseContext)
                    rec.setHasFixedSize(true)
                }
            }

        })


    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1 && resultCode== Activity.RESULT_OK){

            uri= data?.data!!

        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    








    fun add(view: View) {

        storge= FirebaseStorage.getInstance().reference
        var path=storge.child(Calendar.getInstance().time.toString())
        
        
        if (uri !=null) {

            path.putFile(uri).addOnSuccessListener {

                path.downloadUrl.addOnSuccessListener {

                    var d= it.toString()
                    var database = FirebaseDatabase.getInstance().getReference(id)

                    var familyid = database.push().key.toString()
                    var fm = Item(familyid, edit.text.toString(),d)




                    database.child(fm.id).setValue(fm).addOnCompleteListener {
                        Toast.makeText(baseContext, "save", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }
}
