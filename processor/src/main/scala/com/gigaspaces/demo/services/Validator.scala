package com.gigaspaces.demo.services

import _root_.scala.beans.BeanProperty
import java.util.logging.Logger
import org.openspaces.events.adapter.SpaceDataEvent
import com.gigaspaces.demo.common.Data

/**
 * The validator simulates work done to unvalidated Data objects. The validateData
 * method accepts a Data object, simulates work by sleeping, sets the valid flag to
 * true and returns the Data object.
 */
class Validator {

  val log = Logger.getLogger(this.getClass.getName)

  @BeanProperty
  var workDuration = 100

  @SpaceDataEvent
  def validateData(data: Data): Data = {
    Thread.sleep(workDuration)
    data.setValidated(true)
    log.fine("VALIDATED: " + data)
    data
  }

}
