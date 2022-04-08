package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;

@Repository
public class UserRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "users";

    public UserEntity findByUsername(String username) {
        MongoClient client = ConnectionMongo.createConnection();
        Document docUser = getCollectionUser(client)
                .find(new Document("username", username)).first();
        ;
        ConnectionMongo.closeConnection(client);
        return convertDocument(docUser);
    }

    public void aggregatingUser(Bson pipeline, Bson... options) {
        MongoClient client = ConnectionMongo.createConnection();
        List<Bson> aggregation = new ArrayList<>();
        aggregation.add(pipeline);
        aggregation.addAll(Arrays.asList(options));
        getCollectionUser(client).aggregate(aggregation).forEach(doc -> System.out.println(doc.toJson()));
        ConnectionMongo.closeConnection(client);
    }

    public void findAllProjection(String... fields) {
        MongoClient client = ConnectionMongo.createConnection();
        Bson projection = fields(Projections.include(fields), Projections.excludeId());
        FindIterable<Document> users = getCollectionUser(client).find().projection(projection);
        Iterator<Document> it = users.iterator();
        while (it.hasNext())
            System.out.println(it.next().toJson());
        ConnectionMongo.closeConnection(client);
    }

    public void saveUser(UserEntity user) throws UserAlreadyExistsException {
        MongoClient client = ConnectionMongo.createConnection();
        Document docUser = getCollectionUser(client)
                .find(new Document("username", user.getUsername())).first();
        if (docUser != null) {
            throw new UserAlreadyExistsException("User already created!");
        }
        getCollectionUser(client).insertOne(
                new Document("username", user.getUsername())
                        .append("email", user.getEmail())
                        .append("password", user.getPassword())
                        .append("isactive", user.isIsactive())
        );
        ConnectionMongo.closeConnection(client);
    }

    public void updateUser(String username, String attr, String value) {
        MongoClient client = ConnectionMongo.createConnection();
        getCollectionUser(client).updateOne(eq("username", username),
                new Document("$set", new Document(attr, value)));
        ConnectionMongo.closeConnection(client);
    }

    public void deleteUser(String username) {
        MongoClient client = ConnectionMongo.createConnection();
        getCollectionUser(client).deleteOne(eq("username", username));
        ConnectionMongo.closeConnection(client);
    }

    private MongoCollection<Document> getCollectionUser(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }

    private UserEntity convertDocument(Document docUser) {
        return UserEntity.builder().id(docUser.getObjectId("_id").toString())
                .username(docUser.getString("username"))
                .age(docUser.getInteger("age"))
                .email(docUser.getString("email"))
                .password(docUser.getString("password"))
                .isactive(docUser.getBoolean("isactive"))
                .build();
    }

}
