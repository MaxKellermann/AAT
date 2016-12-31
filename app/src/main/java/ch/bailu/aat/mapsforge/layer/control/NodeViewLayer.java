package ch.bailu.aat.mapsforge.layer.control;

import android.graphics.Color;
import android.view.View;

import ch.bailu.aat.helpers.AppTheme;
import ch.bailu.aat.mapsforge.layer.context.MapContext;
import ch.bailu.aat.views.HtmlScrollTextView;

public abstract class NodeViewLayer extends NodeSelectorLayer implements View.OnLongClickListener {
    private static final int XMARGIN=0;
    private static final int YMARGIN=0;

    private final int big_margin;

    private final HtmlScrollTextView infoView;

    private int xoffset, yoffset;

    private final MapContext clayer;


    public NodeViewLayer(MapContext cl) {
        super(cl);
        clayer = cl;
        big_margin = AppTheme.getBigButtonSize(cl.context) + XMARGIN;

        infoView = new HtmlScrollTextView(cl.context);
        infoView.setBackgroundColor(Color.argb(0xcc, 0xff, 0xff, 0xff));
        infoView.getTextView().setTextColor(Color.BLACK);
        infoView.getTextView().setOnLongClickListener(this);
        infoView.setVisibility(View.GONE);

        cl.mapView.addView(infoView);

    }



    public void setHtmlText(String text) {
        infoView.setHtmlText(text);

        measure();
        layout();
        infoView.invalidate();
    }

    public void showAtLeft() {
        toLeft();
        show();
    }


    public void showAtRight() {
        toRight();
        show();
    }


    public void hide() {
        infoView.setVisibility(View.GONE);
        requestRedraw();
    }


    private void show() {
        measure();
        layout();
        infoView.setVisibility(View.VISIBLE);

        requestRedraw();

    }


    private void layout() {
        infoView.layout(
                xoffset,
                yoffset,
                xoffset+getWidth(),
                yoffset+getHeight());
    }

    private void measure() {
        int wspec = View.MeasureSpec.makeMeasureSpec(getWidth(),
                View.MeasureSpec.EXACTLY);
        int hspec = View.MeasureSpec.makeMeasureSpec(getHeight(),
                View.MeasureSpec.EXACTLY);

        infoView.measure(wspec, hspec);
    }



    private void toLeft() {
        xoffset=XMARGIN;
        yoffset=YMARGIN;
    }

    public void toRight() {
        xoffset=big_margin;
        yoffset=YMARGIN;

    }

    private int getHeight() {
        return clayer.mapView.getHeight() / 3;
    }

    private int getWidth() {
        return clayer.mapView.getWidth() - big_margin;
    }
}