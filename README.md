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
 ```



Analyze consumption groups:
```sh
bin/kafka-consumer-groups.sh  --all-groups --bootstrap-server localhost:9092 --describe
```


  
