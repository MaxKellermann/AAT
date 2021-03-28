package ch.bailu.aat;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mapsforge.core.model.LatLong;

import ch.bailu.aat.coordinates.CH1903Coordinates;
import ch.bailu.aat.coordinates.LatLongE6;
import ch.bailu.aat.coordinates.LatLongE6Interface;
import ch.bailu.aat.coordinates.UTMCoordinates;
import ch.bailu.aat.coordinates.WGS84Coordinates;

public class CoordinatesTest {


    @Test
    public void testSexagesimal() {
        final double la = 47.629994;
        final double lo = 8.727773;

        LatLongE6 lalo = new LatLongE6(la, lo);

        WGS84Coordinates.Sexagesimal sla = new WGS84Coordinates.Sexagesimal(la);
        WGS84Coordinates.Sexagesimal slo = new WGS84Coordinates.Sexagesimal(lo);

        assertEquals(lalo.la, sla.toE6(),0);
        assertEquals(lalo.lo, slo.toE6(),0);

        sla = new WGS84Coordinates.Sexagesimal(sla.getDegree(), sla.getMinute(), sla.getSecond());
        slo = new WGS84Coordinates.Sexagesimal(slo.getDegree(), slo.getMinute(), slo.getSecond());

        assertEquals(lalo.la, sla.toE6(),0);
        assertEquals(lalo.lo, slo.toE6(),0);

    }



    @Test
    public void testCH1903Coordinates2() {
        // φ = 46° 2' 38.87" λ = 8° 43' 49.79" h = 650.60 m

        int la = new WGS84Coordinates.Sexagesimal(46,2,38.87).toE6();
        int lo = new WGS84Coordinates.Sexagesimal(8,43,49.79).toE6();

        CH1903Coordinates c = new CH1903Coordinates(new LatLongE6(la, lo));

        assertEquals(700000, c.getEasting());
        assertEquals(100000, c.getNorthing());

        assertEquals(la, c.toLatLongE6().la,5);
        assertEquals(lo, c.toLatLongE6().lo,5);


        WGS84Coordinates.Sexagesimal sla =  new WGS84Coordinates.Sexagesimal(c.toLatLong().latitude);
        WGS84Coordinates.Sexagesimal slo = new WGS84Coordinates.Sexagesimal(c.toLatLong().longitude);

        //           ⇒ λ = 8° 43' 49.80" φ = 46° 02' 38.86"
        // aus NAVREF: λ = 8° 43' 49.79" φ = 46° 02' 38.87"    h = 650.60 m=
        assertEquals(8, slo.getDegree());
        assertEquals(43, slo.getMinute());
        assertEquals(49.80, slo.getSecond(),0.005);

        assertEquals(46, sla.getDegree());
        assertEquals(02, sla.getMinute());
        assertEquals(38.86, sla.getSecond(),0.005);

    }

    @Test
    public void testRorschach() {
        testCH1903Coordinate(754900 , 260700, 47.478814, 9.493842);
        testUTMCoordinate(537208, 5258493, 32, 'T', 47.478814, 9.493842);
    }


    private void testUTMCoordinate(int easting, int northing, int ezone, char nzone,  double la, double lo) {
        UTMCoordinates u1 = new UTMCoordinates(la, lo);
        UTMCoordinates u2 = new UTMCoordinates(easting, northing, ezone, nzone);

        assertEquals(easting, u1.getEasting());
        assertEquals(northing, u1.getNorthing());

        assertEquals(la, u1.toLatLong().latitude,0.0001);
        assertEquals(lo, u1.toLatLong().longitude,0.0001);

        assertEquals(nzone, u1.getNorthingZoneCharacter());
        assertEquals(ezone, u1.getZone());

        assertEquals(la < 0, u1.isInSouthernHemnisphere());

        assertEquals(easting, u2.getEasting());
        assertEquals(northing, u2.getNorthing());

        assertEquals(la, u2.toLatLong().latitude,0.0001);
        assertEquals(lo, u2.toLatLong().longitude,0.0001);

        assertEquals(nzone, u2.getNorthingZoneCharacter());
        assertEquals(ezone, u2.getZone());

        assertEquals(la < 0, u2.isInSouthernHemnisphere());

    }

