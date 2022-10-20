package com.glasserino.caller

import com.glasserino.caller.base.GlBaseGlCallGlServiceGl
import com.glasserino.caller.ui.call.GlCallGlActivityGl

class GlCallGlServiceGl : GlBaseGlCallGlServiceGl() {
    override fun getCallActivityClass() = GlCallGlActivityGl::class.java
}