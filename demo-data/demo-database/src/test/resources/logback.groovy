//For more information on configuration files in Groovy
//please see http://logback.qos.ch/manual/groovy.html
//For assistance related to this tool or configuration files
//in general, please contact the logback user mailing list at
//http://qos.ch/mailman/listinfo/logback-user

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter

import static ch.qos.logback.classic.Level.*
import static ch.qos.logback.core.spi.FilterReply.*

appender("STDOUT", ConsoleAppender) {
    //LevelFilter(只输出指定的级别) ThresholdFilter(输出以上的级别)
    filter(LevelFilter) {
        level = DEBUG
        onMismatch = NEUTRAL
        onMatch = ACCEPT
    }
    filter(LevelFilter) {
        level = INFO
        onMismatch = DENY
        onMatch = ACCEPT
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} %green(%-5level) %magenta([%thread]) - %boldWhite(%msg) [at %class.%method:\\(%file:%line\\)]%n"
    }
}
//只输出ERROR级别的日志
appender("STDERR", ConsoleAppender) {
    //See http://logback.qos.ch/manual/filters.html
    filter(LevelFilter) {
        level = ERROR
        onMatch = ACCEPT
        onMismatch = DENY
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} %red(%-5level)  %magenta([%thread])  - %boldRed(%msg) [at %class.%method:\\(%file:%line\\)]%n"
    }
}

//druid日志
appender("STD_DRUID", ConsoleAppender) {
    //See http://logback.qos.ch/manual/filters.html
//    filter(EvaluatorFilter) {
//        evaluator(JaninoEventEvaluator) {
//            expression = 'return message.contains("UPDATE")||message.contains("DELETE");'
//        }
//        //不符合就执行下一个过滤器
//        onMismatch = DENY
//        //符合条件就应用
//        onMatch = NEUTRAL
//    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} %green(%-5level) %magenta([%thread]) - %boldWhite(%msg) [at %class.%method:\\(%file:%line\\)]%n"
    }
}
logger("druid.sql.Statement", DEBUG, ["STD_DRUID"], false)
logger("com.lzy", INFO, ["STDOUT", "STDERR"], false)
logger("org.jooq.tools.LoggerListener", DEBUG, ["STDOUT"], false)
root(INFO, ["STDOUT", "STDERR"])