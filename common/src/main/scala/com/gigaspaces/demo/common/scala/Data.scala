package com.gigaspaces.demo.common.scala

import scala.beans.{BooleanBeanProperty, BeanProperty}
import com.gigaspaces.annotation.pojo.{SpaceRouting, SpaceId, SpaceClass}
import scala.annotation.meta.beanGetter

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
  var id: String = null;

  @BooleanBeanProperty
  var processed: Boolean = false

  @BeanProperty
  var data: String = null

  override def toString = "id[" + id + "] dataType[" + dataType + "] rawData[" + rawData + "] data[" + data + "] processed[" + processed + "]"
}