    @Test
    public void testGeneve() {
        testCH1903Coordinate(500532,117325, 46.200013,6.149985);
    }

    @Test
    public void testBern() {
        testCH1903Coordinate(600000, 200000, 46.9510827861504654, 7.4386324175389165);
        testUTMCoordinate( 	 381851, 5200553,32, 'T', 46.94798, 7.44743);
    }

    @Test
    public void testBerlin() {
        testUTMCoordinate( 	 392002, 5819913, 33, 'U', "geo:52.518611,13.408333");
    }

    private void testUTMCoordinate(int easting, int northing, int ezone, char nzone, String geoUrl) {
        testUTMCoordinate(easting, northing, ezone, nzone, new  WGS84Coordinates(geoUrl).getLatitude().getDecimal(), new WGS84Coordinates(geoUrl).getLongitude().getDecimal());
    }

    @Test
    public void testMontreal() {
        testUTMCoordinate("18T 612284 5040357", "geo:45.5077,-73.5626");
    }


    @Test
    public void testJohannesburg() {
        testUTMCoordinate("35J 606572 7101729", "geo:-26.2,28.066667");
    }

    private void testUTMCoordinate(String utm, String geoUrl) {
        UTMCoordinates u = new  UTMCoordinates(utm);

        testUTMCoordinate(u.getEasting(), u.getNorthing(), u.getZone(), u.getNorthingZoneCharacter(), geoUrl);
    }


    private void testCH1903Coordinate(int easting, int northing, double la, double lo) {
        final LatLongE6 latLongE6 = new LatLongE6(la, lo);
        final int laE6 = latLongE6.la;
        final int loE6 = latLongE6.lo;



        LatLongE6 l = new LatLongE6(laE6, loE6);
        CH1903Coordinates c1 = new CH1903Coordinates(l);
        CH1903Coordinates c2 = new CH1903Coordinates(easting, northing);
        CH1903Coordinates c3 = new CH1903Coordinates(la, lo);

        assertEquals(loE6, c1.toLatLongE6().lo, CH1903Coordinates.LO_PRECISSION.toE6());
        assertEquals(laE6, c1.toLatLongE6().la, CH1903Coordinates.LA_PRECISSION.toE6());

        assertEquals(la, c1.toLatLong().latitude, CH1903Coordinates.LA_PRECISSION.getDecimal());
        assertEquals(lo, c1.toLatLong().longitude, CH1903Coordinates.LO_PRECISSION.getDecimal());

        assertEquals(loE6, c2.toLatLongE6().lo,CH1903Coordinates.LO_PRECISSION.toE6());
        assertEquals(laE6, c2.toLatLongE6().la,CH1903Coordinates.LA_PRECISSION.toE6());

        assertEquals(la, c2.toLatLong().latitude, CH1903Coordinates.LA_PRECISSION.getDecimal());
        assertEquals(lo, c2.toLatLong().longitude, CH1903Coordinates.LO_PRECISSION.getDecimal());

        assertEquals(loE6, c3.toLatLongE6().lo,CH1903Coordinates.LO_PRECISSION.toE6());
        assertEquals(laE6, c3.toLatLongE6().la,CH1903Coordinates.LA_PRECISSION.toE6());

        assertEquals(la, c3.toLatLong().latitude, 0.0001);
        assertEquals(lo, c3.toLatLong().longitude, 0.0001);


        assertEquals(c1.getNorthing(), c2.getNorthing());
        assertEquals(c1.getEasting(),  c2.getEasting());
        assertEquals(c3.getEasting(),  c2.getEasting());
        assertEquals(c3.getNorthing(), c2.getNorthing());

        assertEquals(northing, c1.getNorthing());
        assertEquals(easting, c1.getEasting());

        assertEquals(northing, c2.getNorthing());
        assertEquals(easting, c2.getEasting());

        assertEquals(northing, c3.getNorthing());
        assertEquals(easting, c3.getEasting());

        assertEquals(c1.toLatLong().latitude, c2.toLatLong().latitude, 0.0001);
        assertEquals(c1.toLatLong().longitude, c2.toLatLong().longitude, 0.0001);
    }
}
