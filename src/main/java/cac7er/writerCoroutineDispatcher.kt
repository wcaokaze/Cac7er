package cac7er

import kotlinx.coroutines.*
import java.util.concurrent.*

internal val writerCoroutineDispatcher: CoroutineDispatcher
      = Executors.newSingleThreadScheduledExecutor().asCoroutineDispatcher()
