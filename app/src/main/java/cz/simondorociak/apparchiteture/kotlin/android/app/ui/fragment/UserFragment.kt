package cz.simondorociak.apparchiteture.kotlin.android.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cz.simondorociak.apparchiteture.kotlin.android.app.R
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel.UserViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_user.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.loadUser("JakeWharton")
        viewModel.user.observe(this, Observer {
            progressBar.visibility = if (it is Resource.Loading) View.VISIBLE else View.GONE
            when(it) {
                is Resource.Success -> {
                    Timber.tag(TAG).d("User load success")
                    it.data?.let { update(it) } ?: activity?.contentView?.snackbar(getString(R.string.msg_something_went_wrong))
                }
                is Resource.Error -> {
                    Timber.tag(TAG).e("User load error")
                    activity?.contentView?.snackbar(getString(R.string.msg_something_went_wrong))
                }
            }
        })
    }

    private fun update(data: User) {
        imageUser.loadURL(data.avatarUrl)
        textName.text = data.name
        textInfo.text = data.company
    }
}