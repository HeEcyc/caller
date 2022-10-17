package com.galaxy.call

import com.galaxy.call.base.GBaseGCallGServiceG
import com.galaxy.call.ui.call.activity.GCallGActivityG

class GCallGServiceG : GBaseGCallGServiceG() {
    override fun getCallActivityClass() = GCallGActivityG::class.java
}