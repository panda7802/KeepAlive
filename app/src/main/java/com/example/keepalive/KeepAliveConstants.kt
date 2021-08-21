package com.example.keepalive

class KeepAliveConstants {
    companion object {
        const val TAG = "KeepAliveTest"
        const val MESSENGER_INTENT_KEY = "$TAG.MESSENGER_INTENT_KEY"
        const val WORK_DURATION_KEY = "$TAG.WORK_DURATION_KEY"
        const val MSG_JOB_START = 0
        const  val MSG_JOB_STOP = 1
        const  val MSG_ONJOB_START = 2
        const  val MSG_ONJOB_STOP = 3
    }
}