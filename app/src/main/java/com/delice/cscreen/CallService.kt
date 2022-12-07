package com.delice.cscreen

import com.delice.cscreen.base.BaseCallService
import com.delice.cscreen.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}