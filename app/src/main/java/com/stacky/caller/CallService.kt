package com.stacky.caller

import com.stacky.caller.base.BaseCallService
import com.stacky.caller.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}