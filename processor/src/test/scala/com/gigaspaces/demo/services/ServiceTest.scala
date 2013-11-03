package com.gigaspaces.demo.services

import com.gigaspaces.async.{AsyncResult, AsyncFutureListener}
import com.gigaspaces.demo.UnitSpec
import com.gigaspaces.demo.common.Data
import org.openspaces.core.GigaSpace
import org.scalatest.time.SpanSugar._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.{TestContextManager, ContextConfiguration}


/**
 * A simple test suite to verify the respective functionality of the validator, enricher and processor services.
 */
@ContextConfiguration(Array("classpath:/META-INF/spring/ServiceTestContext.xml"))
class ServiceTest extends UnitSpec {


  @Autowired
  val gigaSpace: GigaSpace = null
  val timeout = 600


  override def beforeAll() {
    new TestContextManager(this.getClass).prepareTestInstance(this)
  }
  override def beforeEach() {
    gigaSpace.clear(new Data())
  }



  /* ---------------------------------------------------------------------------------------------------
   * Validator Service:
   * ---------------------------------------------------------------------------------------------------
   * Tests to verify that the Validator.validateData method actually validates the Data object and that
   * it will take unvalidated data from the Space and perform validation (using a test context).
   * ---------------------------------------------------------------------------------------------------
   */

  "A validator" should "validate data" in {
    val original = new Data("validatorTest1", 1L, false)
    val validated = new Validator().validateData(original)

    validated.getData should be (original.getData)
    validated.getDataType should be (original.getDataType)
    validated.isValidated should be (true)
  }


  it should "validate data from the space" in {
    gigaSpace.write(new Data())
    gigaSpace.write(new Data("validatorTest2", 1L, false))
    gigaSpace.take(new Data("validatorTest2", 1L, true), timeout) should not be (null)
  }



  /* ---------------------------------------------------------------------------------------------------
   * Enricher Service:
   * ---------------------------------------------------------------------------------------------------
   * Tests to verify that the Enricher.enrichData method actually enriches the Data object and that it
   * will take unenriched data from the Space and perform enrichment (using a test context).
   * ---------------------------------------------------------------------------------------------------
   */

  "An enricher" should "enrich data" in {
    val original = new Data("enricherTest1", 1L, true, false)
    val enriched = new Enricher().enrichData(original)

    enriched.getData should be (original.getData)
    enriched.getDataType should equal (original.getDataType)
    enriched.isEnriched should be (true)
  }


  it should "enrich data from the space" in {
    gigaSpace.write(new Data("enricherTest2", 1L, true, false))
    gigaSpace.take(new Data("enricherTest2", 1L, true, true), timeout) should not be (null)
  }



  /* ---------------------------------------------------------------------------------------------------
   * Processor Service:
   * ---------------------------------------------------------------------------------------------------
   * Tests to verify that the Processor.processData method actually processes the Data object and that
   * it will take unprocessed data from the Space and perform processing (using a test context).
   * ---------------------------------------------------------------------------------------------------
   */

  "A processor" should "process data" in {
    val original = new Data("processorTest1", 1L, true, true, false)
    val processed = new Processor().processData(original)

    processed.getData should be (original.getData)
    processed.getDataType should equal (original.getDataType)
    processed.isProcessed should be (true)
  }


  it should "process data from the space" in {
    gigaSpace.write(new Data("processorTest2", 1L, true, true, false))
    gigaSpace.take(new Data("processorTest2", 1L, true, true, true), timeout) should not be (null)
  }



  /* ---------------------------------------------------------------------------------------------------
   * Processing Unit:
   * ---------------------------------------------------------------------------------------------------
   * Tests to verify that a processing unit with Validator, Enricher and Processor services perform
    * their respective operations on data in the Space in the correct order and within a certain time.
   * ---------------------------------------------------------------------------------------------------
   */

  "A processing unit" should "validate, enrich and process data from the space" in {
    gigaSpace.write(new Data("PUTest1", 1L))
    gigaSpace.take(new Data("PUTest1", 1L, true, true, true), timeout) should not be (null)
  }


  it should "complete within " + timeout + "ms" in {
    val w = new Waiter
    gigaSpace.asyncTake(new Data("PUTest2", 1L, true, true, true), timeout, new AsyncFutureListener[Data] {
      def onResult(result: AsyncResult[Data]) = {
        w { result.getResult should not be (null) }
        w.dismiss()
      }
    })

    gigaSpace.write(new Data("PUTest2", 1L))
    w.await(timeout(timeout millis))
  }

}
