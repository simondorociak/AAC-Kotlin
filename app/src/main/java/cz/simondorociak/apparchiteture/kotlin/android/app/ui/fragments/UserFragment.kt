package cz.simondorociak.apparchiteture.kotlin.android.app.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import cz.simondorociak.apparchiteture.kotlin.android.app.R
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import cz.simondorociak.apparchiteture.kotlin.android.app.ui.components.DynamicImageView
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

    @BindView(R.id.image_user)
    lateinit var imageUser : DynamicImageView
    @BindView(R.id.text_name)
    lateinit var textName : AppCompatTextView
    @BindView(R.id.text_info)
    lateinit var textInfo : AppCompatTextView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_user, container, false)
        ButterKnife.bind(this, root)
        return root
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
            imageUser.loadURL(user.avatarUrl)
            textName.text = user.name
            textInfo.text = user.company
        }
    }
}