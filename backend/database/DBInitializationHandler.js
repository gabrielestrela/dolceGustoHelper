/*jslint node: true */
"use strict"

const coffees = require('./dolcegusto.json')

module.exports.getDolcegustoDB = function() {
    return coffees
}