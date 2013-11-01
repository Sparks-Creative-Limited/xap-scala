package com.gigaspaces.demo.common

import _root_.scala.annotation.meta.beanGetter
import _root_.scala.beans.{BooleanBeanProperty, BeanProperty}
import com.gigaspaces.annotation.pojo.{SpaceRouting, SpaceId, SpaceClass}

/**
 * @author Jez
 */
@SpaceClass
class Data(@BeanProperty @SpaceRouting var dataType: java.lang.Long,
           @BeanProperty var rawData: String) {

  def this() { // Auxiliary constructor where params unavailable
    this(null, null)
  }

  @BeanProperty
  @(SpaceId @beanGetter)(autoGenerate = true)
  var id: String = null

  @BooleanBeanProperty
  var processed: Boolean = false

  @BeanProperty
  var data: String = null

  override def toString = "id[" + id + "] dataType[" + dataType + "] rawData[" + rawData + "] data[" + data + "] processed[" + processed + "]"
}
