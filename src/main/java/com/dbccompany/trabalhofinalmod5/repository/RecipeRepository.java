package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
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

import static com.mongodb.client.model.Projections.fields;

@Repository
public class RecipeRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "recipes";

    public String findBy(String field, String condition) {
        MongoClient client = ConnectionMongo.createConnection();
        String json = getCollectionRecipe(client).find(new Document(field, condition)).first().toJson();
        ConnectionMongo.closeConnection(client);
        return json;
    }

    public void aggregatingRecipe(Bson pipeline, Bson... options) {
        MongoClient client = ConnectionMongo.createConnection();
        List<Bson> aggregation = new ArrayList<>();
        aggregation.add(pipeline);
        aggregation.addAll(Arrays.asList(options));
        getCollectionRecipe(client).aggregate(aggregation).forEach(doc -> System.out.println(doc.toJson()));
        ConnectionMongo.closeConnection(client);
    }

    public void findAllProjection(String... fields) {
        MongoClient client = ConnectionMongo.createConnection();
        Bson projection = fields(Projections.include(fields), Projections.excludeId());
        FindIterable<Document> users = getCollectionRecipe(client).find().projection(projection);
        for (Document user : users) System.out.println(user.toJson());
        ConnectionMongo.closeConnection(client);
    }

    public void saveRecipe(RecipeEntity recipe) {
        MongoClient client = ConnectionMongo.createConnection();
        getCollectionRecipe(client).insertOne(
                new Document("author", recipe.getAuthor())
                        .append("recipeName", recipe.getRecipeName())
                        .append("prepareRecipe", recipe.getPrepareRecipe())
                        .append("prepareTime", recipe.getPrepareTime())
                        .append("price", recipe.getPrice())
                        .append("calories", recipe.getCalories())
                        .append("ingredients", recipe.getIngredients())
                        .append("classifications", recipe.getClassifications())
        );
        ConnectionMongo.closeConnection(client);
    }

//    public void deleteRecipe (){
//        MongoClient client = ConnectionMongo.createConnection();
//        getCollectionRecipe(client).deleteOne()
//    }

    private MongoCollection<Document> getCollectionRecipe(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }
}
