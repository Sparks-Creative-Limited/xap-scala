package com.gigaspaces.demo.processor

import com.gigaspaces.demo.UnitSpec
import org.springframework.test.context.{TestContextManager, ContextConfiguration}
import org.openspaces.core.GigaSpace
import org.springframework.beans.factory.annotation.Autowired
import com.gigaspaces.demo.common.Data
import com.gigaspaces.async.{AsyncResult, AsyncFutureListener, AsyncFuture}
import org.scalatest.time.SpanSugar._

/**
 * A simple test suite to verify that the Processor.processData method actually processes the Data object and that it
 * will take unprocessed data from the Space and perform processing (using a test application context).
 */
@ContextConfiguration(Array("classpath:/META-INF/spring/ProcessorTest-context.xml"))
class ProcessorTest extends UnitSpec {

  @Autowired
  val gigaSpace: GigaSpace = null
  val timeout = 600
  new TestContextManager(this.getClass).prepareTestInstance(this)

  before { gigaSpace.clear(null) }


  "A processor" should "process data" in {
    val original = new Data("test1", 1L)
    val processed = new Processor().processData(original)

    processed.getData should be (original.getData)
    processed.getDataType should equal (original.getDataType)
    processed.getProcessed should be (1)
  }


  it should "process data from the space" in {
    val original = new Data("test2", 1L)
    gigaSpace.write(original)
    val processed: Data = gigaSpace.take(new Data("test2", 1L, 1), timeout)

    processed should not be (null)
  }

  it should "process data from the space (async)" in {
    val original = new Data("test3", 1L)
    val w = new Waiter

    gigaSpace.asyncTake(new Data("test3", 1L, 1), timeout, new AsyncFutureListener[Data] {
      def onResult(result: AsyncResult[Data]) = {
        w { result.getResult should not be (null) }
        w.dismiss()
      }
    })

    gigaSpace.write(original)
    w.await()
  }
}
