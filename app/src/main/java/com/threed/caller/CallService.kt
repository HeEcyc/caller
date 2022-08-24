package com.threed.caller

import com.threed.caller.base.BaseCallService
import com.threed.caller.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}