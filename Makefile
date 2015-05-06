all: src/main/java/me/geso/geoutils/iarea/data/MeshCode2AreaCode.java

src/main/java/me/geso/geoutils/iarea/data/MeshCode2AreaCode.java: author/iarea.pl
	perl author/iarea.pl

clean:
	rm -f src/main/java/me/geso/geoutils/iarea/IArea.java src/main/java/me/geso/geoutils/data/MeshCode2AreaCode.java

.PHONY: all clean

