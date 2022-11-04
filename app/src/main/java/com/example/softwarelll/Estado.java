package com.example.softwarelll;

import androidx.annotation.NonNull;

public enum Estado {
    Procesando, Entregado;

    @NonNull
    @Override
    public String toString() {
        if (this == Entregado) return "Entregado";
        else                  return "Procesando";
    }

    public static Estado fromString(String string) {
        if (string.toLowerCase().equals("entregado")) return Entregado;
        else                                        return Procesando;

    }
}