package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.util.Log
import com.elcomercio.mvvm_dagger_kotlin.R
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request.UserRequest
import com.elcomercio.mvvm_dagger_kotlin.ui.detail.DetailActivity
import com.elcomercio.mvvm_dagger_kotlin.utils.Status
import com.elcomercio.mvvm_dagger_kotlin.utils.TAG_USER_DIALOG_FRAGMENT
import com.elcomercio.mvvm_dagger_kotlin.utils.showToast
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.content_user.*
import javax.inject.Inject

class UserActivity : DaggerAppCompatActivity(), UserDialogFragment.OnNewUserDialogListener {

    private lateinit var userAdapter: UserAdapter
    private var userList: MutableList<UserEntity> = mutableListOf()

    @Inject
    lateinit var userViewModel: UserViewModel

    private lateinit var userDialogFragment: UserDialogFragment

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
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("USER_ID", it.id)
            startActivity(intent)
        })

        //Setting up RecyclerView
        rvUser.also {
            it.adapter = userAdapter
        }

        userViewModel.loadAllUsers()

        settingUpAllUserObserver()
        settingUpInsertUserObserver()

        srlRefresh.setOnRefreshListener {
            if (srlRefresh.isRefreshing) srlRefresh.isRefreshing = false
            userViewModel.loadAllUsers()
        }

        //Setting up Listeners
        fab.setOnClickListener {
            showUserDialogFragment()
        }
    }

    private fun settingUpAllUserObserver() {
        userViewModel.getAllUsersLiveData().observe(this, Observer {
            Log.i("ENTRAAAAA", "ENTRAAA ${it?.status}")

            when (it!!.status) {
                Status.SUCCESS -> {
                    userAdapter.addAllUsers(it.data!!)
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

    private fun settingUpInsertUserObserver() {
        userViewModel.getSaveUserOnServerLiveData().observe(this, Observer {
            Log.i("ENTRAAAAA", "ENTRAAA ${it?.status}")
            when (it!!.status) {
                Status.SUCCESS -> {
                    showToast(it.data!!.message)
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

    private fun showUserDialogFragment() {
        userDialogFragment = UserDialogFragment.newInstance("", "", "")
        userDialogFragment.show(supportFragmentManager, TAG_USER_DIALOG_FRAGMENT)
    }

    override fun onCreateClickListener(userRequest: UserRequest) {
        userViewModel.saveUserOnFromServer(userRequest)
    }
}
