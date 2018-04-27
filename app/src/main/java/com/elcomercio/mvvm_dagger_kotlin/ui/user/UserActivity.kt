package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.util.Log
import com.elcomercio.mvvm_dagger_kotlin.R
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.utils.Status
import com.elcomercio.mvvm_dagger_kotlin.utils.showToast
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.content_user.*
import javax.inject.Inject

class UserActivity : DaggerAppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private var userList: MutableList<UserEntity> = mutableListOf()

    @Inject
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)
        initialize()
    }

    private fun initialize() {
        //Setting up Adapter
        userAdapter = UserAdapter(userList, {
            //Click User Item
            showToast("User: ${it.id}")
        })
        rvUser.adapter = userAdapter

        setUpUserListObserver()
        userViewModel.loadUser(1)

        //Setting up Listeners
        fab.setOnClickListener { _ ->
            //Todo INSERT USER

            setUpUserListtObserver()
        }
    }

    private fun setUpUserListtObserver(){
        userViewModel.getUsers().observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> {
                    Log.i("successsss","successss ${it.data!!.body!!.data!!.size}")

                    //userAdapter.addAllUsers(it.data.body.)
                }
                Status.ERROR -> {
                    showSnackBar(it.message!!)
                }
                Status.LOADING -> {
                    showToast("Loading...")
                }
            }
        })
    }

    private fun setUpUserListObserver() {
        userViewModel.userResourceLiveData.observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> {
                    userAdapter.insertUserItem(it.data!!)
                }
                Status.ERROR -> {
                    showSnackBar(it.message!!)
                }
                Status.LOADING -> {
                    showToast("Loading...")
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
