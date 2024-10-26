package com.work.webhook.transformer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Interface Transformer.
 *
 * @param <E> the element type
 * @param <D> the generic type
 */
public interface Transformer<E, D> {

    /**
     * Transform.
     *
     * @param dto the dto
     * @return the e
     */
    public E transform(D dto);

    /**
     * Transform.
     *
     * @param dtos the dtos
     * @return the list
     */
    public default List<E> transform(List<D> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::transform).collect(Collectors.toList());
    }
}
