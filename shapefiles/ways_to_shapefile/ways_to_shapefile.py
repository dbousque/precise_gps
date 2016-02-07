

import shapefile, sys

class Way:

	def __init__(self, lines):
		self.type = lines[0]
		self.length = lines[1]
		self.time = lines[2]
		self.speed_limit = lines[3]
		self.id = lines[4]
		self.oneway = lines[5]
		self.access = lines[6]
		self.nodes = self.lines_to_nodes(lines[7:])

	def lines_to_nodes(self, lines):
		nodes = []
		for line in lines:
			nodes.append([float(line.split(",")[0]), float(line.split(",")[1])])
		return nodes

def get_ways(lines):
	ways = []
	start = 0
	for i,line in enumerate(lines):
		if len(line) == 0:
			ways.append(Way(lines[start:i]))
			start = i + 1
	return ways

def ways_to_shapefile(filename):
	res = shapefile.Writer(shapefile.POLYLINE)
	res.field("type")
	res.field("length")
	res.field("time")
	res.field("speed_limit")
	res.field("id")
	res.field("oneway")
	res.field("access")
	fich = open(filename).read().split('\n')
	ways = get_ways(fich)
	for way in ways:
		res.line(parts=[way.nodes])
		res.record(way.type, way.length, way.time, way.speed_limit, way.id, way.oneway, way.access)
	res.save(filename.replace(".ways", ""))

if sys.argv[1]:
	ways_to_shapefile(sys.argv[1])