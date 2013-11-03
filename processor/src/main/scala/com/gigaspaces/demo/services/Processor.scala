package com.gigaspaces.demo.services

import _root_.scala.beans.BeanProperty
import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.Data

/**
 * The processor simulates work done to unprocessed Data objects. The processData
 * method accepts a Data object, simulates work by sleeping, sets the processed flag
 * to true and returns the Data object.
 */
class Processor {

  val log = Logger.getLogger(this.getClass.getName)

  @BeanProperty
  var workDuration = 100

  @SpaceDataEvent
  def processData(data: Data): Data = {
    Thread.sleep(workDuration)
    data.setProcessed(true)
    log.fine("PROCESSED: " + data)
    data
  }

}
