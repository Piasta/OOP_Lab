package pl.wsb.OOP_Lab;

import java.util.List;
import java.util.Map;

public interface WarehouseInterface {

    void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
            throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException;

    Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId);

    double getTotalVolumeOccupiedByClient(String clientId);

    List<SupportedMetalType> getStoredMetalTypesByClient(String clientId);

}
