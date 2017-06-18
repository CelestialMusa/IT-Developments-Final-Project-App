package gida.wiiplan;

/**
 * Created by Zacharia Manyoni on 2016/11/07.
 */

public class VenueClass {
    private int venue_resource;
    String venue_id;
    String building;
    String room;
    String venue_name;
    String capacity;

    public VenueClass(int venue_resource, String venue_id, String building, String room, String venue_name, String capacity) {
        setVenue_resource(venue_resource);
        setVenue_id(venue_id);
        setBuilding(building);
        setRoom(room);
        setVenue_name(venue_name);
        setCapacity(capacity);
    }

    @Override
    public String toString() {
        return getVenue_id();
    }

    public int getVenue_resource() {
        return venue_resource;
    }

    public void setVenue_resource(int venue_resource) {
        this.venue_resource = venue_resource;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
