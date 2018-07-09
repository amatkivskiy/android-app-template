package com.github.amatkivskiy.template.domain

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}