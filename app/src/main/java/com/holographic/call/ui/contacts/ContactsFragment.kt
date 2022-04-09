package com.holographic.call.ui.contacts

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.holographic.call.R
import com.holographic.call.base.BaseActivity
import com.holographic.call.base.BaseFragment
import com.holographic.call.databinding.ContactsFragmentsBinding
import com.holographic.call.model.contact.UserContact
import com.holographic.call.ui.call.CallActivity
import com.holographic.call.ui.call.dialog.NumberDialog
import com.holographic.call.ui.contact.ContactFragment
import com.holographic.call.ui.contacts.ContactsFragment.Mode.DEFAULT
import com.holographic.call.ui.dial.fragment.DialFragment
import com.holographic.call.ui.home.HomeFragment
import com.holographic.call.ui.main.MainActivity
import com.holographic.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactsFragment : BaseFragment<ContactsViewModel, ContactsFragmentsBinding>(R.layout.contacts_fragments) {

    val viewModel: ContactsViewModel by viewModel { parametersOf(mode, this) }

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: DEFAULT }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"

        fun newInstance(mode: Mode = DEFAULT) = ContactsFragment().apply {
            arguments = bundleOf(ARGUMENT_MODE to mode)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        binding.recyclerContacts.addItemDecoration(getNewItemDecoration())
        binding.buttonBack.setOnClickListener(::onBackPressed)
        binding.buttonCall.setOnClickListener {
            activityAs<MainActivity>().addFragment(DialFragment())
        }
        binding.buttonApply.setOnClickListener {
            applyThemeToContacts(viewModel.contactsRepository.contacts)
        }
        viewModel.onContactSelected.observe(this) {
            applyThemeToContacts(listOf(it))
        }
        viewModel.openContact.observe(this) {
            activityAs<MainActivity>().addFragment(ContactFragment.newInstance(it))
        }
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        viewModel.selectInterlocutorNumber.observe(this) {
            NumberDialog.newInstance(it).show(parentFragmentManager)
        }
    }

    private fun onBackPressed() {
        (requireActivity() as? MainActivity)?.onBackPressed()
        (requireActivity() as? CallActivity)?.removeNoneCallFragment(this)
    }

    fun addInterlocutor(number: String) {
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            if (it) activityAs<BaseActivity<*, *>>().call(number)
            if (mode == Mode.INTERLOCUTOR_SELECTOR) activityAs<CallActivity>().removeNoneCallFragment(this)
        }
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        activityAs<MainActivity>().fragment(HomeFragment::class.java)?.viewModel?.applyThemeToContacts(contacts)
        requireActivity().onBackPressed()
    }

    private fun getNewItemDecoration() = object : RecyclerView.ItemDecoration() {

        private val paint = Paint().apply { color = Color.parseColor("#EDEDED") }

        private val lineWidth by lazy { binding.root.width / 360f * 320 }
        private val lineHeight by lazy { binding.root.width / 360f * 1 }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            for (i in 1 until parent.childCount) {
                c.drawRect(
                    (parent.width - lineWidth) / 2,
                    parent.children.first().height * i - lineHeight / 2,
                    parent.width - (parent.width - lineWidth) / 2,
                    parent.children.first().height * i + lineHeight / 2,
                    paint
                )
            }
        }

    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}