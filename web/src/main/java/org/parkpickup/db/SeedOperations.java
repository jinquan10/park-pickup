package org.parkpickup.db;

public interface SeedOperations {
    void addPark(Long id, String name, double[] lats, double[] lngs);
    void addBadRelation(Long id);
    void addBadWay(Long id);
    void flush();
}
