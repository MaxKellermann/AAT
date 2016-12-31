package ch.bailu.aat.mapsforge.layer.gpx;

import android.content.SharedPreferences;
import android.graphics.Color;


import org.mapsforge.core.graphics.Canvas;
import org.mapsforge.core.graphics.Cap;
import org.mapsforge.core.graphics.Join;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;

import ch.bailu.aat.mapsforge.layer.context.MapContext;
import ch.bailu.aat.mapsforge.layer.context.MapContextTwoNodes;
import ch.bailu.aat.views.graph.ColorTable;

public class TrackLayer  extends GpxLayer{
    private static final int STROKE_WIDTH=3;
    private final Paint paint;


    private final MapContext mcontext;

    public TrackLayer(MapContext mc) {
        super(Color.BLACK);
        mcontext = mc;
        paint = AndroidGraphicFactory.INSTANCE.createPaint();

        paint.setStrokeWidth(mcontext.metrics.density.toDPf(STROKE_WIDTH));
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeJoin(Join.ROUND);
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void draw(BoundingBox boundingBox, byte zoomLevel, Canvas canvas, Point topLeftPoint) {
        new TrackPainter().walkTrack(getGpxList());
    }


    private class TrackPainter extends GpxListPainter {
        public TrackPainter() {
            super(mcontext);

        }


        @Override
        public void drawEdge(MapContextTwoNodes nodes) {
            mcontext.draw.edge(nodes, paint);
        }




        @Override
        public void drawNode(MapContextTwoNodes.PixelNode node) {
            int altitude=node.point.getAltitude();
            int color= ColorTable.altitude.getColor(altitude);
            paint.setColor(color);
        }
    }
}