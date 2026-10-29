package com.megastrata.world;

public enum MegastructureType {
    ENDLESS_FRAME("endless_frame", -1200, 1800, StructureModel.INFINITE),
    VERTICAL_HORIZON_CITY("vertical_city", -500, 1500, StructureModel.CLOSED),
    DEAD_OCEAN("dead_ocean", -1400, -200, StructureModel.CLOSED),
    INDUSTRIAL_CONTINENT("industrial_continent", -1000, 600, StructureModel.INFINITE),
    BIOMECHANICAL_FOREST("biomech_forest", -800, 400, StructureModel.FRAGMENTED),
    HORIZONTAL_SKIES("horizontal_skies", 800, 2000, StructureModel.CLOSED),
    BURIED_ARCHIVE("buried_archive", -1600, -600, StructureModel.FRAGMENTED),
    ZERO_LAYER("zero_layer", -2000, -1400, StructureModel.INFINITE);

    public enum StructureModel { INFINITE, CLOSED, FRAGMENTED }

    public final String id;
    public final int minY;
    public final int maxY;
    public final StructureModel model;

    MegastructureType(String id, int minY, int maxY, StructureModel model) {
        this.id = id;
        this.minY = minY;
        this.maxY = maxY;
        this.model = model;
    }
}
