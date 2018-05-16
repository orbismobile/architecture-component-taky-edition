package com.elcomercio.mvvm_dagger_kotlin.ui.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.util.Log
import com.elcomercio.mvvm_dagger_kotlin.R
import com.elcomercio.mvvm_dagger_kotlin.ui.user.UserViewModel
import com.elcomercio.mvvm_dagger_kotlin.utils.Status
import com.elcomercio.mvvm_dagger_kotlin.utils.showToast
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val userId = intent.getIntExtra("USER_ID", 0)

        Log.e("USER ID", "USER ID - $userId")
        userViewModel.loadUser(userId)

        settingUpGetUserByIdObserver()
    }

    private fun settingUpGetUserByIdObserver() {
        userViewModel.getSpecificUserResourceLiveData.observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> {
                    tvUserId.text = it.data!!.id.toString()
                    tvFirstName.text = it.data.name
                    tvLastName.text = it.data.lastName
                }
                Status.ERROR -> {
                    showSnackBar(it.message!!)
                }
                Status.LOADING -> {
                    showToast("Loading Detail...")
                }
            }
        })
    }

    private fun showSnackBar(@StringRes errorMessage: String) {
        Snackbar
                .make(clGeneral, errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, { _ -> userViewModel.retryLoadUser() })
                .show()
    }
}
