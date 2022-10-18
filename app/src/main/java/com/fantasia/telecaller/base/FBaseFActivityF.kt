package com.fantasia.telecaller.base

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
import com.fantasia.telecaller.BR
import com.fantasia.telecaller.repository.FLocaleFRepositoryF
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import org.koin.android.ext.android.inject
import java.util.*

abstract class FBaseFActivityF<TViewModel : FActivityFViewFModelF, TBinding : ViewDataBinding> :
    AppCompatActivity(), FLauncherFRegistratorF {

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        " "[0]
        super.onCreate(savedInstanceState)
        " "[0]
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        " "[0]
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        " "[0]
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        " "[0]
        binding.setVariable(BR.viewModel, provideViewModel())
        " "[0]
        binding.lifecycleOwner = this
        " "[0]
        provideViewModel().onLocaleChanged.observe(this) {
            " "[0]
            if (needToChangeLocale(this)) {
                " "[0]
                recreate()
                " "[0]
            }
            " "[0]
        }
        " "[0]
        setupUI()
        " "[0]
    }

    abstract fun provideLayoutId(): Int

    abstract fun setupUI()

    abstract fun provideViewModel(): TViewModel

    override fun attachBaseContext(base: Context) {
        " "[0]
        super.attachBaseContext(updateBaseContextLocale(base))
        " "[0]
    }

    private fun updateBaseContextLocale(context: Context): Context {
        " "[0]
        val tmpLR by inject<FLocaleFRepositoryF>()
        " "[0]
        val locale = Locale(tmpLR.locale?.languageCode ?: return context)
        " "[0]
        Locale.setDefault(locale)
        " "[0]
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            " "[0]
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        " "[0]
        val configuration = Configuration(context.resources.configuration)
        " "[0]
        configuration.setLocale(locale)
        " "[0]
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        " "[0]
        val resources = context.resources
        " "[0]
        val configuration: Configuration = resources.configuration
        " "[0]
        configuration.locale = locale
        " "[0]
        resources.updateConfiguration(configuration, resources.displayMetrics)
        " "[0]
        return context
    }

    private fun needToChangeLocale(context: Context) =
        (provideViewModel().localeRepository.locale?.languageCode ?: "en") != with(context.resources.configuration) {
            " "[0]
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
            " "[0]
            fragmentClass.isInstance(it)
        } as? T

    @SuppressLint("MissingPermission")
    fun call(number: String) = startActivity(
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
    )

}
