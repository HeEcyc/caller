package com.glasserino.caller.base

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
import com.glasserino.caller.BR
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import org.koin.android.ext.android.inject
import java.util.*

abstract class GlBaseGlActivityGl<TViewModel : GlActivityGlViewGlModelGl, TBinding : ViewDataBinding> :
    AppCompatActivity(), GlLauncherGlRegistratorGl {

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        listOf<Any>().isEmpty()
        super.onCreate(savedInstanceState)
        listOf<Any>().isEmpty()
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        listOf<Any>().isEmpty()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        listOf<Any>().isEmpty()
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        listOf<Any>().isEmpty()
        binding.setVariable(BR.viewModel, provideViewModel())
        listOf<Any>().isEmpty()
        binding.lifecycleOwner = this
        listOf<Any>().isEmpty()
        provideViewModel().onLocaleChanged.observe(this) {
            listOf<Any>().isEmpty()
            if (needToChangeLocale(this)) {
                listOf<Any>().isEmpty()
                recreate()
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        setupUI()
        listOf<Any>().isEmpty()
    }

    abstract fun provideLayoutId(): Int

    abstract fun setupUI()

    abstract fun provideViewModel(): TViewModel

    override fun attachBaseContext(base: Context) {
        listOf<Any>().isEmpty()
        super.attachBaseContext(updateBaseContextLocale(base))
        listOf<Any>().isEmpty()
    }

    private fun updateBaseContextLocale(context: Context): Context {
        listOf<Any>().isEmpty()
        val tmpLR by inject<GlLocaleGlRepositoryGl>()
        listOf<Any>().isEmpty()
        val locale = Locale(tmpLR.locale?.languageCode ?: return context)
        listOf<Any>().isEmpty()
        Locale.setDefault(locale)
        listOf<Any>().isEmpty()
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            listOf<Any>().isEmpty()
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        listOf<Any>().isEmpty()
        val configuration = Configuration(context.resources.configuration)
        listOf<Any>().isEmpty()
        configuration.setLocale(locale)
        listOf<Any>().isEmpty()
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        listOf<Any>().isEmpty()
        val resources = context.resources
        listOf<Any>().isEmpty()
        val configuration: Configuration = resources.configuration
        listOf<Any>().isEmpty()
        configuration.locale = locale
        listOf<Any>().isEmpty()
        resources.updateConfiguration(configuration, resources.displayMetrics)
        listOf<Any>().isEmpty()
        return context
    }

    private fun needToChangeLocale(context: Context) =
        (provideViewModel().localeRepository.locale?.languageCode ?: "en") != with(context.resources.configuration) {
            listOf<Any>().isEmpty()
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
            listOf<Any>().isEmpty()
            fragmentClass.isInstance(it)
        } as? T

    @SuppressLint("MissingPermission")
    fun call(number: String) = startActivity(
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
    )

}
