package com.galala.lalaxy.base

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
import com.galala.lalaxy.BR
import com.galala.lalaxy.repository.GLocaleGRepositoryG
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import org.koin.android.ext.android.inject
import java.util.*

abstract class GBaseGActivityG<TViewModel : GActivityGViewGModelG, TBinding : ViewDataBinding> :
    AppCompatActivity(), GLauncherGRegistratorG {

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        println("")
        super.onCreate(savedInstanceState)
        println("")
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        println("")
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        println("")
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        println("")
        binding.setVariable(BR.viewModel, provideViewModel())
        println("")
        binding.lifecycleOwner = this
        println("")
        provideViewModel().onLocaleChanged.observe(this) {
            println("")
            if (needToChangeLocale(this)) {
                println("")
                recreate()
                println("")
            }
            println("")
        }
        println("")
        setupUI()
        println("")
    }

    abstract fun provideLayoutId(): Int

    abstract fun setupUI()

    abstract fun provideViewModel(): TViewModel

    override fun attachBaseContext(base: Context) {
        println("")
        super.attachBaseContext(updateBaseContextLocale(base))
        println("")
    }

    private fun updateBaseContextLocale(context: Context): Context {
        println("")
        val tmpLR by inject<GLocaleGRepositoryG>()
        println("")
        val locale = Locale(tmpLR.locale?.languageCode ?: return context)
        println("")
        Locale.setDefault(locale)
        println("")
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            println("")
            updateResourcesLocale(context, locale)
        } else updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        println("")
        val configuration = Configuration(context.resources.configuration)
        println("")
        configuration.setLocale(locale)
        println("")
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        println("")
        val resources = context.resources
        println("")
        val configuration: Configuration = resources.configuration
        println("")
        configuration.locale = locale
        println("")
        resources.updateConfiguration(configuration, resources.displayMetrics)
        println("")
        return context
    }

    private fun needToChangeLocale(context: Context) =
        (provideViewModel().localeRepository.locale?.languageCode ?: "en") != with(context.resources.configuration) {
            println("")
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
            println("")
            fragmentClass.isInstance(it)
        } as? T

    @SuppressLint("MissingPermission")
    fun call(number: String) = startActivity(
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
    )

}
