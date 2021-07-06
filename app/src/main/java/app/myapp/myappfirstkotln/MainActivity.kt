package app.myapp.myappfirstkotln

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.myapp.myappfirstkotln.newPackage.NewActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    lateinit var rec: RecyclerView
    lateinit var text: TextView
    lateinit var edit: EditText
    lateinit var editPassword: EditText
  lateinit  var googleSignInClient:GoogleSignInClient
    lateinit var callbackManager: CallbackManager


    lateinit var firebase :FirebaseAuth


    companion object {
        var id = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        edit = findViewById(R.id.edit_name)

        editPassword = findViewById(R.id.edit_password)


        firebase= FirebaseAuth.getInstance()



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)









        var buttonFacebookLogin = findViewById<LoginButton>(R.id.login_button)

        callbackManager = CallbackManager.Factory.create()

        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                handleFacebookAccessToken(loginResult.accessToken)


            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }
        })

    }



    private fun handleFacebookAccessToken(token: AccessToken) {


        val credential = FacebookAuthProvider.getCredential(token.token)
        firebase.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    var intent = Intent(baseContext, MainActivity3::class.java)

                    startActivity(intent)
                    val user = firebase.currentUser


                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,task.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()


                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }









    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode ==5) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebase.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = firebase.currentUser.uid

                    Toast.makeText(baseContext,user.toString(),Toast.LENGTH_LONG).show()

                    var intent = Intent(baseContext, MainActivity3::class.java)


                    startActivity(intent)

                } else {

                    Toast.makeText(baseContext, task.exception!!.message.toString(),Toast.LENGTH_LONG).show()


                }
            }
    }




    fun ok(view: View) {



        val f = firebase.currentUser

                firebase.createUserWithEmailAndPassword(
                    edit.text.toString(),
                    editPassword.text.toString()

                )


                        .addOnCompleteListener(this) { task ->


                        if (task.isSuccessful) {
                            Toast.makeText(baseContext,"done reg", Toast.LENGTH_SHORT).show()
                            f?.sendEmailVerification()?.addOnCompleteListener(this) {task->

                                if (task.isSuccessful) {
                                    Toast.makeText(baseContext,"done send", Toast.LENGTH_SHORT).show()

                        } else {
                            text.text = task.exception?.message
                        }

                    }
            }else{
                Toast.makeText(baseContext,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(view: View) {


        firebase.signInWithEmailAndPassword(edit.text.toString(), editPassword.text.toString())

            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    val f = firebase.currentUser

                    if (f.isEmailVerified) {

                        var intent = Intent(baseContext, MainActivity3::class.java)

                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "لم يتم التحقق", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    text.text = task.exception?.message
                }
            }


    }

    fun google(view: View) {


        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,5)
    }
}










