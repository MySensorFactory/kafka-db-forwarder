# Kafka database forwarder

## Description

This module is responsible for forwarding batches of processes data from Kafka topics to relational database. 

## Configuration Properties Explanation

The application.yaml file contains configuration properties for a Kafka listener in a Spring Boot application. Below is an explanation of the properties:


### Kafka Configuration

#### Sensors Configuration

* Property: kafka.config.{sensor-type}.enabled
* Description: Indicates whether the sensor-related Kafka listener is enabled.

#### Buffer Size
* Property: kafka.config.{sensor-type}.bufferSize
* Description: The size of the buffer for storing sensor-related data.

#### Topics
* Property: kafka.config.{sensor-type}.inputTopics
* Description: Comma-separated list of input topics related to sensor.

#### Listener Identifier
* Property: kafka.config.{sensor-type}.listenerId
* Description: Identifier for the sensor mean listener.

#### Group Identifier
* Property: kafka.config.{sensor-type}.groupId
* Description: Identifier for the Kafka group related to sensor mean.

#### Available {sensor-type} values:
* pressure
* temperature
* flowRate
* gasComposition
* compressorState
