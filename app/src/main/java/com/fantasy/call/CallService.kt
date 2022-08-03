package com.fantasy.call

import com.fantasy.call.base.BaseCallService
import com.fantasy.call.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}