package com.lzy.demo.spark.scala

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.funsuite.AnyFunSuite

class SparkTest extends AnyFunSuite {

  test("Spark") {
    val conf = new SparkConf().setAppName("Test").setMaster("spark://hadoop-pseudo:7077")
    val sc = new SparkContext(conf)
    val input = sc.parallelize(Array("1", "2", "3", "4"))
    val inputMap = input.map(t => Integer.valueOf(t))
    println(inputMap.count())
  }
}
