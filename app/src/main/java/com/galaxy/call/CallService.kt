package com.galaxy.call

import com.galaxy.call.base.BaseCallService
import com.galaxy.call.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}