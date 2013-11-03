package com.gigaspaces.demo.common

import _root_.scala.annotation.meta.beanGetter
import scala.beans.{BooleanBeanProperty, BeanProperty}
import com.gigaspaces.annotation.pojo.{SpaceRouting, SpaceId, SpaceClass}

@SpaceClass
class Data( @BeanProperty var data: String,
            @BeanProperty @SpaceRouting var dataType: java.lang.Long = 1L,
            @BooleanBeanProperty var validated: Boolean = false,
            @BooleanBeanProperty var enriched: Boolean = false,
            @BooleanBeanProperty var processed: Boolean = false) {

  def this() = this(null)

  @BeanProperty
  @(SpaceId @beanGetter)(autoGenerate = true)
  var id: String = null

  override def toString = "{ data: " + data + ", type: " + dataType +
    ", validated: " + validated + ", enriched: " + enriched + ", processed: " + processed + "}"
}
