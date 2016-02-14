import sys
from lxml import etree

path = "ile-de-france-latest.osm"
i = 0
for event, element in etree.iterparse(path):
	print etree.tostring(element)
	if i == int(sys.argv[1]):
		exit(0)
	i += 1

BEST QUERY :

<osm-script output="json" timeout="90">
  <union>
    <query type="way">
      <has-kv k="highway" regv="motorway|motorway_link|trunk|trunk_link|primary|primary_link|secondary|secondary_link|tertiary|tertiary_link|unclassified|unclassified_link|residential|residential_link|service|service_link|living_street"/>
      <bbox-query {{bbox}}/>
    </query>
  </union>
  <!-- print results -->
  <print mode="body"/>
  <recurse type="down"/>
  <print mode="skeleton" order="quadtile"/>
</osm-script>



Query for overpass-turbo:
<osm-script output="json" timeout="90">
  <union>
    <query type="way">
      <has-kv k="highway" v="motorway"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="motorway_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="trunk"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="trunk_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="primary"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="primary_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="secondary"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="secondary_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="tertiary"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="tertiary_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="unclassified"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="unclassified_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="residential"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="residential_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="service"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="service_link"/>
      <bbox-query {{bbox}}/>
    </query>
    <query type="way">
      <has-kv k="highway" v="living_street"/>
      <bbox-query {{bbox}}/>
    </query>
  </union>
  <!-- print results -->
  <print mode="body"/>
  <recurse type="down"/>
  <print mode="skeleton" order="quadtile"/>
</osm-script>


