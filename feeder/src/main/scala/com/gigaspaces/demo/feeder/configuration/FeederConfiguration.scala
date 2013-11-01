package com.gigaspaces.demo.feeder.configuration

import org.springframework.context.annotation.{Configuration, DependsOn, Bean}
import com.gigaspaces.demo.feeder.Feeder

/**
 * @author Jez
 */
@Configuration
class FeederConfiguration {

  @Bean
  //@DependsOn(Array("gigaSpace"))
  def dataFeeder = {
    val feeder = new Feeder
    feeder.numberOfTypes = 100
    feeder
  }

}
