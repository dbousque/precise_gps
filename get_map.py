import requests, codecs


req = "<osm-script output=\"json\" timeout=\"910\">\
  <union>\
    <query type=\"way\">\
      <has-kv k=\"highway\" regv=\"motorway|motorway_link|trunk|trunk_link|primary|primary_link\"/>\
      <bbox-query e=\"8.297352\" w=\"-4.908836\" n=\"51.443333\" s=\"42.092404\"/>\
    </query>\
  </union>\
  <!-- print results -->\
  <print mode=\"body\"/>\
  <recurse type=\"down\"/>\
  <print mode=\"skeleton\" order=\"quadtile\"/>\
</osm-script>"

res = codecs.open("france_simple.json", "w", "utf-8")
r = requests.post("http://overpass-api.de/api/interpreter", data={'data': req})
res.write(r.text)
res.close()