package com.callerafter.lovelycall

import com.callerafter.lovelycall.base.BaseCallService
import com.callerafter.lovelycall.ui.call.CallActivity

class CallService : BaseCallService() {

    override fun getCallActivityClass() = CallActivity::class.java

}