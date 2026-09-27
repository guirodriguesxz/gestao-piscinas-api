package com.empresa.gestao_piscinas.model;

/**
 * Classificação da água a partir das medições da visita.
 * Faixas usuais para piscinas: pH 7,2–7,8 e cloro livre 1–3 ppm.
 */
public enum CondicaoAgua {
    IDEAL,
    ATENCAO,
    CRITICA;

    public static CondicaoAgua avaliar(double ph, double cloro) {
        boolean phIdeal = ph >= 7.2 && ph <= 7.8;
        boolean cloroIdeal = cloro >= 1.0 && cloro <= 3.0;
        if (phIdeal && cloroIdeal) {
            return IDEAL;
        }

        boolean phCritico = ph < 6.8 || ph > 8.2;
        boolean cloroCritico = cloro < 0.5 || cloro > 5.0;
        return (phCritico || cloroCritico) ? CRITICA : ATENCAO;
    }
}
