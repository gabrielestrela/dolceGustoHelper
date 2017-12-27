/*jslint node: true */
"use strict"

const Express = require('express')
const BodyParser = require('body-parser')
const CoffeeService = require('./services/CoffeeService.js')
const WikipediaHandler = require('./database/WikipediaHandler.js')
const DBInitializationHandler = require('./database/DBInitializationHandler.js')
const MongoHandler = require("./database/MongoHandler.js")
const app = Express()

const USE_WIKIPEDIA = false

const listeningPort = 8080
app.use(BodyParser.json())

/* ROUTES */
app.post(CoffeeService.shared.createCoffeeEndpoint, CoffeeService.shared.createCoffeeHandler.bind(CoffeeService.shared))
app.put(CoffeeService.shared.updateCoffeeEndpoint, CoffeeService.shared.updateCoffeeHandler.bind(CoffeeService.shared))
app.get(CoffeeService.shared.getCoffeeEndpoint, CoffeeService.shared.getCoffeeHandler.bind(CoffeeService.shared))
app.delete(CoffeeService.shared.deleteCoffeeEndpoint, CoffeeService.shared.deleteCoffeeHandler.bind(CoffeeService.shared))
app.delete(CoffeeService.shared.deleteAllCoffeeEndpoint, CoffeeService.shared.deleteAllCoffeeHandler.bind(CoffeeService.shared))

/* STARTUP */
app.listen(listeningPort, function(){
    console.log("Started listening at " + listeningPort)
    
    // updates the database with the new data from wikipedia
    var wikipediaHandler = new WikipediaHandler()
    var dbHandler = new MongoHandler("coffee", "mongodb://localhost:27017/dolceGusto")
    CoffeeService.shared.setupDatabase()
    .then((result) => {
        if(USE_WIKIPEDIA) {
            return wikipediaHandler.getParsedDolceGustoPage()
        }
        return DBInitializationHandler.getDolcegustoDB()
    }).then((result) => {
        return Promise.all(result.map((element) => {
            return dbHandler.updateField({name: element.name}, element, true)
        }))
    }).then((dbOperationResult) => {
        console.log("success in updating database")
    }).catch((error) => {
        console.log(error)
    })
})


