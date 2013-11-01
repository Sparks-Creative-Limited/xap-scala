package com.gigaspaces.demo.processor.configuration

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import com.gigaspaces.demo.processor.Processor
import com.gigaspaces.demo.common.Data

/**
 * @author Jez
 */
@Configuration
class ProcessorConfiguration {

  /**
   * Template object bean to identify non-processed Data objects
   * @return
   */
  @Bean
  def unprocessedDataTemplate = {
    val template = new Data()
    template.setProcessed(false)
    template
  }

  /**
   * The data processor Spring bean for the configuration
   * @return
   */
  @Bean
  def dataProcessor = new Processor

  /**
   * Spring property configurer which allows us to use system properties (such as user.name).
   * @return the property placeholder configurer bean
   */
  @Bean
  def propertiesConfigurer = new PropertyPlaceholderConfigurer

}
