package com.fantasia.telecaller

import com.fantasia.telecaller.base.FBaseFCallFServiceF
import com.fantasia.telecaller.ui.call.activity.FCallFActivityF

class FCallFServiceF : FBaseFCallFServiceF() {
    override fun getCallActivityClass() = FCallFActivityF::class.java
}