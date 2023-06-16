# Mobile Development
This is source code or part of mobile developemnt from this application

## Dependencies
These are libraries added to this project
1. Retrofit
2. Room
3. CameraX
4. TF Lite
5. Glide
6. Data Store
7. Live Data
8. Navigation

## How does the image image classifying work

The image classifying system
1. You can use camera or you can get picture from gallery. Then the file is processed by TF Lite Model to classify the fruit. The result will shows after that and you can see detail of that fruit by pressing detail button, or you can go back to home page by using finish button

## Fetching data
This API endpoint is used for fetching fruits data by using `GET` method
```
https://nutrifruity1-qfwlnztfjq-as.a.run.app/buah/
```
and here is the endpoint for fetching detail of each fruit
```
https://nutrifruity1-qfwlnztfjq-as.a.run.app/buah/{id}
```
## Guessing fruit game
There is one feature for make this application more atractive, that is guessing game. You will guess random blank fruit image and you must choose the correct fruit name.
