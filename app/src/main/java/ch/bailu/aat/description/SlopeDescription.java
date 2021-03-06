package ch.bailu.aat.description;

import android.content.Context;

import ch.bailu.aat.R;
import ch.bailu.aat.gpx.attributes.AltitudeDelta;
import ch.bailu.aat.gpx.GpxInformation;

public class SlopeDescription extends ContentDescription {
    private String slope="0";

    public SlopeDescription(Context c) {
        super(c);
    }

    @Override
    public void onContentUpdated(int iid, GpxInformation info) {
        slope = info.getAttributes().get(AltitudeDelta.INDEX_SLOPE);
    }

    @Override
    public String getValue() {
        return slope;
    }

    public String getUnit() {
        return "%";
    }

    @Override
    public String getLabel() {
        return getContext().getString(R.string.d_slope);
    }
}
