package com.pecall.poscreen

import com.pecall.poscreen.base.BaseCallService
import com.pecall.poscreen.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}