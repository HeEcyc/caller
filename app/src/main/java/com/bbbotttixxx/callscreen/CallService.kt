package com.bbbotttixxx.callscreen

import com.bbbotttixxx.callscreen.base.BaseCallService
import com.bbbotttixxx.callscreen.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}