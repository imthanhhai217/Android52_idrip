package com.vndevpro.android52_idrip.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vndevpro.android52_idrip.R

class AccountViewModel(private val application: Application) : AndroidViewModel(application) {
    private var auth: FirebaseAuth = Firebase.auth
    private var currentUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    var _currentUser: MutableLiveData<FirebaseUser?> = currentUser
    private val signInRequest =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            application.getString(
                R.string.client_id
            )
        ).requestEmail().build()

    init {
        currentUser.value = auth.currentUser
    }

    private val signInClient: GoogleSignInClient =
        GoogleSignIn.getClient(application, signInRequest)
    lateinit var callback: OnSignInStated

    fun signInWithOneTap() {
        callback.startSignIn(signInClient)
    }

    fun signOut() {
        Firebase.auth.signOut()
        currentUser.value = null
    }

    fun firebaseAuthWithToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnSuccessListener {
            updateData(it)
        }.addOnFailureListener {
            currentUser.value = null
            Log.d("TAG", "addOnFailureListener: ${it.message}")
            Toast.makeText(application, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(it: AuthResult?) {
        if (it != null) {
            currentUser.value = it.user
            Toast.makeText(application, "Sign in success", Toast.LENGTH_SHORT).show()
        } else {
            currentUser.value = null
            Toast.makeText(application, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun registerWithEmailAndPassword(email: String, passwords: String) {
        auth.createUserWithEmailAndPassword(email, passwords).addOnSuccessListener {
            updateData(it)
        }.addOnFailureListener {
            currentUser.value = null
            Log.d("TAG", "addOnFailureListener: ${it.message}")
            Toast.makeText(application, "Register failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun signInWithEmailAndPassword(email: String, passwords: String) {
        auth.signInWithEmailAndPassword(email, passwords).addOnSuccessListener {
            updateData(it)
        }.addOnFailureListener {
            currentUser.value = null
            Log.d("TAG", "addOnFailureListener: ${it.message}")
            Toast.makeText(application, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    interface OnSignInStated {
        fun startSignIn(signInClient: GoogleSignInClient)
    }
}