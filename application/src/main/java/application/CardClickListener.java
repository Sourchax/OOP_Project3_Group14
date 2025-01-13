package application;


import entities.Product;

/**
 * Interface to define a click listener for handling card interactions.
 * This interface provides a method to handle click events on a card 
 * that represents a {@link Product}.
 * 
 * <p>Implement this interface to define the behavior when a product card is clicked.</p>
 * 
 * @see Product
 */


public interface CardClickListener {
    /**
     * Called when a card representing a {@link Product} is clicked.
     * 
     * @param product the {@link Product} associated with the clicked card.
     */
    public void clickListener(Product product);
}