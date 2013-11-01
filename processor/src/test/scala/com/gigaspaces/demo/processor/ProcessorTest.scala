package com.gigaspaces.demo.processor

import com.gigaspaces.demo.UnitSpec
import com.gigaspaces.demo.common.Data

/**
 * A simple unit test that verifies the ProcessorJava processData method actually processes the Data object.
 */
class ProcessorTest extends UnitSpec {

  "A processor" should "process data" in {

    val original = new Data(1L, "test")
    val processed = new Processor().processData(original)

    processed.isProcessed should be (true)
    processed.getData should be ("PROCESSED : test")
    processed.getDataType should equal (original.getDataType)
  }
}
