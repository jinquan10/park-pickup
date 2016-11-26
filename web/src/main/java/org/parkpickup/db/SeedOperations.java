package org.parkpickup.db;

public interface SeedOperations {
    void addPark(Long id, String name, Double lat, Double lng);
}
