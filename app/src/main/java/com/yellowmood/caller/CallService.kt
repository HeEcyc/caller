package com.yellowmood.caller

import com.yellowmood.caller.base.BaseCallService
import com.yellowmood.caller.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}