package app.myapp.myappfirstkotln

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.reflect.Array.get
import java.util.*

class MainActivity2 : AppCompatActivity() {

    lateinit var t:TextView
    var firebase=FirebaseAuth.getInstance()

    lateinit var edit_name:EditText
    lateinit var edit_age:EditText
    lateinit var storge:StorageReference
    lateinit var image: ImageView;
    lateinit var picasso:Picasso
      lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        edit_name=findViewById(R.id.edit_myname)


        image=findViewById(R.id.imageView)

        image.setImageResource(R.drawable.ic_baseline_add_24)




        image.setOnClickListener {

            val img=Intent(Intent.ACTION_PICK)
            img.type="image/*"
            startActivityForResult(img,1)

        }















    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1 && resultCode== Activity.RESULT_OK){

             uri= data?.data!!

        }
    }







    fun databa(view: View) {


        var name=edit_name.text.toString().trim()


         if (name.isEmpty()){

             edit_name.error="اضف الاسم"

             return
         }

        storge= FirebaseStorage.getInstance().reference
        var path=storge.child(Calendar.getInstance().time.toString())
        if (uri!=null) {


            path.putFile(uri).addOnSuccessListener {

                   path.downloadUrl.addOnSuccessListener {
                      var d= it.toString()


                       var database = FirebaseDatabase.getInstance().getReference("food")
                       var familyid = database.push().key.toString()
                       var fm = Family(familyid, d, name)

                       //  database.push().setValue(fm)
                       database.child(familyid).setValue(fm).addOnCompleteListener {
                           Toast.makeText(baseContext, "save", Toast.LENGTH_SHORT).show()
                           var intent=Intent(baseContext,MainActivity3::class.java)
                           startActivity(intent)

                       }
                   }

                }


        }

        }




}






