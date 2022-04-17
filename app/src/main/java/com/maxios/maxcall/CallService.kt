package com.maxios.maxcall

import com.maxios.maxcall.base.BaseCallService
import com.maxios.maxcall.ui.call.CallActivity

class CallService : BaseCallService() {

    override fun getCallActivityClass() = CallActivity::class.java

}