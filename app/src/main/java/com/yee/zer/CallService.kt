package com.yee.zer

import com.yee.zer.base.BaseCallService
import com.yee.zer.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}