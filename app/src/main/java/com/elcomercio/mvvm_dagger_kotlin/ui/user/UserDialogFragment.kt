package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.elcomercio.mvvm_dagger_kotlin.R
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.model.request.UserRequest
import kotlinx.android.synthetic.main.user_dialog_fragment.*

class UserDialogFragment : DialogFragment() {

    private var listener: OnNewUserDialogListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.user_dialog_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)

        listener = activity as OnNewUserDialogListener

        btnCreateUser.setOnClickListener {
            val userRequest = UserRequest(
                    etFirstName.text.toString(),
                    etLastName.text.toString(),
                    etEmail.text.toString())
            listener!!.onCreateClickListener(userRequest)
        }
    }

    companion object {
        fun newInstance(): UserDialogFragment {
            return UserDialogFragment()
        }
    }

    interface OnNewUserDialogListener {
        fun onCreateClickListener(userRequest: UserRequest)
    }
}