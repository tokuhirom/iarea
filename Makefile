all: src/main/resources/me/geso/iarea/IAreaFinder/meshcode.properties

src/main/resources/me/geso/iarea/IAreaFinder/meshcode.properties: author/iarea.pl
	perl author/iarea.pl

clean:
	rm -f src/main/resources/me/geso/iarea/IAreaFinder/meshcode.properties

.PHONY: all clean

