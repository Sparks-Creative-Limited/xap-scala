package com.gigaspaces.demo.services

import _root_.scala.beans.BeanProperty
import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.Data

/**
 * The enricher simulates work done to unenriched Data objects. The enrichData
 * method accepts a Data object, simulates work by sleeping, sets the enriched flag
 * to true and returns the Data object.
 */
class Enricher {

  val log = Logger.getLogger(this.getClass.getName)

  @BeanProperty
  var workDuration = 100

  @SpaceDataEvent
  def enrichData(data: Data): Data = {
    Thread.sleep(workDuration)
    data.setEnriched(true)
    log.fine("ENRICHED: " + data)
    data
  }

}
