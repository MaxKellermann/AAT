package ch.bailu.aat.preferences.location;

import android.content.Context;

import org.mapsforge.core.model.LatLong;

import ch.bailu.aat.R;
import ch.bailu.aat.coordinates.CH1903Coordinates;
import ch.bailu.aat.coordinates.Coordinates;
import ch.bailu.aat.coordinates.OlcCoordinates;
import ch.bailu.aat.coordinates.UTMCoordinates;
import ch.bailu.aat.coordinates.WGS84Coordinates;
import ch.bailu.aat.exception.ValidationException;
import ch.bailu.aat.map.MapViewInterface;
import ch.bailu.aat.preferences.SolidString;
import ch.bailu.aat.util.ui.AppLog;
import ch.bailu.aat.views.preferences.SolidTextInputDialog;

public class SolidGoToLocation extends SolidString {
    private final static String KEY = "GoToLocation";
    public SolidGoToLocation(Context c) {
        super(c, KEY);
    }


    private LatLong reference = null;


    @Override
    public String getLabel() {
        return getString(R.string.p_goto_location);
    }


    public void goToLocationFromUser(MapViewInterface map) {

        reference = map.getMapViewPosition().getCenter();

        new SolidTextInputDialog(this, SolidTextInputDialog.TEXT, v -> goToLocation(map, getValueAsString()));
    }

    public void goToLocation(MapViewInterface map, String s) {
        reference = map.getMapViewPosition().getCenter();

        try {
            map.setCenter(latLongFromString(s));

        } catch (Exception e) {
            AppLog.e(map.getMContext().getContext(), e);
        }

    }


    private LatLong latLongFromString(String code)
            throws  IllegalArgumentException, IllegalStateException {

        try {
            if (reference != null)
                return new OlcCoordinates(code, reference).toLatLong();

            else
                return new OlcCoordinates(code).toLatLong();


        } catch (Exception eOLC) {
            try {
                return new CH1903Coordinates(code).toLatLong();

            } catch(Exception eCH1903) {
                try {
                    return new WGS84Coordinates(code).toLatLong();

                } catch (Exception eWGS) {
                    try {
                        return new UTMCoordinates(code).toLatLong();
                    } catch(Exception eUTM) {
                        throw Coordinates.getCodeNotValidException(code);
                    }
                }
            }
        }
    }


    @Override
    public void setValueFromString(String s) throws ValidationException {
        s = s.trim();


        if (! validate(s)) {
            throw new ValidationException(getString(R.string.p_goto_location_hint));
        } else {
            setValue(s);
        }
    }


    @Override
    public boolean validate(String s) {
        try  {
            return latLongFromString(s) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
