HOW TO USE THIS SERVER

1.INSTALLING DEPENDENCIES:

MAC:  

    Homebrew: https://brew.sh/
        Command Line install: /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)”
        
    NodeJs: https://nodejs.org/
        Command Line install: brew install node

    MongoDB: https://docs.mongodb.com
        Command Line install: brew install mongodb

    Backend Code: https://gitlab.com/daitan-group/cops/mobile/edit/master/RampUp/iOS/DolceGusto/backend
    
2.RUNNING THE SERVER:

    ON ONE TERMINAL:
        mongod
    ON ANOTHER TERMINAL WINDOW:
        cd [path to Backend Code]
        npm install
        node app.js
        
        wait until "success in updating database" is printed on your screen.
        Upon start, the database will have all the coffees present on wikipedia, plus custom ones created by the user with custom names
        
3.SERVER DOCUMENTATION: 

It's a REST server with a small number of endpoints. It takes it's data from wikipedia to create all coffees objects.
The server runs as a http service on "localhost:8080"
A coffee has it's name as a unique index

ENDPOINTS:

/coffee/ (GET):

    Return an array with all the kinds of coffees we have on our database.
    The coffee object are like the following template:
    {
        "name": String,
        "intensity": Number,
        "mainWater": Number,
        "mainMilk": Number,
        "availability": [String],
        "notes": String
    }
    
/coffee/ (PUT):

    Add a new coffee to the database. If the coffee you add has the same name of one at the database, you will replace the existing one.
    The body of the request must have a object having at least a name on it, you can also add any other information you find usefull.
    {
        "name": String,
        ...
        ...
        ...
    }
    
/coffee/ (POST):

    Add a new coffee to the database. If the coffee you add has the same name of one at the database, an error will happen.
    The body of the request must have a object having at least a name on it, you can also add any other information you find usefull.
    {
        "name": String,
        ...
        ...
        ...
    }
    
    
/coffee/ (DELETE):

    Deletes the entire database of coffees.
    

/coffee/:name (DELETE):

    Deletes the coffee with the defined name