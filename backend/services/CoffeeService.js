/*jslint node: true */
"use strict"
const MongoHandler = require("../database/MongoHandler.js")

function CoffeeService() {
    this.dbHandler = new MongoHandler("coffee", "mongodb://localhost:27017/dolceGusto")
}

//SETUP
CoffeeService.prototype.setupDatabase = function(){
    return this.dbHandler.setUniqueIndex({name: 1})
}


// CREATE COFFEE
CoffeeService.prototype.createCoffeeEndpoint = "/coffee"
CoffeeService.prototype.createCoffeeHandler = function (request, response){
    if(!request.body || !request.body.name) {
        var error = new Error()
        error.statusCode = 1
        error.message = "unable to insert data without a name"
        response.status(400).send(error)
        return
    }
    
    this.dbHandler.insertOne(request.body)
    .then(response.send.bind(response))
    .catch(err => response.status(400).send(err))
}

// CREATE/UPDATE COFFEE
CoffeeService.prototype.updateCoffeeEndpoint = "/coffee"
CoffeeService.prototype.updateCoffeeHandler = function (request, response){
    if (!request.body || !request.body.name) {
        var error = new Error()
        error.statusCode = 1
        error.message = "unable to insert data without a name"
        response.status(400).send(error)
        return
    }
    this.dbHandler.updateField({name: request.body.name},request.body,true)
    .then(response.send.bind(response))
    .catch(err => response.status(400).send(err))
}

// GET COFFEES
CoffeeService.prototype.getCoffeeEndpoint = "/coffee"
CoffeeService.prototype.getCoffeeHandler = function (request, response){
    this.dbHandler.getAll({},{_id:0})
    .then(response.send.bind(response))
    .catch(err => response.status(400).send(err))
}

// DELETE COFFEES
CoffeeService.prototype.deleteCoffeeEndpoint = "/coffee/:name"
CoffeeService.prototype.deleteCoffeeHandler = function (request, response){
    if (!request.params || !request.params.name) {
        var error = new Error()
        error.statusCode = 1
        error.message = "unable to delete data without a name"
        response.status(400).send(error)
        return
    }
    
    this.dbHandler.removeOne(request.params)
    .then(response.send.bind(response))
    .catch(err => response.status(404).send(err))
}

CoffeeService.prototype.deleteAllCoffeeEndpoint = "/coffee"
CoffeeService.prototype.deleteAllCoffeeHandler = function (request, response){
    this.dbHandler.removeAll()
    .then(response.send.bind(response))
    .catch(err => response.status(404).send(err))
}

module.exports.shared = new CoffeeService()