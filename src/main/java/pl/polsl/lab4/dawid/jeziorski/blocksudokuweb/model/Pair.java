package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

/**
 * Generic type class that holds 2 values.
 *
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.0
 */
public class Pair<T, U> {

    /**
     * First class field.
     */
    private T first;

    /**
     * Second class field.
     */
    private U second;

    /**
     * 2-parameter constructor.
     *
     * @param first used to assign value to first field.
     * @param second used to assign value to second field.
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Getter method for first field.
     *
     * @return First field.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Getter method for second field.
     *
     * @return Second field.
     */
    public U getSecond() {
        return second;
    }

    /**
     * Setter method for first field.
     *
     * @param first used to assign value to first field.
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Setter method for second field.
     *
     * @param second used to assign value to second field.
     */
    public void setSecond(U second) {
        this.second = second;
    }

}
