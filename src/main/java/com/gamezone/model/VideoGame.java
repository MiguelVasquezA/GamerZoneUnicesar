package com.gamezone.model;

/**
 * Represents a video game product in the store.
 * @author Miguel Vasquez
 * @version 1.0
 */

public class VideoGame extends Product{
    private String platform;
    private String genre;
    private int ageRating;

    /**
     * Constructs a VideoGame.
     * @param id product id
     * @param title product title
     * @param price unit price
     * @param stock inventory quantity
     * @param platform gaming platform
     * @param genre game genre
     * @param ageRating age classification
     */

    public VideoGame(String id, double price, int stock, String title, int ageRating, String genre, String platform) {
        super(id, price, stock, title);
        this.ageRating = ageRating;
        this.genre = genre;
        this.platform = platform;
    }

    public int getAgeRating() {

        return ageRating;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    /**
     * Returns the complete description of the video game.
     * @return formatted description string
     */

    @Override
    public String getDescription(){
        return "VideoGame: "+ getTitle() +
                " | Platform: "+ platform +
                " | Genre: "+genre+
                " | Price: $"+ getPrice();
    }
}
