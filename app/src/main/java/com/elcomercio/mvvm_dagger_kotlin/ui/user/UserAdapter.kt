package com.elcomercio.mvvm_dagger_kotlin.ui.user

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.elcomercio.mvvm_dagger_kotlin.R
import com.elcomercio.mvvm_dagger_kotlin.repository.local.db.entity.UserEntity
import com.elcomercio.mvvm_dagger_kotlin.utils.inflate
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * @author Carlos Vargas on 4/12/18.
 * @author Carlo Renzo on 4/12/18.
 * @version 1.0.5
 * @since 1.0.5
 */
class UserAdapter(private val listUserEntity: MutableList<UserEntity>,
                  val onUserItemClickListener: (UserEntity) -> Unit)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
            UserViewHolder(parent)

    override fun getItemCount(): Int = listUserEntity.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUserEntity[position])
    }

    inner class UserViewHolder(viewGroup: ViewGroup) :
            RecyclerView.ViewHolder(viewGroup.inflate(R.layout.item_user)) {

        private lateinit var userEntity: UserEntity

        init {
            itemView.setOnClickListener {
                onUserItemClickListener(userEntity)
            }
        }

        fun bind(userEntity: UserEntity) {
            this.userEntity = userEntity
            itemView.tvUserId.text = "${this.userEntity.name} - ${this.userEntity.id}"
        }
    }

    fun addAllUsers(listUserEntity: List<UserEntity>) {
        this.listUserEntity.clear()
        this.listUserEntity.addAll(listUserEntity)
        notifyDataSetChanged()
    }

    fun insertUserItem(userEntity: UserEntity) {
        listUserEntity.clear()
        listUserEntity.add(userEntity)
        notifyDataSetChanged()
    }
}