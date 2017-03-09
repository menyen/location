# Locations
Rest API to save locations using spring boot and mongodb

To run the application, simply run LocationApplication.java as a java application. This will make it work as a web application.
To run the tests, run the LocationApplicationTests.java as a JUnit test.

## Test the application

Now that the application is running, you can test it. You can use any REST client you wish. The following examples use the *nix tool curl.

First you want to see the top level service.

```sh
$ curl http://localhost:8080

{
  "_links" : {
    "locations" : {
      "href" : "http://localhost:8080/locations{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/profile"
    }
  }
}
```
Here you get a first glimpse of what this server has to offer. There is a **locations** link located at http://localhost:8080/locations. It has some options such as `?page`, `?size`, and `?sort`.

```sh
$ curl http://localhost:8080/locations
{
  "_embedded" : {
    "locations" : [ ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/locations"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/locations"
    },
    "search" : {
      "href" : "http://localhost:8080/locations/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 0,
    "totalPages" : 0,
    "number" : 0
  }
}
```

There are currently no elements and hence no pages. Time to create a new `LocationEntity`!
```sh
$ curl -i -X POST -H "Content-Type:application/json" -d "{\"address\":\"Avenida Pedro Álvares Cabral\", \"location\":{\"type\": \"Point\", \"coordinates\": [-23.5874162, -46.6576336]}, \"name\": \"Parque Ibirapuera\", \"enabled\": true}" http://localhost:8080/locations

HTTP/1.1 201 
Location: http://localhost:8080/locations/58be0fd6871c030fc440d42a
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 07 Mar 2017 01:41:42 GMT

{
  "name" : "Parque Ibirapuera",
  "address" : "Avenida Pedro Álvares Cabral",
  "location" : {
    "x" : -23.5874162,
    "y" : -46.6576336,
    "coordinates" : [ -23.5874162, -46.6576336 ],
    "type" : "Point"
  },
  "enabled" : true,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/locations/58be0fd6871c030fc440d42a"
    },
    "locationEntity" : {
      "href" : "http://localhost:8080/locations/58be0fd6871c030fc440d42a"
    }
  }
}
```


 * `-i` ensures you can see the response message including the headers. The URI of the newly created Person is shown

 * `-X POST` signals this a POST used to create a new entry

 * `-H "Content-Type:application/json"` sets the content type so the application knows the payload contains a JSON object

 * `-d '{"addressess":["Avenida Pedro Álvares Cabral"], "location":{"type": "Point", "coordinates": [-23.5874162, -46.6576336]}, "names": ["Parque Ibirapuera", "parque", "ibirapuera", "Pq Ibirapuera", "Ibira"], "enabled": true}'` is the data being sent

You can query directly for the individual record:
```sh
$ curl http://localhost:8080/locations/58be0fd6871c030fc440d42a
{
   "id": "58be0fd6871c030fc440d42a",
   "name": "Parque Ibirapuera",
   "address": "Avenida Pedro Álvares Cabral",
   "location": {
      "x":-23.5874162,
      "y":-46.6576336,
      "coordinates":[-23.5874162,-46.6576336],
      "type":"Point"
   },
   "enabled":true
}
```

There is a custom query that we can use to find locations:
* Using `findByNameAndLocationNear`:
```sh
$ curl "http://localhost:8080/locations/search/findByNameAndLocationNear?name=Parque Ibirapuera&longitude=-23.5874162&latitude=-46.6576336&distance=1"
[
   {
      "id":"58be0fd6871c030fc440d42a",
      "name":"Parque Ibirapuera",
      "address":"Avenida Pedro Álvares Cabral",
      "location":{
         "x":-23.5874162,
         "y":-46.6576336,
         "coordinates":[-23.5874162,-46.6576336],
         "type":"Point"
      },
      "enabled":true
   }
]
```

You can also issue `PUT`, `PATCH`, and `DELETE` REST calls to either replace, update, or delete existing records.
```sh
$ curl -X PUT -H "Content-Type:application/json" -d "{\"address\":\"Avenida Pedro Álvares Cabral\", \"location\":{\"type\": \"Point\", \"coordinates\": [-23.5874162, -46.6576336]}, \"name\": \"Parque Ibirapueraaa\", \"enabled\": true}" http://localhost:8080/locations/58be0fd6871c030fc440d42a
$ curl http://localhost:8080/locations/58be0fd6871c030fc440d42a
{
   "id":"58be0fd6871c030fc440d42a",
   "name":"Parque Ibirapueraaa",
   "address":"Avenida Pedro Álvares Cabral",
   "location":{
      "x":-23.5874162,
      "y":-46.6576336,
      "coordinates":[-23.5874162,-46.6576336],
      "type":"Point"
   },
   "enabled":true
}
```

```sh
$ curl -X PATCH -H "Content-Type:application/json" -d "{\"address\":\"Av Pedro Álvares Cabraaal\"}" http://localhost:8080/locations/58be0fd6871c030fc440d42a
$ curl http://localhost:8080/locations/58be0fd6871c030fc440d42a
{
   "id":"58be0fd6871c030fc440d42a",
   "name":"Parque Ibirapueraaa",
   "address":"Av Pedro Álvares Cabraaal",
   "location":{
      "x":-23.5874162,
      "y":-46.6576336,
      "coordinates":[-23.5874162,-46.6576336],
      "type":"Point"
   },
   "enabled":true
}
```

```sh
$ curl -X DELETE http://localhost:8080/locations/58be0fd6871c030fc440d42a
$ curl http://localhost:8080/locations/58be0fd6871c030fc440d42a
{
   "id":"58be0fd6871c030fc440d42a",
   "names":"Parque Ibirapueraaa",
   "address":"Av Pedro Álvares Cabraaal",
   "location":{
      "x":-23.5874162,
      "y":-46.6576336,
      "coordinates":[-23.5874162,-46.6576336],
      "type":"Point"
   },
   "enabled":false
}
```
As you can see, `DELETE` doesn't actually delete the location, it only change the `enabled` value to `false`
