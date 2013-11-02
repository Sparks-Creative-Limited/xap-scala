package com.gigaspaces.demo.feeder

import _root_.scala.beans.BeanProperty
import _root_.scala.concurrent._
import org.springframework.beans.factory.{DisposableBean, InitializingBean}
import java.util.logging.Logger
import org.openspaces.core.GigaSpace
import org.openspaces.core.context.GigaSpaceContext
import ExecutionContext.Implicits.global
import com.gigaspaces.demo.common.Data

class Feeder extends InitializingBean with DisposableBean {

  val log= Logger.getLogger(this.getClass.getName)

  @BeanProperty
  var defaultDelay = 1000

  @BeanProperty
  var numberOfTypes = 10

  @GigaSpaceContext
  var gigaSpace: GigaSpace = null

  private var cancelled = false

  override def destroy() = {
    cancelled = true
  }

  override def afterPropertiesSet() = future {
    def feed(counter: Int): Unit = {
      val time = System.currentTimeMillis
      val data: Data = new Data("FEEDER " + time, counter % numberOfTypes)

      gigaSpace.write(data)
      log.info("--- FEEDER WROTE " + data)

      blocking(Thread.sleep(defaultDelay))
      feed(counter + 1)
    }

    feed(1)
  }


}
