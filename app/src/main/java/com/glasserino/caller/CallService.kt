package com.glasserino.caller

import com.glasserino.caller.base.BaseCallService
import com.glasserino.caller.ui.call.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}