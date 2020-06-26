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

import static com.example.kafka_java.Constants.ECOMMERCE_SEND_EMAIL;

public class CreateUserServiceMain {

    private final Connection connection;

    CreateUserServiceMain(){
        String url = "jdbc:sqlite:target/users_database.db";
        try {
            this.connection = DriverManager.getConnection(url);
            connection.createStatement()
                    .execute("create table Users( uuid varchar(200) primary key, email varchar(200))");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao abrir con", e);
        }
    }

    public static void main(String[] args)  {
        var createUserService = new CreateUserServiceMain();
        try (var service = new KafkaService<>
                (CreateUserServiceMain.class.getSimpleName(),
                        ECOMMERCE_SEND_EMAIL,
                        createUserService::parse, OrderDomain.class )) {
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, OrderDomain> record){

        System.out.println("---------------------------------");
        System.out.println("Sending email");
        System.out.println(record.topic());
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("offset: " + record.offset());

        if(isNewUser(record.value().getEmail())){
            insertNewUser(record.value().getEmail());
        }
    }

    private void insertNewUser(String email) {

        String sql = "insert into Users (uuid, email) values (?, ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, email);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        System.out.println("Usuario: "+ email+ " inserido com sucesso!");
    }

    private boolean isNewUser(String email) {
        String sql = "select uuid from Users where email = ?";

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
