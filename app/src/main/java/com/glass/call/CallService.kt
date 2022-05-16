package com.glass.call

import com.glass.call.base.BaseCallService
import com.glass.call.ui.call.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}