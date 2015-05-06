package me.geso.iarea;

import java.util.List;
import java.util.Optional;

/**
 * Created by tokuhirom on 5/6/15.
 */
public interface IAreaRepository {
	List<String> getRegions();

	List<String> getPrefectures();

	Optional<IArea> getAreaByWgs84LatLng(double latWGS84Degree, double lngWGS84Degree);
}
