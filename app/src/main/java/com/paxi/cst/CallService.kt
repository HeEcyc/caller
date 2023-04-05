package com.paxi.cst

import com.paxi.cst.base.BaseCallService
import com.paxi.cst.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}