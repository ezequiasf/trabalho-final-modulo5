package com.dbccompany.trabalhofinalmod5.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ConnectionMongo {

    public static MongoClient createConnection(String url) {
        return MongoClients.create(url);
    }

    public static void closeConnection (MongoClient client){
        client.close();
    }
}
