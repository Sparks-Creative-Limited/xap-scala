package com.gigaspaces.demo.services.configuration

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import com.gigaspaces.demo.services.{Enricher, Validator, Processor}
import com.gigaspaces.demo.common.Data

@Configuration
class ServiceConfiguration {

  /**
   * Template object bean to identify unvalidated Data objects
   * @return The template object representing unvalidated data
   */
  @Bean
  def unvalidatedDataTemplate = new Data(null, null, false)

  /**
   * Template object bean to identify unenriched Data objects
   * @return The template object representing unenriched data
   */
  @Bean
  def unenrichedDataTemplate = new Data(null, null, true, false)

  /**
   * Template object bean to identify unprocessed Data objects
   * @return The template object representing unprocessed data
   */
  @Bean
  def unprocessedDataTemplate = new Data(null, null, true, true, false)

  /**
   * The data validator service for processing unit deployment
   * @return The data validator Spring bean
   */
  @Bean
  def dataValidator = new Validator

  /**
   * The data enricher service for processing unit deployment
   * @return The data enricher Spring bean
   */
  @Bean
  def dataEnricher = new Enricher

  /**
   * The data processor service for processing unit deployment
   * @return The data processor Spring bean
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
