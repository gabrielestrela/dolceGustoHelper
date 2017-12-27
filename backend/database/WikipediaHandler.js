/*jslint node: true */
"use strict"

const https = require('https')

function WikipediaHandler() {}

var getContent = function(url){
    return new Promise((resolve, reject) => {
        https.get(url, (resp) => {
            let data = ""
            resp.on("data", (chunk) => {
                data += chunk
            })
            resp.on("end", () => {
                resolve(data);
            })
        }).on("error", (error) => {
            reject(error)
        })                 
    })
}

WikipediaHandler.prototype.getParsedDolceGustoPage = function (){
    return getContent("https://en.wikipedia.org/wiki/Dolce_Gusto")
    .then((data) => {
        var initialParsedData = data.match(/<table class="wikitable">([\d\D]*?)<\/table>/)[1]
        var regex = /<td.*?>([\d\D]*?)<\/td>\n<td.*?>([\d\D]*?)<\/td>\n<td.*?>([\d\D]*?)<\/td>\n<td.*?>([\d\D]*?)<\/td>\n<td.*?>([\d\D]*?)<\/td>\n<td.*?>([\d\D]*?)<\/td>\n/g
        var partialResult
        var result = []
        do {
            partialResult = regex.exec(initialParsedData)
            if (partialResult) {
                var name = partialResult[1].replace(/<.*?>/g,"")
                var intensity = parseInt(partialResult[2])
                intensity = isNaN(intensity) ? -1 : intensity
                var mainWater = parseInt(partialResult[3])
                mainWater = isNaN(mainWater) ? -1 : mainWater
                var mainMilk = parseInt(partialResult[4])
                mainMilk = isNaN(mainMilk) ? -1 : mainMilk
                var availability = partialResult[5].split(/ ?[,\.] ?/)
                var notes = partialResult[6].replace(/<.*?>/g,"")
                var fulldata = {
                    name: name,
                    intensity: intensity,
                    mainWater: mainWater,
                    mainMilk: mainMilk,
                    availability: availability,
                    notes:notes
                }
                result = result.concat(fulldata)
            }
        }while(partialResult)
        return result
    })
}

module.exports = WikipediaHandler