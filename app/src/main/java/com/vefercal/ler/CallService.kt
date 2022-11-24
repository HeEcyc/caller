package com.vefercal.ler

import com.vefercal.ler.base.BaseCallService
import com.vefercal.ler.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}