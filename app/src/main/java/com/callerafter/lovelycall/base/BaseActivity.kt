package com.callerafter.lovelycall.base

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.callerafter.lovelycall.repository.LocaleRepository
import com.callerafter.lovelycall.BR
import com.callerafter.lovelycall.utils.*
import org.koin.android.ext.android.inject
import java.util.*
import java.util.Locale

abstract class BaseActivity<TViewModel : BaseViewModel, TBinding : ViewDataBinding> :
    AppCompatActivity() {

    lateinit var binding: TBinding

    private val localeRepository: LocaleRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        binding.setVariable(BR.viewModel, provideViewModel())
        binding.lifecycleOwner = this
        localeRepository.localeObservable.observe(this) {
            if (needToChangeLocale(this)) recreate()
        }
        setupUI()
    }

    abstract fun provideLayoutId(): Int

    abstract fun setupUI()

    abstract fun provideViewModel(): TViewModel

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val locale = Locale(localeRepository.locale?.languageCode ?: return context)
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    private fun needToChangeLocale(context: Context) =
        (localeRepository.locale?.languageCode ?: "en") != with(context.resources.configuration) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales[0] else locale
        }.language

}
