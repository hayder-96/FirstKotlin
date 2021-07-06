package app.myapp.myappfirstkotln

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {


    lateinit var r:RecyclerView

    lateinit var text:TextView
    lateinit var context: Context
    var firebase= FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)




        database=FirebaseDatabase.getInstance().getReference("food")

        var h=FragHome()
        var r=FragmentRequest()
        var d=RequestDone()

        Frag(h)

        var navbar=findViewById<BottomNavigationView>(R.id.nav_bar)


        navbar.setOnNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.home -> {
                    Frag(h)
                }
                R.id.request -> {
                    Frag(r)
                }

                R.id.done_request->{

                    Frag(d)
                }
            }
            true

        }




//        list.add(Family("1","hayder","25"))
//        var a=myAdapter(list,baseContext)
//        r.adapter=a



        firebase.addAuthStateListener{

            if (firebase.currentUser==null){
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }


    private fun Frag(fragment:Fragment){

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.frame,fragment)
            commit()
        }
    }








    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when(item.itemId){

           R.id.log_out->{
               firebase.signOut()
           }
           R.id.add_item->{
               var intent= Intent(baseContext,MainActivity2::class.java)
               startActivity(intent)
           }



       }




        return super.onOptionsItemSelected(item)
    }
}