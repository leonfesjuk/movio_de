package de.upteams.tasktracker.utils;

import java.util.Collection;


/**
 * Utility class for handling operations related to entities, such as generating string
 * representations for the IDs of entities or collections of entities. Provides utility
 * methods for use in toString implementations or logging/debugging purposes.
 */
public abstract class EntityUtil {

    private static final String EMPTY_COLLECTION = "[]";
    private static final String ENTITY_NULL = "null";

    private EntityUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates a string representation of the IDs of all entities in the given collection.
     * If the collection is null or empty, an empty collection representation ("[]") is returned.
     *
     * @param entities a collection of BaseEntity objects from which IDs are extracted
     * @return a string representation of the list of IDs of entities, or "[]" if the collection is null or empty
     */
    public static String getIdsForToString(Collection<? extends UuidEntityId> entities) {
        if (entities == null || entities.isEmpty()) {
            return EMPTY_COLLECTION;
        }
        return entities.stream()
                .map(UuidEntityId::getId)
                .map(Object::toString)
                .toList()
                .toString();
    }

    /**
     * Generates a string representation of the IDs of all Long-based entities in the given collection.
     *
     * @param entities collection of entities with Long ids
     * @return a string representation of the list of IDs of entities, or "[]" if the collection is null or empty
     */
    public static String getLongIdsForToString(Collection<? extends LongEntityId> entities) {
        if (entities == null || entities.isEmpty()) {
            return EMPTY_COLLECTION;
        }
        return entities.stream()
                .map(LongEntityId::getId)
                .map(Object::toString)
                .toList()
                .toString();
    }

    /**
     * Retrieves a string representation of the ID of the given UUID-based entity.
     *
     * @param entity the entity whose ID is to be retrieved; can be null
     * @param <E> the type of the entity that extends EntityId
     * @return the ID of the entity as a string if the entity is not null, or "null" if the entity is null
     */
    public static <E extends UuidEntityId> String getIdForToString(E entity) {
        return entity == null ? ENTITY_NULL : entity.getId().toString();
    }

    /**
     * Retrieves a string representation of the ID of the given Long-based entity.
     *
     * @param entity the entity whose ID is to be retrieved; can be null
     * @param <E> the type of the entity that extends LongEntityId
     * @return the ID of the entity as a string if the entity is not null, or "null" if the entity is null
     */
    public static <E extends LongEntityId> String getLongIdForToString(E entity) {
        return entity == null ? ENTITY_NULL : entity.getId().toString();
    }
}
