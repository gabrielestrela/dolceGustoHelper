/*jslint node: true */
"use strict"
//PromiseUtils

/*
Wrap a map function around a promise.
Arguments:
    - iterable: Any iterable were we are capable of doing a map
    - mappingFunction: A function which receives each of the iterable values and returns the mapped data
    - acceptUndefined (Optional): If true, if any step of the mapping returns a undefined, this will be added to the result. Otherwise, it will reject the promise 
*/
Promise.map = function (iterable, mappingFunction, acceptUndefined){
    return Promise.all(iterable.map(
        (value) => {
            return Promise.resolve(value)
            .then((value) => mappingFunction(value))
            .then((mappedData) => {
                if (mappedData !== undefined || acceptUndefined) {
                    return mappedData
                } else {
                    throw {data: value, error:new Error("unable to map the data with the given function")}
                }
            }).catch((error) => {throw error})
        }
    ))
}


/*
Makes a filter as a Promise.
Arguments:
    - iterable: Any iterable were we are capable of doing a map
    - filterFunction: A function which receives each of the iterable values and returns a boolean telling if that value must or must not be on the final answer
*/
Promise.filter = function (iterable, filterFunction){
    return Promise.map(iterable, filterFunction, true)
    .then((mappedData) => {
        var response = []
        for (var i = 0; i < mappedData.length; i++) {
            if (mappedData[i]) {
                response = response.concat(iterable[i])
            }
        }
        return response
    })
}