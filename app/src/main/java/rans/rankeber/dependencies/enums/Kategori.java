package rans.rankeber.dependencies.enums;

import java.util.HashMap;
import java.util.Map;

public enum Kategori {
    CHOOSE("Kategori"),
    RODA_DUA("Roda 2"),
    RODA_EMPAT("Roda 4");

    private String opsi;
    private static final Map<String, Kategori> map = new HashMap<>();
    static {
        for (Kategori en : values()) {
            map.put(en.toString(), en);
        }
    }

    public static Kategori valueFor(String name) {
        return map.get(name);
    }

    Kategori(String opsi) {
        this.opsi = opsi;
    }
        
    @Override
    public String toString() {
        return opsi;
    }
}