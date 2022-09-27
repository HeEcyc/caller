package com.sappy.callthemes

import com.sappy.callthemes.base.BaseCallService
import com.sappy.callthemes.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}