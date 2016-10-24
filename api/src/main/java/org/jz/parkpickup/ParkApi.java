package main.java.org.jz.parkpickup;

import java.util.Set;

public final class ParkApi {
    public interface Foo {
        /**
         * So other users know where to find pickup games
         * @param playerId Phone, or some id
         * @param lat
         * @param lng
         */
        void updateLocation(long playerId, double lat, double lng);

        /**
         * @param lat
         * @param lng
         * @param radiusMeters 0 for default
         * @return
         */
        Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters);

        void playToday(long playerId, long parkId, boolean isPlayingToday);

        void requestPickupGame(long playerId, long parkId);
    }

    public static class PickupPlace {
        public long id;
        public String displayName;
        public Set<Player> playingNow;
        public Set<Player> playingToday;
    }

    public static class Player {
        public long id;
        public String displayName;
    }
}
