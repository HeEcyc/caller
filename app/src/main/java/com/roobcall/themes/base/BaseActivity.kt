package com.roobcall.themes.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.Window
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.roobcall.themes.BR
import com.roobcall.themes.repository.LocaleRepository
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import org.koin.android.ext.android.inject
import java.util.*

abstract class BaseActivity<TViewModel : ActivityViewModel, TBinding : ViewDataBinding> :
    AppCompatActivity(), LauncherRegistrator {

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        binding.setVariable(BR.viewModel, provideViewModel())
        binding.lifecycleOwner = this
        provideViewModel().onLocaleChanged.observe(this) {
            if (needToChangeLocale(this)) {
                recreate()
            }
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
        val tmpLR by inject<LocaleRepository>()
        val locale = Locale(tmpLR.locale?.languageCode ?: return context)
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
        (provideViewModel().localeRepository.locale?.languageCode ?: "en") != with(context.resources.configuration) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales[0] else locale
        }.language

    override fun <I, O> registerActivityResultLauncher(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I> = registerForActivityResult(contract, callback)

    override fun registerImagePickerLauncher(
        callback: ImagePickerCallback
    ): ImagePickerLauncher = registerImagePicker(callback = callback)

    fun <T : Fragment> fragment(fragmentClass: Class<T>): T? =
        supportFragmentManager.fragments.firstOrNull {
            fragmentClass.isInstance(it)
        } as? T

    @SuppressLint("MissingPermission")
    fun call(number: String) = startActivity(
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
    )

}
