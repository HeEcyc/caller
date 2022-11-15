package com.roobcall.themes

import com.roobcall.themes.base.BaseCallService
import com.roobcall.themes.ui.call.activity.CallActivity

class CallService : BaseCallService() {
    override fun getCallActivityClass() = CallActivity::class.java
}