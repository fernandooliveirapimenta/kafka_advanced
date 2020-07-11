package com.example.kafka_java.basico;

import com.example.kafka_java.basico.commonKafka.KafkaService;
import com.example.kafka_java.basico.domain.OrderDomain;
import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static com.example.kafka_java.Constants.ECOMMERCE_NEW_ORDER;

public class CreateUserServiceMain {

    private Connection connection;

    CreateUserServiceMain(){
        String url = "jdbc:sqlite:target/users_database.db";
        try {
            this.connection = DriverManager.getConnection(url);
            try {
                connection.createStatement()
                        .execute("create table Users( uuid varchar(200) primary key, email varchar(200))");
            } catch (SQLException e) {
                System.out.println("Erro ao criar a tabela");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        var createUserService = new CreateUserServiceMain();
        try (var service = new KafkaService<>
                (CreateUserServiceMain.class.getSimpleName(),
                        ECOMMERCE_NEW_ORDER,
                        createUserService::parse, OrderDomain.class )) {
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, OrderDomain> record){

        System.out.println("---------------------------------");
        System.out.println("Processing new user");
        System.out.println(record.topic());
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("offset: " + record.offset());

        var r = record.value();
        if(isNewUser(r.getEmail())){
            insertNewUser(UUID.randomUUID().toString(), r.getEmail());
        } else {
            System.out.println("User ja exist " + r.getEmail());
        }
    }

    private void insertNewUser(String uuid, String email) {

        String sql = "insert into Users (uuid, email) values (?, ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, email);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        System.out.println("Usuario: "+ email+ " inserido com sucesso!");
    }

    private boolean isNewUser(String email) {
        String sql = "select uuid from Users where email = ? limit 1";

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            var results = preparedStatement.executeQuery();
            return  results.getFetchSize() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
