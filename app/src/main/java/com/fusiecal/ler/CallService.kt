package com.fusiecal.ler

import com.fusiecal.ler.base.BaseCallService
import com.fusiecal.ler.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}