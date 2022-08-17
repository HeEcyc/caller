package com.fancy.call

import com.fancy.call.base.BaseCallService
import com.fancy.call.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}