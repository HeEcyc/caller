package com.megaaa.caaall

import com.megaaa.caaall.base.BaseCallService
import com.megaaa.caaall.ui.call.CallActivity

class CallService : BaseCallService() {

    override fun getCallActivityClass() = CallActivity::class.java

}