# precise_gps
GPS path finding based on data from OpenStreetMap.

Best path searching for cars. The algorithm is based around a priority queue, from which search nodes are popped in the order defined by the cost function (in Priority.java), currently based on the length of the path.
A map from Paris is provided as a example, so you can run tests with adresses in Paris.

Private roads are not taken, and oneway roads directions are respected.

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
You can open the shapefiles in QGIS (http://www.qgis.org/en/site/) on OpenJump for example.
The algorithm is working really well but I currently have trouble seeing how I could scale (finding paths on a whole continent) with very few memory. With full details (absolutely all roads crossable by cars from OpenStreeMap), you can load up to a 150km*150km map into 4GB of memory, but that's not really satisfactory. With 128 GB, I guess you could load Western Europe. I am eager to find out what kind of techniques could be used to reduce the amount of memory used, while remaining fast (maybe loading 50km*50km subsets while searching ?), so if you have ideas about that, I'd be happy to ear them.
