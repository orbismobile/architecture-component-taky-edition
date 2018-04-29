package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.elcomercio.mvvm_dagger_kotlin.R
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request.UserRequest
import kotlinx.android.synthetic.main.user_dialog_fragment.*

class UserDialogFragment : DialogFragment() {

    private var listener: OnNewUserDialogListener? = null

    private var firstName = ""
    private var lastName = ""
    private var email = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.user_dialog_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)

        firstName = arguments!!.getString(TAG_FIRST_NAME)
        lastName = arguments!!.getString(TAG_LAST_NAME)
        email = arguments!!.getString(TAG_EMAIL)

        listener = activity as OnNewUserDialogListener

        btnCreateUser.setOnClickListener {
            val userRequest = UserRequest(
                    etFirstName.text.toString(),
                    etLastName.text.toString(),
                    etEmail.text.toString())
            listener!!.onCreateClickListener(userRequest)
            this.dismiss()
        }
    }

    companion object {

        private const val TAG_FIRST_NAME = "TAG_FIRST_NAME"
        private const val TAG_LAST_NAME = "TAG_LAST_NAME"
        private const val TAG_EMAIL = "TAG_EMAIL"

        fun newInstance(firstName: String, lastName: String, email: String): UserDialogFragment {
            val fragment = UserDialogFragment()
            val bundle = Bundle().also {
                it.putString(TAG_FIRST_NAME, firstName)
                it.putString(TAG_LAST_NAME, lastName)
                it.putString(TAG_EMAIL, email)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    interface OnNewUserDialogListener {
        fun onCreateClickListener(userRequest: UserRequest)
    }
}