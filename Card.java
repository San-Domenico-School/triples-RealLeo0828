import greenfoot.*; // Import the Greenfoot package

public class Card extends Actor {
    // Enumerations for Shape and Color
    public enum Shape {
        CIRCLE, NO_SHAPE, SQUARE, TRIANGLE
    }

    public enum Color {
        BLUE, GREEN, NO_COLOR, RED
    }

    // Fields
    private Shape shape;
    private Color color;
    private boolean isSelected;
    private GreenfootImage cardImage, selectedCardImage;
    private int numberOfShapes, shading;

    // Constructor
    public Card(Shape shape, Color color, int numberOfShapes, int shading, GreenfootImage cardImage, GreenfootImage selectedCardImage) {
        this.shape = shape;
        this.color = color;
        this.numberOfShapes = numberOfShapes;
        this.shading = shading;
        this.cardImage = cardImage;
        this.selectedCardImage = selectedCardImage;
        this.isSelected = false;
        setImage(cardImage); // Set the initial image
    }

    // Getters
    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getNumberOfShapes() {
        return numberOfShapes;
    }

    public int getShading() {
        return shading;
    }

    public GreenfootImage getCardImage() {
        return cardImage;
    }

    public GreenfootImage getSelectedCardImage() {
        return selectedCardImage;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    // Setter for isSelected
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            setImage(selectedCardImage);
        } else {
            setImage(cardImage);
        }
    }
}