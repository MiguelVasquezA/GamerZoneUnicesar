package com.gamezone.model;

public class VideoGame extends Product{
    private String platform;
    private String genre;
    private int ageRating;

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

    @Override
    public String getDescription(){
        return "VideoGame: "+ getTitle() +
                " | Platform: "+ platform +
                " | Genre: "+genre+
                " | Price: $"+ getPrice();
    }
}
