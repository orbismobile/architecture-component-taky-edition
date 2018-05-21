package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
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

        //Fetch all users at the first time
        userViewModel.loadAllUsers(true)

        //Setting up Listeners
        srlRefresh.setOnRefreshListener {
            if (srlRefresh.isRefreshing) srlRefresh.isRefreshing = false
            userViewModel.retryLoadAllUsers()
        }

        fab.setOnClickListener {
            showUserDialogFragment()
        }

        subscribeToUserModel()
    }

    private fun subscribeToUserModel() {
        //OBSERVER GET ALL USERS
        userViewModel.getAllUsersResourceLiveData.observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> {
                    userAdapter.addAllUsers(it.data!!)
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
                Status.LOADING -> {
                    showToast("Loading Users...")
                }
            }
        })

        //OBSERVER POST NEW USER
        userViewModel.postUserResourceLiveData.observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> {
                    userAdapter.addUser(it.data!!)
                    userDialogFragment.dismiss()
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
                Status.LOADING -> {
                    showToast("Saving User...")
                }
            }
        })
    }

    /**
     * Show User Dialog Fragment.
     */
    private fun showUserDialogFragment() {
        userDialogFragment = UserDialogFragment.newInstance()
        userDialogFragment.show(supportFragmentManager, TAG_USER_DIALOG_FRAGMENT)
    }

    /**
     * Listener when user click Create New User button.
     */
    override fun onCreateClickListener(userRequest: UserRequest) {
        userViewModel.saveUserOnFromServer(userRequest)
    }
}
