package com.galala.lalaxy

import com.galala.lalaxy.base.GBaseGCallGServiceG
import com.galala.lalaxy.ui.call.activity.GCallGActivityG

class GCallGServiceG : GBaseGCallGServiceG() {
    override fun getCallActivityClass() = GCallGActivityG::class.java
}