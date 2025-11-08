package org.ayed.gta;


public enum TipoVehiculo {
    AUTO(ConstantesVehiculo.RUEDAS_AUTO, ConstantesVehiculo.COSTO_AUTO),
    MOTO(ConstantesVehiculo.RUEDAS_MOTO, ConstantesVehiculo.COSTO_MOTO);

    private final int ruedas;
    private final int costoRueda;

    TipoVehiculo(int ruedas, int costoRueda) {
        this.ruedas = ruedas;
        this.costoRueda = costoRueda;
    }

    public int getRuedas() {
        return ruedas;
    }

    public int getCostoRueda() {
        return costoRueda;
    }
}