package com.baseutils.transitions

import android.annotation.TargetApi
import android.os.Build
import android.transition.*


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class DetailsTransition : TransitionSet() {
    init {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(ChangeClipBounds()).addTransition(ChangeImageTransform())
    }
}