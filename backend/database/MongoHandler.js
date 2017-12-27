/*jslint node: true */
"use strict"

const MongoClient = require("mongodb").MongoClient

function MongoHandler(collectionName, url) {
    this.collectionName = collectionName
    this.url = url || MongoHandler.defaultDatabase
}

MongoHandler.prototype.handleError = function(error){
    throw error
}

MongoHandler.prototype.insertOne = function(data){
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        return collection.insertOne(data)
    }).then((result) => {
        console.log("Inserted data");
        if(database){
            database.close()
        }
        return result.ops[0]
    }).catch((error) => {
        if(database){
            database.close()
        }
        this.handleError(error)
    })
}

MongoHandler.prototype.getOne = function(filter, conditions) {
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        return collection.findOne(filter, conditions)
    }).then((result) => {
        if (!result){
            throw new Error("data not found")
        }
        if(database){
            database.close()
        }
        return result
    }).catch((error) => {
        if(database){
            database.close()
        }
        this.handleError(error)
    })
}

MongoHandler.prototype.getAll = function(filter, conditions) {
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        return collection.find(filter, conditions)
    }).then((result) => {
        if (!result){
            throw new Error("data not found")
        }
        return result.toArray()
    }).then((result) => {
        if(database){
            database.close()
        }
        return result
    }).catch((error) => {
        if(database){
            database.close()
        }
        this.handleError(error)
    })
}

MongoHandler.prototype.updateField = function(query, update, upsert) {
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        var options = {new:true,
                       upsert: upsert}
        return collection.findAndModify(query, [], update, options)
    }).then((result) => {
        if (!result.value) {
            throw new Error("data not found")
        }
        if (database){
            database.close()
        }
        return result.value
    }).catch((error) => {
        if(database) {
            database.close()
        }
        this.handleError(error)
    })
}

MongoHandler.prototype.removeOne = function (query) {
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        var options = {justOne: true}
        return collection.remove(query, options)
    }).then((result) => {
        if (result.result.n == 0) {
            var error = new Error()
            error.message = "data not found"
            error.statusCode = 404
            throw error
        }
        if(database) {
            database.close()
        }
        return true
    }).catch((error) => {
        console.log(error)
        if(database) {
            database.close()
        }
        this.handleError(error)
    })
}

MongoHandler.prototype.removeAll = function() {
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        return collection.drop()
    }).then((result) => {
        console.log(result)
        if(database) {
            database.close()
        }
        return result
    }).catch((error) => {
        console.log(error)
        if(database) {
            database.close()
        }
        this.handleError(error)
    })
}

MongoHandler.prototype.setUniqueIndex = function(object) {
    var database = null
    return MongoClient.connect(this.url)
    .then((db) => {
        database = db
        var collection = db.collection(this.collectionName)
        var options = {unique: true}
        return collection.createIndex(object, options)
    }).then((result) => {
        console.log(result)
        if(database) {
            database.close()
        }
        return result
    }).catch((error) => {
        console.log(error)
        if(database) {
            database.close()
        }
        this.handleError(error)
    })
}



module.exports = MongoHandler
module.exports.defaultDatabase = "mongodb://localhost:27017/test"