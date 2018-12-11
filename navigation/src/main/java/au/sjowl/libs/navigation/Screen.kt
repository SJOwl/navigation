package au.sjowl.libs.navigation

import android.os.Bundle

interface Screen {
    val key: String
    val screenArguments: Bundle?
    var state: Bundle
}