package cac7er

import kotlinx.coroutines.*
import java.util.concurrent.*

internal val readerCoroutineDispatcher: CoroutineDispatcher
      = Executors.newScheduledThreadPool(3).asCoroutineDispatcher()

internal val writerCoroutineDispatcher: CoroutineDispatcher
      = Executors.newSingleThreadScheduledExecutor().asCoroutineDispatcher()
