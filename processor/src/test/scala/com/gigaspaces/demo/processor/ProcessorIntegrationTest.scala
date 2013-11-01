package com.gigaspaces.demo.processor

import com.gigaspaces.demo.UnitSpec
import org.springframework.test.context.{TestContextManager, ContextConfiguration}
import org.openspaces.core.GigaSpace
import org.springframework.beans.factory.annotation.Autowired
import com.gigaspaces.demo.common.Data

/**
 * Integration test for the ProcessorJava. Uses similar xml definition file (ProcessorIntegrationTestJava-context.xml)
 * to the actual pu.xml. Writs an unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
@ContextConfiguration
class ProcessorIntegrationTest extends UnitSpec {

  @Autowired
  val gigaSpace: GigaSpace = null
  new TestContextManager(this.getClass).prepareTestInstance(this)


  before {
    gigaSpace.clear(null)
  }


  "A processor" should "process data from the space" in {
    val original = new Data(1L, "test")
    gigaSpace.write(original)

    val processed: Data = gigaSpace.take(template(1L, null, true), 600)
    processed should not be (null)
    processed.isProcessed should be (true)
    processed.getRawData should equal (original.getRawData)
  }


  def template(dataType: Long, rawData: String, processed: Boolean): Data = {
    val data = new Data(dataType, rawData)
    data.setProcessed(processed)
    data
  }
}
