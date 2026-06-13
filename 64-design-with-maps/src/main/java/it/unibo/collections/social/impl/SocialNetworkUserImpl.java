/**
 *
 */

package it.unibo.collections.social.impl;

import it.unibo.collections.social.api.SocialNetworkUser;
import it.unibo.collections.social.api.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This will be an implementation of
 * {@link SocialNetworkUser}:
 * 1) complete the definition of the methods by following the suggestions
 * included in the comments below.
 *
 * @param <U>
 *            Specific {@link User} type
 */
public final class SocialNetworkUserImpl<U extends User> extends UserImpl implements SocialNetworkUser<U> {

    /*
     *
     * [FIELDS]
     *
     * Define any necessary field
     *
     * In order to save the people followed by a user organized in groups, adopt
     * a generic-type Map:
     *
     * think of what type of keys and values would best suit the requirements
     */

    private final Map<String, Collection<U>> followedUsers;

    /*
     * [CONSTRUCTORS]
     *
     * 1) Complete the definition of the constructor below, for building a user
     * participating in a social network, with 4 parameters, initializing:
     *
     * - firstName
     * - lastName
     * - username
     * - age and every other necessary field
     */
    /**
     * Builds a user participating in a social network.
     *
     * @param name
     *            the user firstname
     * @param surname
     *            the user lastname
     * @param userAge
     *            user's age
     * @param user
     *            alias of the user, i.e. the way a user is identified on an
     *            application
     */

    public SocialNetworkUserImpl(final String name, final String surname, final String user, final int userAge) {
        super(name, surname, user, userAge);
        this.followedUsers = new HashMap<>();
    }

    

    /*
     * 2) Define a further constructor where the age defaults to -1
     */

    public SocialNetworkUserImpl(final String name, final String surname, final String user) {
        this(name, surname, user, -1);
    }

    /*
     * [METHODS]
     *
     * Implements the methods below
     */
    @Override
    public boolean addFollowedUser(final String circle, final U user) {
        if (this.followedUsers.containsKey(circle)) {
            // Recuperiamo il gruppo usando Collection<U> in modo coerente con la mappa, senza cast!
            final Collection<U> group = this.followedUsers.get(circle);
            
            if (!group.contains(user)) {
                // Caso A: Il gruppo esiste e l'utente NON è ancora seguito in questo gruppo
                group.add(user);
                return true;
            } else {
                // Caso B: Il gruppo esiste e l'utente è GIÀ seguito in questo gruppo
                return false;
            }
        } else {
            // Caso C: Il gruppo non esiste ancora (la mappa non contiene la chiave 'circle')
            // 1. Crea la collezione reale (es. = new HashSet<>() oppure new ArrayList<>())
            // 2. Aggiungi l'utente alla nuova collezione
            // 3. Inserisci la coppia (circle, nuovaCollezione) nella mappa con il metodo .put()
            // 4. Scrivi il return corretto
            final Collection<U> newGroup = new LinkedList<>();
            newGroup.add(user);
            this.followedUsers.put(circle, newGroup);
            return true;
            
        }
    }

    /**
     *
     * [NOTE] If no group with groupName exists yet, this implementation must
     * return an empty Collection.
     */
    @Override
    public Collection<U> getFollowedUsersInGroup(final String groupName) {
        if (this.followedUsers.containsKey(groupName)) {
            final Collection<U> originalGroup = this.followedUsers.get(groupName);
            return new LinkedList<>(originalGroup);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public List<U> getFollowedUsers() {
        final List<U> followedUser = new LinkedList<>();
        for (Collection<U> u : this.followedUsers.values()) {
            followedUser.addAll(u);
        }
        return followedUser;
    }
}
