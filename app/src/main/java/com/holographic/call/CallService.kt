package com.holographic.call

import com.holographic.call.base.BaseCallService
import com.holographic.call.ui.call.CallActivity

class CallService : BaseCallService() {

    override fun getCallActivityClass() = CallActivity::class.java

}