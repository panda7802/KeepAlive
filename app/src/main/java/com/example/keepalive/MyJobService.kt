package com.example.keepalive

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Messenger
import android.util.Log

class MyJobService : JobService() {
    private var mActivityMessenger: Messenger? = null
    private var running = false
    private var mJobId = 0 // 执行的JobId

    private inner class RunThread internal constructor() : Thread() {
        var index = 0
        override fun run() {
            while (running) {
                try {
                    Log.i(KeepAliveConstants.TAG, "task running : $index")
                    index++
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        init {
            index = 0
        }
    }

    private var runTask: RunThread? = null
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service created")
        scheduleJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service onDestroy")
    }

    // 当应用程序的MainActivity被创建时，它启动这个服务。
    // 这是为了使活动和此服务可以来回通信。 请参见“setUiCallback（）”
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mActivityMessenger = intent.getParcelableExtra(KeepAliveConstants.MESSENGER_INTENT_KEY)
        return START_NOT_STICKY
    }

    override fun onStartJob(params: JobParameters): Boolean {
//        // 该服务做的工作只是等待一定的持续时间并完成作业（在另一个线程上）。
//        sendMessage(KeepAliveConstants.MSG_JOB_START, params.getJobId());
//        // 当然这里可以处理其他的一些任务
//
//        // 获取在activity里边设置的每个任务的周期，其实可以使用setPeriodic()
//        long duration = params.getExtras().getLong(KeepAliveConstants.WORK_DURATION_KEY);
//
//        // 使用一个handler处理程序来延迟jobFinished（）的执行。
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                sendMessage(KeepAliveConstants.MSG_JOB_STOP, params.getJobId());
//                jobFinished(params, false);
//            }
//        }, duration);
        Log.i(TAG, "on start job: " + params.jobId)
        startTask();
        // 返回true，很多工作都会执行这个地方，我们手动结束这个任务
        return false
    }

    override fun onStopJob(params: JobParameters): Boolean {
        // 停止跟踪这些作业参数，因为我们已经完成工作。
//        sendMessage(KeepAliveConstants.MSG_JOB_STOP, params.getJobId());
        Log.i(TAG, "on stop job: " + params.jobId)
        stopTask()
        // 返回false来销毁这个工作
        return false
    }

    private fun startTask() {
        Log.i(KeepAliveConstants.TAG, "startTask")
        stopTask()
        running = true
        runTask = RunThread()
        runTask!!.start()
    }

    private fun stopTask() {
        Log.i(KeepAliveConstants.TAG, "stopTask")
        running = false
        if (null != runTask) {
            try {
                runTask!!.join(2100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            runTask = null
        }
    }

    // 当用户单击SCHEDULE JOB时执行。
    private fun scheduleJob() {
        //开始配置JobInfo
        val mServiceComponent = ComponentName(this, MyJobService::class.java)
        val builder = JobInfo.Builder(mJobId++, mServiceComponent)


//        //设置任务的延迟执行时间(单位是毫秒)
//        val delay = etDelayTime!!.text.toString()
//        if (!TextUtils.isEmpty(delay)) {
//            builder.setMinimumLatency(java.lang.Long.valueOf(delay) * 1000)
//        }

//        // 设置任务周期执行，其周期为intervalMillis参数
//        // 你无法控制任务的执行时间，系统只保证在此时间间隔内，任务最多执行一次。
//        builder.setPeriodic(3000)
        builder.setPeriodic(500)
        builder.setPersisted(true);

//        1，周期任务setPeriodic和setMinimumLatency设置任务延时，setOverrideDeadline设置任务最大执行时间冲突，我们不应该一起设置它们，否则会抛出异常。
//          2，setOverrideDeadline方法比较特殊，我们为任务指定一个最晚的执行时间，该事件达到后，不管其他约束条件是否满足，任务都将会执行
//        val deadline = etDeadlineTime!!.text.toString()
//        if (!TextUtils.isEmpty(deadline)) {
//            builder.setOverrideDeadline(java.lang.Long.valueOf(deadline) * 1000)
//        }


//        val requiresUnmetered = mRb_WiFiConnectivity!!.isChecked
//        val requiresAnyConnectivity = mRb_AnyConnectivity!!.isChecked
//
//        //让你这个任务只有在满足指定的网络条件时才会被执行
//        if (requiresUnmetered) {
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//        } else if (requiresAnyConnectivity) {
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//        }
//
//        //你的任务只有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务。
//        builder.setRequiresDeviceIdle(mCb_RequiresIdle!!.isChecked)
//        //告诉你的应用，只有当设备在充电时这个任务才会被执行。
//        builder.setRequiresCharging(mCb_RequiresCharging!!.isChecked)

//        // Extras, work duration.
//        val extras = PersistableBundle()
//        extras.putLong(KeepAliveConstants.WORK_DURATION_KEY, 3000)
//        builder.setExtras(extras)

        // Schedule job
        Log.d(KeepAliveConstants.TAG, "Scheduling job")
        val mJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        // 这里就将开始在service里边处理我们配置好的job
        val res = mJobScheduler.schedule(builder.build()) // 会返回一个int类型的数据
        Log.d(KeepAliveConstants.TAG, "mJobScheduler id = $res")
        startTask()
        //如果schedule方法失败了，它会返回一个小于0的错误码。否则它会返回我们在JobInfo.Builder中定义的标识id。
    }

    // 当用户点击取消所有时执行
    private fun cancelAllJobs() {
        val mJobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        mJobScheduler.cancelAll()
    }

    companion object {
        private const val TAG = KeepAliveConstants.TAG
    }
}