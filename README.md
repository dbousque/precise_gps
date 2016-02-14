# precise_gps
GPS path finding based on data from OpenStreetMap.

Best path searching for cars. The algorithm is based around a priority queue, from which search nodes are popped in the order defined by the cost function (in Priority.java), currently based on the length of the path.
A map from Paris is provided as a example, so you can run tests with adresses in Paris.

The program outputs .ways files, descripting the best way found. You can convert those .ways file to shapefiles using the python script like so :
```
python shapefiles/ways_to_shapefile/ways_to_shapefile.py shapefiles/address1_addres2.ways
```

To compile and run the program, you must first download json.jar here : http://mvnrepository.com/artifact/org.json/json. Then update your java CLASSPATH like so (on *nixes systems) :
```
CLASSPATH=$CLASSPATH:<location_of_json.jar>:.
export CLASSPATH
```
Then :
```
javac *.java
java Parse
```
