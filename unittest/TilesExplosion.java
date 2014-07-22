import java.io.FileNotFoundException;

import json.geojson.FeatureCollection;
import json.graphic.Display;
import json.topojson.api.TopojsonApi;
import json.topojson.topology.Topology;


public class TilesExplosion {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int iN = 16;
		int iM = 16;
		
		Topology[][] _res;
		int _N, _C_N;
		int _M, _C_M;
		Display _display;
		
		_display = new Display(1024, 600);
		_display.start();
		_display.clear();
		//_display.setDisplayListener(this);

		_N = iN;
		_M = iM;
		
		_C_N = iN/2;
		_C_M = iM/2;
		
		try {
			
			FeatureCollection aFeat = TopojsonApi.shpToGeojsonFeatureCollection("./data/MA.shp");
			
			aFeat._bnd.scale(1.9);
			_display.setBound(aFeat._bnd);
			
			_res = TopojsonApi.tileFeatureCollectionToTopojson(aFeat, 
					_N,_M,
					"MA", 
					10000 // Simplify
					);
			
			double CX=(aFeat._bnd.maxx+aFeat._bnd.minx)/2;
			double CY=(aFeat._bnd.maxy+aFeat._bnd.miny)/2;
			
			for (int i=0;i<_N; i++) {
				
				for (int j=0; j<_M; j++) {
					
					Topology aTopo = _res[i][j];
					
					double CPX = (aTopo._bnd.maxx+aTopo._bnd.minx)/2;
					double CPY = (aTopo._bnd.maxy+aTopo._bnd.miny)/2;
					
					double fact = 1.3;
					double Vx = (CPX-CX)*fact;
					double Vy = (CPY-CY)*fact;
					
					_res[i][j].draw(Vx,Vy, _display);
					
				}
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
