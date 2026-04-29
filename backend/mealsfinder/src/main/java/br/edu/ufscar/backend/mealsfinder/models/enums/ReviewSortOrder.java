package br.edu.ufscar.backend.mealsfinder.models.enums;

/**
 * Ordenação de avaliações (telas &quot;Ordenar por&quot;).
 */
public enum ReviewSortOrder {
    /** Mais relevantes: nota geral e recência */
    RELEVANCE,
    /** Preço (menor → maior) */
    PRICE_ASC,
    /** Preço (maior → menor) */
    PRICE_DESC,
    /** Nota geral (maior → menor) */
    RATING_DESC,
    /** Nota geral (menor → maior) */
    RATING_ASC
}
