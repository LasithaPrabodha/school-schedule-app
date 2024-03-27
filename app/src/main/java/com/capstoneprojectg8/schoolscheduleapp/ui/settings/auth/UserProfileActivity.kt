package com.capstoneprojectg8.schoolscheduleapp.ui.settings.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstoneprojectg8.schoolscheduleapp.R
import com.capstoneprojectg8.schoolscheduleapp.databinding.ActivityUserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserProfileActivity : AppCompatActivity() {

    private var _binding: ActivityUserProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    var adapter: ArrayAdapter<String>? = null

    private val viewModel: UserProfileViewModel by viewModels<UserProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUserProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("53599845427-cm626895kfifqdfkthtt29sve7vuepak.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth


        signIn()
        setupAutoComplete()
        observeSearchResult()
        loadSaveInfo()
        onSaveButtonTapped()

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun loadSaveInfo() {

        try {

            val sh = getSharedPreferences("UserUniversity", MODE_PRIVATE)
            val university = sh.getString("university", "")
            val year = sh.getInt("year", 0)

            binding.textInputUniversity.editText?.setText(university)
            binding.editTextYear.setText(year.toString())
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun onSaveButtonTapped() {
        binding.buttonSubmit.setOnClickListener {
            val sharedPreferences = getSharedPreferences("UserUniversity", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()

            myEdit.putString("university", binding.textInputUniversity.editText?.text.toString())
            myEdit.putInt("year", binding.editTextYear.text.toString().toInt())
            myEdit.apply()

            finish()
        }
    }


    private fun setupAutoComplete() {
        val autoComplete = binding.autoCompleteUniversity
        adapter = ArrayAdapter(this, R.layout.list_item, emptyList())

        autoComplete.setAdapter(adapter)
        var searchFor = ""

        autoComplete.addTextChangedListener {
            val searchText = it.toString().trim()
            if (searchText == searchFor)
                return@addTextChangedListener

            searchFor = searchText

            lifecycleScope.launch {
                delay(300)  //debounce timeOut
                if (searchText != searchFor)
                    return@launch

                viewModel.searchUniversities(it.toString())
            }
        }

    }

    private fun observeSearchResult() {
        viewModel.universities.observe(this) {

            when (it) {
                is UserProfileViewModel.UniversitySearchState.Success -> {
                    adapter?.clear()
                    adapter?.addAll(it.data.map { it.name })
                    adapter?.notifyDataSetChanged()
                }

                is UserProfileViewModel.UniversitySearchState.Loading -> {

                }

                is UserProfileViewModel.UniversitySearchState.Error -> {
                    Log.e(TAG, it.throwable.message.toString())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.profile_tools, menu)
        return true
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(user: FirebaseUser?) {
        binding.progressCircular.visibility = View.GONE

        binding.username.text = user?.displayName
        binding.email.text = user?.email

        Glide
            .with(this)
            .load(user?.photoUrl)
            .centerCrop()
            .into(binding.userDp)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
            }
        }
        return true
    }

    companion object {
        private const val TAG = "UserActivity"
        private const val RC_SIGN_IN = 9001
    }
}