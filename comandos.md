Version Kafka:

Start Zooleeper
> bin/zookeeper-server-start.sh  config/zookeeper.properties

Start Kafka
> bin/kafka-server-start.sh config/server.properties


Create Topic
> bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO

List Topic
> bin/kafka-topics.sh --list --bootstrap-server localhost:9092

Describre  Topic
> bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe

Create Producer
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
  * pedido0,550
  * pedido1,3434
  * pedido2,3453
  
Create Consumer
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --from-beginning
  