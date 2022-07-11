package io.storydoc.fabric.mongo;

import com.mongodb.client.*;
import io.storydoc.fabric.core.ComponentHandler;
import org.bson.Document;

public class MongoHandler extends ComponentHandler<MongoComponent, MongoComponentDescriptor> {

    public MongoHandler() {
        super(MongoComponent.class);
    }

    @Override
    public void createBundle(MongoComponentDescriptor componentDescriptor) {
        try (MongoClient mongoClient = MongoClients.create(componentDescriptor.getConnectionUrl())) {
            MongoDatabase database = mongoClient.getDatabase("dbName");
            MongoIterable collectionNames = database.listCollectionNames();
            collectionNames.forEach(name -> System.out.println("******" + name));

            MongoCollection<Document> collection = database.getCollection("collName");
            FindIterable<Document> documents = collection.find();

            documents.forEach(doc -> System.out.println("******************" + doc.toJson()));
        }

    }

}
