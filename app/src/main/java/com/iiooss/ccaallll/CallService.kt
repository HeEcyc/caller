package com.iiooss.ccaallll

import com.iiooss.ccaallll.base.BaseCallService
import com.iiooss.ccaallll.ui.call.CallActivity

class CallService : BaseCallService() {

    override fun getCallActivityClass() = CallActivity::class.java

}