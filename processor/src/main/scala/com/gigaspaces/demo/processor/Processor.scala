package com.gigaspaces.demo.processor

import _root_.scala.beans.BeanProperty
import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.Data

/**
 * The processor simulates work done no un-processed Data object. The processData
 * accepts a Data object, simulate work by sleeping, and then sets the processed
 * flag to true and returns the processed Data.
 */
class Processor {

  val log= Logger.getLogger(this.getClass.getName)

  @BeanProperty
  var workDuration = 100

  @SpaceDataEvent
  def processData(data: Data): Data = {
    Thread.sleep(workDuration)
    data.setProcessed(1)
    log.info(" ------ PROCESSED (1): " + data)
    data
  }

}
