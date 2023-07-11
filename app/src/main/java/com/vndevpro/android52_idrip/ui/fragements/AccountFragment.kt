package com.vndevpro.android52_idrip.ui.fragements

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.databinding.FragmentAccountBinding
import com.vndevpro.android52_idrip.ui.MainActivity
import com.vndevpro.android52_idrip.ui.viewmodels.AccountViewModel
import com.vndevpro.android52_idrip.utils.PermissionHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AccountFragment : Fragment() {
    lateinit var accountViewModel: AccountViewModel
    lateinit var binding: FragmentAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        binding = FragmentAccountBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel = (activity as MainActivity).accountViewModel
        accountViewModel.callback = onSignInStatedListener

        binding.btnSignInOneTap.setOnClickListener {
            accountViewModel.signInWithOneTap()
        }
        binding.btnSignOut.setOnClickListener {
            accountViewModel.signOut()
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.edtUserName.text.toString()
            val passwords = binding.edtPassword.text.toString()
            accountViewModel.registerWithEmailAndPassword(email, passwords)
        }

        binding.btnSignInWithPasswords.setOnClickListener {
            val email = binding.edtUserName.text.toString()
            val passwords = binding.edtPassword.text.toString()
            accountViewModel.signInWithEmailAndPassword(email, passwords)
        }

        accountViewModel._currentUser.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })


        binding.btnPermission.setOnClickListener {
            PermissionHelper.askPermission(
                requestPermissionLauncher,
                activity as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback { isGranted ->
                if (isGranted) {
                    Log.d("TAG", ": requestPermissionLauncher granted")
                } else {
                    Log.d("TAG", ": requestPermissionLauncher denied")
                }
            })


    private fun updateUi(it: FirebaseUser?) {
        binding.user = it
    }

    private val onSignInStatedListener = object : AccountViewModel.OnSignInStated {
        override fun startSignIn(signInClient: GoogleSignInClient) {
            signInClient?.signInIntent?.let {
                signInLauncher.launch(it)
            }
        }
    }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                        val account = task.getResult(ApiException::class.java)
                        account.idToken?.let { token ->
                            accountViewModel.firebaseAuthWithToken(token)
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(activity, "Sign Failed : ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                if (it.resultCode == RESULT_CANCELED) {
                    Log.d("TAG", "RESULT_CANCELED : ")
                }
            })
}