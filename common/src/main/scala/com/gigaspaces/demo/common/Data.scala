package com.gigaspaces.demo.common

import _root_.scala.annotation.meta.beanGetter
import _root_.scala.beans.BeanProperty
import com.gigaspaces.annotation.pojo.{SpaceRouting, SpaceId, SpaceClass}

@SpaceClass
class Data(@BeanProperty var data: String,
           @BeanProperty @SpaceRouting var dataType: java.lang.Long = 1L,
           @BeanProperty var processed: Int = 0) {

  def this() = this(null)

  @BeanProperty
  @(SpaceId @beanGetter)(autoGenerate = true)
  var id: String = null

  override def toString = "{ id: " + id + ", type: " + dataType + ", data: " + data + ", processed: " + processed + "}"
}
