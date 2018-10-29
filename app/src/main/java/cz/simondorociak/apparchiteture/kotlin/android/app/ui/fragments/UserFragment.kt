package cz.simondorociak.apparchiteture.kotlin.android.app.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.simondorociak.apparchiteture.kotlin.android.app.R
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import cz.simondorociak.apparchiteture.kotlin.android.app.databinding.FragmentUserBinding
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels.UserViewModel
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserFragment : BaseFragment() {

    companion object {
        val TAG : String = UserFragment::class.java.simpleName
        fun newInstance() : UserFragment {
            return UserFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: UserViewModel
    lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.init("JakeWharton")
        viewModel.getUser().observe(viewLifecycleOwner, Observer<User> { updateUI(it) })
    }

    private fun updateUI(user: User?) {
        user?.let {
            Timber.d("User UI will be updated")
            binding.imageUser.loadURL(user.avatarUrl)
            binding.textName.text = user.name
            binding.textInfo.text = user.company
        }
    }
}