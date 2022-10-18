package com.fantasia.telecaller

import com.fantasia.telecaller.base.BaseCallService
import com.fantasia.telecaller.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}