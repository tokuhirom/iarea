? my $iareas = shift;
package me.geso.geoutils.iarea.data;

public class MeshCode2AreaCode {
	public String getAreaCodeFromMeshCode(String meshCode) {
		switch (meshCode) {
? for my $i (2..7) {
?	for my $iarea (@{$iareas}) {
?		for my $mesh (@{$iarea->meshs->[$i-2]}) {
		case "<?= $mesh ?>":
			return "<?= $iarea->area_id . $iarea->sub_area_id ?>";
?		}
?	}
? }
		}
		return null;
	}

}
