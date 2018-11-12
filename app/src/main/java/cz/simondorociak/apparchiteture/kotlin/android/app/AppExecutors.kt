package cz.simondorociak.apparchiteture.kotlin.android.app

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class AppExecutors @JvmOverloads constructor(
    private val diskIO: Executor = Executors.newSingleThreadExecutor(),
    private val networkIO: Executor = Executors.newFixedThreadPool(3),
    private val mainThread: Executor = MainThreadExecutor()) {

    fun getDiskIO() = diskIO

    fun getNetworkIO() = networkIO

    fun getMainThread() = mainThread

    private class MainThreadExecutor : Executor {

        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            handler.post(command)
        }
    }
}