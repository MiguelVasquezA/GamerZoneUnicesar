package com.gamezone.model;

public class VideoGame extends Product {
    private String platform;
    private String genre;

    public VideoGame(String id, String title, double price, int stock, String platform, String genre) {
        super(id, title, price, stock);
        this.platform = platform;
        this.genre = genre;
    }

    @Override
    public String toCsvRecord() {
        return String.join(",", "VIDEOGAME", getId(), getTitle(), String.valueOf(getPrice()), String.valueOf(getStock()), platform, genre);
    }

    public String getPlatform() { return platform; }
    public String getGenre() { return genre; }
}