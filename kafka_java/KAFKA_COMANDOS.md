bin/zookeeper-server-start.sh config/zookeeper.properties 
bin/kafka-server-start.sh config/server.properties 

bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO

bin/kafka-topics.sh --list --bootstrap-server localhost:9092
bin/kafka-topics.sh --describe --bootstrap-server localhost:9092

bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning

Alterando topicos
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic ECOMMERCE_NEW_ORDER --partitions 3

Descrevendo grupos de consumo

bin/kafka-consumer-groups.sh --all-groups --bootstrap-server localhost:9092 --describe