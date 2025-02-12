/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.common.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

@UtilityClass
@Slf4j
public class ThreadUtils {

    public static ThreadPoolExecutor newDaemonFixedThreadExecutor(String threadName, int threadsNum) {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsNum, newDaemonThreadFactory(threadName));
    }

    public static ScheduledExecutorService newSingleDaemonScheduledExecutorService(String threadName) {
        return Executors.newSingleThreadScheduledExecutor(newDaemonThreadFactory(threadName));
    }

    public static ScheduledExecutorService newDaemonScheduledExecutorService(String threadName, int threadsNum) {
        return Executors.newScheduledThreadPool(threadsNum, newDaemonThreadFactory(threadName));
    }

    public static ThreadFactory newDaemonThreadFactory(String threadName) {
        return new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat(threadName)
                .setUncaughtExceptionHandler(DefaultUncaughtExceptionHandler.getInstance())
                .build();
    }

    /**
     * Sleep in given mills, this is not accuracy.
     */
    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            log.error("Current thread sleep error", interruptedException);
        }
    }
}
