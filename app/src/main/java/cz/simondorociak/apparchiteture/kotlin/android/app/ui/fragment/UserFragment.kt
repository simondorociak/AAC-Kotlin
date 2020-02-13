package cz.simondorociak.apparchiteture.kotlin.android.app.ui.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cz.simondorociak.apparchiteture.kotlin.android.app.R
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_user

    companion object {

        val TAG : String = UserFragment::class.java.name

        fun newInstance() : UserFragment {
            return UserFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner, Observer {
            progressBar.isVisible = it is Resource.Loading
            when(it) {
                is Resource.Success -> {
                    it.data?.let { data -> update(data) } ?: activity?.contentView?.snackbar(getString(R.string.msg_something_went_wrong))
                }
                is Resource.Error -> {
                    activity?.contentView?.snackbar(getString(R.string.msg_something_went_wrong))
                }
            }
        })
        viewModel.loadUser("JakeWharton")
    }

    private fun update(data: User) {
        imageUser?.loadURL(data.avatarUrl)
        textName?.text = data.name
        textInfo?.text = data.company
    }
}