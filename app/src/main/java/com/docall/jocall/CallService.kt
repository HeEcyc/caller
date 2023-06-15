package com.docall.jocall

import com.docall.jocall.base.BaseCallService
import com.docall.jocall.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}