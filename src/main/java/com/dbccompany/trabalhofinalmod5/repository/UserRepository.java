package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
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
    public final static String URL = "mongodb+srv://ezeq:HRznroxVyy37xQRw@clusterformyapp.cycds.mongodb.net/recipes_app?retryWrites=true&w=majority";

    public String findBy(String field, String condition) {
        MongoClient client = ConnectionMongo.createConnection(URL);
        String json = getCollectionUser(client).find(new Document(field, condition)).first().toJson();
        ConnectionMongo.closeConnection(client);
        return json;
    }

    public void aggregatingUser(Bson pipeline, Bson... options) {
        MongoClient client = ConnectionMongo.createConnection(URL);
        List<Bson> aggregation = new ArrayList<>();
        aggregation.add(pipeline);
        aggregation.addAll(Arrays.asList(options));
        getCollectionUser(client).aggregate(aggregation).forEach(doc -> System.out.println(doc.toJson()));
        ConnectionMongo.closeConnection(client);
    }

    public void findAllProjection(String... fields) {
        MongoClient client = ConnectionMongo.createConnection(URL);
        Bson projection = fields(Projections.include(fields), Projections.excludeId());
        FindIterable<Document> users = getCollectionUser(client).find().projection(projection);
        Iterator<Document> it = users.iterator();
        while (it.hasNext())
            System.out.println(it.next().toJson());
        ConnectionMongo.closeConnection(client);
    }

    public void saveUser(UserEntity user) {
        MongoClient client = ConnectionMongo.createConnection(URL);
        getCollectionUser(client).insertOne(
                new Document("username", user.getUsername())
                        .append("email", user.getEmail())
                        .append("password", user.getPassword())
                        .append("isactive", user.isIsactive())
        );
        ConnectionMongo.closeConnection(client);
    }

    public void updateUser(String username, String attr, String value) {
        MongoClient client = ConnectionMongo.createConnection(URL);
        getCollectionUser(client).updateOne(eq("username", username),
                new Document("$set", new Document(attr, value)));
        ConnectionMongo.closeConnection(client);
    }

    public void deleteUser(String username) {
        MongoClient client = ConnectionMongo.createConnection(URL);
        getCollectionUser(client).deleteOne(eq("username", username));
        ConnectionMongo.closeConnection(client);
    }

    private MongoCollection<Document> getCollectionUser(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }

}
