package cz.simondorociak.apparchiteture.kotlin.android.app.ui.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.simondorociak.apparchiteture.kotlin.android.app.R
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels.UserViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_user.*
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.getUser("JakeWharton").observe(viewLifecycleOwner, Observer<Resource<User>> { data ->
            update(data)
        })
    }

    private fun update(data: Resource<User>?) {
        data?.let {
            Timber.d("User UI will be updated")
            if (it.status.isLoading()) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
            if (it.status.isSuccessful()) {
                imageUser.loadURL(it.data?.avatarUrl)
                textName.text = it.data?.name
                textInfo.text = it.data?.company
                layoutUser.visibility = View.VISIBLE
                textError.visibility = View.GONE
            } else if (it.status.isError()) {
                Timber.d("Error: ${it.message}, ${it.code}")
                textError.visibility = View.VISIBLE
                layoutUser.visibility = View.GONE
            }
        }
    }
}