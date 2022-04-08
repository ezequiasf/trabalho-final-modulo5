package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;

@Repository
public class UserRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "users";

    public UserEntity findByUsername(String username) throws UserDontExistException {
        MongoClient client = ConnectionMongo.createConnection();

        UserEntity user = verifyIfUserDontExist(client, username);

        ConnectionMongo.closeConnection(client);
        return user;
    }

    public List<UserEntity> aggregatingUser(Bson pipeline, Bson... options) {
        MongoClient client = ConnectionMongo.createConnection();

        List<UserEntity> userEntities = new ArrayList<>();
        List<Bson> aggregation = new ArrayList<>();

        aggregation.add(pipeline);
        aggregation.addAll(Arrays.asList(options));

        getCollectionUser(client).aggregate(aggregation).forEach(doc -> userEntities.add(convertDocument(doc)));

        ConnectionMongo.closeConnection(client);
        return userEntities;
    }

    public List<UserEntity> findAllProjection(String... fields) {
        MongoClient client = ConnectionMongo.createConnection();

        List<UserEntity> userEntities = new ArrayList<>();

        Bson projection = fields(Projections.include(fields), Projections.excludeId());

        FindIterable<Document> users = getCollectionUser(client).find().projection(projection);

        users.forEach(document -> userEntities.add(convertDocument(document)));

        ConnectionMongo.closeConnection(client);
        return userEntities;
    }

    public void saveUser(UserEntity user) throws UserAlreadyExistsException {
        MongoClient client = ConnectionMongo.createConnection();

        verifyIfUserAlreadyExists(client, user).insertOne(convertUserEntity(user));

        ConnectionMongo.closeConnection(client);
    }

    public void updateUser(String username, UserEntity newUser) throws UserDontExistException {
        MongoClient client = ConnectionMongo.createConnection();

        UserEntity user = verifyIfUserDontExist(client, username);
        newUser.setUsername(user.getUsername());

        getCollectionUser(client).updateOne(eq("username", username),
                new Document("$set", convertUserEntity(newUser)));

        ConnectionMongo.closeConnection(client);
    }

    public void deleteUser(String username) {
        MongoClient client = ConnectionMongo.createConnection();

        getCollectionUser(client).deleteOne(eq("username", username));

        ConnectionMongo.closeConnection(client);
    }

    public MongoCollection<Document> verifyIfUserAlreadyExists(MongoClient client, UserEntity user) throws UserAlreadyExistsException {
        MongoCollection<Document> userCollection = getCollectionUser(client);

        Document docUser = userCollection.find(new Document("username", user.getUsername())).first();
        if (docUser != null) {
            throw new UserAlreadyExistsException("User already created!");
        }

        return userCollection;
    }

    private MongoCollection<Document> getCollectionUser(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }

    private UserEntity convertDocument(Document docUser) {
        return UserEntity.builder()
                .username(docUser.getString("username"))
                .age(docUser.getInteger("age"))
                .email(docUser.getString("email"))
                .password(docUser.getString("password"))
                .isactive(docUser.getBoolean("isactive"))
                .build();
    }

    private Document convertUserEntity(UserEntity user) {
        return new Document("username", user.getUsername())
                .append("email", user.getEmail())
                .append(("age"), user.getAge())
                .append("password", user.getPassword())
                .append("isactive", user.isIsactive());
    }

    private UserEntity verifyIfUserDontExist(MongoClient client, String username) throws UserDontExistException {
        Document docUser = getCollectionUser(client)
                .find(new Document("username", username)).first();

        if (docUser != null) {
            return convertDocument(docUser);
        }

        throw new UserDontExistException("User don't exist");
    }

}
