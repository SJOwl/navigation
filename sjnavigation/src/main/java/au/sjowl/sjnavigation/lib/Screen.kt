package au.sjowl.sjnavigation.lib

import android.os.Bundle

interface Screen {
    val key: String
    val screenArguments: Bundle?
    var state: Bundle
}