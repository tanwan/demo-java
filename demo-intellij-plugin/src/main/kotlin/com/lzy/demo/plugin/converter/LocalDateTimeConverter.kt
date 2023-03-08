package com.lzy.demo.plugin.converter

import com.intellij.util.xmlb.Converter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


class LocalDateTimeConverter : Converter<LocalDateTime?>() {

    override fun toString(value: LocalDateTime): String {
        val zoneId = ZoneId.systemDefault()
        val toEpochMilli: Long = value.atZone(zoneId).toInstant().toEpochMilli()
        return toEpochMilli.toString()
    }

    override fun fromString(value: String): LocalDateTime {
        val epochMilli = value.toLong()
        val zoneId = ZoneId.systemDefault()
        return Instant.ofEpochMilli(epochMilli).atZone(zoneId).toLocalDateTime()
    }
}