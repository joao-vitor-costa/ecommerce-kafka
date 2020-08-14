## Kafka case study with microservices

Version Kafka(bin): kafka_2.12-2.3.0

[Kafak](https://kafka.apache.org/downloads)

Start Zooleeper
```sh
 bin/zookeeper-server-start.sh  config/zookeeper.properties
 ```
 
Start Kafka:
```sh
bin/kafka-server-start.sh config/server.properties
```

Create Topic:
```sh
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
```

List Topic:
```sh
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

Describre Topic:
```sh
bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

Create Producer:
```sh
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```
```sh
  * pedido0,55
  
  * pedido1,3434
  
  * pedido2,3453
  
```
  
  
Create Consumer:
```sh
 bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
 ```
```sh
 bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --from-beginning
 ```

 
Parallelizing using partitions. The partitions that define how many consumers are running:
```sh
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic ECOMMERCE_NEW_ORDER --partitions 3

or

File config/server.properties
# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
num.partitions=1 to num.partitions=3
 ```



Analyze consumption groups:
```sh
bin/kafka-consumer-groups.sh  --all-groups --bootstrap-server localhost:9092 --describe
```


Create directory out of time, in the root folder of apps:
```sh
mkdir data
mkdir data/zookeeper
mkdir data/kafka

vi config/server.properties

Replace the path log.dirs=/temp/Kafka 
with path log.dirs=/Users/JoaoVitor/Documents/apps/data/kafka

vi config/zookeeper.properties

Replace the path log.dirs=/temp/zookeeper 
with path dataDir=/Users/JoaoVitor/Documents/apps/data/zookeeper
```

Duplicate broker
```sh
cp config/server.properties config/server2.properties

vi config/server2.properties

Replace the path broker.id=0
with path broker.id=2

Replace the path log.dirs=/Users/JoaoVitor/Documents/apps/data/kafka
with path log.dirs=/Users/JoaoVitor/Documents/apps/data/kafka2

Replace the path listeners=PLAINTEXT://:9092
with path listeners=PLAINTEXT://:9093

```

Start Kafka serve2:
```sh
bin/kafka-server-start.sh config/server2.properties
```


Replicate topic to another broker. In the server config file add the command:
```sh
broker.id=0
default.replication.factor=2
```

Replicate topic to another broker. In the server2 config file add the command:
```sh
broker.id=2
default.replication.factor=2
```




  
